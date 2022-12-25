package com.tencent.liteav.beauty.p115b;

import android.util.Log;
import com.tencent.liteav.p126k.TXCGPUGridGeneralFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.beauty.b.o */
/* loaded from: classes3.dex */
public class TXCGPUGridShapeFilter {

    /* renamed from: e */
    private static String f3097e = "GridShape";

    /* renamed from: a */
    private TXCGPUGridGeneralFilter f3098a = null;

    /* renamed from: b */
    private TXCGPUBulgeDistortionFilter f3099b = null;

    /* renamed from: c */
    private TXCGPURotateScaleFilter f3100c = null;

    /* renamed from: d */
    private TXCGPUSwirlFilter f3101d = null;

    /* renamed from: f */
    private int f3102f = 0;

    /* renamed from: g */
    private int f3103g = 0;

    /* renamed from: h */
    private TXCVideoEffect.C3535c f3104h = null;

    /* renamed from: a */
    public boolean m2677a(int i, int i2) {
        return m2673c(i, i2);
    }

    /* renamed from: c */
    private boolean m2673c(int i, int i2) {
        if (this.f3098a == null) {
            this.f3098a = new TXCGPUGridGeneralFilter();
            this.f3098a.mo1353a(true);
            if (!this.f3098a.mo2653c()) {
                Log.e(f3097e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCGPUGridGeneralFilter tXCGPUGridGeneralFilter = this.f3098a;
        if (tXCGPUGridGeneralFilter != null) {
            tXCGPUGridGeneralFilter.mo1333a(i, i2);
        }
        if (this.f3099b == null) {
            this.f3099b = new TXCGPUBulgeDistortionFilter();
            this.f3099b.mo1353a(true);
            if (!this.f3099b.mo2653c()) {
                Log.e(f3097e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCGPUBulgeDistortionFilter tXCGPUBulgeDistortionFilter = this.f3099b;
        if (tXCGPUBulgeDistortionFilter != null) {
            tXCGPUBulgeDistortionFilter.mo1333a(i, i2);
        }
        if (this.f3100c == null) {
            this.f3100c = new TXCGPURotateScaleFilter();
            this.f3100c.mo1353a(true);
            if (!this.f3100c.mo2653c()) {
                Log.e(f3097e, "mRotateScaleFilter init Failed!");
                return false;
            }
        }
        TXCGPURotateScaleFilter tXCGPURotateScaleFilter = this.f3100c;
        if (tXCGPURotateScaleFilter != null) {
            tXCGPURotateScaleFilter.mo1333a(i, i2);
        }
        return true;
    }

    /* renamed from: b */
    private void m2675b() {
        TXCGPUGridGeneralFilter tXCGPUGridGeneralFilter = this.f3098a;
        if (tXCGPUGridGeneralFilter != null) {
            tXCGPUGridGeneralFilter.mo1351e();
            this.f3098a = null;
        }
        TXCGPUBulgeDistortionFilter tXCGPUBulgeDistortionFilter = this.f3099b;
        if (tXCGPUBulgeDistortionFilter != null) {
            tXCGPUBulgeDistortionFilter.mo1351e();
            this.f3099b = null;
        }
        TXCGPURotateScaleFilter tXCGPURotateScaleFilter = this.f3100c;
        if (tXCGPURotateScaleFilter != null) {
            tXCGPURotateScaleFilter.mo1351e();
            this.f3100c = null;
        }
    }

    /* renamed from: a */
    public void m2676a(TXCVideoEffect.C3535c c3535c) {
        this.f3104h = c3535c;
        TXCGPUGridGeneralFilter tXCGPUGridGeneralFilter = this.f3098a;
        if (tXCGPUGridGeneralFilter != null) {
            tXCGPUGridGeneralFilter.m1342a(this.f3104h);
        }
        TXCGPURotateScaleFilter tXCGPURotateScaleFilter = this.f3100c;
        if (tXCGPURotateScaleFilter != null) {
            tXCGPURotateScaleFilter.m2648a(this.f3104h.f4584d);
        }
        TXCGPUBulgeDistortionFilter tXCGPUBulgeDistortionFilter = this.f3099b;
        if (tXCGPUBulgeDistortionFilter != null) {
            tXCGPUBulgeDistortionFilter.m2701a(this.f3104h.f4589i);
            this.f3099b.m2699b(this.f3104h.f4588h);
        }
    }

    /* renamed from: b */
    public void m2674b(int i, int i2) {
        if (i == this.f3102f && i2 == this.f3103g) {
            return;
        }
        m2673c(i, i2);
    }

    /* renamed from: a */
    public int m2678a(int i) {
        if (this.f3104h == null) {
            return i;
        }
        TXCGPUGridGeneralFilter tXCGPUGridGeneralFilter = this.f3098a;
        if (tXCGPUGridGeneralFilter != null) {
            i = tXCGPUGridGeneralFilter.mo2294a(i);
        }
        TXCGPURotateScaleFilter tXCGPURotateScaleFilter = this.f3100c;
        if (tXCGPURotateScaleFilter != null) {
            i = tXCGPURotateScaleFilter.mo2294a(i);
        }
        TXCGPUBulgeDistortionFilter tXCGPUBulgeDistortionFilter = this.f3099b;
        return tXCGPUBulgeDistortionFilter != null ? tXCGPUBulgeDistortionFilter.mo2294a(i) : i;
    }

    /* renamed from: a */
    public void m2679a() {
        m2675b();
    }
}
