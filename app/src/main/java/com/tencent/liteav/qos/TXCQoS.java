package com.tencent.liteav.qos;

import android.os.Bundle;
import android.os.Handler;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class TXCQoS {
    public static final int AUTO_ADJUST_LIVEPUSH_RESOLUTION_STRATEGY = 1;
    public static final int AUTO_ADJUST_REALTIME_VIDEOCHAT_STRATEGY = 5;
    static final String TAG = "TXCQos";
    private long mInstance;
    private TXIQoSListener mListener;
    private TXINotifyListener mNotifyListener;
    private long mInterval = 1000;
    private long mUserID = 0;
    private boolean mIsEnableDrop = false;
    private int mBitrate = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mAutoStrategy = -1;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() { // from class: com.tencent.liteav.qos.TXCQoS.1
        @Override // java.lang.Runnable
        public void run() {
            if (TXCQoS.this.mListener != null) {
                int onGetEncoderRealBitrate = TXCQoS.this.mListener.onGetEncoderRealBitrate();
                int onGetQueueInputSize = TXCQoS.this.mListener.onGetQueueInputSize();
                int onGetQueueOutputSize = TXCQoS.this.mListener.onGetQueueOutputSize();
                int onGetVideoQueueMaxCount = TXCQoS.this.mListener.onGetVideoQueueMaxCount();
                int onGetVideoQueueCurrentCount = TXCQoS.this.mListener.onGetVideoQueueCurrentCount();
                int onGetVideoDropCount = TXCQoS.this.mListener.onGetVideoDropCount();
                TXCQoS tXCQoS = TXCQoS.this;
                tXCQoS.nativeSetVideoRealBitrate(tXCQoS.mInstance, onGetEncoderRealBitrate);
                TXCQoS tXCQoS2 = TXCQoS.this;
                tXCQoS2.nativeAdjustBitrate(tXCQoS2.mInstance, onGetVideoQueueMaxCount, onGetVideoQueueCurrentCount, onGetVideoDropCount, onGetQueueOutputSize, onGetQueueInputSize);
                TXCQoS tXCQoS3 = TXCQoS.this;
                boolean nativeIsEnableDrop = tXCQoS3.nativeIsEnableDrop(tXCQoS3.mInstance);
                if (TXCQoS.this.mIsEnableDrop != nativeIsEnableDrop) {
                    TXCQoS.this.mIsEnableDrop = nativeIsEnableDrop;
                    TXCQoS.this.mListener.onEnableDropStatusChanged(nativeIsEnableDrop);
                }
                TXCQoS tXCQoS4 = TXCQoS.this;
                int nativeGetBitrate = tXCQoS4.nativeGetBitrate(tXCQoS4.mInstance);
                TXCQoS tXCQoS5 = TXCQoS.this;
                int nativeGetWidth = tXCQoS5.nativeGetWidth(tXCQoS5.mInstance);
                TXCQoS tXCQoS6 = TXCQoS.this;
                int nativeGetHeight = tXCQoS6.nativeGetHeight(tXCQoS6.mInstance);
                if (nativeGetWidth != TXCQoS.this.mWidth || nativeGetHeight != TXCQoS.this.mHeight) {
                    if (TXCQoS.this.mAutoStrategy == 1 || TXCQoS.this.mAutoStrategy == 5) {
                        TXCQoS.this.mListener.onEncoderParamsChanged(nativeGetBitrate, nativeGetWidth, nativeGetHeight);
                        if (TXCQoS.this.mNotifyListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("EVT_MSG", "调整分辨率:new bitrate:" + nativeGetBitrate + " new resolution:" + nativeGetWidth + Marker.ANY_MARKER + nativeGetHeight);
                            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                            TXCQoS.this.mNotifyListener.onNotifyEvent(1005, bundle);
                        }
                    }
                } else if (nativeGetBitrate != TXCQoS.this.mBitrate) {
                    TXCQoS.this.mListener.onEncoderParamsChanged(nativeGetBitrate, 0, 0);
                    if (TXCQoS.this.mNotifyListener != null) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putCharSequence("EVT_MSG", "调整编码码率:new bitrate:" + nativeGetBitrate);
                        bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                        bundle2.putLong(TXCAVRoomConstants.EVT_USERID, TXCQoS.this.mUserID);
                        TXCQoS.this.mNotifyListener.onNotifyEvent(1006, bundle2);
                    }
                }
                TXCQoS.this.mBitrate = nativeGetBitrate;
                TXCQoS.this.mWidth = nativeGetWidth;
                TXCQoS.this.mHeight = nativeGetHeight;
            }
            TXCQoS.this.mHandler.postDelayed(this, TXCQoS.this.mInterval);
        }
    };

    private native void nativeAddQueueInputSize(long j, int i);

    private native void nativeAddQueueOutputSize(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeAdjustBitrate(long j, int i, int i2, int i3, int i4, int i5);

    private native void nativeDeinit(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetBitrate(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetHeight(long j);

    public static native int nativeGetProperResolutionByVideoBitrate(boolean z, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeGetWidth(long j);

    private native long nativeInit(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeIsEnableDrop(long j);

    private native void nativeReset(long j, boolean z);

    private native void nativeSetAutoAdjustBitrate(long j, boolean z);

    private native void nativeSetAutoAdjustStrategy(long j, int i);

    private native void nativeSetHasVideo(long j, boolean z);

    private native void nativeSetVideoDefaultResolution(long j, int i);

    private native void nativeSetVideoEncBitrate(long j, int i, int i2, int i3);

    private native void nativeSetVideoExpectBitrate(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetVideoRealBitrate(long j, int i);

    public TXCQoS(boolean z) {
        this.mInstance = nativeInit(z);
    }

    protected void finalize() throws Throwable {
        try {
            nativeDeinit(this.mInstance);
        } finally {
            super.finalize();
        }
    }

    public void start(long j) {
        this.mInterval = j;
        this.mHandler.postDelayed(this.mRunnable, this.mInterval);
    }

    public void stop() {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mAutoStrategy = -1;
    }

    public long getUserID() {
        return this.mUserID;
    }

    public void setUserID(long j) {
        this.mUserID = j;
    }

    public void setNotifyListener(TXINotifyListener tXINotifyListener) {
        this.mNotifyListener = tXINotifyListener;
    }

    public void setListener(TXIQoSListener tXIQoSListener) {
        this.mListener = tXIQoSListener;
    }

    public void reset(boolean z) {
        nativeReset(this.mInstance, z);
    }

    public boolean isEnableDrop() {
        return nativeIsEnableDrop(this.mInstance);
    }

    public void setHasVideo(boolean z) {
        nativeSetHasVideo(this.mInstance, z);
    }

    public void setAutoAdjustBitrate(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("autoAdjustBitrate is ");
        sb.append(z ? "yes" : "no");
        TXCLog.m2915d(TAG, sb.toString());
        nativeSetAutoAdjustBitrate(this.mInstance, z);
    }

    public void setAutoAdjustStrategy(int i) {
        TXCLog.m2915d(TAG, "autoAdjustStrategy is " + i);
        nativeSetAutoAdjustStrategy(this.mInstance, i);
        this.mAutoStrategy = i;
    }

    public void setDefaultVideoResolution(int i) {
        TXCLog.m2915d(TAG, "DefaultVideoResolution is " + i);
        this.mWidth = 0;
        this.mHeight = 0;
        nativeSetVideoDefaultResolution(this.mInstance, i);
    }

    public void setVideoEncBitrate(int i, int i2, int i3) {
        this.mBitrate = 0;
        nativeSetVideoEncBitrate(this.mInstance, i, i2, i3);
    }

    public void setVideoExpectBitrate(int i) {
        nativeSetVideoExpectBitrate(this.mInstance, i);
    }

    static {
        TXCSystemUtil.m2873e();
    }
}
