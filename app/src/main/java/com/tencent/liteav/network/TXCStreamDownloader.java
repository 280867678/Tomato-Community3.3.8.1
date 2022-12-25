package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.network.TXCMultiStreamDownloader;
import com.tencent.liteav.network.TXIStreamDownloader;
import com.tencent.liteav.network.TXRTMPAccUrlFetcher;
import com.tencent.rtmp.TXLiveConstants;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes3.dex */
public class TXCStreamDownloader extends TXCModule implements TXINotifyListener, TXIStreamDownloader.AbstractC3570a, TXCMultiStreamDownloader.AbstractC3576a, TXIStreamDownloaderListener {
    public static final String TAG = "TXCStreamDownloader";
    public static final int TXE_DOWNLOAD_ERROR_ALLADDRESS_FAILED = 12031;
    public static final int TXE_DOWNLOAD_ERROR_CONNECT_FAILED = 12011;
    public static final int TXE_DOWNLOAD_ERROR_DISCONNECT = 12012;
    public static final int TXE_DOWNLOAD_ERROR_GET_RTMP_ACC_URL_FAIL = 12030;
    public static final int TXE_DOWNLOAD_ERROR_NET_RECONNECT = 12015;
    public static final int TXE_DOWNLOAD_ERROR_READ_FAILED = 12013;
    public static final int TXE_DOWNLOAD_ERROR_WRITE_FAILED = 12014;
    public static final int TXE_DOWNLOAD_INFO_CONNECT_END = 12007;
    public static final int TXE_DOWNLOAD_INFO_CONNECT_FAILED = 12004;
    public static final int TXE_DOWNLOAD_INFO_CONNECT_SUCCESS = 12001;
    public static final int TXE_DOWNLOAD_INFO_HANDSHAKE_FAIL = 12005;
    public static final int TXE_DOWNLOAD_INFO_PLAY_BEGIN = 12008;
    public static final int TXE_DOWNLOAD_INFO_SERVER_REFUSE = 12009;
    private TXRTMPAccUrlFetcher mAccUrlFetcher;
    private Context mApplicationContext;
    private int mDownloadFormat;
    private TXIStreamDownloader mDownloader;
    private Handler mHandler;
    protected Map<String, String> mHeaders;
    private TXIStreamDownloaderListener mListener = null;
    private byte[] mListenerLock = new byte[0];
    private TXINotifyListener mNotifyListener = null;
    private boolean mDownloaderRunning = false;
    private String mOriginPlayUrl = "";
    private boolean mEnableNearestIP = false;
    private int mChannelType = 0;
    private boolean mEnableMessage = false;
    private long mLastTimeStamp = 0;
    private DownloadStats mLastDownloadStats = null;
    private boolean mRecvFirstNal = false;
    private long mSwitchStartTime = 0;
    private long mCurrentNalTs = 0;
    private long mLastIFramelTs = 0;
    private TXCMultiStreamDownloader mStreamSwitcher = null;
    private Runnable mReportNetStatusRunnalbe = new Runnable() { // from class: com.tencent.liteav.network.TXCStreamDownloader.3
        @Override // java.lang.Runnable
        public void run() {
            TXCStreamDownloader.this.reportNetStatus();
        }
    };

    /* loaded from: classes3.dex */
    public static class DownloadStats {
        public long afterParseAudioBytes;
        public long afterParseVideoBytes;
        public long beforeParseAudioBytes;
        public long beforeParseVideoBytes;
        public long connTS;
        public long dnsTS;
        public long firstAudioTS;
        public long firstVideoTS;
        public String serverIP;
        public long startTS;
    }

    /* renamed from: com.tencent.liteav.network.TXCStreamDownloader$a */
    /* loaded from: classes3.dex */
    public static class C3564a {

        /* renamed from: a */
        public String f4767a;

        /* renamed from: b */
        public String f4768b;

        /* renamed from: c */
        public String f4769c;

