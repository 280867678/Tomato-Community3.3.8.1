package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.NativeLoad;

/* renamed from: com.tencent.liteav.beauty.b.i */
/* loaded from: classes3.dex */
public class TXCGPUFaceFilter extends TXCGPUFilter {

    /* renamed from: A */
    private int[] f3060A;

    /* renamed from: B */
    private float f3061B = 4.0f;

    /* renamed from: r */
    int f3062r;

    /* renamed from: s */
    int f3063s;

    /* renamed from: t */
    boolean f3064t;

    /* renamed from: u */
    private TXCGPUBoxBlurFilter f3065u;

    /* renamed from: v */
    private TXCGPUFilter f3066v;

    /* renamed from: w */
    private C3387c f3067w;

    /* renamed from: x */
    private C3385a f3068x;

    /* renamed from: y */
    private C3386b f3069y;

    /* renamed from: z */
    private int[] f3070z;

    /* compiled from: TXCGPUFaceFilter.java */
    /* renamed from: com.tencent.liteav.beauty.b.i$c */
    /* loaded from: classes3.dex */
    private static class C3387c extends TXCGPUTwoInputFilter {

        /* renamed from: r */
        int f3071r;

        /* renamed from: s */
        int f3072s;

        /* renamed from: t */
        float f3073t = 1.5f;

        public C3387c() {
            super(null, null);
        }

        @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: c */
        public boolean mo2653c() {
            NativeLoad.getInstance();
            this.f2612a = NativeLoad.nativeLoadGLProgram(2);
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
            if (super.mo1321a()) {
                this.f3071r = GLES20.glGetUniformLocation(this.f2612a, "texelWidthOffset");
                this.f3072s = GLES20.glGetUniformLocation(this.f2612a, "texelHeightOffset");
                return true;
            }
            return false;
        }

        /* renamed from: a */
        void m2693a(float f) {
            this.f3073t = f;
            m3035a(this.f3071r, this.f3073t / this.f2616e);
            m3035a(this.f3072s, this.f3073t / this.f2617f);
        }

        @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: a */
        public void mo1333a(int i, int i2) {
            super.mo1333a(i, i2);
            m2693a(this.f3073t);
        }
    }

    /* compiled from: TXCGPUFaceFilter.java */
    /* renamed from: com.tencent.liteav.beauty.b.i$a */
    /* loaded from: classes3.dex */
    private static class C3385a extends TXCGPUTwoInputFilter {
        public C3385a(String str) {
            super(str);
        }

        @Override // com.tencent.liteav.beauty.p115b.TXCGPUTwoInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: a */
        public boolean mo1321a() {
            return super.mo1321a();
        }
    }

    /* compiled from: TXCGPUFaceFilter.java */
    /* renamed from: com.tencent.liteav.beauty.b.i$b */
    /* loaded from: classes3.dex */
    private static class C3386b extends TXCGPUThreeInputFilter {
        public C3386b(String str) {
            super(str);
        }

