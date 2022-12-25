package com.tencent.liteav.muxer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.muxer.a */
/* loaded from: classes3.dex */
public interface ITXCMP4Muxer {
    /* renamed from: a */
    int mo1235a();

    /* renamed from: a */
    void mo1234a(int i);

    /* renamed from: a */
    void mo1232a(MediaFormat mediaFormat);

    /* renamed from: a */
    void mo1231a(String str);

    /* renamed from: a */
    void mo1230a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo);

    /* renamed from: a */
    void mo1228a(byte[] bArr, int i, int i2, long j, int i3);

    /* renamed from: b */
    int mo1227b();

    /* renamed from: b */
    void mo1226b(MediaFormat mediaFormat);

    /* renamed from: b */
    void mo1224b(byte[] bArr, int i, int i2, long j, int i3);

    /* renamed from: c */
    boolean mo1223c();

    /* renamed from: d */
    boolean mo1221d();
}
