package com.tencent.liteav.beauty.p115b.p116a;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.p115b.TXCBeautyInterFace;
import com.tencent.liteav.beauty.p115b.TXCGPUGammaFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUSharpenAlphaFilter;

/* renamed from: com.tencent.liteav.beauty.b.a.a */
/* loaded from: classes3.dex */
public class TXCBeauty2Filter extends TXCBeautyInterFace {

    /* renamed from: r */
    private TXCTILSkinFilter f2921r = null;

    /* renamed from: s */
    private TXCTILSmoothHorizontalFilter f2922s = null;

    /* renamed from: t */
    private TXCTILSmoothVerticalFilter f2923t = null;

    /* renamed from: u */
    private TXCGPUGammaFilter f2924u = null;

    /* renamed from: v */
    private TXCGPUSharpenAlphaFilter f2925v = null;

    /* renamed from: w */
    private TXCBeautyBlend f2926w = null;

    /* renamed from: x */
    private String f2927x = "TXCBeauty2Filter";

    /* renamed from: y */
    private int f2928y = 0;

    /* renamed from: z */
    private int f2929z = 0;

    /* renamed from: A */
    private int f2912A = 0;

    /* renamed from: B */
    private float f2913B = 1.0f;

    /* renamed from: C */
    private final float f2914C = 0.7f;

    /* renamed from: D */
    private float f2915D = 0.8f;

    /* renamed from: E */
    private float f2916E = 2.0f;

    /* renamed from: F */
    private int f2917F = 0;

    /* renamed from: G */
    private int f2918G = 0;

    /* renamed from: H */
    private int f2919H = 0;

