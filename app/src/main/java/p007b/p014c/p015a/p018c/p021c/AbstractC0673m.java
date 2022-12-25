package p007b.p014c.p015a.p018c.p021c;

import java.util.HashMap;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.C0723u;
import p007b.p014c.p015a.C0725w;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.p019a.AbstractC0634a;

/* renamed from: b.c.a.c.c.m */
/* loaded from: classes2.dex */
public abstract class AbstractC0673m extends C0723u implements AbstractC0670j, AbstractC0610a {

    /* renamed from: h */
    public String f292h;

    /* renamed from: j */
    public AbstractC0712l f294j;

    /* renamed from: n */
    public String f297n;

    /* renamed from: o */
    public AbstractC0634a f298o;

    /* renamed from: i */
    public C0649b f293i = new C0649b();

    /* renamed from: l */
    public AbstractC0610a f295l = new C0671k(this);

    /* renamed from: m */
    public C0725w.AbstractC0726a f296m = new C0672l(this);

    public AbstractC0673m() {
        new HashMap();
    }

    /* renamed from: a */
    public abstract AbstractC0634a mo5402a(C0649b c0649b);

    @Override // p007b.p014c.p015a.AbstractC0718q, p007b.p014c.p015a.AbstractC0717p
    /* renamed from: a */
    public void mo5292a(AbstractC0613c abstractC0613c) {
        this.f294j.mo5292a(abstractC0613c);
    }

    /* renamed from: a */
    public void mo5196a(Exception exc) {
        mo3859b(exc);
    }

    /* renamed from: b */
    public abstract AbstractC0634a mo5398b(C0649b c0649b);

    /* renamed from: b */
    public void m5396b(AbstractC0712l abstractC0712l) {
        this.f294j = abstractC0712l;
        C0725w c0725w = new C0725w();
        this.f294j.mo5292a(c0725w);
        c0725w.m5278a(this.f296m);
        this.f294j.mo5293a(new AbstractC0610a.C0611a());
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: d */
    public void mo5294d() {
        this.f294j.mo5294d();
    }

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.AbstractC0717p
    /* renamed from: f */
    public boolean mo5281f() {
        return this.f294j.mo5281f();
    }

    @Override // p007b.p014c.p015a.AbstractC0718q, p007b.p014c.p015a.AbstractC0717p
    /* renamed from: g */
    public AbstractC0613c mo5291g() {
        return this.f294j.mo5291g();
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0670j
    public String getMethod() {
        return this.f297n;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0670j
    /* renamed from: i */
    public C0649b mo5394i() {
        return this.f293i;
    }

    /* renamed from: k */
    public AbstractC0634a m5393k() {
        return this.f298o;
    }

    /* renamed from: l */
    public String m5392l() {
        return this.f292h;
    }

    /* renamed from: m */
    public abstract void mo5391m();

    /* renamed from: n */
    public void m5390n() {
        System.out.println("not http!");
    }

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.AbstractC0717p
    public void pause() {
        this.f294j.pause();
    }

    public String toString() {
        C0649b c0649b = this.f293i;
        return c0649b == null ? super.toString() : c0649b.m5432e(this.f292h);
    }
}
