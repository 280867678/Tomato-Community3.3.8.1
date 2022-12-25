package com.tencent.liteav;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.f */
/* loaded from: classes3.dex */
public class TXCGPUMirrorFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f3811r = -1;

    public TXCGPUMirrorFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \nuniform lowp float mode; \n \nvoid main() \n{ \n    highp vec2 position = textureCoordinate; \n     \n    if (mode <= 0.5) \n    { \n        if (position.x > 0.5) \n        { \n            position.x = 1.0 - position.x; \n        } \n    } \n    else \n    { \n        if (position.x > 0.5) \n        { \n            position.x = position.x - 0.5; \n        } \n        else \n        { \n            position.x = 0.5 - position.x; \n        } \n    } \n     \n    gl_FragColor = texture2D(inputImageTexture, position); \n} \n");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3811r = GLES20.glGetUniformLocation(this.f2612a, "mode");
        return mo1321a;
    }

    /* renamed from: a */
    public void m1936a(TXCVideoEffect.C3543j c3543j) {
        m3035a(this.f3811r, c3543j.f4616a);
    }
}
