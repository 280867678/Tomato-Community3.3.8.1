package com.tencent.liteav.p120e;

import android.media.MediaCodec;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.e.r */
/* loaded from: classes3.dex */
public interface TXIAudioEncoderListener {
    /* renamed from: a */
    void mo1530a();

    /* renamed from: a */
    void mo1529a(MediaFormat mediaFormat);

    /* renamed from: a */
    void mo1528a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo);
}
