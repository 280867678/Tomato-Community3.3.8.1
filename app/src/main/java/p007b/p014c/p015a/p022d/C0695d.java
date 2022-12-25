package p007b.p014c.p015a.p022d;

import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p017b.C0627i;

/* renamed from: b.c.a.d.d */
/* loaded from: classes2.dex */
public class C0695d implements AbstractC0610a {

    /* renamed from: a */
    public final /* synthetic */ C0627i f341a;

    /* renamed from: b */
    public final /* synthetic */ C0714n f342b;

    public C0695d(C0696e c0696e, C0627i c0627i, C0714n c0714n) {
        this.f341a = c0627i;
        this.f342b = c0714n;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0610a
    /* renamed from: a */
    public void mo5196a(Exception exc) {
        if (exc != null) {
            this.f341a.m5462a(exc);
            return;
        }
        try {
            this.f341a.m5460a((C0627i) this.f342b);
        } catch (Exception e) {
            this.f341a.m5462a(e);
        }
    }
}
