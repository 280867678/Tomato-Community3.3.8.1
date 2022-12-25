package com.tencent.liteav.beauty.p115b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

/* renamed from: com.tencent.liteav.beauty.b.ah */
/* loaded from: classes3.dex */
public class TXCGPUWatermarkFilter extends TXCGPUFilter {

    /* renamed from: B */
    private String f2989B;

    /* renamed from: r */
    protected C3383a[] f2990r;

    /* renamed from: s */
    protected List<TXCVideoPreprocessor.C3397d> f2991s;

    /* renamed from: t */
    protected boolean f2992t;

    /* renamed from: u */
    protected int f2993u;

    /* renamed from: w */
    protected ShortBuffer f2994w;

    /* renamed from: x */
    private C3383a f2995x;

    /* renamed from: y */
    private int f2996y;

    /* renamed from: v */
    protected static final short[] f2987v = {1, 2, 0, 2, 0, 3};

    /* renamed from: z */
    private static final float[] f2988z = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    /* renamed from: A */
    private static final float[] f2986A = {0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};

    /* compiled from: TXCGPUWatermarkFilter.java */
    /* renamed from: com.tencent.liteav.beauty.b.ah$a */
    /* loaded from: classes3.dex */
    public class C3383a {

        /* renamed from: c */
        public Bitmap f2999c;

        /* renamed from: a */
        public FloatBuffer f2997a = null;

        /* renamed from: b */
        public FloatBuffer f2998b = null;

        /* renamed from: d */
        public int[] f3000d = null;

        public C3383a() {
        }
    }

