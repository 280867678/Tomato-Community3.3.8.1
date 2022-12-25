package p007b.p014c.p015a.p018c.p019a;

import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0725w;
import p007b.p014c.p015a.p016a.AbstractC0613c;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.p019a.C0639f;

/* renamed from: b.c.a.c.a.e */
/* loaded from: classes2.dex */
public class C0638e implements C0725w.AbstractC0726a {

    /* renamed from: a */
    public final /* synthetic */ C0649b f230a;

    /* renamed from: b */
    public final /* synthetic */ C0639f f231b;

    public C0638e(C0639f c0639f, C0649b c0649b) {
        this.f231b = c0639f;
        this.f230a = c0649b;
    }

    @Override // p007b.p014c.p015a.C0725w.AbstractC0726a
    /* renamed from: a */
    public void mo5277a(String str) {
        if (!"\r".equals(str)) {
            this.f230a.m5440a(str);
            return;
        }
        this.f231b.m5447n();
        C0639f c0639f = this.f231b;
        c0639f.f232k = null;
        c0639f.mo5292a((AbstractC0613c) null);
        C0641g c0641g = new C0641g(this.f230a);
        C0639f.AbstractC0640a abstractC0640a = this.f231b.f236p;
        if (abstractC0640a != null) {
            abstractC0640a.m5446a(c0641g);
        }
        if (this.f231b.mo5291g() != null) {
            return;
        }
        C0639f c0639f2 = this.f231b;
        c0639f2.f235n = c0641g;
        c0639f2.f234m = new C0714n();
        this.f231b.mo5292a(new C0637d(this));
    }
}
