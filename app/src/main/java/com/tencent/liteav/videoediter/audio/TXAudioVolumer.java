package com.tencent.liteav.videoediter.audio;

/* renamed from: com.tencent.liteav.videoediter.audio.d */
/* loaded from: classes3.dex */
public class TXAudioVolumer {

    /* renamed from: a */
    private volatile float f5518a = 1.0f;

    /* renamed from: a */
    public void m510a(float f) {
        this.f5518a = f;
    }

    /* renamed from: a */
    public short[] m509a(short[] sArr) {
        if (this.f5518a == 1.0f) {
            return sArr;
        }
        for (int i = 0; i < sArr.length; i++) {
            int i2 = (int) (sArr[i] * this.f5518a);
            short s = Short.MIN_VALUE;
            if (i2 > 32767) {
                s = Short.MAX_VALUE;
            } else if (i2 >= -32768) {
                s = (short) i2;
            }
            sArr[i] = s;
        }
        return sArr;
    }
}
