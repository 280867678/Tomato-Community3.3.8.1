package com.tencent.liteav.p119d;

import android.graphics.Bitmap;

/* renamed from: com.tencent.liteav.d.d */
/* loaded from: classes3.dex */
public class ComStaticFilter {

    /* renamed from: a */
    private float f3459a;

    /* renamed from: b */
    private Bitmap f3460b;

    /* renamed from: c */
    private Bitmap f3461c;

    /* renamed from: d */
    private float f3462d;

    /* renamed from: e */
    private float f3463e;

    public ComStaticFilter() {
    }

    public ComStaticFilter(float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        this.f3459a = f;
        this.f3460b = bitmap;
        this.f3461c = bitmap2;
        this.f3462d = f2;
        this.f3463e = f3;
    }

    /* renamed from: a */
    public void m2354a() {
        Bitmap bitmap = this.f3460b;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.f3460b.recycle();
            this.f3460b = null;
        }
        Bitmap bitmap2 = this.f3461c;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.f3461c.recycle();
        this.f3461c = null;
    }

    /* renamed from: b */
    public float m2352b() {
        return this.f3462d;
    }

    /* renamed from: c */
    public float m2350c() {
        return this.f3463e;
    }

    /* renamed from: d */
    public float m2349d() {
        return this.f3459a;
    }

    /* renamed from: e */
    public Bitmap m2348e() {
        return this.f3460b;
    }

    /* renamed from: f */
    public Bitmap m2347f() {
        return this.f3461c;
    }

    /* renamed from: a */
    public void m2353a(float f) {
        this.f3462d = f;
    }

    /* renamed from: b */
    public void m2351b(float f) {
        this.f3463e = f;
    }
}
