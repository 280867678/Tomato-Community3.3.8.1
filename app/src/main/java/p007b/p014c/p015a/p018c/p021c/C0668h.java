package p007b.p014c.p015a.p018c.p021c;

import p007b.p014c.p015a.AbstractC0711k;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.p016a.AbstractC0615d;

/* renamed from: b.c.a.c.c.h */
/* loaded from: classes2.dex */
public class C0668h implements AbstractC0615d {

    /* renamed from: a */
    public final /* synthetic */ C0669i f285a;

    public C0668h(C0669i c0669i) {
        this.f285a = c0669i;
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0615d
    /* renamed from: a */
    public void mo5416a(AbstractC0711k abstractC0711k) {
        this.f285a.f287g.add(abstractC0711k);
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0615d
    /* renamed from: a */
    public void mo5415a(AbstractC0712l abstractC0712l) {
        new C0667g(this, abstractC0712l).m5396b(abstractC0712l);
        abstractC0712l.mo5294d();
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0610a
    /* renamed from: a */
    public void mo5196a(Exception exc) {
        this.f285a.m5406a(exc);
    }
}
