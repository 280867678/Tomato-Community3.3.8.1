package com.tencent.liteav.network;

import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;

/* renamed from: com.tencent.liteav.network.h */
/* loaded from: classes3.dex */
public interface TXIStreamDownloaderListener {
    void onPullAudio(TXSAudioPacket tXSAudioPacket);

    void onPullNAL(TXSNALPacket tXSNALPacket);
}
