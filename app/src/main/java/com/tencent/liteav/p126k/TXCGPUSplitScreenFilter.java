package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.k.k */
/* loaded from: classes3.dex */
public class TXCGPUSplitScreenFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f4537r = 0;

    /* renamed from: s */
    private int[] f4538s = {1, 4, 9};

    /* renamed from: t */
    private C3531a[] f4539t = null;

    /* compiled from: TXCGPUSplitScreenFilter.java */
    /* renamed from: com.tencent.liteav.k.k$a */
    /* loaded from: classes3.dex */
    private static class C3531a {

        /* renamed from: a */
        int f4540a;

        /* renamed from: b */
        public int f4541b;

        /* renamed from: c */
        public int f4542c;

        /* renamed from: d */
        public int f4543d;

        private C3531a() {
            this.f4540a = 0;
            this.f4541b = 0;
            this.f4542c = 0;
            this.f4543d = 0;
        }
    }

    /* renamed from: a */
    public void m1323a(TXCVideoEffect.C3546m c3546m) {
        int i = c3546m.f4625a;
        if (i != this.f4537r) {
            int[] iArr = this.f4538s;
            int i2 = 0;
            if (i != iArr[0] && i != iArr[1] && i != iArr[2]) {
                return;
            }
            this.f4537r = c3546m.f4625a;
            this.f4539t = new C3531a[this.f4537r];
            for (int i3 = 0; i3 < this.f4537r; i3++) {
                this.f4539t[i3] = new C3531a();
            }
            int i4 = c3546m.f4625a;
            int[] iArr2 = this.f4538s;
            if (i4 == iArr2[0]) {
                C3531a[] c3531aArr = this.f4539t;
                c3531aArr[0].f4540a = 0;
                c3531aArr[0].f4541b = 0;
                c3531aArr[0].f4542c = this.f2616e;
                c3531aArr[0].f4543d = this.f2617f;
            } else if (i4 == iArr2[1]) {
                while (i2 < this.f4538s[1]) {
                    C3531a[] c3531aArr2 = this.f4539t;
                    C3531a c3531a = c3531aArr2[i2];
                    int i5 = this.f2616e;
                    c3531a.f4540a = ((i2 % 2) * i5) / 2;
                    C3531a c3531a2 = c3531aArr2[i2];
                    int i6 = this.f2617f;
                    c3531a2.f4541b = ((i2 / 2) * i6) / 2;
                    c3531aArr2[i2].f4542c = i5 / 2;
                    c3531aArr2[i2].f4543d = i6 / 2;
                    i2++;
                }
            } else if (i4 == iArr2[2]) {
                while (i2 < this.f4538s[2]) {
                    C3531a[] c3531aArr3 = this.f4539t;
                    C3531a c3531a3 = c3531aArr3[i2];
                    int i7 = this.f2616e;
                    c3531a3.f4540a = ((i2 % 3) * i7) / 3;
                    C3531a c3531a4 = c3531aArr3[i2];
                    int i8 = this.f2617f;
                    c3531a4.f4541b = ((i2 / 3) * i8) / 3;
                    c3531aArr3[i2].f4542c = i7 / 3;
                    c3531aArr3[i2].f4543d = i8 / 3;
                    i2++;
                }
            }
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (this.f4539t == null) {
            super.mo1324a(i, floatBuffer, floatBuffer2);
            return;
        }
        int i2 = 0;
        while (true) {
            C3531a[] c3531aArr = this.f4539t;
            if (i2 >= c3531aArr.length) {
                return;
            }
            if (c3531aArr[i2] != null) {
                GLES20.glViewport(c3531aArr[i2].f4540a, c3531aArr[i2].f4541b, c3531aArr[i2].f4542c, c3531aArr[i2].f4543d);
            }
            super.mo1324a(i, floatBuffer, floatBuffer2);
            i2++;
        }
    }
}