        /* renamed from: d */
        public int f4770d;

        /* renamed from: e */
        public String f4771e;

        /* renamed from: f */
        public boolean f4772f;
    }

    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
    public void onNotifyEvent(int i, Bundle bundle) {
        synchronized (this.mListenerLock) {
            if (this.mNotifyListener != null) {
                Bundle bundle2 = new Bundle();
                int i2 = 3005;
                if (i != 12001) {
                    if (i == 12004) {
                        bundle2.putString("EVT_MSG", "连接服务器失败");
                    } else if (i == 12005) {
                        i2 = 3003;
                        bundle2.putString("EVT_MSG", "RTMP服务器握手失败");
                    } else if (i != 12030) {
                        if (i == 12031) {
                            bundle2.putString("EVT_MSG", "所有拉流地址尝试失败,可以放弃治疗");
                        } else {
                            switch (i) {
                                case TXE_DOWNLOAD_INFO_CONNECT_END /* 12007 */:
                                    bundle2.putString("EVT_MSG", "连接结束");
                                    i2 = i;
                                    break;
                                case TXE_DOWNLOAD_INFO_PLAY_BEGIN /* 12008 */:
                                    i2 = 2002;
                                    bundle2.putString("EVT_MSG", "开始拉流");
                                    break;
                                case TXE_DOWNLOAD_INFO_SERVER_REFUSE /* 12009 */:
                                    bundle2.putString("EVT_MSG", "服务器拒绝连接请求");
                                    i2 = TXLiveConstants.PLAY_WARNING_RECONNECT;
                                    break;
                                default:
                                    switch (i) {
                                        case TXE_DOWNLOAD_ERROR_CONNECT_FAILED /* 12011 */:
                                            bundle2.putString("EVT_MSG", "连接服务器失败");
                                            break;
                                        case TXE_DOWNLOAD_ERROR_DISCONNECT /* 12012 */:
                                            bundle2.putString("EVT_MSG", "经多次自动重连失败，放弃连接");
                                            break;
                                        case TXE_DOWNLOAD_ERROR_READ_FAILED /* 12013 */:
                                            bundle2.putString("EVT_MSG", "读数据错误，网络连接断开");
                                            break;
                                        case TXE_DOWNLOAD_ERROR_WRITE_FAILED /* 12014 */:
                                            bundle2.putString("EVT_MSG", "写数据错误，网络连接断开");
                                            break;
                                        case TXE_DOWNLOAD_ERROR_NET_RECONNECT /* 12015 */:
                                            bundle2.putString("EVT_MSG", "启动网络重连");
                                            i2 = TXLiveConstants.PLAY_WARNING_RECONNECT;
                                            break;
                                        default:
                                            bundle2.putString("EVT_MSG", "UNKNOWN event = " + i);
                                            i2 = i;
                                            break;
                                    }
                            }
                        }
                        i2 = TXLiveConstants.PLAY_ERR_NET_DISCONNECT;
                    } else {
                        i2 = TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL;
                        bundle2.putString("EVT_MSG", "获取加速拉流地址失败");
                    }
                    i2 = 3002;
                } else {
                    i2 = 2001;
                    bundle2.putString("EVT_MSG", "已连接服务器");
                }
                String str = "";
                if (bundle != null) {
                    str = bundle.getString("EVT_MSG", "");
                }
                if (str != null && !str.isEmpty()) {
                    bundle2.putString("EVT_MSG", str);
                }
                bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                this.mNotifyListener.onNotifyEvent(i2, bundle2);
            }
        }
        if (i == 12001) {
            reportNetStatusInternal();
        }
    }

