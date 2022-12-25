package com.tencent.liteav.videoencoder;

import android.media.MediaFormat;
import com.tencent.liteav.basic.p111g.TXSNALPacket;

/* renamed from: com.tencent.liteav.videoencoder.d */
/* loaded from: classes3.dex */
public interface TXIVideoEncoderListener {
    void onEncodeFormat(MediaFormat mediaFormat);

    void onEncodeNAL(TXSNALPacket tXSNALPacket, int i);
}
