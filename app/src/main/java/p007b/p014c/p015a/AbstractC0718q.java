package p007b.p014c.p015a;

import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0613c;

/* renamed from: b.c.a.q */
/* loaded from: classes2.dex */
public abstract class AbstractC0718q implements AbstractC0717p {

    /* renamed from: a */
    public boolean f377a;

    /* renamed from: b */
    public AbstractC0610a f378b;

    /* renamed from: c */
    public AbstractC0613c f379c;

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: a */
    public final void mo5293a(AbstractC0610a abstractC0610a) {
        this.f378b = abstractC0610a;
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: a */
    public void mo5292a(AbstractC0613c abstractC0613c) {
        this.f379c = abstractC0613c;
    }

    /* renamed from: b */
    public void mo3859b(Exception exc) {
        if (this.f377a) {
            return;
        }
        this.f377a = true;
        if (m5290j() == null) {
            return;
        }
        m5290j().mo5196a(exc);
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: g */
    public AbstractC0613c mo5291g() {
        return this.f379c;
    }

    /* renamed from: j */
    public final AbstractC0610a m5290j() {
        return this.f378b;
    }
}
