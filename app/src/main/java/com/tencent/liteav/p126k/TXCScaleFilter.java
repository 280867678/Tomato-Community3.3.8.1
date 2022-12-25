package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.k.m */
/* loaded from: classes3.dex */
public class TXCScaleFilter extends TXCGPUFilter {

    /* renamed from: r */
    private static String f4545r = " precision mediump float;\n varying mediump vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform float scale;\n \n void main(void) {\n       float x = 0.5 + (textureCoordinate.x - 0.5) / scale; \n       float y = 0.5 + (textureCoordinate.y - 0.5) / scale; \n       if (x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0) { \n           gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0); \n       } else { \n           gl_FragColor = texture2D(inputImageTexture, vec2(x, y)); \n       } \n }\n";

    /* renamed from: s */
    private static String f4546s = "GuidRefilne";

    /* renamed from: t */
    private int f4547t = -1;

    public TXCScaleFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", f4545r);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            TXCLog.m2914e(f4546s, "onInit failed");
            return false;
        }
        this.f4547t = GLES20.glGetUniformLocation(this.f2612a, "scale");
        m1320a(1.0f);
        return true;
    }

    /* renamed from: a */
    public void m1320a(float f) {
        m3035a(this.f4547t, f);
    }
}
