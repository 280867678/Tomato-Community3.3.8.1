package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.audio.impl.TXCTraeJNI;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.impl.Record.g */
/* loaded from: classes3.dex */
public class TXCAudioTraeRecordController extends TXCAudioBaseRecordController {
    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void sendCustomPCMData(byte[] bArr) {
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public int startRecord(Context context) {
        TXCLog.m2913i("AudioCenter:TXCAudioTraeRecordController", "trae startRecord");
        super.startRecord(context);
        TXCTraeJNI.InitTraeEngineLibrary(this.mContext);
        TXCTraeJNI.setTraeRecordListener(this.mWeakRecordListener);
        TXCTraeJNI.nativeTraeStartRecord(context, this.mSampleRate, this.mChannels, this.mBits);
        TXCTraeJNI.nativeTraeSetChangerType(this.mVoiceKind, this.mVoiceEnvironment);
        WeakReference<TXIAudioRecordListener> weakReference = this.mWeakRecordListener;
        if (weakReference == null || weakReference.get() == null) {
            return 0;
        }
        this.mWeakRecordListener.get().onRecordError(TXEAudioDef.TXE_AUDIO_NOTIFY_AUDIO_INFO, "TRAE-AEC,采样率(" + this.mSampleRate + "|" + this.mSampleRate + "),声道数" + this.mChannels);
        return 0;
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public int stopRecord() {
        TXCLog.m2913i("AudioCenter:TXCAudioTraeRecordController", "trae stopRecord");
        TXCTraeJNI.nativeTraeStopRecord(true);
        TXCTraeJNI.setTraeRecordListener(null);
        return 0;
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public boolean isRecording() {
        return TXCTraeJNI.nativeTraeIsRecording();
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setReverbType(int i) {
        super.setReverbType(i);
        TXCTraeJNI.nativeTraeSetRecordReverb(i);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setChangerType(int i, int i2) {
        super.setChangerType(i, i2);
        TXCTraeJNI.nativeTraeSetChangerType(i, i2);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setVolume(float f) {
        super.setVolume(f);
        TXCTraeJNI.nativeTraeSetVolume(f);
    }

    @Override // com.tencent.liteav.audio.impl.Record.TXCAudioBaseRecordController
    public void setMute(boolean z) {
        super.setMute(z);
        TXCTraeJNI.nativeTraeSetRecordMute(z);
        this.mIsMute = z;
    }
}
