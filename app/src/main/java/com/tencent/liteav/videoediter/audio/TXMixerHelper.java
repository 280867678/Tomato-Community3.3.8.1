package com.tencent.liteav.videoediter.audio;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* renamed from: com.tencent.liteav.videoediter.audio.f */
/* loaded from: classes3.dex */
public class TXMixerHelper {

    /* renamed from: a */
    private volatile float f5522a = 1.0f;

    /* renamed from: b */
    private volatile float f5523b = 1.0f;

    /* renamed from: a */
    private short m502a(int i) {
        if (i > 32767) {
            return Short.MAX_VALUE;
        }
        if (i >= -32768) {
            return (short) i;
        }
        return Short.MIN_VALUE;
    }

    /* renamed from: a */
    public void m503a(float f) {
        this.f5522a = f;
    }

    /* renamed from: b */
    public void m500b(float f) {
        this.f5523b = f;
    }

    /* renamed from: a */
    public short[] m501a(@NonNull short[] sArr, @Nullable short[] sArr2) {
        int i = 0;
        if (sArr2 == null || sArr2.length == 0) {
            if (this.f5523b == 1.0f) {
                return sArr;
            }
            while (i < sArr.length) {
                sArr[i] = m502a((int) (sArr[i] * this.f5523b));
                i++;
            }
            return sArr;
        }
        while (i < sArr.length) {
            sArr[i] = m502a((int) ((sArr[i] * this.f5523b) + (sArr2[i] * this.f5522a)));
            i++;
        }
        return sArr;
    }
}
