package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.k */
/* loaded from: classes3.dex */
public class TXCGPUGammaFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f3082r;

    /* renamed from: s */
    private float f3083s;

    public TXCGPUGammaFilter() {
        this(1.2f);
    }

    public TXCGPUGammaFilter(float f) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float gamma;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }");
        this.f3083s = f;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3082r = GLES20.glGetUniformLocation(m3011q(), "gamma");
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        m2688a(this.f3083s);
    }

    /* renamed from: a */
    public void m2688a(float f) {
        this.f3083s = f;
        m3035a(this.f3082r, this.f3083s);
    }
}
