package com.tencent.liteav.audio.impl.Record;

/* renamed from: com.tencent.liteav.audio.impl.Record.h */
/* loaded from: classes3.dex */
public interface TXIAudioPcmRecordListener {
    void onAudioRecordError(int i, String str);

    void onAudioRecordPCM(byte[] bArr, int i, long j);

    void onAudioRecordStart();

    void onAudioRecordStop();
}
