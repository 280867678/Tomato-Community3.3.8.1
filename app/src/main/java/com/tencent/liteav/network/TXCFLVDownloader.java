package com.tencent.liteav.network;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import javax.net.ssl.SSLException;

/* loaded from: classes3.dex */
public class TXCFLVDownloader extends TXIStreamDownloader {
    private TXCStreamDownloader.DownloadStats mStats;
    public final String TAG = "network.TXCFLVDownloader";
    private final int FLV_HEAD_SIZE = 9;
    private final int MAX_FRAME_SIZE = 1048576;
    private final int MSG_CONNECT = 100;
    private final int MSG_RECV_DATA = 101;
    private final int MSG_DISCONNECT = 102;
    private final int MSG_RECONNECT = 103;
    private final int MSG_SEEK = 104;
    private final int MSG_RESUME = 105;
    private final int MSG_QUIT = 106;
    private final int CONNECT_TIMEOUT = 8000;
    private final int READ_STREAM_SIZE = 1388;
    private HandlerThread mFlvThread = null;
    private Handler mFlvHandler = null;
    private InputStream mInputStream = null;
    private HttpURLConnection mConnection = null;
    private byte[] mPacketBytes = null;
    private boolean mRecvData = false;
    private long mContentLength = 0;
    private long mDownloadedSize = 0;
    private long mFLVParser = 0;
    private String mPlayUrl = "";
    private boolean mbFirstVideo = false;
    private boolean mbFirstAudio = false;

    private native void nativeCleanData(long j);

    private native long nativeInitFlvHander(String str, int i, boolean z);

    private native int nativeParseData(long j, byte[] bArr, int i);

    private native void nativeUninitFlvhander(long j);

