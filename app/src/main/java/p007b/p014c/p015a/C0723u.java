package p007b.p014c.p015a;

import com.koushikdutta.async.AsyncServer;
import p007b.p014c.p015a.AbstractC0720s;
import p007b.p014c.p015a.p016a.AbstractC0613c;
import p007b.p014c.p015a.p024f.AbstractC0706a;

/* renamed from: b.c.a.u */
/* loaded from: classes2.dex */
public class C0723u extends AbstractC0718q implements AbstractC0717p, AbstractC0613c, AbstractC0706a, AbstractC0720s {

    /* renamed from: d */
    public AbstractC0717p f381d;

    /* renamed from: e */
    public AbstractC0720s.AbstractC0721a f382e;

    /* renamed from: f */
    public int f383f;

    /* renamed from: g */
    public boolean f384g;

    /* renamed from: a */
    public void m5284a(AbstractC0717p abstractC0717p) {
        AbstractC0717p abstractC0717p2 = this.f381d;
        if (abstractC0717p2 != null) {
            abstractC0717p2.mo5292a((AbstractC0613c) null);
        }
        this.f381d = abstractC0717p;
        this.f381d.mo5292a(this);
        this.f381d.mo5293a(new C0722t(this));
    }

    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        if (this.f384g) {
            c0714n.m5304l();
            return;
        }
        if (c0714n != null) {
            this.f383f += c0714n.m5303m();
        }
        C0608K.m5480a(this, c0714n);
        if (c0714n != null) {
            this.f383f -= c0714n.m5303m();
        }
        AbstractC0720s.AbstractC0721a abstractC0721a = this.f382e;
        if (abstractC0721a == null || c0714n == null) {
            return;
        }
        abstractC0721a.m5285a(this.f383f);
    }

    @Override // p007b.p014c.p015a.AbstractC0717p, p007b.p014c.p015a.AbstractC0719r
    /* renamed from: c */
    public AsyncServer mo5283c() {
        return this.f381d.mo5283c();
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    public void close() {
        this.f384g = true;
        AbstractC0717p abstractC0717p = this.f381d;
        if (abstractC0717p != null) {
            abstractC0717p.close();
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: e */
    public String mo5282e() {
        AbstractC0717p abstractC0717p = this.f381d;
        if (abstractC0717p == null) {
            return null;
        }
        return abstractC0717p.mo5282e();
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    /* renamed from: f */
    public boolean mo5281f() {
        return this.f381d.mo5281f();
    }

    @Override // p007b.p014c.p015a.AbstractC0717p
    public void pause() {
        this.f381d.pause();
    }
}
