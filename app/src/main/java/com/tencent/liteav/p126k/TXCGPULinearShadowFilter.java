package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.h */
/* loaded from: classes3.dex */
public class TXCGPULinearShadowFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f4522r = -1;

    /* renamed from: s */
    private int f4523s = -1;

    /* renamed from: t */
    private int f4524t = -1;

    /* renamed from: u */
    private int f4525u = -1;

    /* renamed from: v */
    private int f4526v = -1;

    /* renamed from: w */
    private int f4527w = -1;

    /* renamed from: x */
    private int f4528x = -1;

    /* renamed from: y */
    private int f4529y = -1;

    public TXCGPULinearShadowFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision mediump float; \nvarying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \n \nuniform float a; \nuniform float b; \nuniform float c; \nuniform float d; \nuniform float mode; \nuniform float width; \nuniform float stride; \nuniform float alpha; \n \nvoid main() \n{ \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate); \n    if(b == 0.0){ \n\t\t//float mx = textureCoordinate.x > (stride - c) ? mod(textureCoordinate.x + c, stride) - c : textureCoordinate.x; \n\t\t//if((mode < 0.5 && mx > -1.0*c && mx <= width - c) || (mode > 0.5 && (mx > width - c || mx < -1.0 *c))){ \n\t\tfloat mx = mod(textureCoordinate.x + c, stride); \n\t\tif((mode < 0.5 && mx <= width) || (mode > 0.5 && (mx > width))){ \n\t\t\tgl_FragColor.rgb = gl_FragColor.rgb*alpha; \n\t\t} \n\t} \n} \n");
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
        this.f4523s = GLES20.glGetUniformLocation(this.f2612a, EGL10Helper.f2537a);
        this.f4524t = GLES20.glGetUniformLocation(this.f2612a, "c");
        this.f4526v = GLES20.glGetUniformLocation(this.f2612a, "mode");
        this.f4527w = GLES20.glGetUniformLocation(this.f2612a, "width");
        this.f4528x = GLES20.glGetUniformLocation(this.f2612a, "stride");
        this.f4529y = GLES20.glGetUniformLocation(this.f2612a, "alpha");
        return true;
    }

    /* renamed from: a */
    public void m1332a(TXCVideoEffect.C3542i c3542i) {
        m1334a(c3542i.f4611a, c3542i.f4612b, c3542i.f4613c, c3542i.f4614d, c3542i.f4615e);
    }

    /* renamed from: a */
    private void m1334a(float f, float f2, float f3, float f4, float f5) {
        m3035a(this.f4526v, f);
        m3035a(this.f4529y, f2);
        m3035a(this.f4524t, f3 * (-1.0f));
        m3035a(this.f4527w, f4);
        m3035a(this.f4528x, f5);
    }
}
