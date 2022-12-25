package com.tencent.liteav.beauty.p115b.p116a;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;
import com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter;

/* renamed from: com.tencent.liteav.beauty.b.a.b */
/* loaded from: classes3.dex */
public class TXCBeautyBlend extends TXCGPUTwoInputFilter {

    /* renamed from: r */
    private int f2930r = -1;

    /* renamed from: s */
    private int f2931s = -1;

    /* renamed from: t */
    private int f2932t = -1;

    /* renamed from: x */
    private final String f2933x = "BeautyBlend";

    public TXCBeautyBlend() {
        super("varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        NativeLoad.getInstance();
        this.f2612a = NativeLoad.nativeLoadGLProgram(12);
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
        m2753r();
        return true;
    }

    /* renamed from: a */
    public void m2755a(float f) {
        TXCLog.m2913i("BeautyBlend", "setBrightLevel " + f);
        m3035a(this.f2931s, f);
    }

    /* renamed from: b */
    public void m2754b(float f) {
        Log.i("BeautyBlend", "setRuddyLevel level " + f);
        m3035a(this.f2932t, f / 2.0f);
    }

    /* renamed from: r */
    private void m2753r() {
        this.f2931s = GLES20.glGetUniformLocation(m3011q(), "whiteDegree");
        this.f2930r = GLES20.glGetUniformLocation(m3011q(), "contrast");
        this.f2932t = GLES20.glGetUniformLocation(m3011q(), "ruddyDegree");
    }
}
