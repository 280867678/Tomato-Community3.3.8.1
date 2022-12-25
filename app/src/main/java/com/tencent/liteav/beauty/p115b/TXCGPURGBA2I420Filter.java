package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.v */
/* loaded from: classes3.dex */
public class TXCGPURGBA2I420Filter extends TXCGPUFilter {

    /* renamed from: C */
    private static float[] f3142C = {0.1826f, 0.6142f, 0.062f, -0.1006f, -0.3386f, 0.4392f, 0.4392f, -0.3989f, -0.0403f};

    /* renamed from: D */
    private static float[] f3143D = {0.256816f, 0.504154f, 0.0979137f, -0.148246f, -0.29102f, 0.439266f, 0.439271f, -0.367833f, -0.071438f};

    /* renamed from: E */
    private static float[] f3144E = {0.0625f, 0.5f, 0.5f};

    /* renamed from: B */
    private int f3146B;

    /* renamed from: r */
    private int f3147r = -1;

    /* renamed from: s */
    private int f3148s = -1;

    /* renamed from: t */
    private int f3149t = -1;

    /* renamed from: u */
    private int f3150u = -1;

    /* renamed from: v */
    private int f3151v = -1;

    /* renamed from: w */
    private int f3152w = -1;

    /* renamed from: x */
    private int f3153x = -1;

    /* renamed from: y */
    private int f3154y = -1;

    /* renamed from: z */
    private int f3155z = -1;

    /* renamed from: A */
    private String f3145A = "RGBA2I420Filter";

    public TXCGPURGBA2I420Filter(int i) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.f3146B = 1;
        this.f3146B = i;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: c */
    public boolean mo2653c() {
        int i = this.f3146B;
        if (1 == i) {
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(8);
            TXCLog.m2913i(this.f3145A, "RGB-->I420 init!");
        } else if (3 == i) {
            TXCLog.m2913i(this.f3145A, "RGB-->NV21 init!");
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(11);
        } else if (2 == i) {
            TXCLog.m2913i(this.f3145A, "RGBA Format init!");
            return super.mo2653c();
        } else {
            String str = this.f3145A;
            TXCLog.m2913i(str, "don't support format " + this.f3146B + " use default I420");
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(8);
        }
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
        this.f3147r = GLES20.glGetUniformLocation(this.f2612a, "width");
        this.f3148s = GLES20.glGetUniformLocation(this.f2612a, "height");
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            TXCLog.m2914e(this.f3145A, "width or height is error!");
            return;
        }
        super.mo1333a(i, i2);
        String str = this.f3145A;
        TXCLog.m2913i(str, "RGBA2I420Filter width " + i + " height " + i2);
        m3035a(this.f3147r, (float) i);
        m3035a(this.f3148s, (float) i2);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: d */
    public void mo2643d() {
        super.mo2643d();
    }
}
