package com.tencent.liteav.basic.p109e;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/* renamed from: com.tencent.liteav.basic.e.g */
/* loaded from: classes3.dex */
public class TXCGPUFilter {

    /* renamed from: a */
    protected int f2612a;

    /* renamed from: b */
    protected int f2613b;

    /* renamed from: c */
    protected int f2614c;

    /* renamed from: d */
    protected int f2615d;

    /* renamed from: e */
    protected int f2616e;

    /* renamed from: f */
    protected int f2617f;

    /* renamed from: g */
    protected boolean f2618g;

    /* renamed from: h */
    protected FloatBuffer f2619h;

    /* renamed from: i */
    protected FloatBuffer f2620i;

    /* renamed from: j */
    protected float[] f2621j;

    /* renamed from: k */
    protected float[] f2622k;

    /* renamed from: l */
    protected AbstractC3355a f2623l;

    /* renamed from: m */
    protected int f2624m;

    /* renamed from: n */
    protected int f2625n;

    /* renamed from: o */
    protected boolean f2626o;

    /* renamed from: p */
    protected boolean f2627p;

    /* renamed from: q */
    protected boolean f2628q;

    /* renamed from: r */
    private final LinkedList<Runnable> f2629r;

    /* renamed from: s */
    private final String f2630s;

    /* renamed from: t */
    private final String f2631t;

    /* renamed from: u */
    private boolean f2632u;

    /* renamed from: v */
    private int f2633v;

    /* renamed from: w */
    private float[] f2634w;

    /* renamed from: x */
    private String f2635x;