    public TXCFLVDownloader(Context context) {
        super(context);
        this.mStats = null;
        this.mStats = new TXCStreamDownloader.DownloadStats();
        TXCStreamDownloader.DownloadStats downloadStats = this.mStats;
        downloadStats.afterParseAudioBytes = 0L;
        downloadStats.dnsTS = 0L;
        downloadStats.startTS = TXCTimeUtil.getTimeTick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgConnect() {
        try {
            connect();
            if (this.mFLVParser == 0) {
                this.mFLVParser = nativeInitFlvHander(this.mPlayUrl, 0, this.mEnableMessage);
            }
            Handler handler = this.mFlvHandler;
            if (handler == null) {
                return;
            }
            handler.sendEmptyMessage(101);
        } catch (FileNotFoundException e) {
            TXCLog.m2914e("network.TXCFLVDownloader", "file not found, reconnect");
            e.printStackTrace();
            postReconnectMsg();
        } catch (Error e2) {
            TXCLog.m2914e("network.TXCFLVDownloader", "error, reconnect");
            e2.printStackTrace();
            postReconnectMsg();
        } catch (SocketTimeoutException unused) {
            TXCLog.m2914e("network.TXCFLVDownloader", "socket timeout, reconnect");
            postReconnectMsg();
        } catch (Exception e3) {
            TXCLog.m2914e("network.TXCFLVDownloader", "exception, reconnect");
            e3.printStackTrace();
            postReconnectMsg();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgRecvData() {
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            try {
                int i = 0;
                int read = inputStream.read(this.mPacketBytes, 0, 1388);
                if (read > 0) {
                    long j = read;
                    this.mDownloadedSize += j;
                    if (!this.mRecvData) {
                        TXCLog.m2911w("network.TXCFLVDownloader", "flv play receive first data");
                        this.mRecvData = true;
                    }
                    if (this.mFLVParser != 0) {
                        this.mStats.beforeParseVideoBytes += j;
                        i = nativeParseData(this.mFLVParser, this.mPacketBytes, read);
                    }
                    if (i > 1048576) {
                        TXCLog.m2914e("network.TXCFLVDownloader", "flv play parse frame: " + i + " > 1048576,sart reconnect");
                        postReconnectMsg();
                        return;
                    }
                } else if (read < 0) {
                    TXCLog.m2911w("network.TXCFLVDownloader", "http read: " + read + " < 0, start reconnect");
                    postReconnectMsg();
                    return;
                }
                if (this.mFlvHandler == null) {
                    return;
                }
                this.mFlvHandler.sendEmptyMessage(101);
            } catch (EOFException unused) {
                TXCLog.m2911w("network.TXCFLVDownloader", "eof exception start reconnect");
                postReconnectMsg();
            } catch (Error e) {
                TXCLog.m2914e("network.TXCFLVDownloader", "error");
                e.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            } catch (SocketException unused2) {
                TXCLog.m2911w("network.TXCFLVDownloader", "socket exception start reconnect");
                postReconnectMsg();
            } catch (SocketTimeoutException unused3) {
                TXCLog.m2911w("network.TXCFLVDownloader", "socket timeout start reconnect");
                postReconnectMsg();
            } catch (SSLException unused4) {
                TXCLog.m2911w("network.TXCFLVDownloader", "ssl exception start reconnect");
                postReconnectMsg();
            } catch (Exception e2) {
                TXCLog.m2914e("network.TXCFLVDownloader", "exception");
                e2.printStackTrace();
                this.mInputStream = null;
                this.mConnection = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgDisConnect() {
        try {
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long j = this.mFLVParser;
        if (j != 0) {
            nativeUninitFlvhander(j);
            this.mFLVParser = 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processMsgReconnect() {
        reconnect();
    }

    private void startInternal() {
        if (this.mFlvThread == null) {
            this.mFlvThread = new HandlerThread("FlvThread");
            this.mFlvThread.start();
        }
        if (this.mFlvHandler == null) {
            this.mFlvHandler = new Handler(this.mFlvThread.getLooper()) { // from class: com.tencent.liteav.network.TXCFLVDownloader.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i != 106) {
                        switch (i) {
                            case 100:
                                TXCFLVDownloader.this.processMsgConnect();
                                return;
                            case 101:
                                TXCFLVDownloader.this.processMsgRecvData();
                                return;
                            case 102:
                                TXCFLVDownloader.this.processMsgDisConnect();
                                return;
                            case 103:
                                TXCFLVDownloader.this.processMsgReconnect();
                                return;
                            default:
                                return;
                        }
                    }
                    try {
                        Looper.myLooper().quit();
                    } catch (Exception unused) {
                    }
                }
            };
        }
        postConnectMsg();
    }

    private void reconnect() {
        processMsgDisConnect();
        int i = this.connectRetryTimes;
        if (i < this.connectRetryLimit) {
            this.connectRetryTimes = i + 1;
            TXCLog.m2915d("network.TXCFLVDownloader", "reconnect retry time:" + this.connectRetryTimes + ", limit:" + this.connectRetryLimit);
            processMsgConnect();
            sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_NET_RECONNECT);
            return;
        }
        TXCLog.m2914e("network.TXCFLVDownloader", "reconnect all times retried, send failed event ");
        sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_DISCONNECT);
    }

    private void postReconnectMsg() {
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(103, this.connectRetryInterval * 1000);
        }
    }

    private void postDisconnectMsg() {
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendEmptyMessage(102);
        }
    }

    private void postConnectMsg() {
        this.mInputStream = null;
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        Message message = new Message();
        message.what = 100;
        message.arg1 = 0;
        Handler handler = this.mFlvHandler;
        if (handler != null) {
            handler.sendMessage(message);
        }
    }

    private void connect() throws Exception {
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        this.mConnection = (HttpURLConnection) new URL(this.mPlayUrl).openConnection();
        this.mStats.dnsTS = TXCTimeUtil.getTimeTick();
        this.mConnection.setConnectTimeout(8000);
        this.mConnection.setReadTimeout(8000);
        this.mConnection.setRequestProperty("Accept-Encoding", "identity");
        this.mConnection.setInstanceFollowRedirects(true);
        Map<String, String> map = this.mHeaders;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        this.mConnection.connect();
        this.mInputStream = this.mConnection.getInputStream();
        this.mPacketBytes = new byte[1388];
        this.mRecvData = false;
        this.mContentLength = this.mConnection.getContentLength();
        this.mDownloadedSize = 0L;
        this.mStats.serverIP = InetAddress.getByName(this.mConnection.getURL().getHost()).getHostAddress();
        this.mStats.connTS = TXCTimeUtil.getTimeTick();
        sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_INFO_CONNECT_SUCCESS);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public String getRealStreamUrl() {
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            return httpURLConnection.getURL().toString();
        }
        return null;
    }

    private void disconnect() throws Exception {
        HttpURLConnection httpURLConnection = this.mConnection;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
            this.mConnection = null;
        }
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            inputStream.close();
            this.mInputStream = null;
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        TXCStreamDownloader.DownloadStats downloadStats = new TXCStreamDownloader.DownloadStats();
        TXCStreamDownloader.DownloadStats downloadStats2 = this.mStats;
        downloadStats.afterParseAudioBytes = downloadStats2.afterParseAudioBytes;
        downloadStats.afterParseVideoBytes = downloadStats2.afterParseVideoBytes;
        downloadStats.beforeParseVideoBytes = downloadStats2.beforeParseVideoBytes;
        downloadStats.beforeParseAudioBytes = downloadStats2.beforeParseAudioBytes;
        downloadStats.startTS = downloadStats2.startTS;
        downloadStats.dnsTS = downloadStats2.dnsTS;
        downloadStats.connTS = downloadStats2.connTS;
        downloadStats.firstAudioTS = downloadStats2.firstAudioTS;
        downloadStats.firstVideoTS = downloadStats2.firstVideoTS;
        downloadStats.serverIP = downloadStats2.serverIP;
        return downloadStats;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void startDownload(Vector<TXCStreamPlayUrl> vector, boolean z, boolean z2, boolean z3) {
        if (!this.mIsRunning && vector != null && !vector.isEmpty()) {
            this.mEnableMessage = z3;
            this.mIsRunning = true;
            this.mPlayUrl = vector.get(0).f4851a;
            TXCLog.m2915d("network.TXCFLVDownloader", "start pull with url " + this.mPlayUrl);
            startInternal();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void stopDownload() {
        if (!this.mIsRunning) {
            return;
        }
        this.mIsRunning = false;
        TXCLog.m2915d("network.TXCFLVDownloader", "stop pull");
        try {
            if (this.mFlvHandler == null) {
                return;
            }
            this.mFlvHandler.removeCallbacksAndMessages(null);
            this.mFlvHandler.sendEmptyMessage(102);
            this.mFlvHandler.sendEmptyMessage(106);
            this.mFlvHandler = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void onRecvVideoData(byte[] bArr, int i, long j, long j2, int i2) {
        if (!this.mbFirstVideo) {
            this.mbFirstVideo = true;
            this.mStats.firstVideoTS = TXCTimeUtil.getTimeTick();
            TXCLog.m2915d("network.TXCFLVDownloader", "receive first video with ts " + this.mStats.firstVideoTS);
        }
        this.mStats.afterParseVideoBytes += bArr.length;
        super.onRecvVideoData(bArr, i, j, j2, i2);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void onRecvAudioData(byte[] bArr, int i, int i2, int i3) {
        if (!this.mbFirstAudio) {
            this.mbFirstAudio = true;
            this.mStats.firstAudioTS = TXCTimeUtil.getTimeTick();
            TXCLog.m2915d("network.TXCFLVDownloader", "receive first audio with ts " + this.mStats.firstAudioTS);
        }
        this.mStats.afterParseAudioBytes += bArr.length;
        super.onRecvAudioData(bArr, i, i2, i3);
    }
}
