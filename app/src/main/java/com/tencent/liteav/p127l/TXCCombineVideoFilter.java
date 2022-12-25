package com.tencent.liteav.p127l;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import com.tencent.liteav.basic.p112h.TXCCombineFrame;
import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.TXIVideoPreprocessorListener;
import com.tencent.liteav.beauty.p115b.TXCGPUPurlColorFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUWatermarkAlphaTextureFilter;

/* renamed from: com.tencent.liteav.l.b */
/* loaded from: classes3.dex */
public class TXCCombineVideoFilter {

    /* renamed from: f */
    private TXCGPUWatermarkAlphaTextureFilter f4660f = null;

    /* renamed from: a */
    TXCGPUFilter f4655a = null;

    /* renamed from: b */
    TXCGPUFilter f4656b = null;

    /* renamed from: g */
    private TXCGPUPurlColorFilter f4661g = null;

    /* renamed from: h */
    private final int f4662h = 2;

    /* renamed from: i */
    private final int f4663i = 3;

    /* renamed from: j */
    private int f4664j = 0;

    /* renamed from: k */
    private int f4665k = 0;

    /* renamed from: l */
    private int f4666l = 0;

    /* renamed from: m */
    private int f4667m = 0;

    /* renamed from: n */
    private CropRect f4668n = null;

    /* renamed from: o */
    private float[] f4669o = {0.0f, 0.0f, 0.0f, 0.0f};

    /* renamed from: c */
    protected TXCOpenGlUtils.C3356a[] f4657c = null;

    /* renamed from: d */
    protected TXCOpenGlUtils.C3356a f4658d = null;

    /* renamed from: e */
    protected int[] f4659e = null;

    /* renamed from: p */
    private TXIVideoPreprocessorListener f4670p = null;

    /* renamed from: q */
    private String f4671q = "CombineVideoFilter";

    /* renamed from: a */
    public void m1273a(int i, int i2) {
        if (i > 0 && i2 > 0 && (i != this.f4664j || i2 != this.f4665k)) {
            TXCOpenGlUtils.m2996a(this.f4657c);
            this.f4657c = null;
        }
        this.f4664j = i;
        this.f4665k = i2;
    }

    /* renamed from: b */
    public void m1267b(int i, int i2) {
        if (i > 0 && i2 > 0 && (i != this.f4666l || i2 != this.f4667m)) {
            m1268b();
        }
        this.f4666l = i;
        this.f4667m = i2;
    }

    /* renamed from: a */
    public void m1271a(CropRect cropRect) {
        this.f4668n = cropRect;
    }

