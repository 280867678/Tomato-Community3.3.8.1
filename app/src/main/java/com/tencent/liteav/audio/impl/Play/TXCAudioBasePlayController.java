package com.tencent.liteav.audio.impl.Play;

import android.content.Context;
import android.os.Environment;
import com.tencent.liteav.audio.TXCAudioPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioPlayListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;

/* loaded from: classes3.dex */
public class TXCAudioBasePlayController implements TXIAudioPlayListener {
    private static final String TAG = "AudioCenter:" + TXCAudioBasePlayController.class.getSimpleName();
    protected Context mContext;
    protected TXIAudioPlayListener mListener;
    protected float mCacheTime = TXCAudioPlayer.f2024b;
    protected boolean mIsAutoAdjustCache = TXCAudioPlayer.f2025c;
    protected float mAutoAdjustMaxCache = TXCAudioPlayer.f2026d;
    protected float mAutoAdjustMinCache = TXCAudioPlayer.f2027e;
    protected boolean mIsRealTimePlay = false;
    protected boolean mIsHWAcceleration = false;
    protected boolean mIsMute = TXCAudioPlayer.f2028f;
    protected int mSmoothMode = 0;
    protected long mJitterBuffer = 0;
    protected volatile boolean mIsPlaying = false;

    public static native byte[] nativeGetMixedTracksData(int i);

    public static native boolean nativeIsTracksEmpty();

    public static native void nativeSetTempPath(String str);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeAddData(long j, byte[] bArr, int i, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native long nativeCreateJitterBuffer(boolean z, TXCAudioBasePlayController tXCAudioBasePlayController);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeDestoryJitterBuffer(long j);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeEnableAutoAdjustCache(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeEnableRealTimePlay(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    public native long nativeGetCacheDuration(long j);

    protected native float nativeGetCacheThreshold(long j);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int nativeGetChannel(long j);

    protected native long nativeGetCurPts(long j);

    protected native TXAudioJitterBufferReportInfo nativeGetLoadingInfo(long j);

    protected native long nativeGetNetRecvTS(long j);

    protected native int nativeGetRecvJitter(long j);

    /* JADX INFO: Access modifiers changed from: protected */
    public native int nativeGetSamplerate(long j);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetAutoAdjustMaxCache(long j, float f);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetAutoAdjustMinCache(long j, float f);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetCacheTime(long j, float f);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetJitterCycle(long j, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetLoadingThreshold(long j, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetMute(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetRTCPlayHungryTimeThreshold(long j, int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetRealtimeJitterCycle(long j, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetSmoothAdjust(long j, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetSmoothMode(long j, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void nativeSetSmoothSpeed(long j, float f);

    static {
        nativeSetTempPath(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public TXCAudioBasePlayController(Context context) {
        this.mContext = context;
    }

    public static void setAudioMode(Context context, int i) {
        TXCAudioSysPlayController.m3429a(context, i);
        TXCAudioTraePlayController.m3426a(context, i);
    }

    public synchronized void setListener(TXIAudioPlayListener tXIAudioPlayListener) {
        this.mListener = tXIAudioPlayListener;
    }

    public void setCacheTime(float f) {
        String str = TAG;
        TXCLog.m2913i(str, "set cache time to " + f);
        nativeSetCacheTime(this.mJitterBuffer, f);
        this.mCacheTime = f;
    }

    public void enableAutojustCache(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "set auto adjust cache to " + z);
        nativeEnableAutoAdjustCache(this.mJitterBuffer, z);
        this.mIsAutoAdjustCache = z;
    }

    public void setAutoAdjustMaxCache(float f) {
        String str = TAG;
        TXCLog.m2913i(str, "set auto adjust max cache to " + f);
        nativeSetAutoAdjustMaxCache(this.mJitterBuffer, f);
        this.mAutoAdjustMaxCache = f;
    }

    public void setAutoAdjustMinCache(float f) {
        String str = TAG;
        TXCLog.m2913i(str, "set auto adjust min cache to " + f);
        nativeSetAutoAdjustMinCache(this.mJitterBuffer, f);
        this.mAutoAdjustMinCache = f;
    }

    public void enableHWAcceleration(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "set hw acceleration to " + z);
        this.mIsHWAcceleration = z;
    }

    public void setSmootheMode(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "set careful mode to " + i);
        this.mSmoothMode = i;
    }

    public long getCacheDuration() {
        return nativeGetCacheDuration(this.mJitterBuffer);
    }

    public void enableRealTimePlay(boolean z) {
        if (z == this.mIsRealTimePlay) {
            return;
        }
        String str = TAG;
        TXCLog.m2913i(str, "set real-time play to " + z);
        nativeEnableRealTimePlay(this.mJitterBuffer, z);
        this.mIsRealTimePlay = z;
    }

    public TXAudioJitterBufferReportInfo getReportInfo() {
        if (this.mIsPlaying) {
            long j = this.mJitterBuffer;
            if (j == 0) {
                return null;
            }
            return nativeGetLoadingInfo(j);
        }
        return null;
    }

    public long getCurPts() {
        return nativeGetCurPts(this.mJitterBuffer);
    }

    public int getRecvJitter() {
        return nativeGetRecvJitter(this.mJitterBuffer);
    }

    public long getCurRecvTS() {
        return nativeGetNetRecvTS(this.mJitterBuffer);
    }

    public float getCacheThreshold() {
        return nativeGetCacheThreshold(this.mJitterBuffer);
    }

    public boolean isPlaying() {
        return this.mIsPlaying;
    }

    public int startPlay() {
        this.mIsPlaying = true;
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    public int playData(TXSAudioPacket tXSAudioPacket) {
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    public int stopPlay() {
        this.mIsPlaying = false;
        this.mCacheTime = TXCAudioPlayer.f2024b;
        this.mIsAutoAdjustCache = TXCAudioPlayer.f2025c;
        this.mAutoAdjustMaxCache = TXCAudioPlayer.f2026d;
        this.mAutoAdjustMinCache = TXCAudioPlayer.f2027e;
        this.mIsRealTimePlay = false;
        this.mIsHWAcceleration = false;
        this.mIsMute = TXCAudioPlayer.f2028f;
        this.mSmoothMode = 0;
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    public void setMute(boolean z) {
        if (z != this.mIsMute) {
            nativeSetMute(this.mJitterBuffer, z);
        }
        String str = TAG;
        TXCLog.m2913i(str, "set mute to " + z);
        this.mIsMute = z;
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public synchronized void onPlayPcmData(byte[] bArr, long j) {
        if (this.mListener != null) {
            this.mListener.onPlayPcmData(bArr, j);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public synchronized void onPlayError(int i, String str) {
        if (this.mListener != null) {
            this.mListener.onPlayError(i, str);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public synchronized void onPlayJitterStateNotify(int i) {
        String str = TAG;
        TXCLog.m2914e(str, "onPlayJitterStateNotify  cur state " + i);
        if (this.mListener != null) {
            this.mListener.onPlayJitterStateNotify(i);
        }
    }

    @Override // com.tencent.liteav.audio.TXIAudioPlayListener
    public synchronized void onPlayAudioInfoChanged(TXSAudioPacket tXSAudioPacket, TXSAudioPacket tXSAudioPacket2) {
        if (this.mListener != null) {
            this.mListener.onPlayAudioInfoChanged(tXSAudioPacket, tXSAudioPacket2);
        }
    }
}
