package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.audio.TXCLiveBGMPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.impl.TXCAudioRouteMgr;
import com.tencent.liteav.audio.impl.TXIHeadsetMgrListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class TXCAudioSysRecordController extends TXCAudioBaseRecordController implements TXIAudioPcmRecordListener, TXIHeadsetMgrListener {
    private static final String TAG = "AudioCenter:" + TXCAudioSysRecordController.class.getSimpleName();
    private long mEffectorObj = 0;
    private long mSoftEncObj = 0;
    private int mRecordSampleRate = TXEAudioTypeDef.f2318e;
    private TXCAudioHWEncoder mHWEcoder = null;
    private byte[] mBuf = null;
    private int mPosition = 0;
    private long mLastPTS = 0;
    private boolean mNeedMix = false;
    private TXCAudioCustomRecord mCustomRecord = null;

    private native void nativeAddEffect(long j, byte[] bArr);

    private native void nativeAddEffectAndSoftEnc(long j, long j2, byte[] bArr);

    private native long nativeCreateEffector(int i, int i2, int i3);

    private native long nativeCreateSoftEncoder(int i, int i2, int i3);

    private native void nativeDestorySoftEncoder(long j);

    private native void nativeDestroyEffector(long j);

    private native void nativeEnableEncoder(long j, boolean z);

    private native void nativeMixBGM(long j, boolean z);

    private native byte[] nativeReadOneEncFrame();

    private native byte[] nativeReadOneFrame(long j, int i);

    private native void nativeSetChangerType(long j, int i, int i2);

    private native void nativeSetInputInfo(long j, int i, int i2, int i3);

    private native void nativeSetNoiseSuppression(long j, int i);

    private native void nativeSetReverbParam(long j, int i, float f);

    private native void nativeSetReverbType(long j, int i);

    private native void nativeSetVolume(long j, float f);

    static {
        TXCSystemUtil.m2873e();
    }

    public TXCAudioSysRecordController() {
        TXCAudioRouteMgr.m3371a().m3362a(this);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public int startRecord(Context context) {
        TXCLog.m2913i(TAG, "startRecord");
        super.startRecord(context);
        if (!this.mIsCustomRecord) {
            if (this.mAECType == TXEAudioDef.TXE_AEC_SYSTEM) {
                if (this.mSampleRate > TXCConfigCenter.m2988a().m2958h()) {
                    this.mRecordSampleRate = TXCConfigCenter.m2988a().m2956i();
                } else {
                    this.mRecordSampleRate = TXCConfigCenter.m2988a().m2958h();
                }
                if (this.mRecordSampleRate == 0) {
                    this.mRecordSampleRate = TXRecordCommon.AUDIO_SAMPLERATE_16000;
                }
            } else {
                this.mRecordSampleRate = this.mSampleRate;
            }
            TXCAudioSysRecord.m3382a().m3379a(this);
            TXCAudioSysRecord.m3382a().m3380a(this.mContext, this.mRecordSampleRate, this.mChannels, this.mBits, this.mAECType);
        } else {
            setNoiseSuppression(-1);
            this.mRecordSampleRate = this.mSampleRate;
            this.mCustomRecord = new TXCAudioCustomRecord();
            this.mCustomRecord.m3402a(this);
            this.mCustomRecord.mo3399a(this.mContext, this.mRecordSampleRate, this.mChannels, this.mBits);
        }
        WeakReference<TXIAudioRecordListener> weakReference = this.mWeakRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return 0;
        }
        String str = this.mAECType == TXEAudioDef.TXE_AEC_SYSTEM ? "SYSTEM-AEC," : "NO-AEC,";
        this.mWeakRecordListener.get().onRecordError(TXEAudioDef.TXE_AUDIO_NOTIFY_AUDIO_INFO, str + "采样率(" + this.mRecordSampleRate + "|" + this.mSampleRate + "),声道数" + this.mChannels);
        return 0;
    }

    private String getProperty(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
        } catch (Exception unused) {
            return "unknown";
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public int stopRecord() {
        TXCLog.m2913i(TAG, "stopRecord");
        TXCAudioCustomRecord tXCAudioCustomRecord = this.mCustomRecord;
        if (tXCAudioCustomRecord != null) {
            tXCAudioCustomRecord.m3397c();
            this.mCustomRecord = null;
        }
        TXCAudioSysRecord.m3382a().m3377b();
        TXCAudioRouteMgr.m3371a().m3355b(this);
        this.mNeedMix = false;
        return 0;
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void sendCustomPCMData(byte[] bArr) {
        TXCAudioCustomRecord tXCAudioCustomRecord = this.mCustomRecord;
        if (tXCAudioCustomRecord != null) {
            tXCAudioCustomRecord.m3398a(bArr);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public boolean isRecording() {
        TXCAudioCustomRecord tXCAudioCustomRecord = this.mCustomRecord;
        if (tXCAudioCustomRecord != null) {
            return tXCAudioCustomRecord.m3396d();
        }
        return TXCAudioSysRecord.m3382a().m3376c();
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public synchronized void setEarphoneOn(boolean z) {
        super.setEarphoneOn(z);
        if (this.mEffectorObj > 0) {
            if (!z && this.mAECType != TXEAudioDef.TXE_AEC_SYSTEM) {
                this.mNeedMix = false;
                nativeMixBGM(this.mEffectorObj, this.mNeedMix);
            }
            this.mNeedMix = true;
            nativeMixBGM(this.mEffectorObj, this.mNeedMix);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public synchronized void setReverbType(int i) {
        super.setReverbType(i);
        if (this.mEffectorObj > 0) {
            nativeSetReverbType(this.mEffectorObj, i);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public synchronized void setNoiseSuppression(int i) {
        super.setNoiseSuppression(i);
        if (this.mEffectorObj > 0) {
            nativeSetNoiseSuppression(this.mEffectorObj, i);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setVolume(float f) {
        super.setVolume(f);
        long j = this.mEffectorObj;
        if (j != 0) {
            nativeSetVolume(j, f);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public synchronized void setReverbParam(int i, float f) {
        super.setReverbParam(i, f);
        if (this.mEffectorObj != 0) {
            nativeSetReverbParam(this.mEffectorObj, i, f);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setChangerType(int i, int i2) {
        super.setChangerType(i, i2);
        long j = this.mEffectorObj;
        if (j != 0) {
            nativeSetChangerType(j, i, i2);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public synchronized void onAudioRecordStart() {
        TXCLog.m2913i(TAG, "sys audio record start");
        uninitEffectAndEnc();
        initEffectAndEnc();
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public synchronized void onAudioRecordStop() {
        TXCLog.m2913i(TAG, "sys audio record stop");
        TXCAudioSysRecord.m3382a().m3379a(null);
        uninitEffectAndEnc();
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public synchronized void onAudioRecordError(int i, String str) {
        TXIAudioRecordListener tXIAudioRecordListener;
        String str2 = TAG;
        TXCLog.m2914e(str2, "sys audio record error: " + i + ", " + str);
        TXCAudioSysRecord.m3382a().m3379a(null);
        uninitEffectAndEnc();
        if (this.mWeakRecordListener != null && (tXIAudioRecordListener = this.mWeakRecordListener.get()) != null) {
            tXIAudioRecordListener.onRecordError(i, str);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public synchronized void onAudioRecordPCM(byte[] bArr, int i, long j) {
        byte[] nativeReadOneFrame;
        if (this.mEffectorObj > 0) {
            if (this.mIsMute) {
                Arrays.fill(bArr, (byte) 0);
            }
            long j2 = this.mLastPTS >= j ? this.mLastPTS + 2 : j;
            if (this.mHWEcoder != null) {
                nativeAddEffect(this.mEffectorObj, bArr);
                do {
                    nativeReadOneFrame = nativeReadOneFrame(this.mEffectorObj, ((this.mChannels * 1024) * this.mBits) / 8);
                    if (nativeReadOneFrame != null) {
                        this.mLastPTS = j2;
                        if (this.mHWEcoder != null) {
                            doHWEncode(nativeReadOneFrame, j2);
                        } else {
                            TXIAudioRecordListener listener = getListener();
                            if (listener != null) {
                                listener.onRecordEncData(nativeReadOneFrame, j2, this.mSampleRate, this.mChannels, this.mBits);
                            }
                        }
                    }
                    j2 += 1024000 / this.mSampleRate;
                } while (nativeReadOneFrame != null);
            } else {
                nativeAddEffectAndSoftEnc(this.mEffectorObj, this.mSoftEncObj, bArr);
                while (true) {
                    byte[] nativeReadOneEncFrame = nativeReadOneEncFrame();
                    if (nativeReadOneEncFrame == null) {
                        break;
                    }
                    this.mLastPTS = j2;
                    TXIAudioRecordListener listener2 = getListener();
                    if (listener2 != null) {
                        listener2.onRecordEncData(nativeReadOneEncFrame, j2, this.mSampleRate, this.mChannels, this.mBits);
                    }
                    j2 += 1024000 / this.mSampleRate;
                }
            }
        } else {
            TXCLog.m2914e(TAG, "effectorObj is null");
        }
    }

    private void initEffectAndEnc() {
        this.mEffectorObj = nativeCreateEffector(this.mSampleRate, this.mChannels, this.mBits);
        int i = this.mSampleRate;
        int i2 = this.mRecordSampleRate;
        if (i != i2) {
            nativeSetInputInfo(this.mEffectorObj, i2, this.mChannels, this.mBits);
        }
        boolean z = this.mIsEarphoneOn || this.mAECType == TXEAudioDef.TXE_AEC_SYSTEM;
        nativeSetReverbType(this.mEffectorObj, this.mReverbType);
        nativeMixBGM(this.mEffectorObj, z);
        nativeSetNoiseSuppression(this.mEffectorObj, this.mNSMode);
        nativeSetChangerType(this.mEffectorObj, this.mVoiceKind, this.mVoiceEnvironment);
        if (this.mEnableHWEncoder) {
            this.mHWEcoder = new TXCAudioHWEncoder();
            TXIAudioRecordListener listener = getListener();
            this.mHWEcoder.m3392a(TXEAudioDef.TXE_AUDIO_TYPE_AAC, this.mSampleRate, this.mChannels, this.mBits, listener != null ? new WeakReference<>(listener) : null);
            this.mBuf = new byte[((this.mChannels * 1024) * this.mBits) / 8];
            this.mPosition = 0;
        } else {
            this.mSoftEncObj = nativeCreateSoftEncoder(this.mSampleRate, this.mChannels, this.mBits);
        }
        this.mLastPTS = 0L;
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("初始化直播录制:录制模式 = ");
        sb.append(this.mCustomRecord == null ? "麦克风录制" : "用户自定义录制");
        sb.append(", 录制采样率 = ");
        sb.append(this.mRecordSampleRate);
        sb.append(", 输出采样率 = ");
        sb.append(this.mSampleRate);
        sb.append(", 是否混音 = ");
        sb.append(z);
        sb.append(", 混响模式 = ");
        sb.append(this.mReverbType);
        sb.append(", 是否启动硬编码 = ");
        sb.append(this.mEnableHWEncoder);
        sb.append(", 噪声抑制mode = ");
        sb.append(this.mNSMode);
        TXCLog.m2913i(str, sb.toString());
    }

    private void uninitEffectAndEnc() {
        long j = this.mEffectorObj;
        if (j != 0) {
            nativeDestroyEffector(j);
            this.mEffectorObj = 0L;
        }
        long j2 = this.mSoftEncObj;
        if (j2 != 0) {
            nativeDestorySoftEncoder(j2);
            this.mSoftEncObj = 0L;
        }
        TXCAudioHWEncoder tXCAudioHWEncoder = this.mHWEcoder;
        if (tXCAudioHWEncoder != null) {
            tXCAudioHWEncoder.m3393a();
            this.mHWEcoder = null;
        }
    }

    private void doHWEncode(byte[] bArr, long j) {
        byte[] bArr2;
        if (bArr == null || bArr.length == 0 || (bArr2 = this.mBuf) == null || this.mHWEcoder == null) {
            TXCLog.m2914e(TAG, "doHWEncode failed! data = " + bArr + ", buf = " + this.mBuf + ", encoder = " + this.mHWEcoder);
            return;
        }
        int length = bArr2.length - this.mPosition;
        if (length > bArr.length) {
            length = bArr.length;
        }
        System.arraycopy(bArr, 0, this.mBuf, this.mPosition, length);
        this.mPosition += length;
        int i = this.mPosition;
        byte[] bArr3 = this.mBuf;
        if (i != bArr3.length) {
            return;
        }
        this.mPosition = 0;
        this.mHWEcoder.m3389a(bArr3, j);
    }

    private void onRecordRawPcmData(byte[] bArr, int i, int i2) {
        TXIAudioRecordListener listener = getListener();
        if (listener != null) {
            listener.onRecordRawPcmData(bArr, TXCTimeUtil.getTimeTick(), i, i2, this.mBits, TXCLiveBGMPlayer.getInstance().isRunning() && !this.mNeedMix);
        }
    }

    private void onRecordPcmData(byte[] bArr) {
        TXIAudioRecordListener listener = getListener();
        if (listener != null) {
            listener.onRecordPcmData(bArr, TXCTimeUtil.getTimeTick(), this.mSampleRate, this.mChannels, this.mBits);
        }
    }

    @Override // com.tencent.liteav.audio.impl.TXIHeadsetMgrListener
    public void OnHeadsetState(boolean z) {
        setEarphoneOn(z);
    }
}
