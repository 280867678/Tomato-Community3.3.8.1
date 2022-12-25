package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.z */
/* loaded from: classes3.dex */
public class TXCGPUSharpenFilter extends TXCGPUFilter {

    /* renamed from: v */
    private static String f3199v = "GPUSharpen";

    /* renamed from: r */
    private int f3200r;

    /* renamed from: s */
    private float f3201s;

    /* renamed from: t */
    private int f3202t;

    /* renamed from: u */
    private int f3203u;

    public TXCGPUSharpenFilter() {
        this(0.0f);
    }

    public TXCGPUSharpenFilter(float f) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform float imageWidthFactor; \nuniform float imageHeightFactor; \nuniform float sharpness;\n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate; \nvarying vec2 topTextureCoordinate;\nvarying vec2 bottomTextureCoordinate;\n\nvarying float centerMultiplier;\nvarying float edgeMultiplier;\n\nvoid main()\n{\n    gl_Position = position;\n    \n    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n    \n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n    \n    centerMultiplier = 1.0 + 4.0 * sharpness;\n    edgeMultiplier = sharpness;\n}", "precision highp float;\n\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 leftTextureCoordinate;\nvarying highp vec2 rightTextureCoordinate; \nvarying highp vec2 topTextureCoordinate;\nvarying highp vec2 bottomTextureCoordinate;\n\nvarying highp float centerMultiplier;\nvarying highp float edgeMultiplier;\n\nuniform sampler2D inputImageTexture;\n\nvoid main()\n{\n    mediump vec3 textureColor = texture2D(inputImageTexture, textureCoordinate).rgb;\n    mediump vec3 leftTextureColor = texture2D(inputImageTexture, leftTextureCoordinate).rgb;\n    mediump vec3 rightTextureColor = texture2D(inputImageTexture, rightTextureCoordinate).rgb;\n    mediump vec3 topTextureColor = texture2D(inputImageTexture, topTextureCoordinate).rgb;\n    mediump vec3 bottomTextureColor = texture2D(inputImageTexture, bottomTextureCoordinate).rgb;\n\n    gl_FragColor = vec4((textureColor * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), 1.0);\n}");
        this.f3201s = f;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        this.f3200r = GLES20.glGetUniformLocation(m3011q(), "sharpness");
        this.f3202t = GLES20.glGetUniformLocation(m3011q(), "imageWidthFactor");
        this.f3203u = GLES20.glGetUniformLocation(m3011q(), "imageHeightFactor");
        m2641a(this.f3201s);
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        m3035a(this.f3202t, 1.0f / i);
        m3035a(this.f3203u, 1.0f / i2);
    }

    /* renamed from: a */
    public void m2641a(float f) {
        this.f3201s = f;
        String str = f3199v;
        TXCLog.m2913i(str, "set Sharpness " + f);
        m3035a(this.f3200r, this.f3201s);
    }
}
