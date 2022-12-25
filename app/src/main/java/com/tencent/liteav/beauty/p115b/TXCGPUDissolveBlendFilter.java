package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.beauty.b.h */
/* loaded from: classes3.dex */
public class TXCGPUDissolveBlendFilter extends TXCGPUTwoInputFilter {

    /* renamed from: r */
    private static String f3057r = "precision mediump float; \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float mixturePercent;\n\nvoid main()\n{\n   vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n   \n   gl_FragColor = mix(textureColor, textureColor2, mixturePercent);\n}\n";

    /* renamed from: s */
    private static String f3058s = "DissolveBlend";

    /* renamed from: t */
    private int f3059t = -1;

    public TXCGPUDissolveBlendFilter() {
        super(f3057r);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            TXCLog.m2914e(f3058s, "onInit failed");
            return false;
        }
        this.f3059t = GLES20.glGetUniformLocation(this.f2612a, "mixturePercent");
        m2694a(0.5f);
        return true;
    }

    /* renamed from: a */
    public void m2694a(float f) {
        m3035a(this.f3059t, f);
    }
}
