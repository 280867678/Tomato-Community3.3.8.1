package com.alipay.android.phone.mrpc.core;

/* renamed from: com.alipay.android.phone.mrpc.core.p */
/* loaded from: classes2.dex */
public final class C0906p extends C0911u {

    /* renamed from: c */
    private int f804c;

    /* renamed from: d */
    private String f805d;

    /* renamed from: e */
    private long f806e;

    /* renamed from: f */
    private long f807f;

    /* renamed from: g */
    private String f808g;

    /* renamed from: h */
    private HttpUrlHeader f809h;

    public C0906p(HttpUrlHeader httpUrlHeader, int i, String str, byte[] bArr) {
        this.f809h = httpUrlHeader;
        this.f804c = i;
        this.f805d = str;
        this.f830a = bArr;
    }

    /* renamed from: a */
    public final HttpUrlHeader m4824a() {
        return this.f809h;
    }

    /* renamed from: a */
    public final void m4823a(long j) {
        this.f806e = j;
    }

    /* renamed from: a */
    public final void m4822a(String str) {
        this.f808g = str;
    }

    /* renamed from: b */
    public final void m4821b(long j) {
        this.f807f = j;
    }
}
