package com.tencent.liteav.p126k;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.p115b.TXCZoomInOutFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.j */
/* loaded from: classes3.dex */
public class TXCGPUSpiritOutFilter {

    /* renamed from: e */
    private static String f4530e = "SpiritOut";

    /* renamed from: a */
    protected TXCZoomInOutFilter f4531a = null;

    /* renamed from: d */
    private TXCGPUWatermarkTextureFilter f4534d = null;

    /* renamed from: b */
    protected TXCVideoEffect.C3545l f4532b = null;

    /* renamed from: c */
    TXCVideoPreprocessor.C3397d[] f4533c = null;

    /* renamed from: f */
    private int f4535f = -1;

    /* renamed from: g */
    private int f4536g = -1;

    /* renamed from: a */
    public boolean m1329a(int i, int i2) {
        return m1325c(i, i2);
    }

    /* renamed from: c */
    private boolean m1325c(int i, int i2) {
        if (i == this.f4535f && i2 == this.f4536g) {
            return true;
        }
        this.f4535f = i;
        this.f4536g = i2;
        if (this.f4531a == null) {
            this.f4531a = new TXCZoomInOutFilter();
            this.f4531a.mo1353a(true);
            if (!this.f4531a.mo2653c()) {
                TXCLog.m2914e(f4530e, "mZoomInOutFilter init error!");
                return false;
            }
        }
        this.f4531a.mo1333a(i, i2);
        if (this.f4534d == null) {
            this.f4534d = new TXCGPUWatermarkTextureFilter();
            this.f4534d.mo1353a(true);
            if (!this.f4534d.mo2653c()) {
                TXCLog.m2914e(f4530e, "mTextureWaterMarkFilter init error!");
                return false;
            }
        }
        this.f4534d.mo1333a(i, i2);
        return true;
    }

    /* renamed from: b */
    private void m1327b() {
        TXCZoomInOutFilter tXCZoomInOutFilter = this.f4531a;
        if (tXCZoomInOutFilter != null) {
            tXCZoomInOutFilter.mo1351e();
            this.f4531a = null;
        }
        TXCGPUWatermarkTextureFilter tXCGPUWatermarkTextureFilter = this.f4534d;
        if (tXCGPUWatermarkTextureFilter != null) {
            tXCGPUWatermarkTextureFilter.mo1351e();
            this.f4534d = null;
        }
    }

    /* renamed from: a */
    public void m1328a(TXCVideoEffect.C3545l c3545l) {
        this.f4532b = c3545l;
    }

    /* renamed from: a */
    public int mo1330a(int i) {
        TXCZoomInOutFilter tXCZoomInOutFilter;
        TXCVideoEffect.C3545l c3545l = this.f4532b;
        if (c3545l == null || (tXCZoomInOutFilter = this.f4531a) == null) {
            return i;
        }
        tXCZoomInOutFilter.m2722a(0.96f, c3545l.f4623g);
        this.f4531a.m2723a(this.f4532b.f4624h);
        int i2 = i;
        int i3 = 0;
        while (true) {
            TXCVideoEffect.C3545l c3545l2 = this.f4532b;
            if (i3 >= c3545l2.f4622f) {
                return i2;
            }
            if (i3 >= 1) {
                this.f4531a.m2722a(0.9f, c3545l2.f4623g + i3);
            }
            int mo2294a = this.f4531a.mo2294a(i);
            TXCVideoPreprocessor.C3397d[] c3397dArr = {new TXCVideoPreprocessor.C3397d()};
            c3397dArr[0].f3260e = mo2294a;
            c3397dArr[0].f3261f = this.f4535f;
            c3397dArr[0].f3262g = this.f4536g;
            c3397dArr[0].f3257b = 0.0f;
            c3397dArr[0].f3258c = 0.0f;
            c3397dArr[0].f3259d = 1.0f;
            TXCGPUWatermarkTextureFilter tXCGPUWatermarkTextureFilter = this.f4534d;
            if (tXCGPUWatermarkTextureFilter != null) {
                tXCGPUWatermarkTextureFilter.m1322a(c3397dArr);
                i2 = this.f4534d.mo2294a(i2);
            }
            i3++;
        }
    }

    /* renamed from: b */
    public void m1326b(int i, int i2) {
        m1325c(i, i2);
    }

    /* renamed from: a */
    public void m1331a() {
        m1327b();
    }
}
