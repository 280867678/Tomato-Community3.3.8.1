package com.tencent.liteav.beauty;

/* renamed from: com.tencent.liteav.beauty.e */
/* loaded from: classes3.dex */
public interface TXIVideoPreprocessorListener {
    void didProcessFrame(int i, int i2, int i3, long j);

    void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j);

    int willAddWatermark(int i, int i2, int i3);
}
