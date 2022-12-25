package com.tencent.liteav.network;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp.TXLiveConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class TXCStreamUploader extends TXCModule implements AbstractC3573b {
    public static final int RTMPSENDSTRATEGY_LIVE = 1;
    public static final int RTMPSENDSTRATEGY_REALTIME_QUIC = 3;
    public static final int RTMPSENDSTRATEGY_REALTIME_TCP = 2;
    static final String TAG = "TXCStreamUploader";
    public static final int TXE_UPLOAD_ERROR_ALLADDRESS_FAILED = 11011;
    public static final int TXE_UPLOAD_ERROR_INVALID_ADDRESS = 11019;
    public static final int TXE_UPLOAD_ERROR_NET_DISCONNECT = 11012;
    public static final int TXE_UPLOAD_ERROR_NET_RECONNECT = 11016;
    public static final int TXE_UPLOAD_ERROR_NO_DATA = 11013;
    public static final int TXE_UPLOAD_ERROR_NO_NETWORK = 11015;
    public static final int TXE_UPLOAD_ERROR_READ_FAILED = 11017;
    public static final int TXE_UPLOAD_ERROR_WRITE_FAILED = 11018;
    public static final int TXE_UPLOAD_INFO_CONNECT_FAILED = 11006;
    public static final int TXE_UPLOAD_INFO_CONNECT_SUCCESS = 11001;
    public static final int TXE_UPLOAD_INFO_HANDSHAKE_FAIL = 11005;
    public static final int TXE_UPLOAD_INFO_NET_BUSY = 11003;
    public static final int TXE_UPLOAD_INFO_PUBLISH_START = 11008;
    public static final int TXE_UPLOAD_INFO_PUSH_BEGIN = 11002;
    public static final int TXE_UPLOAD_INFO_ROOM_IN = 11021;
    public static final int TXE_UPLOAD_INFO_ROOM_NEED_REENTER = 11024;
    public static final int TXE_UPLOAD_INFO_ROOM_OUT = 11022;
    public static final int TXE_UPLOAD_INFO_ROOM_USERLIST = 11023;
    public static final int TXE_UPLOAD_INFO_SERVER_REFUSE = 11007;
    public static final int TXE_UPLOAD_MODE_AUDIO_ONLY = 1;
    public static final int TXE_UPLOAD_MODE_LINK_MIC = 2;
    public static final int TXE_UPLOAD_MODE_REAL_TIME = 0;
    public static final int TXE_UPLOAD_PROTOCOL_AV = 1;
    public static final int TXE_UPLOAD_PROTOCOL_RTMP = 0;
    private Context mContext;
    private int mCurrentRecordIdx;
    private HandlerThread mHandlerThread;
    private C3574c mIntelligentRoute;
    private ArrayList<TXCIntelligentRoute> mIpList;
    private boolean mIsPushing;
    private int mLastNetworkType;
    private TXSStreamUploaderParam mParam;
    private int mRetryCount;
    private String mRtmpUrl;
    private Thread mThread;
    private Object mThreadLock;
    private UploadQualityReport mUploadQualityReport;
    private long mUploaderInstance;
    private long mPushStartTS = 0;
    private boolean mQuicChannel = false;
    private int mChannelType = 0;
    private boolean mEnableNearestIP = true;
    private WeakReference<TXINotifyListener> mNotifyListener = null;
    private long mConnectSuccessTimeStamps = 0;
    private long mGoodPushTime = 30000;
    private Handler mHandler = null;
    private final int MSG_RECONNECT = 101;
    private final int MSG_EVENT = 102;
    private final int MSG_REPORT_STATUS = 103;
    private final int MSG_RTMPPROXY_HEARTBEAT = 104;
    private long mLastTimeStamp = 0;
    private UploadStats mLastUploadStats = null;
    private Vector<TXSNALPacket> mVecPendingNAL = new Vector<>();
    private int mConnectCountQuic = 0;
    private int mConnectCountTcp = 0;
    private boolean mRtmpProxyEnable = false;
    private boolean mAudioMuted = false;
    private C3568a mRtmpProxyParam = new C3568a();
    private Vector<String> mRtmpProxyIPList = new Vector<>();
    private int mRtmpProxyIPIndex = 0;
    private long mRtmpProxyInstance = 0;
    private long mRtmpMsgRecvThreadInstance = 0;
    private Object mRtmpProxyLock = new Object();
    private Object mRtmpMsgRecvThreadLock = new Object();

    private native void nativeCacheJNIParams();

    private native void nativeEnableDrop(long j, boolean z);

    private native UploadStats nativeGetStats(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpMsgRecvThreadInstance(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitRtmpProxyInstance(long j, long j2, String str, long j3, String str2, long j4, long j5, String str3, boolean z, String str4);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeInitUploader(String str, String str2, boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2, int i8);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeOnThreadRun(long j);

    private native void nativePushAAC(long j, byte[] bArr, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativePushNAL(long j, byte[] bArr, int i, long j2, long j3, long j4);

    private native void nativeReleaseJNIParams();

    private native void nativeRtmpMsgRecvThreadStart(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeRtmpMsgRecvThreadStop(long j);

    private native void nativeRtmpProxyEnterRoom(long j);

    private native void nativeRtmpProxyLeaveRoom(long j);

    private native void nativeRtmpProxySendHeartBeat(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11);

    private native void nativeSendRtmpProxyMsg(long j, byte[] bArr);

    private native void nativeSetSendStrategy(long j, int i, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetVideoDropParams(long j, boolean z, int i, int i2);

    private native void nativeStopPush(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpMsgRecvThreadInstance(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitRtmpProxyInstance(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeUninitUploader(long j);

    public int init() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tencent.liteav.network.TXCStreamUploader$b */
    /* loaded from: classes3.dex */
    public class C3569b {

        /* renamed from: a */
        public String f4790a;

        /* renamed from: b */
        public boolean f4791b;

        public C3569b(String str, boolean z) {
            this.f4790a = "";
            this.f4791b = false;
            this.f4790a = str;
            this.f4791b = z;
        }
    }

    /* loaded from: classes3.dex */
    public class UploadStats {
        public long audioCacheLen;
        public long audioDropCount;
        public long channelType;
        public long connTS;
        public long connectTimeCost;
        public String connectionID;
        public String connectionStats;
        public long dnsTS;
        public long dnsparseTimeCost;
        public long handshakeTimeCost;
        public long inAudioBytes;
        public long inVideoBytes;
        public long outAudioBytes;
        public long outVideoBytes;
        public String serverIP;
        public long startTS;
        public long videoCacheLen;
        public long videoDropCount;

        public UploadStats() {
        }
    }

    /* renamed from: com.tencent.liteav.network.TXCStreamUploader$a */
    /* loaded from: classes3.dex */
    public class C3568a {

        /* renamed from: a */
        public long f4779a;

        /* renamed from: b */
        public long f4780b;

        /* renamed from: c */
        public String f4781c;

        /* renamed from: d */
        public long f4782d;

        /* renamed from: e */
        public String f4783e;

        /* renamed from: f */
        public long f4784f;

        /* renamed from: g */
        public long f4785g;

        /* renamed from: h */
        public String f4786h;

        /* renamed from: i */
        public boolean f4787i;

        /* renamed from: j */
        public String f4788j;

        public C3568a() {
        }

        /* renamed from: a */
        public void m1183a() {
            this.f4779a = 0L;
            this.f4780b = 0L;
            this.f4781c = "";
            this.f4782d = 0L;
            this.f4783e = "";
            this.f4784f = 0L;
            this.f4785g = 0L;
            this.f4787i = false;
            this.f4788j = "";
        }
    }

    /* loaded from: classes3.dex */
    public class RtmpProxyUserInfo {
        public String account = "";
        public String playUrl = "";

        public RtmpProxyUserInfo() {
        }
    }

    static {
        TXCSystemUtil.m2873e();
    }

    public void setNotifyListener(TXINotifyListener tXINotifyListener) {
        this.mNotifyListener = new WeakReference<>(tXINotifyListener);
    }

    public TXCStreamUploader(Context context, TXSStreamUploaderParam tXSStreamUploaderParam) {
        this.mUploaderInstance = 0L;
        this.mThread = null;
        this.mThreadLock = null;
        this.mIsPushing = false;
        this.mRtmpUrl = "";
        this.mIntelligentRoute = null;
        this.mLastNetworkType = 255;
        this.mContext = null;
        this.mIpList = null;
        this.mCurrentRecordIdx = 0;
        this.mRetryCount = 0;
        this.mHandlerThread = null;
        this.mParam = null;
        this.mUploadQualityReport = null;
        this.mContext = context;
        if (tXSStreamUploaderParam == null) {
            tXSStreamUploaderParam = new TXSStreamUploaderParam();
            tXSStreamUploaderParam.f4910a = 0;
            tXSStreamUploaderParam.f4916g = 3;
            tXSStreamUploaderParam.f4915f = 3;
            tXSStreamUploaderParam.f4917h = 40;
            tXSStreamUploaderParam.f4918i = 1000;
            tXSStreamUploaderParam.f4919j = true;
        }
        this.mParam = tXSStreamUploaderParam;
        this.mThreadLock = new Object();
        this.mIntelligentRoute = new C3574c();
        this.mIntelligentRoute.f4818a = this;
        this.mUploaderInstance = 0L;
        this.mRetryCount = 0;
        this.mCurrentRecordIdx = 0;
        this.mIpList = null;
        this.mIsPushing = false;
        this.mThread = null;
        this.mRtmpUrl = null;
        this.mLastNetworkType = 255;
        this.mHandlerThread = null;
        this.mUploadQualityReport = new UploadQualityReport(context);
        UploadQualityData.m1095a().m1094a(context);
    }

    public void setRetryInterval(int i) {
        TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
        if (tXSStreamUploaderParam != null) {
            tXSStreamUploaderParam.f4916g = i;
        }
    }

    public void setAudioInfo(int i, int i2) {
        TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
        if (tXSStreamUploaderParam != null) {
            tXSStreamUploaderParam.f4913d = i2;
            tXSStreamUploaderParam.f4914e = i;
        }
    }

    public void setRetryTimes(int i) {
        TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
        if (tXSStreamUploaderParam != null) {
            tXSStreamUploaderParam.f4915f = i;
        }
    }

    public void setMode(int i) {
        TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
        if (tXSStreamUploaderParam != null) {
            tXSStreamUploaderParam.f4910a = i;
        }
    }

    private void postReconnectMsg(String str, boolean z, int i) {
        Message message = new Message();
        message.what = 101;
        message.obj = str;
        message.arg1 = z ? 2 : 1;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003b  */
    @Override // com.tencent.liteav.network.AbstractC3573b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onFetchDone(int i, ArrayList<TXCIntelligentRoute> arrayList) {
        int i2;
        String str;
        if (!this.mIsPushing) {
            return;
        }
        if (arrayList != null) {
            TXCLog.m2914e(TAG, "onFetchDone: code = " + i + " ip count = " + arrayList.size());
            if (i == 0) {
                this.mIpList = arrayList;
                i2 = arrayList.size();
                this.mCurrentRecordIdx = 0;
                if (i2 > 0) {
                    Iterator<TXCIntelligentRoute> it2 = this.mIpList.iterator();
                    String str2 = "";
                    int i3 = 0;
                    while (it2.hasNext()) {
                        TXCIntelligentRoute next = it2.next();
                        if (next != null && next.f4795c && (str = next.f4793a) != null && str.length() > 0) {
                            i3++;
                        }
                        if (next != null) {
                            str2 = str2 + ConstantUtils.PLACEHOLDER_STR_ONE + getConfusionIP(next.f4793a) + ":" + next.f4794b;
                        }
                    }
                    setStatusValue(7016, Long.valueOf(i3));
                    setStatusValue(7019, "{" + str2 + " }");
                }
                C3569b rtmpRealConnectInfo = getRtmpRealConnectInfo();
                postReconnectMsg(rtmpRealConnectInfo.f4790a, rtmpRealConnectInfo.f4791b, 0);
            }
        }
        i2 = 0;
        if (i2 > 0) {
        }
        C3569b rtmpRealConnectInfo2 = getRtmpRealConnectInfo();
        postReconnectMsg(rtmpRealConnectInfo2.f4790a, rtmpRealConnectInfo2.f4791b, 0);
    }

    public String getConfusionIP(String str) {
        int indexOf;
        String substring;
        int indexOf2;
        if (str == null || (indexOf = str.indexOf(".")) == -1 || (indexOf2 = (substring = str.substring(indexOf + 1)).indexOf(".")) == -1) {
            return str;
        }
        String substring2 = substring.substring(indexOf2 + 1);
        return "A.B." + substring2;
    }

    public String start(String str, boolean z, int i) {
        if (this.mIsPushing) {
            return this.mRtmpUrl;
        }
        this.mIsPushing = true;
        this.mConnectSuccessTimeStamps = 0L;
        this.mRetryCount = 0;
        this.mRtmpUrl = str;
        this.mChannelType = i;
        this.mPushStartTS = 0L;
        this.mConnectCountQuic = 0;
        this.mConnectCountTcp = 0;
        this.mRtmpProxyEnable = false;
        this.mRtmpProxyParam.m1183a();
        this.mRtmpProxyIPList.clear();
        this.mRtmpProxyIPIndex = 0;
        this.mRtmpProxyInstance = 0L;
        this.mRtmpMsgRecvThreadInstance = 0L;
        setStatusValue(7016, 0L);
        setStatusValue(7017, 0L);
        setStatusValue(7018, 0L);
        this.mUploadQualityReport.m1084a();
        StringBuilder sb = new StringBuilder();
        sb.append("start push with url:");
        sb.append(this.mRtmpUrl);
        sb.append(" enable nearest ip:");
        sb.append(z ? "yes" : "no");
        sb.append("channel type:");
        sb.append(i);
        TXCLog.m2915d(TAG, sb.toString());
        if (TXCSystemUtil.m2876c(this.mContext) == 255) {
            sendNotifyEvent(TXE_UPLOAD_ERROR_NO_NETWORK);
            return this.mRtmpUrl;
        }
        this.mEnableNearestIP = z;
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new HandlerThread("RTMP_PUSH");
            this.mHandlerThread.start();
        }
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.tencent.liteav.network.TXCStreamUploader.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 101) {
                    TXCStreamUploader.this.startPushTask((String) message.obj, message.arg1 == 2, 0);
                } else if (i2 == 103) {
                    TXCStreamUploader.this.reportNetStatus();
                } else if (i2 != 104) {
                } else {
                    TXCStreamUploader.this.rtmpProxySendHeartBeat();
                    if (TXCStreamUploader.this.mHandler == null) {
                        return;
                    }
                    TXCStreamUploader.this.mHandler.sendEmptyMessageDelayed(104, 2000L);
                }
            }
        };
        parseProxyInfo(str);
        if (this.mRtmpProxyEnable) {
            this.mLastNetworkType = TXCSystemUtil.m2876c(this.mContext);
            nativeCacheJNIParams();
            startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        } else if (this.mEnableNearestIP && this.mLastNetworkType != TXCSystemUtil.m2876c(this.mContext)) {
            TXCLog.m2915d(TAG, "fetching nearest ip list");
            this.mLastNetworkType = TXCSystemUtil.m2876c(this.mContext);
            this.mIntelligentRoute.m1162a(str, i);
        } else {
            startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
        }
        this.mHandler.sendEmptyMessageDelayed(103, 2000L);
        return this.mRtmpUrl;
    }

    public void stop() {
        if (!this.mIsPushing) {
            return;
        }
        this.mIsPushing = false;
        TXCLog.m2915d(TAG, "stop push");
        if (this.mRtmpProxyEnable) {
            synchronized (this.mRtmpProxyLock) {
                nativeRtmpProxyLeaveRoom(this.mRtmpProxyInstance);
            }
        }
        synchronized (this.mThreadLock) {
            nativeStopPush(this.mUploaderInstance);
            this.mPushStartTS = 0L;
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.getLooper().quit();
            this.mHandlerThread = null;
        }
        if (this.mHandler != null) {
            this.mHandler = null;
        }
        if (this.mRtmpProxyEnable) {
            nativeReleaseJNIParams();
        }
        this.mUploadQualityReport.m1075c();
        this.mUploadQualityReport.m1084a();
    }

    private void tryResetRetryCount() {
        if (this.mConnectSuccessTimeStamps != 0) {
            long timeTick = TXCTimeUtil.getTimeTick() - this.mConnectSuccessTimeStamps;
            TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
            if (timeTick <= tXSStreamUploaderParam.f4915f * (tXSStreamUploaderParam.f4916g + 13) * 1000) {
                return;
            }
            this.mRetryCount = 0;
            this.mConnectSuccessTimeStamps = 0L;
            TXCLog.m2915d(TAG, "reset retry count");
        }
    }

    public void pushAAC(byte[] bArr, long j) {
        tryResetRetryCount();
        synchronized (this.mThreadLock) {
            if (this.mPushStartTS == 0) {
                this.mPushStartTS = j - DateUtils.ONE_HOUR_MILLIONS;
            }
            if (!this.mAudioMuted || !this.mRtmpProxyEnable) {
                nativePushAAC(this.mUploaderInstance, bArr, j - this.mPushStartTS);
            }
        }
    }

    public void pushNAL(TXSNALPacket tXSNALPacket) {
        tryResetRetryCount();
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0) {
                if (this.mPushStartTS == 0) {
                    this.mPushStartTS = tXSNALPacket.dts - DateUtils.ONE_HOUR_MILLIONS;
                }
                if (tXSNALPacket != null && tXSNALPacket.nalData != null && tXSNALPacket.nalData.length > 0) {
                    nativePushNAL(this.mUploaderInstance, tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.frameIndex, tXSNALPacket.pts - this.mPushStartTS, tXSNALPacket.dts - this.mPushStartTS);
                }
            } else {
                if (tXSNALPacket.nalType == 0) {
                    this.mVecPendingNAL.removeAllElements();
                }
                this.mVecPendingNAL.add(tXSNALPacket);
            }
        }
    }

    public void setAudioMute(boolean z) {
        synchronized (this.mThreadLock) {
            this.mAudioMuted = z;
            if (this.mRtmpProxyEnable && this.mUploaderInstance != 0) {
                nativeSetSendStrategy(this.mUploaderInstance, this.mParam.f4922m ? this.mQuicChannel ? 3 : 2 : 1, false);
            }
        }
    }

    public void setDropEanble(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop enable ");
        sb.append(z ? "yes" : "no");
        TXCLog.m2915d(TAG, sb.toString());
        synchronized (this.mThreadLock) {
            nativeEnableDrop(this.mUploaderInstance, z);
        }
    }

    public void setVideoDropParams(boolean z, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("drop params wait i frame:");
        sb.append(z ? "yes" : "no");
        sb.append(" max video count:");
        sb.append(i);
        sb.append(" max video cache time: ");
        sb.append(i2);
        sb.append(" ms");
        TXCLog.m2915d(TAG, sb.toString());
        synchronized (this.mThreadLock) {
            this.mParam.f4919j = z;
            this.mParam.f4917h = i;
            this.mParam.f4918i = i2;
            if (this.mUploaderInstance != 0) {
                nativeSetVideoDropParams(this.mUploaderInstance, this.mParam.f4919j, this.mParam.f4917h, this.mParam.f4918i);
            }
        }
    }

    public void setSendStrategy(boolean z, boolean z2) {
        int i;
        ArrayList<TXCIntelligentRoute> arrayList;
        TXSStreamUploaderParam tXSStreamUploaderParam = this.mParam;
        tXSStreamUploaderParam.f4922m = z;
        tXSStreamUploaderParam.f4923n = z2;
        this.mUploadQualityReport.m1080a(z);
        if (z) {
            i = this.mQuicChannel ? 3 : 2;
        } else {
            i = 1;
        }
        if (!this.mRtmpProxyEnable && ((arrayList = this.mIpList) == null || arrayList.size() == 0)) {
            i = 1;
        }
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0) {
                nativeSetSendStrategy(this.mUploaderInstance, i, z2);
            }
        }
        setStatusValue(7020, Long.valueOf(i));
    }

    public UploadStats getUploadStats() {
        UploadStats nativeGetStats;
        synchronized (this.mThreadLock) {
            nativeGetStats = nativeGetStats(this.mUploaderInstance);
            if (nativeGetStats != null) {
                nativeGetStats.channelType = this.mQuicChannel ? 2L : 1L;
            }
        }
        return nativeGetStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPushTask(final String str, final boolean z, int i) {
        TXCLog.m2915d(TAG, "start push task");
        boolean z2 = this.mQuicChannel;
        if (z2 != z && z2) {
            String str2 = this.mRtmpUrl;
            int i2 = TXCDRDef.f2409M;
            TXCDRApi.reportEvent40003(str2, i2, "switch video push channel from quic to tcp", "limits:" + this.mParam.f4915f + " current:" + this.mRetryCount);
        }
        if (z) {
            int i3 = this.mConnectCountQuic + 1;
            this.mConnectCountQuic = i3;
            setStatusValue(7017, Long.valueOf(i3));
        } else {
            int i4 = this.mConnectCountTcp + 1;
            this.mConnectCountTcp = i4;
            setStatusValue(7018, Long.valueOf(i4));
        }
        this.mThread = new Thread("RTMPUpload") { // from class: com.tencent.liteav.network.TXCStreamUploader.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int i5;
                while (TXCStreamUploader.this.mUploaderInstance != 0) {
                    try {
                        Thread.sleep(100L, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TXCStreamUploader.this.mUploadQualityReport.m1078b();
                TXCStreamUploader.this.mUploadQualityReport.m1080a(TXCStreamUploader.this.mParam.f4922m);
                TXCStreamUploader.this.mUploadQualityReport.m1081a(TXCStreamUploader.this.mRtmpUrl);
                TXCStreamUploader.this.mUploadQualityReport.m1079a(z, TXCStreamUploader.this.getAddressFromUrl(str));
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader.this.mQuicChannel = z;
                    if (TXCStreamUploader.this.mParam.f4922m) {
                        i5 = TXCStreamUploader.this.mQuicChannel ? 3 : 2;
                    } else {
                        i5 = 1;
                    }
                    if (!TXCStreamUploader.this.mRtmpProxyEnable) {
                        if (TXCStreamUploader.this.mIpList == null || TXCStreamUploader.this.mIpList.size() == 0) {
                            i5 = 1;
                        }
                    } else if (TXCStreamUploader.this.mAudioMuted) {
                        TXCStreamUploader.this.mParam.f4923n = false;
                    }
                    TXCStreamUploader.this.setStatusValue(7020, Long.valueOf(i5));
                    TXCStreamUploader.this.mUploaderInstance = TXCStreamUploader.this.nativeInitUploader(TXCStreamUploader.this.mRtmpUrl, str, z, TXCStreamUploader.this.mParam.f4914e, TXCStreamUploader.this.mParam.f4913d, TXCStreamUploader.this.mParam.f4910a, TXCStreamUploader.this.mParam.f4912c, TXCStreamUploader.this.mParam.f4917h, 16, i5, TXCStreamUploader.this.mParam.f4923n, TXCStreamUploader.this.mParam.f4924o);
                    if (TXCStreamUploader.this.mUploaderInstance != 0) {
                        TXCStreamUploader.this.nativeSetVideoDropParams(TXCStreamUploader.this.mUploaderInstance, TXCStreamUploader.this.mParam.f4919j, TXCStreamUploader.this.mParam.f4917h, TXCStreamUploader.this.mParam.f4918i);
                        Iterator it2 = TXCStreamUploader.this.mVecPendingNAL.iterator();
                        boolean z3 = false;
                        while (it2.hasNext()) {
                            TXSNALPacket tXSNALPacket = (TXSNALPacket) it2.next();
                            if (!z3 && tXSNALPacket.nalType == 0) {
                                z3 = true;
                            }
                            if (z3) {
                                if (TXCStreamUploader.this.mPushStartTS == 0) {
                                    TXCStreamUploader.this.mPushStartTS = tXSNALPacket.dts - DateUtils.ONE_HOUR_MILLIONS;
                                }
                                TXCStreamUploader.this.nativePushNAL(TXCStreamUploader.this.mUploaderInstance, tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.frameIndex, tXSNALPacket.pts - TXCStreamUploader.this.mPushStartTS, tXSNALPacket.dts - TXCStreamUploader.this.mPushStartTS);
                            }
                        }
                        TXCStreamUploader.this.mVecPendingNAL.removeAllElements();
                    }
                }
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader.this.mRtmpProxyInstance = TXCStreamUploader.this.nativeInitRtmpProxyInstance(TXCStreamUploader.this.mRtmpProxyParam.f4779a, TXCStreamUploader.this.mRtmpProxyParam.f4780b, TXCStreamUploader.this.mRtmpProxyParam.f4781c, TXCStreamUploader.this.mRtmpProxyParam.f4782d, TXCStreamUploader.this.mRtmpProxyParam.f4783e, TXCStreamUploader.this.mRtmpProxyParam.f4784f, TXCStreamUploader.this.mRtmpProxyParam.f4785g, TXCStreamUploader.this.mRtmpProxyParam.f4786h, TXCStreamUploader.this.mRtmpProxyParam.f4787i, TXCStreamUploader.this.mRtmpProxyParam.f4788j);
                    }
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader.this.mRtmpMsgRecvThreadInstance = TXCStreamUploader.this.nativeInitRtmpMsgRecvThreadInstance(TXCStreamUploader.this.mRtmpProxyInstance, TXCStreamUploader.this.mUploaderInstance);
                    }
                }
                TXCStreamUploader tXCStreamUploader = TXCStreamUploader.this;
                tXCStreamUploader.nativeOnThreadRun(tXCStreamUploader.mUploaderInstance);
                if (TXCStreamUploader.this.mRtmpProxyEnable) {
                    synchronized (TXCStreamUploader.this.mRtmpMsgRecvThreadLock) {
                        TXCStreamUploader.this.nativeRtmpMsgRecvThreadStop(TXCStreamUploader.this.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader.this.nativeUninitRtmpMsgRecvThreadInstance(TXCStreamUploader.this.mRtmpMsgRecvThreadInstance);
                        TXCStreamUploader.this.mRtmpMsgRecvThreadInstance = 0L;
                    }
                    synchronized (TXCStreamUploader.this.mRtmpProxyLock) {
                        TXCStreamUploader.this.nativeUninitRtmpProxyInstance(TXCStreamUploader.this.mRtmpProxyInstance);
                        TXCStreamUploader.this.mRtmpProxyInstance = 0L;
                    }
                }
                synchronized (TXCStreamUploader.this.mThreadLock) {
                    TXCStreamUploader.this.nativeUninitUploader(TXCStreamUploader.this.mUploaderInstance);
                    TXCStreamUploader.this.mUploaderInstance = 0L;
                }
            }
        };
        this.mThread.start();
    }

    private void stopPushTask() {
        TXCLog.m2915d(TAG, "stop push task");
        synchronized (this.mThreadLock) {
            this.mVecPendingNAL.removeAllElements();
            nativeStopPush(this.mUploaderInstance);
        }
    }

    private C3569b getRtmpRealConnectInfo() {
        int i;
        if (!this.mEnableNearestIP) {
            return new C3569b(this.mRtmpUrl, false);
        }
        ArrayList<TXCIntelligentRoute> arrayList = this.mIpList;
        if (arrayList == null) {
            return new C3569b(this.mRtmpUrl, false);
        }
        if (this.mCurrentRecordIdx >= arrayList.size() || (i = this.mCurrentRecordIdx) < 0) {
            return new C3569b(this.mRtmpUrl, false);
        }
        TXCIntelligentRoute tXCIntelligentRoute = this.mIpList.get(i);
        String[] split = this.mRtmpUrl.split("://");
        if (split.length < 2) {
            return new C3569b(this.mRtmpUrl, false);
        }
        String[] split2 = split[1].split("/");
        split2[0] = tXCIntelligentRoute.f4793a + ":" + tXCIntelligentRoute.f4794b;
        StringBuilder sb = new StringBuilder(split2[0]);
        for (int i2 = 1; i2 < split2.length; i2++) {
            sb.append("/");
            sb.append(split2[i2]);
        }
        return new C3569b(split[0] + "://" + sb.toString(), tXCIntelligentRoute.f4795c);
    }

    private boolean nextRecordIdx(boolean z) {
        ArrayList<TXCIntelligentRoute> arrayList = this.mIpList;
        if (arrayList != null && arrayList.size() != 0) {
            if (z) {
                this.mIpList.get(this.mCurrentRecordIdx).f4797e++;
            }
            if (this.mCurrentRecordIdx + 1 < this.mIpList.size()) {
                this.mCurrentRecordIdx++;
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAddressFromUrl(String str) {
        int indexOf;
        String substring;
        int indexOf2;
        return (str == null || (indexOf = str.indexOf("://")) == -1 || (indexOf2 = (substring = str.substring(indexOf + 3)).indexOf("/")) == -1) ? "" : substring.substring(0, indexOf2);
    }

    private void reconnect(final boolean z) {
        stopPushTask();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.network.TXCStreamUploader.3
                @Override // java.lang.Runnable
                public void run() {
                    TXCStreamUploader.this.internalReconnect(z);
                }
            }, this.mParam.f4916g * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalReconnect(boolean z) {
        if (!this.mIsPushing) {
            return;
        }
        if (this.mRtmpProxyEnable) {
            if (this.mLastNetworkType != TXCSystemUtil.m2876c(this.mContext)) {
                TXCLog.m2914e(TAG, "reconnect network switch from " + this.mLastNetworkType + " to " + TXCSystemUtil.m2876c(this.mContext));
                this.mLastNetworkType = TXCSystemUtil.m2876c(this.mContext);
                this.mRetryCount = 0;
                sendNotifyEvent(TXE_UPLOAD_INFO_ROOM_NEED_REENTER, String.format("网络类型发生变化，需要重新进房", new Object[0]));
                return;
            }
            int i = this.mRetryCount;
            if (i < this.mParam.f4915f) {
                this.mRetryCount = i + 1;
                TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2402F, "reconnect rtmp-proxy server", "reconnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f4915f);
                sendNotifyEvent(TXE_UPLOAD_ERROR_NET_RECONNECT);
                startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
                return;
            } else if (getNextRtmpProxyIP()) {
                this.mRetryCount = 0;
                TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2402F, "reconnect rtmp-proxy server", "reconnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f4915f);
                sendNotifyEvent(TXE_UPLOAD_ERROR_NET_RECONNECT);
                startPushTask(this.mRtmpUrl, this.mQuicChannel, 0);
                return;
            } else {
                TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2401E, "connect rtmp-proxy server failed", "try all addresses");
                sendNotifyEvent(TXE_UPLOAD_ERROR_ALLADDRESS_FAILED);
                return;
            }
        }
        this.mUploadQualityReport.m1075c();
        if (this.mEnableNearestIP && this.mLastNetworkType != TXCSystemUtil.m2876c(this.mContext)) {
            TXCLog.m2914e(TAG, "reconnect network switch from " + this.mLastNetworkType + " to " + TXCSystemUtil.m2876c(this.mContext));
            this.mLastNetworkType = TXCSystemUtil.m2876c(this.mContext);
            this.mIntelligentRoute.m1162a(this.mRtmpUrl, this.mChannelType);
            this.mRetryCount = 0;
            return;
        }
        if (!this.mEnableNearestIP) {
            z = false;
        }
        if (this.mQuicChannel) {
            z = true;
        }
        if (z && !nextRecordIdx(true)) {
            TXCLog.m2914e(TAG, "reconnect: try all addresses failed");
            TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2401E, "connect upload server failed", "try all addresses failed");
        }
        C3569b rtmpRealConnectInfo = getRtmpRealConnectInfo();
        String addressFromUrl = getAddressFromUrl(rtmpRealConnectInfo.f4790a);
        StringBuilder sb = new StringBuilder();
        sb.append("reconnect change ip: ");
        sb.append(addressFromUrl);
        sb.append(" enableNearestIP: ");
        sb.append(this.mEnableNearestIP);
        sb.append(" last channel type: ");
        sb.append(this.mQuicChannel ? "Q Channel" : "TCP");
        TXCLog.m2914e(TAG, sb.toString());
        if (this.mQuicChannel) {
            TXCLog.m2914e(TAG, "reconnect last channel type is Q Channel，ignore retry limit");
            TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2402F, "reconnect upload server:" + addressFromUrl, "last channel type is Q Channel");
            startPushTask(rtmpRealConnectInfo.f4790a, rtmpRealConnectInfo.f4791b, 0);
            sendNotifyEvent(TXE_UPLOAD_ERROR_NET_RECONNECT);
            return;
        }
        TXCLog.m2914e(TAG, "reconnect retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f4915f);
        int i2 = this.mRetryCount;
        if (i2 < this.mParam.f4915f) {
            this.mRetryCount = i2 + 1;
            TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2402F, "reconnect upload server:" + addressFromUrl, "retry count:" + this.mRetryCount + " retry limit:" + this.mParam.f4915f);
            startPushTask(rtmpRealConnectInfo.f4790a, rtmpRealConnectInfo.f4791b, 0);
            sendNotifyEvent(TXE_UPLOAD_ERROR_NET_RECONNECT);
            return;
        }
        TXCLog.m2914e(TAG, "reconnect: try all times failed");
        TXCDRApi.reportEvent40003(this.mRtmpUrl, TXCDRDef.f2401E, "connect upload server failed", "try all times failed");
        sendNotifyEvent(TXE_UPLOAD_ERROR_ALLADDRESS_FAILED);
    }

    private void sendNotifyEvent(int i, String str) {
        if (str == null || str.isEmpty()) {
            sendNotifyEvent(i);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("EVT_MSG", str);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            if (i != 11006) {
                switch (i) {
                    case TXE_UPLOAD_ERROR_READ_FAILED /* 11017 */:
                    case TXE_UPLOAD_ERROR_WRITE_FAILED /* 11018 */:
                        i = 3005;
                        break;
                    case TXE_UPLOAD_ERROR_INVALID_ADDRESS /* 11019 */:
                        i = TXLiveConstants.PUSH_ERR_INVALID_ADDRESS;
                        break;
                    default:
                        switch (i) {
                            case TXE_UPLOAD_INFO_ROOM_IN /* 11021 */:
                                i = 1018;
                                break;
                            case TXE_UPLOAD_INFO_ROOM_OUT /* 11022 */:
                                i = 1019;
                                break;
                            case TXE_UPLOAD_INFO_ROOM_USERLIST /* 11023 */:
                                i = 1020;
                                break;
                            case TXE_UPLOAD_INFO_ROOM_NEED_REENTER /* 11024 */:
                                i = 1021;
                                break;
                        }
                }
            } else {
                i = 3002;
            }
            TXCSystemUtil.m2886a(this.mNotifyListener, i, bundle);
        }
        if (i != 11002) {
            if (i != 11003) {
                return;
            }
            this.mUploadQualityReport.m1073d();
            return;
        }
        UploadStats uploadStats = getUploadStats();
        if (uploadStats == null) {
            return;
        }
        this.mUploadQualityReport.m1082a(uploadStats.dnsparseTimeCost, uploadStats.connectTimeCost, uploadStats.handshakeTimeCost);
    }

    private void sendNotifyEvent(int i) {
        if (i == 0) {
            reconnect(false);
        } else if (i == 1) {
            reconnect(true);
        } else {
            if (i == 11001) {
                this.mConnectSuccessTimeStamps = TXCTimeUtil.getTimeTick();
            }
            if (this.mNotifyListener == null) {
                return;
            }
            Bundle bundle = new Bundle();
            switch (i) {
                case TXE_UPLOAD_INFO_CONNECT_SUCCESS /* 11001 */:
                    i = 1001;
                    bundle.putString("EVT_MSG", "已经连接rtmp服务器");
                    break;
                case TXE_UPLOAD_INFO_PUSH_BEGIN /* 11002 */:
                    i = 1002;
                    bundle.putString("EVT_MSG", "rtmp开始推流");
                    break;
                case TXE_UPLOAD_INFO_NET_BUSY /* 11003 */:
                    bundle.putString("EVT_MSG", "上行带宽不足，数据发送不及时");
                    i = TXLiveConstants.PUSH_WARNING_NET_BUSY;
                    break;
                case 11004:
                case 11009:
                case 11010:
                case 11014:
                default:
                    bundle.putString("EVT_MSG", "UNKNOWN");
                    break;
                case TXE_UPLOAD_INFO_HANDSHAKE_FAIL /* 11005 */:
                    i = 3003;
                    bundle.putString("EVT_MSG", "RTMP服务器握手失败");
                    break;
                case TXE_UPLOAD_INFO_CONNECT_FAILED /* 11006 */:
                    bundle.putString("EVT_MSG", "连接服务器失败");
                    i = 3002;
                    break;
                case TXE_UPLOAD_INFO_SERVER_REFUSE /* 11007 */:
                    i = 3004;
                    bundle.putString("EVT_MSG", "服务器拒绝连接请求，可能是该推流地址已经被占用或过期，或者防盗链错误");
                    break;
                case TXE_UPLOAD_INFO_PUBLISH_START /* 11008 */:
                    if (!this.mRtmpProxyEnable) {
                        return;
                    }
                    synchronized (this.mRtmpMsgRecvThreadLock) {
                        nativeRtmpMsgRecvThreadStart(this.mRtmpMsgRecvThreadInstance);
                    }
                    synchronized (this.mRtmpProxyLock) {
                        nativeRtmpProxyEnterRoom(this.mRtmpProxyInstance);
                    }
                    Handler handler = this.mHandler;
                    if (handler == null) {
                        return;
                    }
                    handler.sendEmptyMessageDelayed(104, 2000L);
                    return;
                case TXE_UPLOAD_ERROR_ALLADDRESS_FAILED /* 11011 */:
                    bundle.putString("EVT_MSG", "所有IP都已经尝试失败,可以放弃治疗");
                    i = TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
                    break;
                case TXE_UPLOAD_ERROR_NET_DISCONNECT /* 11012 */:
                    bundle.putString("EVT_MSG", "经连续多次重连失败，放弃重连");
                    i = TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
                    break;
                case TXE_UPLOAD_ERROR_NO_DATA /* 11013 */:
                    bundle.putString("EVT_MSG", "超过30s没有数据发送，主动断开连接");
                    i = TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
                    break;
                case TXE_UPLOAD_ERROR_NO_NETWORK /* 11015 */:
                    bundle.putString("EVT_MSG", "没有网络，请检测WiFi或移动数据是否开启");
                    i = TXLiveConstants.PUSH_ERR_NET_DISCONNECT;
                    break;
                case TXE_UPLOAD_ERROR_NET_RECONNECT /* 11016 */:
                    i = TXLiveConstants.PUSH_WARNING_RECONNECT;
                    bundle.putString("EVT_MSG", "启动网络重连");
                    break;
            }
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            TXCSystemUtil.m2886a(this.mNotifyListener, i, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportNetStatus() {
        long j;
        long j2;
        long j3;
        long timeTick = TXCTimeUtil.getTimeTick();
        long j4 = timeTick - this.mLastTimeStamp;
        UploadStats uploadStats = getUploadStats();
        if (uploadStats != null) {
            UploadStats uploadStats2 = this.mLastUploadStats;
            long j5 = 0;
            if (uploadStats2 != null) {
                long longValue = getSpeed(uploadStats2.inVideoBytes, uploadStats.inVideoBytes, j4).longValue();
                j2 = getSpeed(this.mLastUploadStats.inAudioBytes, uploadStats.inAudioBytes, j4).longValue();
                j3 = getSpeed(this.mLastUploadStats.outVideoBytes, uploadStats.outVideoBytes, j4).longValue();
                j = getSpeed(this.mLastUploadStats.outAudioBytes, uploadStats.outAudioBytes, j4).longValue();
                j5 = longValue;
            } else {
                j = 0;
                j2 = 0;
                j3 = 0;
            }
            setStatusValue(7001, Long.valueOf(j5));
            setStatusValue(7002, Long.valueOf(j2));
            setStatusValue(7003, Long.valueOf(j3));
            setStatusValue(7004, Long.valueOf(j));
            setStatusValue(7005, Long.valueOf(uploadStats.videoCacheLen));
            setStatusValue(7006, Long.valueOf(uploadStats.audioCacheLen));
            setStatusValue(7007, Long.valueOf(uploadStats.videoDropCount));
            setStatusValue(7008, Long.valueOf(uploadStats.audioDropCount));
            setStatusValue(7009, Long.valueOf(uploadStats.startTS));
            setStatusValue(7010, Long.valueOf(uploadStats.dnsTS));
            setStatusValue(7011, Long.valueOf(uploadStats.connTS));
            setStatusValue(7012, String.valueOf(uploadStats.serverIP));
            setStatusValue(7013, Long.valueOf(this.mQuicChannel ? 2L : 1L));
            setStatusValue(7014, uploadStats.connectionID);
            setStatusValue(7015, uploadStats.connectionStats);
            this.mUploadQualityReport.m1083a(uploadStats.videoDropCount, uploadStats.audioDropCount);
            this.mUploadQualityReport.m1077b(uploadStats.videoCacheLen, uploadStats.audioCacheLen);
        }
        this.mLastTimeStamp = timeTick;
        this.mLastUploadStats = uploadStats;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(103, 2000L);
        }
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

    private boolean isQCloudStreamUrl(String str) {
        int indexOf;
        String substring;
        return (str == null || str.length() == 0 || (indexOf = str.indexOf("://")) == -1 || (substring = str.substring(indexOf + 3)) == null || !substring.startsWith("cloud.tencent.com")) ? false : true;
    }

    private void parseProxyInfo(String str) {
        if (str == null || str.length() == 0 || !str.startsWith("room")) {
            return;
        }
        this.mRtmpProxyParam.f4787i = isQCloudStreamUrl(str);
        HashMap paramsFromUrl = getParamsFromUrl(str);
        if (paramsFromUrl == null) {
            return;
        }
        if (paramsFromUrl.containsKey("sdkappid")) {
            this.mRtmpProxyParam.f4779a = Long.valueOf((String) paramsFromUrl.get("sdkappid")).longValue();
        }
        if (!paramsFromUrl.containsKey("roomid") || !paramsFromUrl.containsKey("userid") || !paramsFromUrl.containsKey("roomsig")) {
            return;
        }
        this.mRtmpProxyParam.f4782d = Long.valueOf((String) paramsFromUrl.get("roomid")).longValue();
        this.mRtmpProxyParam.f4781c = (String) paramsFromUrl.get("userid");
        if (paramsFromUrl.containsKey("bizbuf")) {
            try {
                this.mRtmpProxyParam.f4788j = URLDecoder.decode((String) paramsFromUrl.get("bizbuf"), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(URLDecoder.decode((String) paramsFromUrl.get("roomsig"), "UTF-8"));
            this.mRtmpProxyParam.f4780b = 0L;
            if (!jSONObject.has("Key")) {
                return;
            }
            this.mRtmpProxyParam.f4783e = jSONObject.optString("Key");
            JSONObject optJSONObject = jSONObject.optJSONObject("RtmpProxy");
            if (optJSONObject != null && (!optJSONObject.has("Ip") || !optJSONObject.has("Port") || !optJSONObject.has("Type"))) {
                return;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("AccessList");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    if (jSONObject2 != null && jSONObject2.has("Ip") && jSONObject2.has("Port") && jSONObject2.has("Type")) {
                        String optString = jSONObject2.optString("Ip");
                        long optLong = jSONObject2.optLong("Port");
                        if (jSONObject2.optLong("Type") == 2) {
                            this.mRtmpProxyIPList.add(optString + ":" + optLong);
                        }
                    }
                }
            }
            if (!this.mRtmpProxyParam.f4787i) {
                this.mRtmpUrl = str;
                this.mQuicChannel = false;
            } else if (optJSONObject == null) {
                return;
            } else {
                this.mRtmpUrl = str.substring(0, str.indexOf("?")) + "/webrtc/" + (this.mRtmpProxyParam.f4779a + "_" + this.mRtmpProxyParam.f4782d + "_" + this.mRtmpProxyParam.f4781c) + "?real_rtmp_ip=" + optJSONObject.optString("Ip") + "&real_rtmp_port=" + optJSONObject.optLong("Port") + "&tinyid=" + this.mRtmpProxyParam.f4780b + "&srctinyid=0";
                getNextRtmpProxyIP();
            }
            this.mRtmpProxyEnable = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private HashMap getParamsFromUrl(String str) {
        String[] split;
        HashMap hashMap = new HashMap();
        String[] split2 = str.split("[?]");
        if (split2 != null && split2.length >= 2 && split2[1] != null && split2[1].length() != 0) {
            for (String str2 : split2[1].split("[&]")) {
                if (str2.indexOf(SimpleComparison.EQUAL_TO_OPERATION) != -1) {
                    String[] split3 = str2.split("[=]");
                    if (split3.length == 2) {
                        hashMap.put(split3[0], split3[1]);
                    }
                }
            }
        }
        return hashMap;
    }

    private boolean getNextRtmpProxyIP() {
        C3568a c3568a = this.mRtmpProxyParam;
        c3568a.f4784f = 234L;
        c3568a.f4785g = 80L;
        Vector<String> vector = this.mRtmpProxyIPList;
        if (vector == null || vector.size() <= 0) {
            return false;
        }
        if (this.mRtmpProxyIPIndex >= this.mRtmpProxyIPList.size()) {
            this.mRtmpProxyIPIndex = 0;
            return false;
        }
        String[] split = this.mRtmpUrl.split("://");
        if (split.length < 2) {
            return false;
        }
        String substring = split[1].substring(split[1].indexOf("/"));
        String str = this.mRtmpProxyIPList.get(this.mRtmpProxyIPIndex);
        this.mRtmpProxyParam.f4786h = str;
        this.mRtmpUrl = "room://" + str + substring;
        this.mQuicChannel = true;
        this.mRtmpProxyIPIndex = this.mRtmpProxyIPIndex + 1;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rtmpProxySendHeartBeat() {
        int[] m2894a = TXCSystemUtil.m2894a();
        long j = m2894a[0] / 10;
        long j2 = m2894a[1] / 10;
        long m2904d = TXCStatus.m2904d(getID(), 7004);
        long m2904d2 = TXCStatus.m2904d(getID(), 7003);
        long m2904d3 = TXCStatus.m2904d(getID(), 1001);
        long m2904d4 = TXCStatus.m2904d(getID(), 4001);
        long m2904d5 = TXCStatus.m2904d(getID(), 7006);
        long m2904d6 = TXCStatus.m2904d(getID(), 7005);
        long m2904d7 = TXCStatus.m2904d(getID(), 7008);
        long m2904d8 = TXCStatus.m2904d(getID(), 7007);
        synchronized (this.mRtmpProxyLock) {
            try {
                try {
                    nativeRtmpProxySendHeartBeat(this.mRtmpProxyInstance, j, j2, m2904d, m2904d2, m2904d3, m2904d4, m2904d5, m2904d6, m2904d7, m2904d8);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    private void onSendRtmpProxyMsg(byte[] bArr) {
        synchronized (this.mThreadLock) {
            if (this.mUploaderInstance != 0) {
                nativeSendRtmpProxyMsg(this.mUploaderInstance, bArr);
            }
        }
    }

    private void onRtmpProxyUserListPushed(RtmpProxyUserInfo[] rtmpProxyUserInfoArr) {
        if (rtmpProxyUserInfoArr != null && this.mIsPushing && this.mRtmpProxyEnable && this.mRtmpProxyParam != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < rtmpProxyUserInfoArr.length; i++) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("userid", rtmpProxyUserInfoArr[i].account);
                    jSONObject.put("playurl", rtmpProxyUserInfoArr[i].playUrl);
                    jSONArray.put(i, jSONObject);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("userlist", jSONArray);
                sendNotifyEvent(TXE_UPLOAD_INFO_ROOM_USERLIST, jSONObject2.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onRtmpProxyRoomEvent(int i, int i2) {
        if (i == 1) {
            sendNotifyEvent(TXE_UPLOAD_INFO_ROOM_IN, String.format("已在房间中，[%d]", Integer.valueOf(i2)));
        } else if (i != 2) {
        } else {
            sendNotifyEvent(TXE_UPLOAD_INFO_ROOM_OUT, String.format("不在房间中，[%d]", Integer.valueOf(i2)));
        }
    }
}
