package p007b.p014c.p015a.p018c.p019a;

import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p017b.AbstractC0623e;

/* renamed from: b.c.a.c.a.i */
/* loaded from: classes2.dex */
public class C0643i implements AbstractC0623e<String> {

    /* renamed from: a */
    public final /* synthetic */ AbstractC0610a f240a;

    /* renamed from: b */
    public final /* synthetic */ C0644j f241b;

    public C0643i(C0644j c0644j, AbstractC0610a abstractC0610a) {
        this.f241b = c0644j;
        this.f240a = abstractC0610a;
    }

    @Override // p007b.p014c.p015a.p017b.AbstractC0623e
    /* renamed from: a  reason: avoid collision after fix types in other method */
    public void mo5444a(Exception exc, String str) {
        this.f241b.f242a = str;
        this.f240a.mo5196a(exc);
    }
}
