package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.ae */
/* loaded from: classes3.dex */
public class TXCGPUTwoPassTextureSamplingFilter extends TXCGPUTwoPassFilter {

    /* renamed from: u */
    protected float f2963u = 4.0f;

    public TXCGPUTwoPassTextureSamplingFilter(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUFilterGroup, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        return super.mo1321a() && GLES20.glGetError() == 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: v */
    public void m2739v() {
        float mo2685u = mo2685u();
        TXCGPUFilter tXCGPUFilter = ((TXCGPUFilterGroup) this).f3074r.get(0);
        int glGetUniformLocation = GLES20.glGetUniformLocation(tXCGPUFilter.m3011q(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(tXCGPUFilter.m3011q(), "texelHeightOffset");
        tXCGPUFilter.m3035a(glGetUniformLocation, mo2685u / this.f2616e);
        tXCGPUFilter.m3035a(glGetUniformLocation2, 0.0f);
        float mo2686t = mo2686t();
        TXCGPUFilter tXCGPUFilter2 = ((TXCGPUFilterGroup) this).f3074r.get(1);
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(tXCGPUFilter2.m3011q(), "texelWidthOffset");
        int glGetUniformLocation4 = GLES20.glGetUniformLocation(tXCGPUFilter2.m3011q(), "texelHeightOffset");
        tXCGPUFilter2.m3035a(glGetUniformLocation3, 0.0f);
        tXCGPUFilter2.m3035a(glGetUniformLocation4, mo2686t / this.f2617f);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUFilterGroup, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        m2739v();
    }

    /* renamed from: t */
    public float mo2686t() {
        return this.f2963u;
    }

    /* renamed from: u */
    public float mo2685u() {
        return this.f2963u;
    }
}
