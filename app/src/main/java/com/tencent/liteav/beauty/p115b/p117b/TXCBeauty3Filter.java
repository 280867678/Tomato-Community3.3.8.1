package com.tencent.liteav.beauty.p115b.p117b;

import android.util.Log;
import com.tencent.liteav.beauty.p115b.TXCBeautyInterFace;
import com.tencent.liteav.beauty.p115b.TXCGPUSharpenFilter;

/* renamed from: com.tencent.liteav.beauty.b.b.a */
/* loaded from: classes3.dex */
public class TXCBeauty3Filter extends TXCBeautyInterFace {

    /* renamed from: r */
    private TXCGChannelBeautyFilter f3013r = null;

    /* renamed from: s */
    private TXCGPUSharpenFilter f3014s = null;

    /* renamed from: t */
    private String f3015t = "TXCBeauty3Filter";

    /* renamed from: u */
    private float f3016u = 0.0f;

    /* renamed from: v */
    private float f3017v = 0.0f;

    /* renamed from: w */
    private float f3018w = 0.0f;

    /* renamed from: x */
    private float f3019x = 0.0f;

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public boolean mo2709c(int i, int i2) {
        return m2720d(i, i2);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2616e == i && this.f2617f == i2) {
            return;
        }
        this.f2616e = i;
        this.f2617f = i2;
        m2720d(i, i2);
    }

    /* renamed from: d */
    private boolean m2720d(int i, int i2) {
        if (this.f3013r == null) {
            this.f3013r = new TXCGChannelBeautyFilter();
            this.f3013r.mo1353a(true);
            if (!this.f3013r.mo2653c()) {
                Log.e(this.f3015t, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.f3013r.mo1333a(i, i2);
        if (this.f3014s == null) {
            this.f3014s = new TXCGPUSharpenFilter();
            this.f3014s.mo1353a(true);
            if (!this.f3014s.mo2653c()) {
                Log.e(this.f3015t, "mSharpnessFilter init failed!!, break init");
                return false;
            }
        }
        this.f3014s.mo1333a(i, i2);
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        if (this.f3016u > 0.0f || this.f3017v > 0.0f || this.f3018w > 0.0f) {
            i = this.f3013r.mo2294a(i);
        }
        return this.f3019x > 0.0f ? this.f3014s.mo2294a(i) : i;
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public void mo2710c(int i) {
        this.f3016u = i / 10.0f;
        TXCGChannelBeautyFilter tXCGChannelBeautyFilter = this.f3013r;
        if (tXCGChannelBeautyFilter != null) {
            tXCGChannelBeautyFilter.m2718a(this.f3016u);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: d */
    public void mo2708d(int i) {
        this.f3017v = i / 10.0f;
        TXCGChannelBeautyFilter tXCGChannelBeautyFilter = this.f3013r;
        if (tXCGChannelBeautyFilter != null) {
            tXCGChannelBeautyFilter.m2717b(this.f3017v);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: e */
    public void mo2707e(int i) {
        this.f3018w = i / 10.0f;
        TXCGChannelBeautyFilter tXCGChannelBeautyFilter = this.f3013r;
        if (tXCGChannelBeautyFilter != null) {
            tXCGChannelBeautyFilter.m2715c(this.f3018w);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: f */
    public void mo2706f(int i) {
        this.f3019x = i / 20.0f;
        TXCGPUSharpenFilter tXCGPUSharpenFilter = this.f3014s;
        if (tXCGPUSharpenFilter != null) {
            tXCGPUSharpenFilter.m2641a(this.f3019x);
        }
    }

    /* renamed from: r */
    void m2719r() {
        TXCGChannelBeautyFilter tXCGChannelBeautyFilter = this.f3013r;
        if (tXCGChannelBeautyFilter != null) {
            tXCGChannelBeautyFilter.mo2293b();
            this.f3013r = null;
        }
        TXCGPUSharpenFilter tXCGPUSharpenFilter = this.f3014s;
        if (tXCGPUSharpenFilter != null) {
            tXCGPUSharpenFilter.mo2293b();
            this.f3014s = null;
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
        m2719r();
    }
}
