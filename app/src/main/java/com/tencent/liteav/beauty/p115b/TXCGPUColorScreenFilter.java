package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;

/* renamed from: com.tencent.liteav.beauty.b.g */
/* loaded from: classes3.dex */
public class TXCGPUColorScreenFilter extends TXCGPUThreeInputFilter {

    /* renamed from: A */
    private int f3052A;

    /* renamed from: B */
    private float[] f3053B;

    /* renamed from: x */
    private int f3054x;

    /* renamed from: y */
    private int f3055y;

    /* renamed from: z */
    private int f3056z;

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        this.f3054x = GLES20.glGetUniformLocation(m3011q(), "screenMode");
        this.f3055y = GLES20.glGetUniformLocation(m3011q(), "screenReplaceColor");
        this.f3056z = GLES20.glGetUniformLocation(m3011q(), "screenMirrorX");
        this.f3052A = GLES20.glGetUniformLocation(m3011q(), "screenMirrorY");
        m2695b(this.f3053B);
    }

    /* renamed from: b */
    public void m2695b(float[] fArr) {
        float[] fArr2 = {(float) ((fArr[0] * 0.2989d) + (fArr[1] * 0.5866d) + (fArr[2] * 0.1145d)), (float) ((fArr[0] - fArr2[0]) * 0.7132d), (float) ((fArr[2] - fArr2[0]) * 0.5647d)};
        m3023b(this.f3055y, fArr2);
    }
}
