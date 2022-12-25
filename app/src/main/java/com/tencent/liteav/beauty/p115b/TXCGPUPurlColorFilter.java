package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.u */
/* loaded from: classes3.dex */
public class TXCGPUPurlColorFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f3141r = -1;

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3141r = GLES20.glGetUniformLocation(m3011q(), "purlColor");
        m3021c(this.f3141r, new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        return mo1321a;
    }

    /* renamed from: b */
    public void m2654b(float[] fArr) {
        m3021c(this.f3141r, fArr);
    }
}
