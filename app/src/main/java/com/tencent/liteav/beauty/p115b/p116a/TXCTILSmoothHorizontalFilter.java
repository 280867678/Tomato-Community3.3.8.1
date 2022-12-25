package com.tencent.liteav.beauty.p115b.p116a;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.a.d */
/* loaded from: classes3.dex */
public class TXCTILSmoothHorizontalFilter extends TXCGPUFilter {

    /* renamed from: r */
    private int f2934r = -1;

    /* renamed from: s */
    private int f2935s = -1;

    /* renamed from: t */
    private float f2936t = 4.0f;

    /* renamed from: u */
    private String f2937u = "SmoothHorizontal";

    /* JADX INFO: Access modifiers changed from: package-private */
    public TXCTILSmoothHorizontalFilter() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        NativeLoad.getInstance();
        this.f2612a = NativeLoad.nativeLoadGLProgram(13);
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        super.mo1321a();
        m2752r();
        return true;
    }

    /* renamed from: r */
    public void m2752r() {
        this.f2934r = GLES20.glGetUniformLocation(m3011q(), "texelWidthOffset");
        this.f2935s = GLES20.glGetUniformLocation(m3011q(), "texelHeightOffset");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        if (i > i2) {
            if (i2 < 540) {
                this.f2936t = 2.0f;
            } else {
                this.f2936t = 4.0f;
            }
        } else if (i < 540) {
            this.f2936t = 2.0f;
        } else {
            this.f2936t = 4.0f;
        }
        String str = this.f2937u;
        TXCLog.m2913i(str, "m_textureRation " + this.f2936t);
        m3035a(this.f2934r, this.f2936t / ((float) i));
        m3035a(this.f2935s, this.f2936t / ((float) i2));
    }
}
