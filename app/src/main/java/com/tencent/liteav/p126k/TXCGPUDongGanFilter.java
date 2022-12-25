package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.c */
/* loaded from: classes3.dex */
public class TXCGPUDongGanFilter extends TXCGPUFilter {

    /* renamed from: s */
    private int f4481s = -1;

    /* renamed from: t */
    private int f4482t = -1;

    /* renamed from: u */
    private int f4483u = -1;

    /* renamed from: v */
    private int f4484v = -1;

    /* renamed from: w */
    private int f4485w = -1;

    /* renamed from: x */
    private int f4486x = -1;

    /* renamed from: y */
    private int f4487y = -1;

    /* renamed from: z */
    private int f4488z = -1;

    /* renamed from: r */
    TXCVideoEffect.C3537d f4480r = null;

    public TXCGPUDongGanFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision highp float; \nvarying vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \nuniform float t; \nuniform float st; \nconst float stride = 7.0; \nconst float interval = 1.0; \nuniform float zMin; \nuniform float zMax; \nuniform vec2 center; \nuniform vec2 offsetR; \nuniform vec2 offsetG; \nuniform vec2 offsetB;\n\nfloat GetFactor(float elapse, float astride, float ainterval, float amp) \n{ \n\tfloat ff = mod(elapse, astride + ainterval) / astride; \n\tif (ff > 1.0) \n\t{ \n\t\tff = 0.0; \n\t} \n\treturn pow(ff, 3.0) * 1.125 * amp; \n} \nvec2 _uv(vec2 uv, vec2 center, float zz, float min) \n{ \n\treturn uv + (zz + min) * (center - uv); \n} \nvoid main() \n{ \n\tvec4 fout; \n\tfloat zz = GetFactor(t - st, stride, interval, zMax - zMin); \n\tfloat coeff = pow(zz, 0.75); \n\tfout.r = texture2D(inputImageTexture, _uv(textureCoordinate, center, zz, zMin) + offsetR * coeff).r; \n\tfout.g = texture2D(inputImageTexture, _uv(textureCoordinate, center, zz, zMin) + offsetG * coeff).g; \n\tfout.b = texture2D(inputImageTexture, _uv(textureCoordinate, center, zz, zMin) + offsetB * coeff).b; \n\tgl_FragColor = vec4(fout.rgb, 1.0); \n}\n");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            return false;
        }
        this.f4481s = GLES20.glGetUniformLocation(this.f2612a, "zMin");
        this.f4482t = GLES20.glGetUniformLocation(this.f2612a, "zMax");
        this.f4483u = GLES20.glGetUniformLocation(this.f2612a, "t");
        this.f4484v = GLES20.glGetUniformLocation(this.f2612a, "st");
        this.f4485w = GLES20.glGetUniformLocation(this.f2612a, "center");
        this.f4486x = GLES20.glGetUniformLocation(this.f2612a, "offsetR");
        this.f4487y = GLES20.glGetUniformLocation(this.f2612a, "offsetG");
        this.f4488z = GLES20.glGetUniformLocation(this.f2612a, "offsetB");
        return true;
    }

    /* renamed from: a */
    public void m1360a(TXCVideoEffect.C3537d c3537d) {
        this.f4480r = c3537d;
        TXCVideoEffect.C3537d c3537d2 = this.f4480r;
        m1359a(c3537d2.f4601f, c3537d2.f4602g, c3537d2.f4603h);
        m1357b(this.f4480r.f4598c);
        TXCVideoEffect.C3537d c3537d3 = this.f4480r;
        m1361a(c3537d3.f4596a, c3537d3.f4597b);
        m1362a(this.f4480r.f4600e);
        m1358b(this.f4480r.f4599d);
    }

    /* renamed from: a */
    private void m1359a(float[] fArr, float[] fArr2, float[] fArr3) {
        m3030a(this.f4486x, fArr);
        m3030a(this.f4487y, fArr2);
        m3030a(this.f4488z, fArr3);
    }

    /* renamed from: b */
    private void m1357b(float[] fArr) {
        m3030a(this.f4485w, fArr);
    }

    /* renamed from: a */
    private void m1361a(float f, float f2) {
        m3035a(this.f4481s, f);
        m3035a(this.f4482t, f2);
    }

    /* renamed from: a */
    private void m1362a(float f) {
        m3035a(this.f4483u, f);
    }

    /* renamed from: b */
    private void m1358b(float f) {
        m3035a(this.f4484v, f);
    }
}