    private void tryResetRetryCount() {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryTimes = 0;
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullAudio(TXSAudioPacket tXSAudioPacket) {
        tryResetRetryCount();
        synchronized (this.mListenerLock) {
            if (this.mListener != null) {
                this.mListener.onPullAudio(tXSAudioPacket);
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloaderListener
    public void onPullNAL(TXSNALPacket tXSNALPacket) {
        tryResetRetryCount();
        if (!this.mRecvFirstNal) {
            reportNetStatusInternal();
            this.mRecvFirstNal = true;
        }
        synchronized (this.mListenerLock) {
            this.mCurrentNalTs = tXSNALPacket.pts;
            if (tXSNALPacket.nalType == 0) {
                this.mLastIFramelTs = tXSNALPacket.pts;
            }
            if (this.mListener != null) {
                this.mListener.onPullNAL(tXSNALPacket);
            }
        }
    }

    @Override // com.tencent.liteav.network.TXIStreamDownloader.AbstractC3570a
    public void onRestartDownloader() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.network.TXCStreamDownloader.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCStreamDownloader.this.stop();
                    TXCStreamDownloader tXCStreamDownloader = TXCStreamDownloader.this;
                    tXCStreamDownloader.start(tXCStreamDownloader.mOriginPlayUrl, TXCStreamDownloader.this.mEnableNearestIP, TXCStreamDownloader.this.mChannelType, TXCStreamDownloader.this.mEnableMessage);
                }
            });
        }
    }

    public void setListener(TXIStreamDownloaderListener tXIStreamDownloaderListener) {
        synchronized (this.mListenerLock) {
            this.mListener = tXIStreamDownloaderListener;
        }
    }

    public void setNotifyListener(TXINotifyListener tXINotifyListener) {
        synchronized (this.mListenerLock) {
            this.mNotifyListener = tXINotifyListener;
        }
    }

    @Override // com.tencent.liteav.network.TXCMultiStreamDownloader.AbstractC3576a
    public void onSwitchFinish(TXIStreamDownloader tXIStreamDownloader, boolean z) {
        synchronized (this.mListenerLock) {
            int currentTimeMillis = (int) (System.currentTimeMillis() - this.mSwitchStartTime);
            this.mSwitchStartTime = 0L;
            Bundle bundle = new Bundle();
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (z) {
                this.mDownloader = tXIStreamDownloader;
                this.mDownloader.setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                bundle.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC);
                bundle.putCharSequence("EVT_MSG", "切换分辨率成功");
                if (this.mNotifyListener != null) {
                    this.mNotifyListener.onNotifyEvent(TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, TXCDRDef.f2502bt, currentTimeMillis, "");
            } else {
                bundle.putInt(TXCAVRoomConstants.EVT_ID, TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC);
                bundle.putCharSequence("EVT_MSG", "切换分辨率失败");
                if (this.mNotifyListener != null) {
                    this.mNotifyListener.onNotifyEvent(TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC, bundle);
                }
                TXCDRApi.txReportDAU(this.mApplicationContext, TXCDRDef.f2503bu);
            }
            this.mStreamSwitcher = null;
        }
    }

    static {
        TXCSystemUtil.m2873e();
    }

    public TXCStreamDownloader(Context context, int i) {
        this.mDownloader = null;
        this.mDownloadFormat = 1;
        this.mHandler = null;
        if (i == 0) {
            this.mDownloader = new TXCFLVDownloader(context);
        } else if (i == 1 || i == 4) {
            this.mDownloader = new TXCRTMPDownloader(context);
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setListener(this);
            this.mDownloader.setNotifyListener(this);
            this.mDownloader.setRestartListener(this);
        }
        this.mDownloadFormat = i;
        this.mAccUrlFetcher = new TXRTMPAccUrlFetcher(context);
        this.mApplicationContext = context;
        Context context2 = this.mApplicationContext;
        if (context2 != null) {
            this.mHandler = new Handler(context2.getMainLooper());
        }
    }

    public void setRetryTimes(int i) {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryLimit = i;
        }
    }

    public void setRetryInterval(int i) {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.connectRetryInterval = i;
        }
    }

    public int start(final String str, boolean z, int i, final boolean z2) {
        boolean z3 = true;
        this.mDownloaderRunning = true;
        this.mRecvFirstNal = false;
        this.mOriginPlayUrl = str;
        this.mEnableNearestIP = z;
        this.mChannelType = i;
        this.mEnableMessage = z2;
        setStatusValue(7116, 0L);
        setStatusValue(7117, 0L);
        setStatusValue(7118, 0L);
        if (str.startsWith("room")) {
            setStatusValue(7116, 1L);
            setStatusValue(7112, 2L);
            if (this.mDownloader != null) {
                Vector<TXCStreamPlayUrl> vector = new Vector<>();
                vector.add(new TXCStreamPlayUrl(str, true));
                this.mDownloader.setOriginUrl(str);
                this.mDownloader.startDownload(vector, false, false, z2);
            }
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
            }
            return 0;
        } else if (z && this.mDownloadFormat == 4) {
            int m1107a = this.mAccUrlFetcher.m1107a(str, i, new TXRTMPAccUrlFetcher.AbstractC3587a() { // from class: com.tencent.liteav.network.TXCStreamDownloader.2
                @Override // com.tencent.liteav.network.TXRTMPAccUrlFetcher.AbstractC3587a
                /* renamed from: a */
                public void mo1096a(int i2, String str2, Vector<TXCStreamPlayUrl> vector2) {
                    String str3;
                    if (i2 == 0 && vector2 != null && !vector2.isEmpty()) {
                        if (TXCStreamDownloader.this.mDownloaderRunning) {
                            if (TXCStreamDownloader.this.mDownloader != null) {
                                int i3 = 0;
                                Iterator<TXCStreamPlayUrl> it2 = vector2.iterator();
                                while (it2.hasNext()) {
                                    TXCStreamPlayUrl next = it2.next();
                                    if (next != null && next.f4852b && (str3 = next.f4851a) != null && str3.length() > 0) {
                                        i3++;
                                    }
                                }
                                TXCStreamDownloader.this.setStatusValue(7116, Long.valueOf(i3));
                                TXCStreamDownloader.this.setStatusValue(7112, 2L);
                                TXCStreamDownloader.this.mDownloader.setOriginUrl(str);
                                TXCStreamDownloader.this.mDownloader.startDownload(vector2, true, true, z2);
                            }
                            if (TXCStreamDownloader.this.mHandler != null) {
                                TXCStreamDownloader.this.mHandler.postDelayed(TXCStreamDownloader.this.mReportNetStatusRunnalbe, 2000L);
                            }
                            TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, TXCDRDef.f2469at, i2, TXCStreamDownloader.this.mAccUrlFetcher.m1103b());
                            return;
                        }
                        TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, TXCDRDef.f2469at, -4, "livePlayer have been stopped");
                        return;
                    }
                    TXCStreamDownloader.this.onNotifyEvent(TXCStreamDownloader.TXE_DOWNLOAD_ERROR_GET_RTMP_ACC_URL_FAIL, null);
                    TXCDRApi.txReportDAU(TXCStreamDownloader.this.mApplicationContext, TXCDRDef.f2469at, i2, str2);
                    TXCLog.m2914e(TXCStreamDownloader.TAG, "getAccelerateStreamPlayUrl failed, play stream with raw url");
                    if (!TXCStreamDownloader.this.mDownloaderRunning) {
                        return;
                    }
                    TXCStreamDownloader.this.playStreamWithRawUrl(str, z2);
                    if (TXCStreamDownloader.this.mHandler == null) {
                        return;
                    }
                    TXCStreamDownloader.this.mHandler.postDelayed(TXCStreamDownloader.this.mReportNetStatusRunnalbe, 2000L);
                }
            });
            if (m1107a != 0) {
                if (m1107a == -1) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, TXCDRDef.f2469at, m1107a, "invalid playUrl");
                } else if (m1107a == -2) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, TXCDRDef.f2469at, m1107a, "invalid streamID");
                } else if (m1107a == -3) {
                    TXCDRApi.txReportDAU(this.mApplicationContext, TXCDRDef.f2469at, m1107a, "invalid signature");
                }
                TXCLog.m2914e(TAG, "getAccelerateStreamPlayUrl failed, result = " + m1107a + ", play stream with raw url");
                onNotifyEvent(TXE_DOWNLOAD_ERROR_GET_RTMP_ACC_URL_FAIL, null);
                playStreamWithRawUrl(str, z2);
                Handler handler2 = this.mHandler;
                if (handler2 != null) {
                    handler2.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
                }
            }
            return 0;
        } else {
            if (this.mDownloader != null) {
                setStatusValue(7112, 1L);
                Vector<TXCStreamPlayUrl> vector2 = new Vector<>();
                vector2.add(new TXCStreamPlayUrl(str, false));
                this.mDownloader.setOriginUrl(str);
                TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
                if (this.mDownloadFormat != 4) {
                    z3 = false;
                }
                tXIStreamDownloader.startDownload(vector2, z3, z, z2);
                Handler handler3 = this.mHandler;
                if (handler3 != null) {
                    handler3.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
                }
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playStreamWithRawUrl(String str, boolean z) {
        if (this.mDownloader != null) {
            if (str != null && ((str.startsWith("http://") || str.startsWith("https://")) && str.contains(".flv"))) {
                TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
                int i = tXIStreamDownloader.connectRetryLimit;
                int i2 = tXIStreamDownloader.connectRetryInterval;
                this.mDownloader = null;
                this.mDownloader = new TXCFLVDownloader(this.mApplicationContext);
                this.mDownloader.setListener(this);
                this.mDownloader.setNotifyListener(this);
                this.mDownloader.setRestartListener(this);
                TXIStreamDownloader tXIStreamDownloader2 = this.mDownloader;
                tXIStreamDownloader2.connectRetryLimit = i;
                tXIStreamDownloader2.connectRetryInterval = i2;
                tXIStreamDownloader2.setHeaders(this.mHeaders);
            }
            setStatusValue(7112, 1L);
            Vector<TXCStreamPlayUrl> vector = new Vector<>();
            vector.add(new TXCStreamPlayUrl(str, false));
            this.mDownloader.setOriginUrl(str);
            this.mDownloader.startDownload(vector, false, false, z);
        }
    }

    public void stop() {
        this.mDownloaderRunning = false;
        this.mRecvFirstNal = false;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.stopDownload();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mReportNetStatusRunnalbe);
        }
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher != null) {
                this.mStreamSwitcher.m1149a((TXIStreamDownloaderListener) null);
                this.mStreamSwitcher.m1154a();
                this.mStreamSwitcher = null;
            }
        }
    }

    public boolean switchStream(String str) {
        synchronized (this.mListenerLock) {
            if (this.mStreamSwitcher != null) {
                TXCLog.m2911w(TAG, "stream_switch stream is changing ignore this change");
                return false;
            }
            TXCFLVDownloader tXCFLVDownloader = new TXCFLVDownloader(this.mApplicationContext);
            tXCFLVDownloader.connectRetryLimit = this.mDownloader.connectRetryLimit;
            tXCFLVDownloader.connectRetryInterval = this.mDownloader.connectRetryInterval;
            tXCFLVDownloader.setHeaders(this.mHeaders);
            this.mStreamSwitcher = new TXCMultiStreamDownloader(this);
            this.mStreamSwitcher.m1149a(this);
            this.mStreamSwitcher.m1152a(this.mDownloader, tXCFLVDownloader, this.mCurrentNalTs, this.mLastIFramelTs, str);
            this.mSwitchStartTime = System.currentTimeMillis();
            return true;
        }
    }

    private DownloadStats getDownloadStats() {
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            return tXIStreamDownloader.getDownloadStats();
        }
        return null;
    }

    private C3564a getRealTimeStreamInfo() {
        C3564a c3564a = new C3564a();
        TXRTMPAccUrlFetcher tXRTMPAccUrlFetcher = this.mAccUrlFetcher;
        if (tXRTMPAccUrlFetcher != null) {
            c3564a.f4768b = tXRTMPAccUrlFetcher.m1112a();
            c3564a.f4769c = this.mAccUrlFetcher.m1103b();
            c3564a.f4770d = this.mAccUrlFetcher.m1100c();
            c3564a.f4771e = this.mAccUrlFetcher.m1098d();
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            c3564a.f4767a = tXIStreamDownloader.getCurrentStreamUrl();
            c3564a.f4772f = this.mDownloader.isQuicChannel();
        }
        return c3564a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportNetStatus() {
        reportNetStatusInternal();
        this.mHandler.postDelayed(this.mReportNetStatusRunnalbe, 2000L);
    }

    private void reportNetStatusInternal() {
        long j;
        long timeTick = TXCTimeUtil.getTimeTick();
        long j2 = timeTick - this.mLastTimeStamp;
        DownloadStats downloadStats = getDownloadStats();
        C3564a realTimeStreamInfo = getRealTimeStreamInfo();
        if (downloadStats != null) {
            DownloadStats downloadStats2 = this.mLastDownloadStats;
            long j3 = 0;
            if (downloadStats2 != null) {
                long longValue = getSpeed(downloadStats2.afterParseVideoBytes, downloadStats.afterParseVideoBytes, j2).longValue();
                j = getSpeed(this.mLastDownloadStats.afterParseAudioBytes, downloadStats.afterParseAudioBytes, j2).longValue();
                j3 = longValue;
            } else {
                j = 0;
            }
            setStatusValue(7101, Long.valueOf(j3));
            setStatusValue(7102, Long.valueOf(j));
            setStatusValue(7103, Long.valueOf(downloadStats.firstVideoTS));
            setStatusValue(7104, Long.valueOf(downloadStats.firstAudioTS));
            if (realTimeStreamInfo != null) {
                setStatusValue(7105, Long.valueOf(realTimeStreamInfo.f4770d));
                setStatusValue(7106, realTimeStreamInfo.f4771e);
                setStatusValue(7111, Long.valueOf(realTimeStreamInfo.f4772f ? 2L : 1L));
                setStatusValue(7113, realTimeStreamInfo.f4767a);
                setStatusValue(7114, realTimeStreamInfo.f4768b);
                setStatusValue(7115, realTimeStreamInfo.f4769c);
            }
            setStatusValue(7107, Long.valueOf(downloadStats.startTS));
            setStatusValue(7108, Long.valueOf(downloadStats.dnsTS));
            setStatusValue(7109, Long.valueOf(downloadStats.connTS));
            setStatusValue(7110, String.valueOf(downloadStats.serverIP));
        }
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            int connectCountQuic = tXIStreamDownloader.getConnectCountQuic();
            int connectCountTcp = this.mDownloader.getConnectCountTcp();
            setStatusValue(7117, Long.valueOf(connectCountQuic + 1));
            setStatusValue(7118, Long.valueOf(connectCountTcp + 1));
            setStatusValue(7119, this.mDownloader.getRealStreamUrl());
        }
        this.mLastTimeStamp = timeTick;
        this.mLastDownloadStats = downloadStats;
    }

    private Long getSpeed(long j, long j2, long j3) {
        if (j <= j2) {
            j2 -= j;
        }
        long j4 = 0;
        if (j3 > 0) {
            j4 = ((j2 * 8) * 1000) / (j3 * 1024);
        }
        return Long.valueOf(j4);
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
        TXIStreamDownloader tXIStreamDownloader = this.mDownloader;
        if (tXIStreamDownloader != null) {
            tXIStreamDownloader.setHeaders(this.mHeaders);
        }
    }
}
