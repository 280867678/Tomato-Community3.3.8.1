package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;

/* renamed from: com.tencent.liteav.beauty.b.n */
/* loaded from: classes3.dex */
public class TXCGPUGridBlendFilter extends TXCGPUThreeInputFilter {

    /* renamed from: x */
    private static String f3095x = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nvarying mediump vec2 textureCoordinate3;\nuniform sampler2D inputImageTexture3;\n\nuniform mediump float scale1;\n\nmediump vec4 textureScale(sampler2D texture, mediump vec2 coor, mediump float scale){\n\t vec2 rCoor = textureCoordinate - (1.0 - scale) * (vec2(0.5, 0.5) - textureCoordinate);\n     float x = rCoor.x;\n     float y = rCoor.y;\n\n    vec4 scaleColor = texture2D(texture, coor);\n     if (x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0) { \n         scaleColor = vec4(1.0, 1.0, 1.0, 1.0); \n     } else { \n         scaleColor = texture2D(texture, vec2(x, y)); \n     } \n\n     return scaleColor;\n}\n\nvoid main()\n{\n    // 第一个纹理 网络(需要放大)\n    vec4 gridColor = texture2D(inputImageTexture, textureCoordinate);\n    if (1.0 != scale1){\n    \tgridColor = textureScale(inputImageTexture, textureCoordinate, scale1);\n    }\n\n    // 第二个纹理 上一张纹理\n    vec4 prevColor = texture2D(inputImageTexture2, textureCoordinate2);\n    // 第三个纹理 当前纹理\n    vec4 currentColor = texture2D(inputImageTexture3, textureCoordinate3);\n\n    // 如果 alpha 为1.0，则显示当前放大 或 缩小的图片\n    if (0.0 == gridColor.a){\n        gl_FragColor = prevColor;\n    }else{\n        gl_FragColor = currentColor;\n    }\n}\n";

    /* renamed from: y */
    private int f3096y = -1;

    public TXCGPUGridBlendFilter() {
        super(f3095x);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUThreeInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3096y = GLES20.glGetUniformLocation(m3011q(), "scale1");
        m2680a(1.5f);
        return mo1321a;
    }

    /* renamed from: a */
    public void m2680a(float f) {
        m3035a(this.f3096y, f);
    }
}
