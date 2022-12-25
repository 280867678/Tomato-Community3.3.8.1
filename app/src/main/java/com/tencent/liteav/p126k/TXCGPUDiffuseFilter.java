package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.beauty.TXCSavePreFrameFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUColorBrushFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUGridBlendFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUGridShapeFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.b */
/* loaded from: classes3.dex */
public class TXCGPUDiffuseFilter {

    /* renamed from: j */
    private static String f4462j = "Diffuse";

    /* renamed from: m */
    private TXCVideoEffect.C3535c.EnumC3536a f4474m;

    /* renamed from: n */
    private TXCVideoEffect.C3535c.EnumC3536a f4475n;

    /* renamed from: a */
    private TXCSavePreFrameFilter f4463a = null;

    /* renamed from: b */
    private TXCGPUGridShapeFilter f4464b = null;

    /* renamed from: c */
    private TXCScaleFilter f4465c = null;

    /* renamed from: d */
    private TXCScaleFilter f4466d = null;

    /* renamed from: e */
    private TXCGPUGridBlendFilter f4467e = null;

    /* renamed from: f */
    private TXCGPUFilter f4468f = null;

    /* renamed from: g */
    private TXCGPUColorBrushFilter f4469g = null;

    /* renamed from: h */
    private int f4470h = 0;

    /* renamed from: i */
    private int f4471i = 0;

    /* renamed from: k */
    private int f4472k = 1;

    /* renamed from: l */
    private TXCVideoEffect.C3535c f4473l = null;

    /* renamed from: o */
    private int[] f4476o = null;

    /* renamed from: p */
    private TXCOpenGlUtils.C3356a f4477p = null;

    /* renamed from: q */
    private TXCOpenGlUtils.C3356a f4478q = null;

    /* renamed from: r */
    private float f4479r = 0.0f;

    public TXCGPUDiffuseFilter() {
        TXCVideoEffect.C3535c.EnumC3536a enumC3536a = TXCVideoEffect.C3535c.EnumC3536a.MODE_NONE;
        this.f4474m = enumC3536a;
        this.f4475n = enumC3536a;
    }

    /* renamed from: a */
    public boolean m1368a(int i, int i2) {
        this.f4470h = i;
        this.f4471i = i2;
        TXCOpenGlUtils.C3356a c3356a = this.f4477p;
        if (c3356a == null || i != c3356a.f2673c || i2 != c3356a.f2674d) {
            this.f4477p = TXCOpenGlUtils.m3002a(this.f4477p);
            this.f4477p = TXCOpenGlUtils.m3001a(this.f4477p, i, i2);
        }
        TXCOpenGlUtils.C3356a c3356a2 = this.f4478q;
        if (c3356a2 == null || i != c3356a2.f2673c || i2 != c3356a2.f2674d) {
            this.f4478q = TXCOpenGlUtils.m3002a(this.f4478q);
            this.f4478q = TXCOpenGlUtils.m3001a(this.f4478q, i, i2);
        }
        return m1363c(i, i2);
    }

