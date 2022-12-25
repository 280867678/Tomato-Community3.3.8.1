package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.c */
/* loaded from: classes3.dex */
public class TXCGPUBeautyFilter extends TXCBeautyInterFace {

    /* renamed from: r */
    private static final String f3024r = "c";

    /* renamed from: s */
    private TXCGPUFaceFilter f3026s;

    /* renamed from: t */
    private C3384a f3027t;

    /* renamed from: u */
    private TXCGPUSharpenFilter f3028u = null;

    /* renamed from: v */
    private int f3029v = -1;

    /* renamed from: w */
    private int f3030w = -1;

    /* renamed from: x */
    private float f3031x = 0.0f;

    /* renamed from: y */
    private float f3032y = 0.0f;

    /* renamed from: z */
    private float f3033z = 0.0f;

    /* renamed from: A */
    private float f3025A = 0.0f;

    /* renamed from: a */
    private static float m2712a(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        if (this.f3031x > 0.0f || this.f3032y > 0.0f || this.f3033z > 0.0f) {
            i = this.f3027t.mo1355a(this.f3031x != 0.0f ? this.f3026s.mo2294a(i) : i, i, i);
        }
        return this.f3025A > 0.0f ? this.f3028u.mo2294a(i) : i;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f3029v == i && this.f3030w == i2) {
            return;
        }
        String str = f3024r;
        Log.i(str, "onOutputSizeChanged mFrameWidth = " + i + "  mFrameHeight = " + i2);
        this.f3029v = i;
        this.f3030w = i2;
        mo2709c(this.f3029v, this.f3030w);
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public boolean mo2709c(int i, int i2) {
        this.f3029v = i;
        this.f3030w = i2;
        String str = f3024r;
        Log.i(str, "init mFrameWidth = " + i + "  mFrameHeight = " + i2);
        if (this.f3026s == null) {
            this.f3026s = new TXCGPUFaceFilter();
            this.f3026s.mo1353a(true);
            if (!this.f3026s.mo2653c()) {
                Log.e(f3024r, "mNewFaceFilter init Failed");
                return false;
            }
        }
        this.f3026s.mo1333a(this.f3029v, this.f3030w);
        if (this.f3027t == null) {
            this.f3027t = new C3384a();
            this.f3027t.mo1353a(true);
            if (!this.f3027t.mo2653c()) {
                Log.e(f3024r, "mBeautyCoreFilter init Failed");
                return false;
            }
        }
        this.f3027t.mo1333a(this.f3029v, this.f3030w);
        if (this.f3028u == null) {
            this.f3028u = new TXCGPUSharpenFilter();
            this.f3028u.mo1353a(true);
            if (!this.f3028u.mo2653c()) {
                Log.e(f3024r, "mSharpenessFilter init Failed");
                return false;
            }
        }
        this.f3028u.mo1333a(this.f3029v, this.f3030w);
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        C3384a c3384a = this.f3027t;
        if (c3384a != null) {
            c3384a.mo1351e();
            this.f3027t = null;
        }
        TXCGPUFaceFilter tXCGPUFaceFilter = this.f3026s;
        if (tXCGPUFaceFilter != null) {
            tXCGPUFaceFilter.mo1351e();
            this.f3026s = null;
        }
        TXCGPUSharpenFilter tXCGPUSharpenFilter = this.f3028u;
        if (tXCGPUSharpenFilter != null) {
            tXCGPUSharpenFilter.mo1351e();
            this.f3028u = null;
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: c */
    public void mo2710c(int i) {
        float f = i;
        this.f3031x = f;
        C3384a c3384a = this.f3027t;
        if (c3384a != null) {
            c3384a.m2705a(f);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: d */
    public void mo2708d(int i) {
        float f = i;
        this.f3032y = f;
        C3384a c3384a = this.f3027t;
        if (c3384a != null) {
            c3384a.m2704b(f);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: e */
    public void mo2707e(int i) {
        float f = i;
        this.f3033z = f;
        C3384a c3384a = this.f3027t;
        if (c3384a != null) {
            c3384a.m2703c(f);
        }
    }

    @Override // com.tencent.liteav.beauty.p115b.TXCBeautyInterFace
    /* renamed from: f */
    public void mo2706f(int i) {
        this.f3025A = i / 15.0f;
        TXCGPUSharpenFilter tXCGPUSharpenFilter = this.f3028u;
        if (tXCGPUSharpenFilter != null) {
            tXCGPUSharpenFilter.m2641a(this.f3025A);
        }
    }

    /* compiled from: TXCGPUBeautyFilter.java */
    /* renamed from: com.tencent.liteav.beauty.b.c$a */
    /* loaded from: classes3.dex */
    public static class C3384a extends TXCGPUThreeInputFilter {

        /* renamed from: x */
        private int f3034x = -1;

        /* renamed from: y */
        private int f3035y = -1;

        /* renamed from: z */
        private int f3036z = -1;

        public C3384a() {
            super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        }

        @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: c */
        public boolean mo2653c() {
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(1);
            if (this.f2612a != 0 && mo1321a()) {
                this.f2618g = true;
            } else {
                this.f2618g = false;
            }
            mo2643d();
            return this.f2618g;
        }

        @Override // com.tencent.liteav.beauty.p115b.TXCGPUThreeInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: a */
        public void mo1333a(int i, int i2) {
            if (this.f2617f == i2 && this.f2616e == i) {
                return;
            }
            super.mo1333a(i, i2);
            this.f3034x = GLES20.glGetUniformLocation(m3011q(), "smoothDegree");
            this.f3035y = GLES20.glGetUniformLocation(m3011q(), "brightDegree");
            this.f3036z = GLES20.glGetUniformLocation(m3011q(), "ruddyDegree");
        }

        @Override // com.tencent.liteav.beauty.p115b.TXCGPUThreeInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: a */
        public boolean mo1321a() {
            return super.mo1321a();
        }

        /* renamed from: a */
        public void m2705a(float f) {
            m3035a(this.f3034x, TXCGPUBeautyFilter.m2711b(f));
        }

        /* renamed from: b */
        public void m2704b(float f) {
            m3035a(this.f3035y, f / 3.0f);
        }

        /* renamed from: c */
        public void m2703c(float f) {
            m3035a(this.f3036z, (f / 10.0f) / 2.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static float m2711b(float f) {
        if (f > 1.0f) {
            double d = f;
            if (d < 2.5d) {
                f = m2712a((f - 1.0f) / 1.5f, 1.0f, 4.1f);
            } else if (f < 4.0f) {
                f = m2712a((f - 2.5f) / 1.5f, 4.1f, 5.6f);
            } else if (d < 5.5d) {
                f = m2712a((f - 4.0f) / 1.5f, 5.6f, 6.8f);
            } else if (d <= 7.0d) {
                f = m2712a((f - 5.5f) / 1.5f, 6.8f, 7.0f);
            }
            return f / 10.0f;
        }
        return 0.1f;
    }
}
