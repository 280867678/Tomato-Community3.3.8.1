package com.tencent.liteav.beauty.p115b.p117b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.b.b */
/* loaded from: classes3.dex */
public class TXCGChannelBeautyFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f3020r = -1;

    /* renamed from: s */
    private int f3021s = -1;

    /* renamed from: t */
    private float[] f3022t = new float[4];

    /* renamed from: u */
    private String f3023u = "Beauty3Filter";

    public TXCGChannelBeautyFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        NativeLoad.getInstance();
        this.f2612a = NativeLoad.nativeLoadGLProgram(14);
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3020r = GLES20.glGetUniformLocation(m3011q(), "singleStepOffset");
        this.f3021s = GLES20.glGetUniformLocation(m3011q(), "beautyParams");
        m2718a(5.0f);
        return mo1321a;
    }

    /* renamed from: c */
    public void m2714c(int i, int i2) {
        m3030a(this.f3020r, new float[]{2.0f / i, 2.0f / i2});
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        m2714c(i, i2);
    }

    /* renamed from: a */
    public void m2718a(float f) {
        float[] fArr = this.f3022t;
        fArr[0] = f;
        m2716b(fArr);
    }

    /* renamed from: b */
    public void m2717b(float f) {
        float[] fArr = this.f3022t;
        fArr[1] = f;
        m2716b(fArr);
    }

    /* renamed from: c */
    public void m2715c(float f) {
        float[] fArr = this.f3022t;
        fArr[2] = f;
        m2716b(fArr);
    }

    /* renamed from: b */
    private void m2716b(float[] fArr) {
        m3021c(this.f3021s, fArr);
    }
}
