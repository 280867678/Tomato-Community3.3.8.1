package com.p065io.liquidlink.p072g;

/* renamed from: com.io.liquidlink.g.a */
/* loaded from: classes3.dex */
public class C2163a implements Cloneable {

    /* renamed from: a */
    private final C2169g f1466a;

    /* renamed from: b */
    private final C2167e f1467b;

    public C2163a(C2167e c2167e, C2169g c2169g) {
        this.f1466a = c2169g;
        this.f1467b = c2167e;
    }

    public C2163a(C2169g c2169g) {
        this(null, c2169g);
    }

    /* renamed from: a */
    public C2169g m3956a() {
        return this.f1466a;
    }

    /* renamed from: a */
    public void m3955a(byte[] bArr) {
        C2167e c2167e = this.f1467b;
        if (c2167e != null) {
            c2167e.m3936a(bArr);
        } else {
            this.f1466a.m3926a(bArr);
        }
    }

    /* renamed from: b */
    public C2167e m3954b() {
        return this.f1467b;
    }

    /* renamed from: c */
    public byte[] m3953c() {
        C2167e c2167e = this.f1467b;
        return c2167e != null ? c2167e.m3932d() : this.f1466a.f1480g;
    }

    /* renamed from: d */
    public C2163a clone() {
        C2167e c2167e = this.f1467b;
        return new C2163a(c2167e == null ? null : c2167e.clone(), this.f1466a.clone());
    }
}
