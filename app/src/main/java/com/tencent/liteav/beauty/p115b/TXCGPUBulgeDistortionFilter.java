package com.tencent.liteav.beauty.p115b;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;

/* renamed from: com.tencent.liteav.beauty.b.e */
/* loaded from: classes3.dex */
public class TXCGPUBulgeDistortionFilter extends TXCGPUFilter {

    /* renamed from: z */
    private static String f3040z = "BulgeDistortion";

    /* renamed from: r */
    private float f3041r;

    /* renamed from: s */
    private int f3042s;

    /* renamed from: t */
    private float f3043t;

    /* renamed from: u */
    private int f3044u;

    /* renamed from: v */
    private PointF f3045v;

    /* renamed from: w */
    private int f3046w;

    /* renamed from: x */
    private float f3047x;

    /* renamed from: y */
    private int f3048y;

    public TXCGPUBulgeDistortionFilter() {
        this(0.5f, 0.3f, new PointF(0.5f, 0.5f));
    }

    public TXCGPUBulgeDistortionFilter(float f, float f2, PointF pointF) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nuniform highp float aspectRatio;\nuniform highp vec2 center;\nuniform highp float radius;\nuniform highp float scale;\n\nvoid main()\n{\nhighp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\nhighp float dist = distance(center, textureCoordinateToUse);\ntextureCoordinateToUse = textureCoordinate;\n\nif (dist < radius)\n{\ntextureCoordinateToUse -= center;\nhighp float percent = 1.0 - ((radius - dist) / radius) * scale;\npercent = percent * percent;\n\ntextureCoordinateToUse = textureCoordinateToUse * percent;\ntextureCoordinateToUse += center;\n}\n\ngl_FragColor = texture2D(inputImageTexture, textureCoordinateToUse );    \n}\n");
        this.f3043t = f;
        this.f3041r = f2;
        this.f3045v = pointF;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            TXCLog.m2914e(f3040z, "TXCGPUBulgeDistortionFilter init Failed!");
            return false;
        }
        this.f3042s = GLES20.glGetUniformLocation(m3011q(), "scale");
        this.f3044u = GLES20.glGetUniformLocation(m3011q(), "radius");
        this.f3046w = GLES20.glGetUniformLocation(m3011q(), "center");
        this.f3048y = GLES20.glGetUniformLocation(m3011q(), "aspectRatio");
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
        m2701a(this.f3043t);
        m2699b(this.f3041r);
        m2700a(this.f3045v);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        this.f3047x = i2 / i;
        m2698c(this.f3047x);
        super.mo1333a(i, i2);
    }

    /* renamed from: c */
    private void m2698c(float f) {
        this.f3047x = f;
        m3035a(this.f3048y, f);
    }

    /* renamed from: a */
    public void m2701a(float f) {
        this.f3043t = f;
        m3035a(this.f3044u, f);
    }

    /* renamed from: b */
    public void m2699b(float f) {
        this.f3041r = f;
        m3035a(this.f3042s, f);
    }

    /* renamed from: a */
    public void m2700a(PointF pointF) {
        this.f3045v = pointF;
        m3032a(this.f3046w, pointF);
    }
}