    /* compiled from: TXCGPUFilter.java */
    /* renamed from: com.tencent.liteav.basic.e.g$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3355a {
        /* renamed from: a */
        void mo458a(int i);
    }

    /* renamed from: d */
    public void mo2643d() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: i */
    public void mo2657i() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: j */
    public void mo2656j() {
    }

    public TXCGPUFilter() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }

    public TXCGPUFilter(String str, String str2) {
        this(str, str2, false);
    }

    public TXCGPUFilter(String str, String str2, boolean z) {
        this.f2632u = false;
        this.f2633v = -1;
        this.f2634w = null;
        this.f2624m = -1;
        this.f2625n = -1;
        this.f2626o = false;
        this.f2627p = false;
        this.f2628q = false;
        this.f2635x = "TXCGPUFilter";
        this.f2629r = new LinkedList<>();
        this.f2630s = str;
        this.f2631t = str2;
        this.f2628q = z;
        if (true == z) {
            TXCLog.m2913i(this.f2635x, "set Oes fileter");
        }
        this.f2619h = ByteBuffer.allocateDirect(TXCTextureRotationUtil.f2684e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f2621j = TXCTextureRotationUtil.f2684e;
        this.f2619h.put(this.f2621j).position(0);
        this.f2620i = ByteBuffer.allocateDirect(TXCTextureRotationUtil.f2680a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f2622k = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true);
        this.f2620i.put(this.f2622k).position(0);
    }

    /* renamed from: c */
    public boolean mo2653c() {
        this.f2612a = TXCOpenGlUtils.m2999a(this.f2630s, this.f2631t);
        if (this.f2612a != 0 && mo1321a()) {
            this.f2618g = true;
        } else {
            this.f2618g = false;
        }
        mo2643d();
        return this.f2618g;
    }

    /* renamed from: a */
    public void mo1353a(boolean z) {
        this.f2626o = z;
    }

    /* renamed from: b */
    public void m3022b(boolean z) {
        this.f2627p = z;
        String str = this.f2635x;
        TXCLog.m2913i(str, "set Nearest model " + z);
    }

    /* renamed from: a */
    public void m3029a(AbstractC3355a abstractC3355a) {
        this.f2632u = abstractC3355a != null;
        this.f2623l = abstractC3355a;
    }

    /* renamed from: a */
    public boolean mo1321a() {
        this.f2613b = GLES20.glGetAttribLocation(this.f2612a, "position");
        this.f2614c = GLES20.glGetUniformLocation(this.f2612a, "inputImageTexture");
        this.f2633v = GLES20.glGetUniformLocation(this.f2612a, "textureTransform");
        this.f2615d = GLES20.glGetAttribLocation(this.f2612a, "inputTextureCoordinate");
        return true;
    }

    /* renamed from: e */
    public void mo1351e() {
        GLES20.glDeleteProgram(this.f2612a);
        mo2293b();
        this.f2618g = false;
    }

    /* renamed from: b */
    public void mo2293b() {
        mo2691f();
        this.f2617f = -1;
        this.f2616e = -1;
    }

    /* renamed from: a */
    private static float[] m3027a(FloatBuffer floatBuffer) {
        if (floatBuffer.limit() <= 0) {
            return null;
        }
        float[] fArr = new float[floatBuffer.limit()];
        for (int i = 0; i < floatBuffer.limit(); i++) {
            fArr[i] = floatBuffer.get(i);
        }
        return fArr;
    }

    /* renamed from: f */
    public void mo2691f() {
        int i = this.f2624m;
        if (i != -1) {
            GLES20.glDeleteFramebuffers(1, new int[]{i}, 0);
            this.f2624m = -1;
        }
        int i2 = this.f2625n;
        if (i2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.f2625n = -1;
        }
    }

    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        if (this.f2617f == i2 && this.f2616e == i) {
            return;
        }
        this.f2616e = i;
        this.f2617f = i2;
        if (!this.f2626o) {
            return;
        }
        if (this.f2624m != -1) {
            mo2691f();
        }
        int[] iArr = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        this.f2624m = iArr[0];
        this.f2625n = TXCOpenGlUtils.m3007a(i, i2, 6408, 6408);
        GLES20.glBindFramebuffer(36160, this.f2624m);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.f2625n, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    /* renamed from: a */
    public void mo1324a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        float[] fArr;
        GLES20.glUseProgram(this.f2612a);
        m3017k();
        if (!this.f2618g) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.f2613b, 2, 5126, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(this.f2613b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.f2615d, 2, 5126, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.f2615d);
        int i2 = this.f2633v;
        if (i2 >= 0 && (fArr = this.f2634w) != null) {
            GLES20.glUniformMatrix4fv(i2, 1, false, fArr, 0);
        }
        if (i != -1) {
            GLES20.glActiveTexture(33984);
            if (true == this.f2628q) {
                GLES20.glBindTexture(36197, i);
            } else {
                GLES20.glBindTexture(3553, i);
            }
            GLES20.glUniform1i(this.f2614c, 0);
        }
        mo2657i();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.f2613b);
        GLES20.glDisableVertexAttribArray(this.f2615d);
        mo2656j();
        if (true == this.f2628q) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(3553, 0);
        }
    }

    /* renamed from: a */
    public void mo3010a(float[] fArr) {
        this.f2634w = fArr;
    }

    /* renamed from: g */
    public void m3019g() {
        if (this.f2622k != null) {
            for (int i = 0; i < 8; i += 2) {
                float[] fArr = this.f2622k;
                fArr[i] = 1.0f - fArr[i];
            }
            m3026a(this.f2621j, this.f2622k);
        }
    }

    /* renamed from: h */
    public void m3018h() {
        if (this.f2622k != null) {
            for (int i = 1; i < 8; i += 2) {
                float[] fArr = this.f2622k;
                fArr[i] = 1.0f - fArr[i];
            }
            m3026a(this.f2621j, this.f2622k);
        }
    }

    /* renamed from: b */
    public int mo1352b(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.f2618g) {
            return -1;
        }
        mo1324a(i, floatBuffer, floatBuffer2);
        AbstractC3355a abstractC3355a = this.f2623l;
        if (!(abstractC3355a instanceof AbstractC3355a)) {
            return 1;
        }
        abstractC3355a.mo458a(i);
        return 1;
    }

    /* renamed from: b */
    public int m3025b(int i) {
        return mo1352b(i, this.f2619h, this.f2620i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: k */
    public void m3017k() {
        while (!this.f2629r.isEmpty()) {
            this.f2629r.removeFirst().run();
        }
    }

    /* renamed from: a */
    public int mo1355a(int i, int i2, int i3) {
        if (!this.f2618g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, i2);
        mo1324a(i, this.f2619h, this.f2620i);
        AbstractC3355a abstractC3355a = this.f2623l;
        if (abstractC3355a instanceof AbstractC3355a) {
            abstractC3355a.mo458a(i3);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i3;
    }

    /* renamed from: a */
    public int mo2294a(int i) {
        return mo1355a(i, this.f2624m, this.f2625n);
    }

    /* renamed from: l */
    public int m3016l() {
        return this.f2625n;
    }

    /* renamed from: m */
    public int m3015m() {
        return this.f2624m;
    }

    /* renamed from: a */
    public void m3026a(float[] fArr, float[] fArr2) {
        this.f2621j = fArr;
        this.f2619h = ByteBuffer.allocateDirect(TXCTextureRotationUtil.f2684e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f2619h.put(fArr).position(0);
        this.f2622k = fArr2;
        this.f2620i = ByteBuffer.allocateDirect(TXCTextureRotationUtil.f2680a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.f2620i.put(fArr2).position(0);
    }

    /* renamed from: a */
    public float[] m3033a(int i, int i2, FloatBuffer floatBuffer, CropRect cropRect, int i3) {
        float[] m3027a;
        if (floatBuffer != null) {
            m3027a = m3027a(floatBuffer);
        } else if (4 == i3) {
            m3027a = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, false);
        } else {
            m3027a = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true);
        }
        if (cropRect != null) {
            int i4 = cropRect.f2533a;
            float f = i * 1.0f;
            float f2 = i4 / f;
            float f3 = ((i - i4) - cropRect.f2535c) / f;
            int i5 = cropRect.f2534b;
            float f4 = i2 * 1.0f;
            float f5 = i5 / f4;
            float f6 = ((i2 - i5) - cropRect.f2536d) / f4;
            for (int i6 = 0; i6 < m3027a.length / 2; i6++) {
                int i7 = i6 * 2;
                if (m3027a[i7] < 0.5f) {
                    m3027a[i7] = m3027a[i7] + f2;
                } else {
                    m3027a[i7] = m3027a[i7] - f3;
                }
                int i8 = i7 + 1;
                if (m3027a[i8] < 0.5f) {
                    m3027a[i8] = m3027a[i8] + f5;
                } else {
                    m3027a[i8] = m3027a[i8] - f6;
                }
            }
        }
        return m3027a;
    }

    /* renamed from: a */
    public void m3034a(int i, int i2, int i3, float[] fArr, float f, boolean z, boolean z2) {
        int i4;
        float[] fArr2;
        if (fArr == null) {
            fArr2 = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true);
            i4 = i;
        } else {
            i4 = i;
            fArr2 = fArr;
        }
        float f2 = i4;
        int i5 = i2;
        float f3 = i5;
        float f4 = f2 / f3;
        if (f4 > f) {
            i4 = (int) (f3 * f);
        } else if (f4 < f) {
            i5 = (int) (f2 / f);
        }
        float f5 = (1.0f - (i4 / f2)) / 2.0f;
        float f6 = (1.0f - (i5 / f3)) / 2.0f;
        for (int i6 = 0; i6 < fArr2.length / 2; i6++) {
            int i7 = i6 * 2;
            if (fArr2[i7] < 0.5f) {
                fArr2[i7] = fArr2[i7] + f5;
            } else {
                fArr2[i7] = fArr2[i7] - f5;
            }
            int i8 = i7 + 1;
            if (fArr2[i8] < 0.5f) {
                fArr2[i8] = fArr2[i8] + f6;
            } else {
                fArr2[i8] = fArr2[i8] - f6;
            }
        }
        int i9 = i3 / 90;
        for (int i10 = 0; i10 < i9; i10++) {
            float f7 = fArr2[0];
            float f8 = fArr2[1];
            fArr2[0] = fArr2[2];
            fArr2[1] = fArr2[3];
            fArr2[2] = fArr2[6];
            fArr2[3] = fArr2[7];
            fArr2[6] = fArr2[4];
            fArr2[7] = fArr2[5];
            fArr2[4] = f7;
            fArr2[5] = f8;
        }
        if (i9 == 0 || i9 == 2) {
            if (z) {
                fArr2[0] = 1.0f - fArr2[0];
                fArr2[2] = 1.0f - fArr2[2];
                fArr2[4] = 1.0f - fArr2[4];
                fArr2[6] = 1.0f - fArr2[6];
            }
            if (z2) {
                fArr2[1] = 1.0f - fArr2[1];
                fArr2[3] = 1.0f - fArr2[3];
                fArr2[5] = 1.0f - fArr2[5];
                fArr2[7] = 1.0f - fArr2[7];
            }
        } else {
            if (z2) {
                fArr2[0] = 1.0f - fArr2[0];
                fArr2[2] = 1.0f - fArr2[2];
                fArr2[4] = 1.0f - fArr2[4];
                fArr2[6] = 1.0f - fArr2[6];
            }
            if (z) {
                fArr2[1] = 1.0f - fArr2[1];
                fArr2[3] = 1.0f - fArr2[3];
                fArr2[5] = 1.0f - fArr2[5];
                fArr2[7] = 1.0f - fArr2[7];
            }
        }
        m3026a((float[]) TXCTextureRotationUtil.f2684e.clone(), fArr2);
    }

    /* renamed from: a */
    public void m3031a(int i, FloatBuffer floatBuffer) {
        float[] m3027a;
        if (floatBuffer == null) {
            m3027a = TXCTextureRotationUtil.m2991a(TXCRotation.NORMAL, false, true);
        } else {
            m3027a = m3027a(floatBuffer);
        }
        int i2 = i / 90;
        for (int i3 = 0; i3 < i2; i3++) {
            float f = m3027a[0];
            float f2 = m3027a[1];
            m3027a[0] = m3027a[2];
            m3027a[1] = m3027a[3];
            m3027a[2] = m3027a[6];
            m3027a[3] = m3027a[7];
            m3027a[6] = m3027a[4];
            m3027a[7] = m3027a[5];
            m3027a[4] = f;
            m3027a[5] = f2;
        }
        m3026a((float[]) TXCTextureRotationUtil.f2684e.clone(), m3027a);
    }

    /* renamed from: n */
    public boolean m3014n() {
        return this.f2618g;
    }

    /* renamed from: o */
    public int m3013o() {
        return this.f2616e;
    }

    /* renamed from: p */
    public int m3012p() {
        return this.f2617f;
    }

    /* renamed from: q */
    public int m3011q() {
        return this.f2612a;
    }

    /* renamed from: b */
    public void m3024b(final int i, final int i2) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.1
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1i(i, i2);
            }
        });
    }

    /* renamed from: a */
    public void m3035a(final int i, final float f) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.2
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1f(i, f);
            }
        });
    }

    /* renamed from: a */
    public void m3030a(final int i, final float[] fArr) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.3
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform2fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    /* renamed from: b */
    public void m3023b(final int i, final float[] fArr) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.4
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform3fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    /* renamed from: c */
    public void m3021c(final int i, final float[] fArr) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.5
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform4fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    /* renamed from: a */
    public void m3032a(final int i, final PointF pointF) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.6
            @Override // java.lang.Runnable
            public void run() {
                PointF pointF2 = pointF;
                GLES20.glUniform2fv(i, 1, new float[]{pointF2.x, pointF2.y}, 0);
            }
        });
    }

    /* renamed from: d */
    public void m3020d(final int i, final float[] fArr) {
        m3028a(new Runnable() { // from class: com.tencent.liteav.basic.e.g.7
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
            }
        });
    }

    /* renamed from: a */
    public void m3028a(Runnable runnable) {
        synchronized (this.f2629r) {
            this.f2629r.addLast(runnable);
        }
    }
}
