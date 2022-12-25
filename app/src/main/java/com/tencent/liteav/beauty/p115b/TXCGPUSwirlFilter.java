package com.tencent.liteav.beauty.p115b;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.aa */
/* loaded from: classes3.dex */
public class TXCGPUSwirlFilter extends TXCGPUFilter {

    /* renamed from: r */
    private float f2945r;

    /* renamed from: s */
    private int f2946s;

    /* renamed from: t */
    private float f2947t;

    /* renamed from: u */
    private int f2948u;

    /* renamed from: v */
    private PointF f2949v;

    /* renamed from: w */
    private int f2950w;

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f2946s = GLES20.glGetUniformLocation(m3011q(), "angle");
        this.f2948u = GLES20.glGetUniformLocation(m3011q(), "radius");
        this.f2950w = GLES20.glGetUniformLocation(m3011q(), "center");
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        m2749a(this.f2947t);
        m2747b(this.f2945r);
        m2748a(this.f2949v);
    }

    /* renamed from: a */
    public void m2749a(float f) {
        this.f2947t = f;
        m3035a(this.f2948u, f);
    }

    /* renamed from: b */
    public void m2747b(float f) {
        this.f2945r = f;
        m3035a(this.f2946s, f);
    }

    /* renamed from: a */
    public void m2748a(PointF pointF) {
        this.f2949v = pointF;
        m3032a(this.f2950w, pointF);
    }
}
