package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.f */
/* loaded from: classes3.dex */
public class TXCGPUGridGeneralFilter extends TXCGPUFilter {

    /* renamed from: r */
    private static String f4500r = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nvarying vec2 textureCoordinate;\nvarying vec2 textureNoRotate; // 保证以中心点旋转\n\n// 旋转逻辑\nuniform mat4 textureTransform;\n\nvoid main() \n{ \n  gl_Position = position;\n  textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n  textureNoRotate = inputTextureCoordinate.xy;\n}\n";

    /* renamed from: s */
    private static String f4501s = "precision mediump float; \nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture; \n \n// x 轴竖条\nuniform float xOffset;\nuniform float xWidth;\nuniform float xStride;\n\n// y 轴竖条\nuniform float yOffset;\nuniform float yWidth;\nuniform float yStride;\n\n// 中心点\nuniform vec2 center;\n// 网格半径\nuniform float radius;\n// 宽高比\nuniform float aspectRatio;\n// 放大 或 缩小\nuniform int zoomModel;  // 0 放大，1缩小\n\nuniform float maxRadius;\n\nvoid drawGrid(){\n    // 第一步：画黑色矩形框\n    // 黑色竖条\n    float mx = mod(textureCoordinate.x + xOffset, xStride); \n    float my = mod(textureCoordinate.y + yOffset, yStride);\n\n    if(mx <= xWidth || my <= yWidth){ \n        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); \n    }else{\n        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n    }\n\n}\n\nvoid main()\n{ \n    highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\n    highp float cRadius = distance(center, textureCoordinateToUse);\n\n    // 如果是缩小模式\n    if (1 == zoomModel){\n        if (cRadius < maxRadius && cRadius > radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }else{\n        if (cRadius < radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }\n}\n";

    /* renamed from: t */
    private static String f4502t = "Diffuse";

    /* renamed from: u */
    private int f4511u = -1;

    /* renamed from: v */
    private int f4512v = -1;

    /* renamed from: w */
    private int f4513w = -1;

    /* renamed from: x */
    private int f4514x = -1;

    /* renamed from: y */
    private int f4515y = -1;

    /* renamed from: z */
    private int f4516z = -1;

    /* renamed from: A */
    private int f4503A = -1;

    /* renamed from: B */
    private int f4504B = -1;

    /* renamed from: C */
    private int f4505C = -1;

    /* renamed from: D */
    private int f4506D = -1;

    /* renamed from: E */
    private int f4507E = -1;

    /* renamed from: F */
    private int f4508F = -1;

    /* renamed from: G */
    private final float[] f4509G = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: H */
    private float[] f4510H = (float[]) this.f4509G.clone();

    public TXCGPUGridGeneralFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", f4501s);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            Log.e(f4502t, "super.onInit failed");
            return false;
        }
        this.f4511u = GLES20.glGetUniformLocation(m3011q(), "xOffset");
        this.f4512v = GLES20.glGetUniformLocation(m3011q(), "xWidth");
        this.f4513w = GLES20.glGetUniformLocation(m3011q(), "xStride");
        this.f4514x = GLES20.glGetUniformLocation(m3011q(), "yOffset");
        this.f4515y = GLES20.glGetUniformLocation(m3011q(), "yWidth");
        this.f4516z = GLES20.glGetUniformLocation(m3011q(), "yStride");
        this.f4503A = GLES20.glGetUniformLocation(m3011q(), "textureTransform");
        this.f4504B = GLES20.glGetUniformLocation(m3011q(), "radius");
        this.f4505C = GLES20.glGetUniformLocation(m3011q(), "center");
        this.f4506D = GLES20.glGetUniformLocation(m3011q(), "aspectRatio");
        this.f4507E = GLES20.glGetUniformLocation(m3011q(), "zoomModel");
        this.f4508F = GLES20.glGetUniformLocation(m3011q(), "maxRadius");
        m3030a(this.f4505C, new float[]{0.5f, 0.5f});
        return true;
    }

    /* renamed from: a */
    public void m1342a(TXCVideoEffect.C3535c c3535c) {
        m3035a(this.f4511u, c3535c.f4581a);
        m3035a(this.f4512v, c3535c.f4582b);
        m3035a(this.f4513w, c3535c.f4583c);
        m3035a(this.f4514x, c3535c.f4581a);
        m3035a(this.f4515y, c3535c.f4582b);
        m3035a(this.f4516z, c3535c.f4583c);
        m3035a(this.f4504B, c3535c.f4586f);
        m3024b(this.f4507E, c3535c.f4587g.value);
        m3035a(this.f4508F, c3535c.f4585e);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2617f == i2 && this.f2616e == i) {
            return;
        }
        super.mo1333a(i, i2);
        m3035a(this.f4506D, (i2 * 1.0f) / i);
    }
}
