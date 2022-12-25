package com.tencent.liteav.audio;

import com.tencent.liteav.basic.p111g.TXSAudioPacket;

/* renamed from: com.tencent.liteav.audio.c */
/* loaded from: classes3.dex */
public interface TXIAudioPlayListener {
    void onPlayAudioInfoChanged(TXSAudioPacket tXSAudioPacket, TXSAudioPacket tXSAudioPacket2);

    void onPlayError(int i, String str);

    void onPlayJitterStateNotify(int i);

    void onPlayPcmData(byte[] bArr, long j);
}