        @Override // com.tencent.liteav.beauty.p115b.TXCGPUThreeInputFilter, com.tencent.liteav.basic.p109e.TXCGPUFilter
        /* renamed from: a */
        public boolean mo1321a() {
            return super.mo1321a();
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        if (mo1321a) {
            this.f3065u = new TXCGPUBoxBlurFilter();
            if (mo1321a) {
                mo1321a = this.f3065u.mo2653c();
            }
            this.f3067w = new C3387c();
            if (mo1321a) {
                mo1321a = this.f3067w.mo2653c();
            }
            this.f3068x = new C3385a("precision highp float;  \nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2;  \nvarying vec2 textureCoordinate;  \nvarying vec2 textureCoordinate2;  \nvoid main()  \n{  \n\tgl_FragColor = texture2D(inputImageTexture2, textureCoordinate2) - texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture2, textureCoordinate2);  \n}  \n");
            if (mo1321a) {
                mo1321a = this.f3068x.mo2653c();
            }
            this.f3069y = new C3386b("precision highp float;   \nuniform sampler2D inputImageTexture;   \nuniform sampler2D inputImageTexture2;  \nuniform sampler2D inputImageTexture3;   \nvarying vec2 textureCoordinate;   \nvarying vec2 textureCoordinate2;  \nvarying vec2 textureCoordinate3;    \nvoid main()   \n{   \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture3, textureCoordinate3) + texture2D(inputImageTexture2, textureCoordinate2);   \n}   \n");
            if (mo1321a) {
                mo1321a = this.f3069y.mo2653c();
            }
            this.f3066v = new TXCGPUFilter();
            this.f3066v.mo1353a(true);
            if (mo1321a) {
                mo1321a = this.f3066v.mo2653c();
            }
            if (mo1321a) {
                return true;
            }
        }
        mo1351e();
        return false;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2617f == i2 && this.f2616e == i) {
            return;
        }
        super.mo1333a(i, i2);
        if (!this.f3064t) {
            if (i < i2) {
                if (i < 540) {
                    this.f3061B = 1.0f;
                } else {
                    this.f3061B = 4.0f;
                }
            } else if (i2 < 540) {
                this.f3061B = 1.0f;
            } else {
                this.f3061B = 4.0f;
            }
        }
        float f = this.f3061B;
        this.f3062r = (int) (i / f);
        this.f3063s = (int) (i2 / f);
        this.f3066v.mo1333a(this.f3062r, this.f3063s);
        this.f3067w.mo1333a(this.f3062r, this.f3063s);
        this.f3068x.mo1333a(this.f3062r, this.f3063s);
        this.f3069y.mo1333a(i, i2);
        this.f3065u.mo1333a(this.f3062r, this.f3063s);
        int[] iArr = this.f3070z;
        if (iArr != null) {
            GLES20.glDeleteFramebuffers(iArr.length, iArr, 0);
            GLES20.glDeleteTextures(this.f3070z.length, this.f3060A, 0);
            this.f3070z = null;
            this.f3060A = null;
        }
        this.f3070z = new int[8];
        int[] iArr2 = this.f3070z;
        this.f3060A = new int[iArr2.length];
        GLES20.glGenFramebuffers(iArr2.length, iArr2, 0);
        GLES20.glGenTextures(this.f3070z.length, this.f3060A, 0);
        for (int i3 = 0; i3 < this.f3070z.length; i3++) {
            GLES20.glBindTexture(3553, this.f3060A[i3]);
            if (i3 >= 5) {
                GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
            } else {
                GLES20.glTexImage2D(3553, 0, 6408, this.f3062r, this.f3063s, 0, 6408, 5121, null);
            }
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.f3070z[i3]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f3060A[i3], 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo2294a(int i) {
        int i2;
        if (this.f3061B != 1.0f) {
            GLES20.glViewport(0, 0, this.f3062r, this.f3063s);
            i2 = this.f3066v.mo2294a(i);
        } else {
            i2 = i;
        }
        int mo1355a = this.f3065u.mo1355a(i2, this.f3070z[4], this.f3060A[4]);
        int m2743a = this.f3067w.m2743a(i2, mo1355a, this.f3070z[0], this.f3060A[0]);
        int m2743a2 = this.f3068x.m2743a(m2743a, mo1355a, this.f3070z[1], this.f3060A[1]);
        int mo1355a2 = this.f3065u.mo1355a(m2743a, this.f3070z[2], this.f3060A[2]);
        int mo1355a3 = this.f3065u.mo1355a(m2743a2, this.f3070z[3], this.f3060A[3]);
        if (this.f3061B != 1.0f) {
            GLES20.glViewport(0, 0, this.f2616e, this.f2617f);
            mo1355a2 = this.f3066v.mo1355a(mo1355a2, this.f3070z[5], this.f3060A[5]);
            mo1355a3 = this.f3066v.mo1355a(mo1355a3, this.f3070z[6], this.f3060A[6]);
        }
        return this.f3069y.m2746a(mo1355a2, mo1355a3, i, this.f3070z[7], this.f3060A[7]);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        if (this.f2618g) {
            super.mo2293b();
            this.f3065u.mo1351e();
            this.f3067w.mo1351e();
            this.f3068x.mo1351e();
            this.f3069y.mo1351e();
            this.f3066v.mo1351e();
            int[] iArr = this.f3070z;
            if (iArr != null) {
                GLES20.glDeleteFramebuffers(iArr.length, iArr, 0);
                GLES20.glDeleteTextures(this.f3070z.length, this.f3060A, 0);
                this.f3070z = null;
            }
            this.f3060A = null;
        }
    }
}
