package p007b.p014c.p015a.p018c.p021c;

import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.c.c.e */
/* loaded from: classes2.dex */
public class C0665e extends C0675o {

    /* renamed from: n */
    public final /* synthetic */ C0667g f272n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0665e(C0667g c0667g, AbstractC0712l abstractC0712l, AbstractC0673m abstractC0673m) {
        super(abstractC0712l, abstractC0673m);
        this.f272n = c0667g;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.C0675o
    /* renamed from: b */
    public void mo5381b(Exception exc) {
        super.mo5381b(exc);
        if (exc != null) {
            this.f272n.f274C.mo5292a(new AbstractC0613c.C0614a());
            this.f272n.f274C.mo5293a(new AbstractC0610a.C0611a());
            this.f272n.f274C.close();
        }
    }

    @Override // p007b.p014c.p015a.p018c.p021c.C0675o
    /* renamed from: f */
    public void mo5377f() {
        this.f272n.f280v = true;
        super.mo5377f();
        this.f302d.mo5293a((AbstractC0610a) null);
        this.f272n.f275D.f285a.m5403c(m5379d(), this.f272n.f282x);
        this.f272n.m5418o();
    }
}
