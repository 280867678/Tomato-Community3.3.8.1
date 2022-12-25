package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.impl.Record.c */
/* loaded from: classes3.dex */
public abstract class TXCAudioBaseRecordController {
    private static final String TAG = "AudioCenter:" + TXCAudioBaseRecordController.class.getSimpleName();
    protected Context mContext;
    protected WeakReference<TXIAudioRecordListener> mWeakRecordListener;
    protected int mSampleRate = TXEAudioTypeDef.f2318e;
    protected int mChannels = TXEAudioTypeDef.f2319f;
    protected int mBits = TXEAudioTypeDef.f2321h;
    protected int mReverbType = TXEAudioDef.TXE_REVERB_TYPE_0;
    protected boolean mIsMute = false;
    protected int mAECType = TXEAudioDef.TXE_AEC_NONE;
    protected boolean mEnableHWEncoder = false;
    protected boolean mIsEarphoneOn = false;
    protected boolean mIsCustomRecord = false;
    protected float mVolume = 1.0f;
    protected int mNSMode = -1;
    protected int mVoiceKind = -1;
    protected int mVoiceEnvironment = -1;

    public abstract boolean isRecording();

    public abstract void sendCustomPCMData(byte[] bArr);

    public void setReverbParam(int i, float f) {
    }

    public abstract int stopRecord();

    public synchronized void setListener(TXIAudioRecordListener tXIAudioRecordListener) {
        String str = TAG;
        TXCLog.m2913i(str, "setListener:" + tXIAudioRecordListener);
        if (tXIAudioRecordListener == null) {
            this.mWeakRecordListener = null;
        } else {
            this.mWeakRecordListener = new WeakReference<>(tXIAudioRecordListener);
        }
    }

    public synchronized TXIAudioRecordListener getListener() {
        if (this.mWeakRecordListener != null) {
            return this.mWeakRecordListener.get();
        }
        return null;
    }

    public void setSamplerate(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "setSampleRate: " + i);
        this.mSampleRate = i;
    }

    public void setChannels(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "setChannels: " + i);
        this.mChannels = i;
    }

    public void setReverbType(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "setReverbType: " + i);
        this.mReverbType = i;
    }

    public void setAECType(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "setAECType: " + i);
        this.mAECType = i;
    }

    public void enableHWEncoder(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "enableHWEncoder: " + z);
        this.mEnableHWEncoder = z;
    }

    public void setMute(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "setMute: " + z);
        this.mIsMute = z;
    }

    public void setVolume(float f) {
        if (f <= 0.2f) {
            String str = TAG;
            TXCLog.m2911w(str, "setVolume, warning volume too low : " + f);
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mVolume = f;
    }

    public void setEarphoneOn(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "setEarphoneOn: " + z);
        this.mIsEarphoneOn = z;
    }

    public void setNoiseSuppression(int i) {
        String str = TAG;
        TXCLog.m2913i(str, "setNoiseSuppression: " + i);
        this.mNSMode = i;
    }

    public void setChangerType(int i, int i2) {
        String str = TAG;
        TXCLog.m2913i(str, "setChangerType: " + i + ConstantUtils.PLACEHOLDER_STR_ONE + i2);
        this.mVoiceKind = i;
        this.mVoiceEnvironment = i2;
    }

    public int startRecord(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
            return 0;
        }
        return 0;
    }

    public void setIsCustomRecord(boolean z) {
        String str = TAG;
        TXCLog.m2913i(str, "setIsCustomRecord: " + z);
        this.mIsCustomRecord = z;
    }
}