    /* renamed from: c */
    private boolean m1363c(int i, int i2) {
        if (this.f4463a == null) {
            this.f4463a = new TXCSavePreFrameFilter();
            if (!this.f4463a.m2638a(i, i2)) {
                Log.e(f4462j, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4463a;
        if (tXCSavePreFrameFilter != null) {
            tXCSavePreFrameFilter.m2636b(this.f4472k);
            this.f4463a.m2635b(i, i2);
        }
        if (this.f4464b == null) {
            this.f4464b = new TXCGPUGridShapeFilter();
            if (!this.f4464b.m2677a(i, i2)) {
                Log.e(f4462j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        TXCGPUGridShapeFilter tXCGPUGridShapeFilter = this.f4464b;
        if (tXCGPUGridShapeFilter != null) {
            tXCGPUGridShapeFilter.m2674b(i, i2);
        }
        if (this.f4465c == null) {
            this.f4465c = new TXCScaleFilter();
            this.f4465c.mo1353a(true);
            if (!this.f4465c.mo2653c()) {
                Log.e(f4462j, "mScaleFilter init Failed!");
                return false;
            }
        }
        TXCScaleFilter tXCScaleFilter = this.f4465c;
        if (tXCScaleFilter != null) {
            tXCScaleFilter.mo1333a(i, i2);
        }
        if (this.f4466d == null) {
            this.f4466d = new TXCScaleFilter();
            this.f4466d.mo1353a(true);
            if (!this.f4466d.mo2653c()) {
                Log.e(f4462j, "mScaleFilter2 init Failed!");
                return false;
            }
        }
        TXCScaleFilter tXCScaleFilter2 = this.f4466d;
        if (tXCScaleFilter2 != null) {
            tXCScaleFilter2.mo1333a(i, i2);
        }
        if (this.f4467e == null) {
            this.f4467e = new TXCGPUGridBlendFilter();
            this.f4467e.mo1353a(true);
            if (!this.f4467e.mo2653c()) {
                Log.e(f4462j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        TXCGPUGridBlendFilter tXCGPUGridBlendFilter = this.f4467e;
        if (tXCGPUGridBlendFilter != null) {
            tXCGPUGridBlendFilter.mo1333a(i, i2);
        }
        if (this.f4468f == null) {
            this.f4468f = new TXCGPUFilter();
            if (!this.f4468f.mo2653c()) {
                Log.e(f4462j, "mDrawFilter init Failed!");
                return false;
            }
        }
        TXCGPUFilter tXCGPUFilter = this.f4468f;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
        if (this.f4469g == null) {
            this.f4469g = new TXCGPUColorBrushFilter();
            if (!this.f4469g.mo2653c()) {
                Log.e(f4462j, "mColorBrushFilter init Failed!");
                return false;
            }
        }
        TXCGPUColorBrushFilter tXCGPUColorBrushFilter = this.f4469g;
        if (tXCGPUColorBrushFilter != null) {
            tXCGPUColorBrushFilter.mo1333a(i, i2);
        }
        return true;
    }

    /* renamed from: b */
    public void m1365b(int i, int i2) {
        if (i == this.f4470h && i2 == this.f4471i) {
            return;
        }
        m1368a(i, i2);
    }

    /* renamed from: b */
    private void m1366b() {
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4463a;
        if (tXCSavePreFrameFilter != null) {
            tXCSavePreFrameFilter.m2637b();
            this.f4463a = null;
        }
        TXCGPUGridShapeFilter tXCGPUGridShapeFilter = this.f4464b;
        if (tXCGPUGridShapeFilter != null) {
            tXCGPUGridShapeFilter.m2679a();
            this.f4464b = null;
        }
        TXCScaleFilter tXCScaleFilter = this.f4465c;
        if (tXCScaleFilter != null) {
            tXCScaleFilter.mo1351e();
            this.f4465c = null;
        }
        TXCGPUGridBlendFilter tXCGPUGridBlendFilter = this.f4467e;
        if (tXCGPUGridBlendFilter != null) {
            tXCGPUGridBlendFilter.mo1351e();
            this.f4467e = null;
        }
        TXCGPUFilter tXCGPUFilter = this.f4468f;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f4468f = null;
        }
        TXCGPUColorBrushFilter tXCGPUColorBrushFilter = this.f4469g;
        if (tXCGPUColorBrushFilter != null) {
            tXCGPUColorBrushFilter.mo1351e();
            this.f4469g = null;
        }
    }

    /* renamed from: c */
    private void m1364c() {
        TXCOpenGlUtils.C3356a c3356a = this.f4478q;
        if (c3356a != null) {
            TXCOpenGlUtils.m3002a(c3356a);
            this.f4478q = null;
        }
        TXCOpenGlUtils.C3356a c3356a2 = this.f4477p;
        if (c3356a2 != null) {
            TXCOpenGlUtils.m3002a(c3356a2);
            this.f4477p = null;
        }
        int[] iArr = this.f4476o;
        if (iArr != null) {
            GLES20.glDeleteTextures(1, iArr, 0);
            this.f4476o = null;
        }
    }

    /* renamed from: a */
    public void m1367a(TXCVideoEffect.C3535c c3535c) {
        this.f4473l = c3535c;
        TXCVideoEffect.C3535c c3535c2 = this.f4473l;
        if (c3535c2 == null) {
            return;
        }
        TXCGPUGridShapeFilter tXCGPUGridShapeFilter = this.f4464b;
        if (tXCGPUGridShapeFilter != null) {
            tXCGPUGridShapeFilter.m2676a(c3535c2);
        }
        TXCScaleFilter tXCScaleFilter = this.f4465c;
        if (tXCScaleFilter != null) {
            tXCScaleFilter.m1320a(this.f4473l.f4590j);
        }
        if (TXCVideoEffect.C3535c.EnumC3536a.MODE_ZOOM_IN != c3535c.f4587g) {
            return;
        }
        this.f4479r = c3535c.f4590j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0057, code lost:
        if (com.tencent.liteav.p126k.TXCVideoEffect.C3535c.EnumC3536a.f4593b == r7.f4474m) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
        r7.f4474m = r7.f4473l.f4587g;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:
        r8 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (com.tencent.liteav.p126k.TXCVideoEffect.C3535c.EnumC3536a.f4594c == r7.f4474m) goto L23;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int m1369a(int i) {
        TXCOpenGlUtils.C3356a c3356a;
        int m2639a;
        if (this.f4473l == null) {
            return i;
        }
        if (this.f4476o == null) {
            this.f4476o = new int[1];
            int[] iArr = this.f4476o;
            iArr[0] = TXCOpenGlUtils.m3005a(this.f4470h, this.f4471i, 6408, 6408, iArr);
        }
        TXCGPUGridShapeFilter tXCGPUGridShapeFilter = this.f4464b;
        int m2678a = tXCGPUGridShapeFilter != null ? tXCGPUGridShapeFilter.m2678a(i) : i;
        TXCScaleFilter tXCScaleFilter = this.f4465c;
        int mo2294a = tXCScaleFilter != null ? tXCScaleFilter.mo2294a(i) : i;
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4463a;
        if (tXCSavePreFrameFilter != null) {
            if (TXCVideoEffect.C3535c.EnumC3536a.MODE_ZOOM_OUT == this.f4473l.f4587g) {
                this.f4466d.m1320a(this.f4479r);
                m2639a = this.f4466d.mo2294a(i);
                int m2639a2 = this.f4463a.m2639a(m2639a);
                if (m2639a2 > 0) {
                    i = m2639a2;
                }
            } else {
                m2639a = tXCSavePreFrameFilter.m2639a(i);
                if (m2639a <= 0) {
                    m2639a = i;
                }
            }
        }
        TXCOpenGlUtils.C3356a c3356a2 = this.f4478q;
        if (c3356a2 != null) {
            GLES20.glBindFramebuffer(36160, c3356a2.f2671a[0]);
            if (true == this.f4473l.f4591k) {
                this.f4469g.m2740d(this.f4476o[0], m2678a);
            } else {
                this.f4469g.m2740d(this.f4478q.f2672b[0], m2678a);
            }
            GLES20.glBindFramebuffer(36160, 0);
        }
        GLES20.glBindFramebuffer(36160, this.f4477p.f2671a[0]);
        TXCGPUGridBlendFilter tXCGPUGridBlendFilter = this.f4467e;
        if (tXCGPUGridBlendFilter != null && (c3356a = this.f4478q) != null) {
            tXCGPUGridBlendFilter.m2744b(c3356a.f2672b[0], i, mo2294a);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return this.f4477p.f2672b[0];
    }

    /* renamed from: a */
    public void m1370a() {
        m1366b();
        m1364c();
    }
}
