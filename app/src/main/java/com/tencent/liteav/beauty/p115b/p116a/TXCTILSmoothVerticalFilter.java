package com.tencent.liteav.beauty.p115b.p116a;

import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;
import com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter;

/* renamed from: com.tencent.liteav.beauty.b.a.e */
/* loaded from: classes3.dex */
public class TXCTILSmoothVerticalFilter extends TXCGPUTwoInputFilter {

    /* renamed from: r */
    private int f2939r = -1;

    /* renamed from: s */
    private int f2940s = -1;

    /* renamed from: t */
    private int f2941t = -1;

    /* renamed from: x */
    private int f2942x = -1;

    /* renamed from: y */
    private float f2943y = 2.0f;

    /* renamed from: z */
    private float f2944z = 0.5f;

    /* renamed from: A */
    private String f2938A = "SmoothVertical";

    /* JADX INFO: Access modifiers changed from: package-private */
    public TXCTILSmoothVerticalFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        if (Build.BRAND.equals("samsung") && Build.MODEL.equals("GT-I9500") && Build.VERSION.RELEASE.equals("4.3")) {
            Log.d(this.f2938A, "SAMSUNG_S4 GT-I9500 + Android 4.3; use diffrent shader!");
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(15);
        } else {
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(5);
        }
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        super.mo1321a();
        m2750r();
        return true;
    }

    /* renamed from: a */
    public void m2751a(float f) {
        this.f2944z = f;
        String str = this.f2938A;
        TXCLog.m2913i(str, "setBeautyLevel " + f);
        m3035a(this.f2941t, f);
    }

    /* renamed from: r */
    public void m2750r() {
        this.f2939r = GLES20.glGetUniformLocation(m3011q(), "texelWidthOffset");
        this.f2940s = GLES20.glGetUniformLocation(m3011q(), "texelHeightOffset");
        this.f2941t = GLES20.glGetUniformLocation(m3011q(), "smoothDegree");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        if (i > i2) {
            if (i2 < 540) {
                this.f2943y = 2.0f;
            } else {
                this.f2943y = 4.0f;
            }
        } else if (i < 540) {
            this.f2943y = 2.0f;
        } else {
            this.f2943y = 4.0f;
        }
        String str = this.f2938A;
        TXCLog.m2913i(str, "m_textureRation " + this.f2943y);
        m3035a(this.f2939r, this.f2943y / ((float) i));
        m3035a(this.f2940s, this.f2943y / ((float) i2));
    }
}