    /* renamed from: a */
    public int m1269a(TXCCombineFrame[] tXCCombineFrameArr, int i) {
        int i2;
        CropRect cropRect;
        if (tXCCombineFrameArr == null || this.f4664j <= 0 || this.f4665k <= 0) {
            Log.e(this.f4671q, "frames or canvaceSize if null!");
            return -1;
        }
        m1270a(tXCCombineFrameArr);
        if (this.f4660f != null) {
            i2 = 0;
            for (int i3 = 0; i3 < tXCCombineFrameArr.length; i3++) {
                GLES20.glBindFramebuffer(36160, this.f4657c[i3].f2671a[0]);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16640);
                TXCVideoPreprocessor.C3397d[] c3397dArr = {new TXCVideoPreprocessor.C3397d()};
                c3397dArr[0].f3260e = tXCCombineFrameArr[i3].f2725a;
                c3397dArr[0].f3261f = tXCCombineFrameArr[i3].f2731g.f2535c;
                c3397dArr[0].f3262g = tXCCombineFrameArr[i3].f2731g.f2536d;
                TXCVideoPreprocessor.C3397d c3397d = c3397dArr[0];
                int i4 = this.f4664j;
                c3397d.f3257b = (tXCCombineFrameArr[i3].f2731g.f2533a * 1.0f) / i4;
                c3397dArr[0].f3258c = (tXCCombineFrameArr[i3].f2731g.f2534b * 1.0f) / this.f4665k;
                c3397dArr[0].f3259d = (tXCCombineFrameArr[i3].f2731g.f2535c * 1.0f) / i4;
                if (tXCCombineFrameArr[i3].f2729e != null) {
                    this.f4660f.m2733a(tXCCombineFrameArr[i3].f2729e.f2734c);
                    this.f4660f.m2732c(tXCCombineFrameArr[i3].f2729e.f2735d);
                }
                this.f4660f.m1322a(c3397dArr);
                GLES20.glViewport(0, 0, this.f4664j, this.f4665k);
                if (i3 == 0) {
                    this.f4660f.m3025b(this.f4658d.f2672b[0]);
                } else {
                    this.f4660f.m3025b(this.f4657c[i3 - 1].f2672b[0]);
                }
                GLES20.glBindFramebuffer(36160, 0);
                i2 = i3;
            }
        } else {
            i2 = 0;
        }
        int i5 = this.f4657c[i2].f2672b[0];
        int i6 = this.f4664j;
        int i7 = this.f4665k;
        if (this.f4656b != null && (cropRect = this.f4668n) != null) {
            GLES20.glViewport(0, 0, cropRect.f2535c, cropRect.f2536d);
            i5 = this.f4656b.mo2294a(i5);
        }
        if (this.f4655a != null) {
            GLES20.glViewport(0, 0, this.f4666l, this.f4667m);
            i5 = this.f4655a.mo2294a(i5);
            i6 = this.f4666l;
            i7 = this.f4667m;
        }
        int i8 = i6;
        int i9 = i7;
        TXIVideoPreprocessorListener tXIVideoPreprocessorListener = this.f4670p;
        if (tXIVideoPreprocessorListener != null) {
            tXIVideoPreprocessorListener.didProcessFrame(i5, i8, i9, i);
        }
        return i5;
    }

    /* renamed from: a */
    public void m1274a() {
        TXCOpenGlUtils.m2996a(this.f4657c);
        this.f4657c = null;
        m1264d();
        TXCOpenGlUtils.C3356a c3356a = this.f4658d;
        if (c3356a != null) {
            TXCOpenGlUtils.m3002a(c3356a);
            this.f4658d = null;
        }
    }

    /* renamed from: a */
    private void m1272a(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        TXCOpenGlUtils.C3356a[] c3356aArr = this.f4657c;
        if (c3356aArr != null && i3 == c3356aArr.length) {
            return;
        }
        TXCOpenGlUtils.m2996a(this.f4657c);
        this.f4657c = null;
        this.f4657c = TXCOpenGlUtils.m2995a(this.f4657c, i3, i, i2);
        if (this.f4659e == null) {
            this.f4659e = new int[1];
            int[] iArr = this.f4659e;
            iArr[0] = TXCOpenGlUtils.m3005a(i, i2, 6408, 6408, iArr);
        }
        TXCOpenGlUtils.C3356a c3356a = this.f4658d;
        if (c3356a != null) {
            TXCOpenGlUtils.m3002a(c3356a);
            this.f4658d = null;
        }
        TXCOpenGlUtils.C3356a c3356a2 = this.f4658d;
        if (c3356a2 == null) {
            this.f4658d = TXCOpenGlUtils.m3001a(c3356a2, i, i2);
        }
        if (this.f4661g == null) {
            return;
        }
        GLES20.glBindFramebuffer(36160, this.f4658d.f2671a[0]);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        this.f4661g.m2654b(this.f4669o);
        this.f4661g.m3025b(-1);
        GLES20.glBindFramebuffer(36160, 0);
    }

    /* renamed from: c */
    private void m1265c(int i, int i2) {
        if (this.f4655a == null) {
            this.f4655a = new TXCGPUFilter();
            this.f4655a.mo1353a(true);
            if (!this.f4655a.mo2653c()) {
                TXCLog.m2914e(this.f4671q, "mOutputFilter.init failed!");
                return;
            }
        }
        TXCGPUFilter tXCGPUFilter = this.f4655a;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
    }

    /* renamed from: d */
    private void m1263d(int i, int i2) {
        if (this.f4660f == null) {
            this.f4660f = new TXCGPUWatermarkAlphaTextureFilter();
            this.f4660f.mo1353a(true);
            if (!this.f4660f.mo2653c()) {
                TXCLog.m2914e(this.f4671q, "TXCGPUWatermarkTextureFilter.init failed!");
                return;
            }
        }
        TXCGPUWatermarkAlphaTextureFilter tXCGPUWatermarkAlphaTextureFilter = this.f4660f;
        if (tXCGPUWatermarkAlphaTextureFilter != null) {
            tXCGPUWatermarkAlphaTextureFilter.mo1333a(i, i2);
        }
    }

    /* renamed from: e */
    private boolean m1262e(int i, int i2) {
        if (this.f4656b == null) {
            this.f4656b = new TXCGPUFilter();
            this.f4656b.mo1353a(true);
            if (!this.f4656b.mo2653c()) {
                TXCLog.m2914e(this.f4671q, "mCropFilter.init failed!");
                return false;
            }
        }
        TXCGPUFilter tXCGPUFilter = this.f4656b;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
        return true;
    }

    /* renamed from: b */
    private void m1268b() {
        TXCGPUFilter tXCGPUFilter = this.f4655a;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f4655a = null;
        }
    }

    /* renamed from: c */
    private void m1266c() {
        TXCGPUFilter tXCGPUFilter = this.f4656b;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f4656b = null;
        }
    }

    /* renamed from: a */
    private void m1270a(TXCCombineFrame[] tXCCombineFrameArr) {
        int i;
        m1263d(this.f4664j, this.f4665k);
        if (this.f4661g == null) {
            this.f4661g = new TXCGPUPurlColorFilter();
            if (!this.f4661g.mo2653c()) {
                TXCLog.m2914e(this.f4671q, "mCropFilter.init failed!");
                return;
            }
        }
        TXCGPUPurlColorFilter tXCGPUPurlColorFilter = this.f4661g;
        if (tXCGPUPurlColorFilter != null) {
            tXCGPUPurlColorFilter.mo1333a(this.f4664j, this.f4665k);
        }
        m1272a(this.f4664j, this.f4665k, tXCCombineFrameArr.length);
        CropRect cropRect = this.f4668n;
        if (cropRect != null) {
            m1262e(cropRect.f2535c, cropRect.f2536d);
            TXCGPUFilter tXCGPUFilter = this.f4656b;
            if (tXCGPUFilter != null) {
                this.f4656b.m3026a(TXCTextureRotationUtil.f2684e, tXCGPUFilter.m3033a(this.f4664j, this.f4665k, null, this.f4668n, 0));
            }
        } else {
            m1266c();
        }
        int i2 = this.f4666l;
        if (i2 <= 0 || (i = this.f4667m) <= 0) {
            return;
        }
        m1265c(i2, i);
    }

    /* renamed from: d */
    private void m1264d() {
        TXCGPUWatermarkAlphaTextureFilter tXCGPUWatermarkAlphaTextureFilter = this.f4660f;
        if (tXCGPUWatermarkAlphaTextureFilter != null) {
            tXCGPUWatermarkAlphaTextureFilter.mo1351e();
            this.f4660f = null;
        }
        m1268b();
        TXCGPUFilter tXCGPUFilter = this.f4656b;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f4656b = null;
        }
    }
}
