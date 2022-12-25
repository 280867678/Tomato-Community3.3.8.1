package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCStreamDownloader;
import com.tencent.liteav.network.TXIStreamDownloader;
import java.util.Vector;

/* loaded from: classes3.dex */
public class TXCRTMPDownloader extends TXIStreamDownloader {
    private boolean mHasTcpPlayUrl;
    private Object mRTMPThreadLock;
    private Vector<TXCStreamPlayUrl> mVecPlayUrls;
    private final String TAG = "network.TXCRTMPDownloader";
    private final int MSG_RECONNECT = 101;
    private final int MSG_EVENT = 102;
    private String mPlayUrl = "";
    private boolean mQuicChannel = false;
    private String mServerIp = "";
    private C3560a mCurrentThread = null;
    private HandlerThread mThread = null;
    private Handler mHandler = null;
    private boolean mIsPlayRtmpAccStream = false;
    private boolean mEnableNearestIP = false;
    private int mConnectCountQuic = 0;
    private int mConnectCountTcp = 0;
    private int mLastNetworkType = 255;

    /* JADX INFO: Access modifiers changed from: private */
    public native TXCStreamDownloader.DownloadStats nativeGetStats(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpHandler(String str, String str2, boolean z, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStart(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStop(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpHandler(long j);

    public TXCRTMPDownloader(Context context) {
        super(context);
        this.mRTMPThreadLock = null;
        this.mRTMPThreadLock = new Object();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.liteav.network.TXCRTMPDownloader$a */
    /* loaded from: classes3.dex */
    public class C3560a extends Thread {

        /* renamed from: b */
        private long f4759b = 0;

        /* renamed from: c */
        private String f4760c;

        /* renamed from: d */
        private boolean f4761d;

        C3560a(String str, boolean z) {
            super("RTMPDownLoad");
            this.f4760c = str;
            this.f4761d = z;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (this) {
                this.f4759b = TXCRTMPDownloader.this.nativeInitRtmpHandler(TXCRTMPDownloader.this.mOriginUrl, this.f4760c, this.f4761d, TXCRTMPDownloader.this.mEnableMessage);
            }
            TXCRTMPDownloader.this.nativeStart(this.f4759b);
            synchronized (this) {
                TXCRTMPDownloader.this.nativeUninitRtmpHandler(this.f4759b);
                this.f4759b = 0L;
            }
        }

        /* renamed from: a */
        public void m1185a() {
            synchronized (this) {
                if (this.f4759b != 0) {
                    TXCRTMPDownloader.this.nativeStop(this.f4759b);
                }
            }
        }

        /* renamed from: b */
        public TXCStreamDownloader.DownloadStats m1184b() {
            TXCStreamDownloader.DownloadStats nativeGetStats;
            synchronized (this) {
                nativeGetStats = this.f4759b != 0 ? TXCRTMPDownloader.this.nativeGetStats(this.f4759b) : null;
            }
            return nativeGetStats;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInternal() {
        if (this.mQuicChannel) {
            this.mConnectCountQuic++;
        } else {
            this.mConnectCountTcp++;
        }
        synchronized (this.mRTMPThreadLock) {
            this.mCurrentThread = new C3560a(this.mPlayUrl, this.mQuicChannel);
            this.mCurrentThread.start();
        }
    }

    private void postReconnectMsg() {
        Message message = new Message();
        message.what = 101;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, this.connectRetryInterval * 1000);
        }
    }

    private void reconnect(final boolean z) {
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.m1185a();
                this.mCurrentThread = null;
            }
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.network.TXCRTMPDownloader.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCRTMPDownloader.this.internalReconnect(z);
                }
            }, this.connectRetryInterval * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalReconnect(boolean z) {
        Vector<TXCStreamPlayUrl> vector;
        if (!this.mIsRunning) {
            return;
        }
        if (this.mIsPlayRtmpAccStream && this.mLastNetworkType != TXCSystemUtil.m2876c(this.mApplicationContext)) {
            this.mLastNetworkType = TXCSystemUtil.m2876c(this.mApplicationContext);
            TXIStreamDownloader.AbstractC3570a abstractC3570a = this.mRestartListener;
            if (abstractC3570a == null) {
                return;
            }
            abstractC3570a.onRestartDownloader();
            return;
        }
        boolean z2 = this.mQuicChannel;
        if (this.mIsPlayRtmpAccStream) {
            if (!this.mEnableNearestIP) {
                z = false;
            }
            if (z2) {
                z = true;
            }
            if (z && (vector = this.mVecPlayUrls) != null && !vector.isEmpty()) {
                TXCStreamPlayUrl tXCStreamPlayUrl = this.mVecPlayUrls.get(0);
                this.mVecPlayUrls.remove(0);
                this.mPlayUrl = tXCStreamPlayUrl.f4851a;
                this.mQuicChannel = tXCStreamPlayUrl.f4852b;
            }
        }
        if (z2 && this.mHasTcpPlayUrl) {
            sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_NET_RECONNECT);
            startInternal();
            return;
        }
        int i = this.connectRetryTimes;
        if (i < this.connectRetryLimit) {
            this.connectRetryTimes = i + 1;
            TXCLog.m2915d("network.TXCRTMPDownloader", "reconnect retry count:" + this.connectRetryTimes + " limit:" + this.connectRetryLimit);
            sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_NET_RECONNECT);
            startInternal();
            return;
        }
        TXCLog.m2914e("network.TXCRTMPDownloader", "reconnect all times retried, send failed event ");
        sendNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_DISCONNECT);
    }