    public TXCGPUWatermarkFilter(String str, String str2) {
        super(str, str2);
        this.f2990r = null;
        this.f2995x = null;
        this.f2991s = null;
        this.f2992t = false;
        this.f2993u = 1;
        this.f2996y = 1;
        this.f2994w = null;
        this.f2989B = "GPUWatermark";
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(f2987v.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.f2994w = allocateDirect.asShortBuffer();
        this.f2994w.put(f2987v);
        this.f2994w.position(0);
        this.f2626o = true;
    }

    public TXCGPUWatermarkFilter() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: e */
    public void mo1351e() {
        super.mo1351e();
        this.f2992t = false;
        m2725r();
    }

    /* renamed from: d */
    public void m2726d(boolean z) {
        this.f2992t = z;
    }

    /* renamed from: a */
    public void m2729a(Bitmap bitmap, float f, float f2, float f3, int i) {
        if (bitmap == null) {
            C3383a[] c3383aArr = this.f2990r;
            if (c3383aArr == null || c3383aArr[i] == null) {
                return;
            }
            String str = this.f2989B;
            Log.i(str, "release " + i + " water mark!");
            C3383a[] c3383aArr2 = this.f2990r;
            if (c3383aArr2[i].f3000d != null) {
                GLES20.glDeleteTextures(1, c3383aArr2[i].f3000d, 0);
            }
            C3383a[] c3383aArr3 = this.f2990r;
            c3383aArr3[i].f3000d = null;
            c3383aArr3[i].f2999c = null;
            c3383aArr3[i] = null;
            return;
        }
        C3383a[] c3383aArr4 = this.f2990r;
        if (c3383aArr4[i] == null || i >= c3383aArr4.length) {
            Log.e(this.f2989B, "index is too large for mSzWaterMark!");
            return;
        }
        m2731a(bitmap.getWidth(), bitmap.getHeight(), f, f2, f3, i);
        C3383a[] c3383aArr5 = this.f2990r;
        if (c3383aArr5[i].f3000d == null) {
            c3383aArr5[i].f3000d = new int[1];
            GLES20.glGenTextures(1, c3383aArr5[i].f3000d, 0);
            GLES20.glBindTexture(3553, this.f2990r[i].f3000d[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
        }
        C3383a[] c3383aArr6 = this.f2990r;
        if (c3383aArr6[i].f2999c == null || !c3383aArr6[i].f2999c.equals(bitmap)) {
            GLES20.glBindTexture(3553, this.f2990r[i].f3000d[0]);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        }
        this.f2990r[i].f2999c = bitmap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m2731a(int i, int i2, float f, float f2, float f3, int i3) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(f2988z.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.f2990r[i3].f2997a = allocateDirect.asFloatBuffer();
        float[] fArr = new float[f2988z.length];
        fArr[0] = (f * 2.0f) - 1.0f;
        fArr[1] = 1.0f - (f2 * 2.0f);
        fArr[2] = fArr[0];
        fArr[3] = fArr[1] - (((((i2 / i) * f3) * this.f2616e) / this.f2617f) * 2.0f);
        fArr[4] = fArr[0] + (f3 * 2.0f);
        fArr[5] = fArr[3];
        fArr[6] = fArr[4];
        fArr[7] = fArr[1];
        for (int i4 = 1; i4 <= 7; i4 += 2) {
            fArr[i4] = fArr[i4] * (-1.0f);
        }
        this.f2990r[i3].f2997a.put(fArr);
        this.f2990r[i3].f2997a.position(0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(f2986A.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.f2990r[i3].f2998b = allocateDirect2.asFloatBuffer();
        this.f2990r[i3].f2998b.put(f2986A);
        this.f2990r[i3].f2998b.position(0);
    }

    /* renamed from: a */
    public void m2730a(Bitmap bitmap, float f, float f2, float f3) {
        if (this.f2990r == null) {
            this.f2990r = new C3383a[1];
        }
        C3383a[] c3383aArr = this.f2990r;
        if (c3383aArr[0] == null) {
            c3383aArr[0] = new C3383a();
        }
        m2729a(bitmap, f, f2, f3, 0);
        this.f2995x = this.f2990r[0];
    }

    /* renamed from: a */
    public void m2728a(List<TXCVideoPreprocessor.C3397d> list) {
        List<TXCVideoPreprocessor.C3397d> list2 = this.f2991s;
        if (list2 != null && m2727a(list2, list)) {
            Log.i(this.f2989B, "Same markList");
            return;
        }
        this.f2991s = list;
        C3383a[] c3383aArr = this.f2990r;
        if (c3383aArr != null && c3383aArr.length > 1) {
            int i = this.f2996y;
            while (true) {
                C3383a[] c3383aArr2 = this.f2990r;
                if (i >= c3383aArr2.length) {
                    break;
                }
                if (c3383aArr2[i].f3000d != null) {
                    GLES20.glDeleteTextures(1, c3383aArr2[i].f3000d, 0);
                }
                i++;
            }
        }
        this.f2990r = new C3383a[list.size() + this.f2996y];
        this.f2990r[0] = this.f2995x;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TXCVideoPreprocessor.C3397d c3397d = list.get(i2);
            if (c3397d != null) {
                this.f2990r[this.f2996y + i2] = new C3383a();
                m2729a(c3397d.f3256a, c3397d.f3257b, c3397d.f3258c, c3397d.f3259d, i2 + this.f2996y);
            }
        }
    }

    /* renamed from: a */
    private boolean m2727a(List<TXCVideoPreprocessor.C3397d> list, List<TXCVideoPreprocessor.C3397d> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            TXCVideoPreprocessor.C3397d c3397d = list.get(i);
            TXCVideoPreprocessor.C3397d c3397d2 = list2.get(i);
            if (!c3397d.f3256a.equals(c3397d2.f3256a) || c3397d.f3257b != c3397d2.f3257b || c3397d.f3258c != c3397d2.f3258c || c3397d.f3259d != c3397d2.f3259d) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: r */
    private void m2725r() {
        if (this.f2990r != null) {
            int i = 0;
            while (true) {
                C3383a[] c3383aArr = this.f2990r;
                if (i >= c3383aArr.length) {
                    break;
                }
                if (c3383aArr[i] != null) {
                    if (c3383aArr[i].f3000d != null) {
                        GLES20.glDeleteTextures(1, c3383aArr[i].f3000d, 0);
                    }
                    C3383a[] c3383aArr2 = this.f2990r;
                    c3383aArr2[i].f3000d = null;
                    c3383aArr2[i].f2999c = null;
                    c3383aArr2[i] = null;
                }
                i++;
            }
        }
        this.f2990r = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: j */
    public void mo2656j() {
        super.mo2656j();
        if (this.f2992t) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(this.f2993u, 771);
            GLES20.glActiveTexture(33984);
            int i = 0;
            while (true) {
                C3383a[] c3383aArr = this.f2990r;
                if (i < c3383aArr.length) {
                    if (c3383aArr[i] != null) {
                        GLES20.glBindTexture(3553, c3383aArr[i].f3000d[0]);
                        GLES20.glUniform1i(this.f2614c, 0);
                        GLES20.glVertexAttribPointer(this.f2613b, 2, 5126, false, 8, (Buffer) this.f2990r[i].f2997a);
                        GLES20.glEnableVertexAttribArray(this.f2613b);
                        GLES20.glVertexAttribPointer(this.f2615d, 4, 5126, false, 16, (Buffer) this.f2990r[i].f2998b);
                        GLES20.glEnableVertexAttribArray(this.f2615d);
                        GLES20.glDrawElements(4, f2987v.length, 5123, this.f2994w);
                        GLES20.glDisableVertexAttribArray(this.f2613b);
                        GLES20.glDisableVertexAttribArray(this.f2615d);
                    }
                    i++;
                } else {
                    GLES20.glDisable(3042);
                    return;
                }
            }
        }
    }
}
