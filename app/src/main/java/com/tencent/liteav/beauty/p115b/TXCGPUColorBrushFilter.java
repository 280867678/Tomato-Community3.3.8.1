package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;

/* renamed from: com.tencent.liteav.beauty.b.f */
/* loaded from: classes3.dex */
public class TXCGPUColorBrushFilter extends TXCGPUTwoInputFilter {

    /* renamed from: r */
    private static String f3049r = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nuniform mediump vec4 brushColor;\nuniform mediump vec4 fillColor;\n\nvoid main()\n{\n    // 第一个纹理 网络\n    vec4 texture1Color = texture2D(inputImageTexture, textureCoordinate);\n    // 第二个纹理 上一张纹理\n    vec4 texture2Color = texture2D(inputImageTexture2, textureCoordinate2);\n\n    if (brushColor.a == texture1Color.a || brushColor.a == texture2Color.a){\n        gl_FragColor = brushColor;\n    }else{\n        gl_FragColor = fillColor;\n    }\n}\n";

    /* renamed from: s */
    private int f3050s = -1;

    /* renamed from: t */
    private int f3051t = -1;

    public TXCGPUColorBrushFilter() {
        super(f3049r);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3050s = GLES20.glGetUniformLocation(m3011q(), "brushColor");
        this.f3051t = GLES20.glGetUniformLocation(m3011q(), "fillColor");
        m2697b(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        m2696c(new float[]{0.0f, 0.0f, 0.0f, 0.0f});
        return mo1321a;
    }

    /* renamed from: b */
    public void m2697b(float[] fArr) {
        m3021c(this.f3050s, fArr);
    }

    /* renamed from: c */
    public void m2696c(float[] fArr) {
        m3021c(this.f3051t, fArr);
    }
}
