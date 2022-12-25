package com.tencent.liteav.p126k;

import android.util.Log;
import com.tencent.liteav.beauty.TXCSavePreFrameFilter;
import com.tencent.liteav.beauty.p115b.TXCGPUDissolveBlendFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;

/* renamed from: com.tencent.liteav.k.e */
/* loaded from: classes3.dex */
public class TXCGPUGhostShadowFilter {

    /* renamed from: c */
    private static String f4494c = "GhostShadow";

    /* renamed from: b */
    private TXCGPUDissolveBlendFilter f4496b = null;

    /* renamed from: a */
    TXCVideoEffect.C3539f f4495a = null;

    /* renamed from: d */
    private TXCSavePreFrameFilter f4497d = null;

    /* renamed from: e */
    private int f4498e = 0;

    /* renamed from: f */
    private int f4499f = 0;

    /* renamed from: a */
    public boolean m1348a(int i, int i2) {
        this.f4498e = i;
        this.f4499f = i2;
        return m1343c(i, i2);
    }

    /* renamed from: c */
    private boolean m1343c(int i, int i2) {
        if (this.f4496b == null) {
            this.f4496b = new TXCGPUDissolveBlendFilter();
            this.f4496b.mo1353a(true);
            if (!this.f4496b.mo2653c()) {
                Log.e(f4494c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCGPUDissolveBlendFilter tXCGPUDissolveBlendFilter = this.f4496b;
        if (tXCGPUDissolveBlendFilter != null) {
            tXCGPUDissolveBlendFilter.mo1333a(i, i2);
        }
        if (this.f4497d == null) {
            this.f4497d = new TXCSavePreFrameFilter();
            if (!this.f4497d.m2638a(i, i2)) {
                Log.e(f4494c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4497d;
        if (tXCSavePreFrameFilter != null) {
            tXCSavePreFrameFilter.m2635b(i, i2);
        }
        return true;
    }

    /* renamed from: b */
    private void m1345b() {
        TXCGPUDissolveBlendFilter tXCGPUDissolveBlendFilter = this.f4496b;
        if (tXCGPUDissolveBlendFilter != null) {
            tXCGPUDissolveBlendFilter.mo1351e();
            this.f4496b = null;
        }
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4497d;
        if (tXCSavePreFrameFilter != null) {
            tXCSavePreFrameFilter.m2637b();
            this.f4497d = null;
        }
    }

    /* renamed from: a */
    public void m1347a(TXCVideoEffect.C3539f c3539f) {
        this.f4495a = c3539f;
        if (c3539f == null) {
            Log.i(f4494c, "GhostShadowParam is null, reset list");
            TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4497d;
            if (tXCSavePreFrameFilter == null) {
                return;
            }
            tXCSavePreFrameFilter.m2640a();
        }
    }

    /* renamed from: a */
    public int m1349a(int i) {
        int i2;
        if (!m1346a(this.f4495a, this.f4498e, this.f4499f)) {
            return i;
        }
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4497d;
        if (tXCSavePreFrameFilter == null || (i2 = tXCSavePreFrameFilter.m2639a(i)) <= 0) {
            i2 = i;
        }
        TXCGPUDissolveBlendFilter tXCGPUDissolveBlendFilter = this.f4496b;
        return tXCGPUDissolveBlendFilter != null ? tXCGPUDissolveBlendFilter.m2741c(i, i2) : i;
    }

    /* renamed from: a */
    private boolean m1346a(TXCVideoEffect.C3539f c3539f, int i, int i2) {
        if (c3539f == null) {
            return false;
        }
        TXCSavePreFrameFilter tXCSavePreFrameFilter = this.f4497d;
        if (tXCSavePreFrameFilter == null) {
            return true;
        }
        tXCSavePreFrameFilter.m2636b(c3539f.f4607a);
        return true;
    }

    /* renamed from: b */
    public void m1344b(int i, int i2) {
        m1343c(i, i2);
    }

    /* renamed from: a */
    public void m1350a() {
        m1345b();
    }
}
