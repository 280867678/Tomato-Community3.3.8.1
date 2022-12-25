package com.tencent.liteav.beauty;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import java.util.ArrayList;

/* renamed from: com.tencent.liteav.beauty.c */
/* loaded from: classes3.dex */
public class TXCSavePreFrameFilter {

    /* renamed from: e */
    private static String f3204e = "avePreFrame";

    /* renamed from: a */
    private ArrayList<TXCOpenGlUtils.C3356a> f3205a = new ArrayList<>();

    /* renamed from: b */
    private ArrayList<TXCOpenGlUtils.C3356a> f3206b = new ArrayList<>();

    /* renamed from: c */
    private int f3207c = 1;

    /* renamed from: d */
    private TXCGPUFilter f3208d = null;

    /* renamed from: f */
    private TXCOpenGlUtils.C3356a[] f3209f = null;

    /* renamed from: g */
    private int f3210g = -1;

    /* renamed from: h */
    private int f3211h = -1;

    /* renamed from: a */
    public boolean m2638a(int i, int i2) {
        return m2634c(i, i2);
    }

    /* renamed from: c */
    private boolean m2634c(int i, int i2) {
        if (this.f3208d == null) {
            this.f3208d = new TXCGPUFilter();
            this.f3208d.mo1353a(true);
            if (!this.f3208d.mo2653c()) {
                Log.e(f3204e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        TXCGPUFilter tXCGPUFilter = this.f3208d;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
        this.f3210g = i;
        this.f3211h = i2;
        return true;
    }

    /* renamed from: b */
    public void m2635b(int i, int i2) {
        m2634c(i, i2);
    }

    /* renamed from: a */
    public int m2639a(int i) {
        TXCOpenGlUtils.C3356a c3356a = null;
        int i2 = -1;
        if (this.f3205a.size() >= this.f3207c) {
            TXCOpenGlUtils.C3356a c3356a2 = this.f3205a.size() > 0 ? this.f3205a.get(0) : null;
            if (c3356a2 != null) {
                TXCGPUFilter tXCGPUFilter = this.f3208d;
                if (tXCGPUFilter != null) {
                    i2 = tXCGPUFilter.mo2294a(c3356a2.f2672b[0]);
                }
                this.f3206b.add(c3356a2);
                this.f3205a.remove(0);
            }
        }
        if (this.f3206b.size() > 0) {
            c3356a = this.f3206b.get(0);
        }
        if (c3356a != null) {
            GLES20.glBindFramebuffer(36160, c3356a.f2671a[0]);
            TXCGPUFilter tXCGPUFilter2 = this.f3208d;
            if (tXCGPUFilter2 != null) {
                tXCGPUFilter2.m3025b(i);
            }
            GLES20.glBindFramebuffer(36160, 0);
            this.f3205a.add(c3356a);
            this.f3206b.remove(0);
        }
        return i2;
    }

    /* renamed from: b */
    public void m2636b(int i) {
        this.f3207c = i;
        TXCOpenGlUtils.C3356a[] c3356aArr = this.f3209f;
        if (c3356aArr == null || c3356aArr.length != this.f3207c) {
            TXCOpenGlUtils.m2996a(this.f3209f);
            m2640a();
            this.f3209f = TXCOpenGlUtils.m2995a(this.f3209f, this.f3207c, this.f3210g, this.f3211h);
            int i2 = 0;
            while (true) {
                TXCOpenGlUtils.C3356a[] c3356aArr2 = this.f3209f;
                if (i2 >= c3356aArr2.length) {
                    return;
                }
                this.f3206b.add(c3356aArr2[i2]);
                i2++;
            }
        }
    }

    /* renamed from: a */
    public void m2640a() {
        this.f3206b.clear();
        this.f3205a.clear();
    }

    /* renamed from: b */
    public void m2637b() {
        TXCGPUFilter tXCGPUFilter = this.f3208d;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1351e();
            this.f3208d = null;
        }
        TXCOpenGlUtils.m2996a(this.f3209f);
        this.f3209f = null;
        m2640a();
    }
}