    /* renamed from: I */
    private int f2920I = 0;

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public boolean mo2709c(int i, int i2) {
        return m2758d(i, i2);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2616e == i && this.f2617f == i2) {
            return;
        }
        this.f2616e = i;
        this.f2617f = i2;
        m2758d(i, i2);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public void mo2710c(int i) {
        TXCTILSmoothVerticalFilter tXCTILSmoothVerticalFilter = this.f2923t;
        if (tXCTILSmoothVerticalFilter != null) {
            tXCTILSmoothVerticalFilter.m2751a(i / 10.0f);
        }
        this.f2928y = i;
        m2757g(i);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: d */
    public void mo2708d(int i) {
        TXCBeautyBlend tXCBeautyBlend = this.f2926w;
        if (tXCBeautyBlend != null) {
            tXCBeautyBlend.m2755a(i / 10.0f);
        }
        this.f2929z = i;
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: e */
    public void mo2707e(int i) {
        TXCBeautyBlend tXCBeautyBlend = this.f2926w;
        if (tXCBeautyBlend != null) {
            tXCBeautyBlend.m2754b(i / 10.0f);
        }
        this.f2912A = i;
    }

    /* renamed from: g */
    private void m2757g(int i) {
        this.f2913B = 1.0f - (i / 50.0f);
        TXCGPUGammaFilter tXCGPUGammaFilter = this.f2924u;
        if (tXCGPUGammaFilter != null) {
            tXCGPUGammaFilter.m2688a(this.f2913B);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: f */
    public void mo2706f(int i) {
        this.f2915D = (i / 12.0f) + 0.7f;
        String str = this.f2927x;
        TXCLog.m2913i(str, "set mSharpenLevel " + i);
        TXCGPUSharpenAlphaFilter tXCGPUSharpenAlphaFilter = this.f2925v;
        if (tXCGPUSharpenAlphaFilter != null) {
            tXCGPUSharpenAlphaFilter.m2642a(this.f2915D);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        if (1.0f != this.f2916E) {
            GLES20.glViewport(0, 0, this.f2919H, this.f2920I);
        }
        int m2741c = this.f2923t.m2741c(this.f2922s.mo2294a(i), i);
        if (1.0f != this.f2916E) {
            GLES20.glViewport(0, 0, this.f2917F, this.f2918G);
        }
        if (this.f2915D > 0.7f) {
            m2741c = this.f2925v.mo2294a(m2741c);
        }
        return this.f2926w.m2741c(m2741c, i);
    }

    /* renamed from: d */
    private boolean m2758d(int i, int i2) {
        this.f2917F = i;
        this.f2918G = i2;
        this.f2919H = i;
        this.f2920I = i2;
        float f = this.f2916E;
        if (1.0f != f) {
            this.f2919H = (int) (this.f2919H / f);
            this.f2920I = (int) (this.f2920I / f);
        }
        String str = this.f2927x;
        TXCLog.m2913i(str, "mResampleRatio " + this.f2916E + " mResampleWidth " + this.f2919H + " mResampleHeight " + this.f2920I);
        if (this.f2926w == null) {
            this.f2926w = new TXCBeautyBlend();
            this.f2926w.mo1353a(true);
            if (!this.f2926w.mo2653c()) {
                TXCLog.m2914e(this.f2927x, "mBeautyBlendFilter init failed!!, break init");
                return false;
            }
        }
        this.f2926w.mo1333a(i, i2);
        if (this.f2922s == null) {
            this.f2922s = new TXCTILSmoothHorizontalFilter();
            this.f2922s.mo1353a(true);
            if (!this.f2922s.mo2653c()) {
                TXCLog.m2914e(this.f2927x, "m_horizontalFilter init failed!!, break init");
                return false;
            }
        }
        this.f2922s.mo1333a(this.f2919H, this.f2920I);
        if (this.f2923t == null) {
            this.f2923t = new TXCTILSmoothVerticalFilter();
            this.f2923t.mo1353a(true);
            this.f2923t.m3022b(1.0f != this.f2916E);
            if (!this.f2923t.mo2653c()) {
                TXCLog.m2914e(this.f2927x, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.f2923t.mo1333a(this.f2919H, this.f2920I);
        if (this.f2924u == null) {
            this.f2924u = new TXCGPUGammaFilter(1.0f);
            this.f2924u.mo1353a(true);
            if (!this.f2924u.mo2653c()) {
                TXCLog.m2914e(this.f2927x, "m_gammaFilter init failed!!, break init");
                return false;
            }
        }
        this.f2924u.mo1333a(this.f2919H, this.f2920I);
        if (this.f2925v == null) {
            this.f2925v = new TXCGPUSharpenAlphaFilter();
            this.f2925v.mo1353a(true);
            if (!this.f2925v.mo2653c()) {
                TXCLog.m2914e(this.f2927x, "mSharpenFilter init failed!!, break init");
                return false;
            }
        }
        this.f2925v.mo1333a(i, i2);
        return true;
    }

    /* renamed from: r */
    void m2756r() {
        TXCBeautyBlend tXCBeautyBlend = this.f2926w;
        if (tXCBeautyBlend != null) {
            tXCBeautyBlend.mo1351e();
            this.f2926w = null;
        }
        TXCTILSmoothHorizontalFilter tXCTILSmoothHorizontalFilter = this.f2922s;
        if (tXCTILSmoothHorizontalFilter != null) {
            tXCTILSmoothHorizontalFilter.mo1351e();
            this.f2922s = null;
        }
        TXCTILSmoothVerticalFilter tXCTILSmoothVerticalFilter = this.f2923t;
        if (tXCTILSmoothVerticalFilter != null) {
            tXCTILSmoothVerticalFilter.mo1351e();
            this.f2923t = null;
        }
        TXCGPUGammaFilter tXCGPUGammaFilter = this.f2924u;
        if (tXCGPUGammaFilter != null) {
            tXCGPUGammaFilter.mo1351e();
            this.f2924u = null;
        }
        TXCGPUSharpenAlphaFilter tXCGPUSharpenAlphaFilter = this.f2925v;
        if (tXCGPUSharpenAlphaFilter != null) {
            tXCGPUSharpenAlphaFilter.mo1351e();
            this.f2925v = null;
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        m2756r();
    }
}
