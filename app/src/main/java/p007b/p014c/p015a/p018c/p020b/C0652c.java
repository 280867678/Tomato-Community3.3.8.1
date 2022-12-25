package p007b.p014c.p015a.p018c.p020b;

import com.koushikdutta.async.http.filter.PrematureDataEndException;
import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0723u;

/* renamed from: b.c.a.c.b.c */
/* loaded from: classes2.dex */
public class C0652c extends C0723u {

    /* renamed from: h */
    public static final /* synthetic */ boolean f251h = !C0652c.class.desiredAssertionStatus();

    /* renamed from: i */
    public long f252i;

    /* renamed from: j */
    public long f253j;

    /* renamed from: k */
    public C0714n f254k = new C0714n();

    public C0652c(long j) {
        this.f252i = j;
    }

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        if (f251h || this.f253j < this.f252i) {
            c0714n.m5327a(this.f254k, (int) Math.min(this.f252i - this.f253j, c0714n.m5303m()));
            int m5303m = this.f254k.m5303m();
            super.mo3861a(abstractC0717p, this.f254k);
            this.f253j += m5303m - this.f254k.m5303m();
            this.f254k.m5328a(c0714n);
            if (this.f253j != this.f252i) {
                return;
            }
            mo3859b(null);
            return;
        }
        throw new AssertionError();
    }

    @Override // p007b.p014c.p015a.AbstractC0718q
    /* renamed from: b */
    public void mo3859b(Exception exc) {
        if (exc == null && this.f253j != this.f252i) {
            exc = new PrematureDataEndException("End of data reached before content length was read: " + this.f253j + "/" + this.f252i + " Paused: " + mo5281f());
        }
        super.mo3859b(exc);
    }
}