    public void sendNotifyEvent(int i, String str) {
        if (str.isEmpty()) {
            sendNotifyEvent(i);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("EVT_MSG", str);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        TXINotifyListener tXINotifyListener = this.mNotifyListener;
        if (tXINotifyListener == null) {
            return;
        }
        tXINotifyListener.onNotifyEvent(i, bundle);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void sendNotifyEvent(int i) {
        boolean z = true;
        if (i == 0 || i == 1) {
            if (i != 1) {
                z = false;
            }
            reconnect(z);
            return;
        }
        super.sendNotifyEvent(i);
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void startDownload(Vector<TXCStreamPlayUrl> vector, boolean z, boolean z2, boolean z3) {
        if (!this.mIsRunning && vector != null && !vector.isEmpty()) {
            this.mEnableMessage = z3;
            this.mIsPlayRtmpAccStream = z;
            this.mEnableNearestIP = z2;
            this.mVecPlayUrls = vector;
            this.mHasTcpPlayUrl = false;
            int i = 0;
            while (true) {
                if (i >= this.mVecPlayUrls.size()) {
                    break;
                } else if (!this.mVecPlayUrls.elementAt(i).f4852b) {
                    this.mHasTcpPlayUrl = true;
                    break;
                } else {
                    i++;
                }
            }
            TXCStreamPlayUrl tXCStreamPlayUrl = this.mVecPlayUrls.get(0);
            this.mVecPlayUrls.remove(0);
            this.mPlayUrl = tXCStreamPlayUrl.f4851a;
            this.mQuicChannel = tXCStreamPlayUrl.f4852b;
            this.mIsRunning = true;
            StringBuilder sb = new StringBuilder();
            sb.append("start pull with url:");
            sb.append(this.mPlayUrl);
            sb.append(" quic:");
            sb.append(this.mQuicChannel ? "yes" : "no");
            TXCLog.m2915d("network.TXCRTMPDownloader", sb.toString());
            this.mConnectCountQuic = 0;
            this.mConnectCountTcp = 0;
            this.connectRetryTimes = 0;
            if (this.mThread == null) {
                this.mThread = new HandlerThread("RTMP_PULL");
                this.mThread.start();
            }
            this.mHandler = new Handler(this.mThread.getLooper()) { // from class: com.tencent.liteav.network.TXCRTMPDownloader.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.what == 101) {
                        TXCRTMPDownloader.this.startInternal();
                    }
                }
            };
            startInternal();
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public void stopDownload() {
        if (!this.mIsRunning) {
            return;
        }
        this.mIsRunning = false;
        this.mVecPlayUrls.removeAllElements();
        this.mVecPlayUrls = null;
        this.mIsPlayRtmpAccStream = false;
        this.mEnableNearestIP = false;
        TXCLog.m2915d("network.TXCRTMPDownloader", "stop pull");
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                this.mCurrentThread.m1185a();
                this.mCurrentThread = null;
            }
        }
        HandlerThread handlerThread = this.mThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mThread = null;
        }
        if (this.mHandler == null) {
            return;
        }
        this.mHandler = null;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        synchronized (this.mRTMPThreadLock) {
            if (this.mCurrentThread != null) {
                return this.mCurrentThread.m1184b();
            }
            return null;
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public String getCurrentStreamUrl() {
        return this.mPlayUrl;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public boolean isQuicChannel() {
        return this.mQuicChannel;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public int getConnectCountQuic() {
        return this.mConnectCountQuic;
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader
    public int getConnectCountTcp() {
        return this.mConnectCountTcp;
    }
}
