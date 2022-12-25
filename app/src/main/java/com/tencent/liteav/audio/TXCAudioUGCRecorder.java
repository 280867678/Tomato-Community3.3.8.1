package com.tencent.liteav.audio;

import android.content.Context;
import com.tencent.liteav.audio.impl.Record.TXCAudioBGMRecord;
import com.tencent.liteav.audio.impl.Record.TXCAudioSysRecord;
import com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class TXCAudioUGCRecorder implements TXIAudioPcmRecordListener {
    private static final String TAG = "AudioCenter:TXCAudioUGCRecorder";
    static TXCAudioUGCRecorder instance = new TXCAudioUGCRecorder();
    protected Context mContext;
    private WeakReference<TXIAudioRecordListener> mWeakRecordListener;
    protected int mSampleRate = TXEAudioTypeDef.f2318e;
    protected int mChannels = TXEAudioTypeDef.f2319f;
    protected int mBits = TXEAudioTypeDef.f2321h;
    protected int mReverbType = TXEAudioDef.TXE_REVERB_TYPE_0;
    protected boolean mIsMute = false;
    protected int mAECType = TXEAudioDef.TXE_AEC_NONE;
    protected boolean mIsEarphoneOn = false;
    private long mEffectorObj = 0;
    private long mLastPTS = 0;
    private float mVolume = 1.0f;
    private TXCAudioBGMRecord mBGMRecorder = null;
    private boolean mEnableBGMRecord = false;
    private boolean mCurBGMRecordFlag = false;
    private float mSpeedRate = 1.0f;
    private boolean mIsPause = false;
    private int mVoiceKind = -1;
    private int mVoiceEnvironment = -1;

    private native void nativeClearCache(long j);

    private native long nativeCreateEffector(int i, int i2, int i3);

    private native void nativeDestroyEffector(long j);

    private native void nativeEnableEncoder(long j, boolean z);

    private native long nativeGetPcmCacheLen(long j);

    private native void nativeMixBGM(long j, boolean z);

    private native void nativeProcess(long j, byte[] bArr, int i);

    private native byte[] nativeReadOneFrame(long j);

    private native void nativeSetChangerType(long j, int i, int i2);

    private native void nativeSetReverbType(long j, int i);

    private native void nativeSetSpeedRate(long j, float f);

    private native void nativeSetVolume(long j, float f);

    public void sendCustomPCMData(byte[] bArr) {
    }

    static {
        TXCSystemUtil.m2873e();
    }

    public static TXCAudioUGCRecorder getInstance() {
        return instance;
    }

    private TXCAudioUGCRecorder() {
    }

    public int startRecord(Context context) {
        TXCLog.m2913i(TAG, "startRecord");
        if (isRecording()) {
            if (this.mEnableBGMRecord == this.mCurBGMRecordFlag) {
                TXCLog.m2914e(TAG, "startRecord failed! recorder is still running!");
                return -1;
            }
            TXCLog.m2914e(TAG, "recorder is still running. will restart record! bgm record flag = " + this.mEnableBGMRecord);
            stopRecord();
            enableBGMRecord(this.mCurBGMRecordFlag ^ true);
        }
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        initEffector();
        if (this.mEnableBGMRecord) {
            TXCLog.m2913i(TAG, "录制BGM");
            this.mCurBGMRecordFlag = true;
            this.mBGMRecorder = new TXCAudioBGMRecord();
            this.mBGMRecorder.m3408a(this);
            setEarphoneOn(true);
            this.mBGMRecorder.m3409a(this.mContext, this.mSampleRate, this.mChannels, this.mBits);
        } else {
            TXCLog.m2913i(TAG, "录制人声");
            this.mCurBGMRecordFlag = false;
            setEarphoneOn(false);
            TXCAudioSysRecord.m3382a().m3379a(this);
            TXCAudioSysRecord.m3382a().m3380a(this.mContext, this.mSampleRate, this.mChannels, this.mBits, this.mAECType);
        }
        return 0;
    }

    public int stopRecord() {
        TXCLog.m2913i(TAG, "stopRecord");
        TXCAudioBGMRecord tXCAudioBGMRecord = this.mBGMRecorder;
        if (tXCAudioBGMRecord != null) {
            tXCAudioBGMRecord.m3410a();
            this.mBGMRecorder = null;
        }
        TXCAudioSysRecord.m3382a().m3377b();
        enableBGMRecord(false);
        this.mIsPause = false;
        uninitEffector();
        return 0;
    }

    public void pause() {
        TXCLog.m2913i(TAG, "pause");
        this.mIsPause = true;
        if (this.mEnableBGMRecord || !isRecording()) {
            return;
        }
        TXCLog.m2913i(TAG, "停止系统录音");
        TXCAudioSysRecord.m3382a().m3377b();
    }

    public void resume() {
        TXCLog.m2913i(TAG, "resume");
        this.mIsPause = false;
        if (this.mEnableBGMRecord || isRecording()) {
            return;
        }
        TXCLog.m2913i(TAG, "恢复系统录音");
        this.mCurBGMRecordFlag = false;
        setEarphoneOn(false);
        TXCAudioSysRecord.m3382a().m3379a(this);
        TXCAudioSysRecord.m3382a().m3380a(this.mContext, this.mSampleRate, this.mChannels, this.mBits, this.mAECType);
    }

    public synchronized void setListener(TXIAudioRecordListener tXIAudioRecordListener) {
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

    public void setChannels(int i) {
        TXCLog.m2913i(TAG, "setChannels: " + i);
        this.mChannels = i;
    }

    public int getChannels() {
        return this.mChannels;
    }

    public void setSampleRate(int i) {
        TXCLog.m2913i(TAG, "setSampleRate: " + i);
        this.mSampleRate = i;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public synchronized void setReverbType(int i) {
        TXCLog.m2913i(TAG, "setReverbType: " + i);
        this.mReverbType = i;
        if (this.mEffectorObj > 0) {
            nativeSetReverbType(this.mEffectorObj, i);
        }
    }

    public void setAECType(int i, Context context) {
        TXCLog.m2913i(TAG, "setAECType: " + i);
        this.mAECType = i;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public void setMute(boolean z) {
        TXCLog.m2913i(TAG, "setMute: " + z);
        this.mIsMute = z;
    }

    public void enableBGMRecord(boolean z) {
        TXCLog.m2913i(TAG, "enableBGMRecord: " + z);
        this.mEnableBGMRecord = z;
    }

    public synchronized void setEarphoneOn(boolean z) {
        TXCLog.m2913i(TAG, "setEarphoneOn: " + z);
        this.mIsEarphoneOn = z;
        if (this.mEffectorObj > 0) {
            boolean z2 = false;
            if (z || this.mAECType == TXEAudioDef.TXE_AEC_SYSTEM) {
                z2 = true;
            }
            nativeMixBGM(this.mEffectorObj, z2);
        }
    }

    public boolean isRecording() {
        TXCAudioBGMRecord tXCAudioBGMRecord = this.mBGMRecorder;
        if (tXCAudioBGMRecord != null) {
            return tXCAudioBGMRecord.m3406b();
        }
        return TXCAudioSysRecord.m3382a().m3376c();
    }

    public synchronized long getPcmCacheLen() {
        if (this.mEffectorObj <= 0) {
            return 0L;
        }
        return nativeGetPcmCacheLen(this.mEffectorObj);
    }

    public synchronized void setVolume(float f) {
        TXCLog.m2913i(TAG, "setVolume: " + f);
        this.mVolume = f;
        if (this.mEffectorObj <= 0) {
            return;
        }
        nativeSetVolume(this.mEffectorObj, f);
    }

    public synchronized void setSpeedRate(float f) {
        TXCLog.m2913i(TAG, "setSpeedRate: " + f);
        this.mSpeedRate = f;
        if (this.mEffectorObj <= 0) {
            return;
        }
        nativeSetSpeedRate(this.mEffectorObj, this.mSpeedRate);
    }

    public synchronized void clearCache() {
        TXCLog.m2913i(TAG, "clearCache");
        if (this.mEffectorObj <= 0) {
            return;
        }
        nativeClearCache(this.mEffectorObj);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public void onAudioRecordStart() {
        TXCLog.m2913i(TAG, "sys audio record start");
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public void onAudioRecordStop() {
        TXCLog.m2913i(TAG, "sys audio record stop");
        TXCAudioSysRecord.m3382a().m3379a(null);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public void onAudioRecordError(int i, String str) {
        TXCLog.m2914e(TAG, "sys audio record error: " + i + ", " + str);
        TXCAudioSysRecord.m3382a().m3379a(null);
        TXIAudioRecordListener listener = getListener();
        if (listener != null) {
            listener.onRecordError(i, str);
        }
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXIAudioPcmRecordListener
    public void onAudioRecordPCM(byte[] bArr, int i, long j) {
        byte[] nativeReadOneFrame;
        if (this.mIsPause) {
            return;
        }
        if (this.mEffectorObj > 0) {
            if (this.mIsMute) {
                Arrays.fill(bArr, (byte) 0);
            }
            long j2 = this.mLastPTS;
            if (j2 >= j) {
                j = 2 + j2;
            }
            synchronized (this) {
                nativeProcess(this.mEffectorObj, bArr, i);
            }
            do {
                synchronized (this) {
                    nativeReadOneFrame = nativeReadOneFrame(this.mEffectorObj);
                }
                if (nativeReadOneFrame != null) {
                    this.mLastPTS = j;
                    TXIAudioRecordListener listener = getListener();
                    if (listener != null) {
                        listener.onRecordEncData(nativeReadOneFrame, j, this.mSampleRate, this.mChannels, this.mBits);
                    }
                }
                synchronized (this) {
                    j += (1024000.0f / this.mSampleRate) * this.mSpeedRate;
                }
            } while (nativeReadOneFrame != null);
            return;
        }
        TXCLog.m2914e(TAG, "effectorObj is null");
    }

    private synchronized void initEffector() {
        uninitEffector();
        this.mEffectorObj = nativeCreateEffector(this.mSampleRate, this.mChannels, this.mBits);
        boolean z = false;
        if (this.mIsEarphoneOn || this.mAECType == TXEAudioDef.TXE_AEC_SYSTEM) {
            z = true;
        }
        nativeSetReverbType(this.mEffectorObj, this.mReverbType);
        nativeSetChangerType(this.mEffectorObj, this.mVoiceKind, this.mVoiceEnvironment);
        nativeMixBGM(this.mEffectorObj, z);
        nativeSetVolume(this.mEffectorObj, this.mVolume);
        nativeSetSpeedRate(this.mEffectorObj, this.mSpeedRate);
        nativeEnableEncoder(this.mEffectorObj, true);
        this.mLastPTS = 0L;
    }

    private synchronized void uninitEffector() {
        if (this.mEffectorObj > 0) {
            nativeDestroyEffector(this.mEffectorObj);
            this.mEffectorObj = 0L;
        }
    }

    public synchronized void setChangerType(int i, int i2) {
        TXCLog.m2913i(TAG, "setChangerType: " + i + ConstantUtils.PLACEHOLDER_STR_ONE + i2);
        this.mVoiceKind = i;
        this.mVoiceEnvironment = i2;
        if (this.mEffectorObj <= 0) {
            return;
        }
        nativeSetChangerType(this.mEffectorObj, i, i2);
    }
}
