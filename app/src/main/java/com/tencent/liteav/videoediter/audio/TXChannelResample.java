package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.videoediter.audio.e */
/* loaded from: classes3.dex */
public class TXChannelResample {

    /* renamed from: a */
    private int f5519a;

    /* renamed from: b */
    private int f5520b;

    /* renamed from: c */
    private volatile boolean f5521c;

    /* renamed from: a */
    public void m507a(int i, int i2) {
        if (this.f5519a == i && this.f5520b == i2) {
            return;
        }
        this.f5521c = true;
        this.f5519a = i;
        this.f5520b = i2;
        m508a();
    }

    /* renamed from: a */
    public short[] m506a(short[] sArr) {
        int i;
        int i2;
        if (sArr == null || m508a() || (i = this.f5519a) == (i2 = this.f5520b)) {
            return sArr;
        }
        if (i == 2 && i2 == 1) {
            return m504c(sArr);
        }
        return (this.f5519a == 1 && this.f5520b == 2) ? m505b(sArr) : sArr;
    }

    /* renamed from: a */
    private boolean m508a() {
        int i;
        if (!this.f5521c) {
            TXCLog.m2914e("FaceDetect", "you must set target channel count first");
            return true;
        }
        int i2 = this.f5519a;
        if (i2 >= 1 && i2 <= 2 && (i = this.f5520b) >= 1 && i <= 2) {
            return false;
        }
        TXCLog.m2914e("FaceDetect", "channel count must between 1 and 2");
        return true;
    }

    /* renamed from: b */
    private short[] m505b(short[] sArr) {
        short[] sArr2 = new short[sArr.length * 2];
        for (int i = 0; i < sArr.length; i++) {
            int i2 = i * 2;
            sArr2[i2] = sArr[i];
            sArr2[i2 + 1] = sArr[i];
        }
        return sArr2;
    }

    /* renamed from: c */
    private short[] m504c(short[] sArr) {
        int length = sArr.length / 2;
        short[] sArr2 = new short[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            sArr2[i] = sArr[i2];
            i++;
            i2 = i2 + 1 + 1;
        }
        return sArr2;
    }
}
