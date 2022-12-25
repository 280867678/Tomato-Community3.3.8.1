package p007b.p014c.p015a.p017b;

/* renamed from: b.c.a.b.h */
/* loaded from: classes2.dex */
public class C0626h implements AbstractC0620b {

    /* renamed from: b */
    public static final AbstractC0619a f211b = new C0625g();

    /* renamed from: c */
    public boolean f212c;

    /* renamed from: d */
    public boolean f213d;

    /* renamed from: e */
    public AbstractC0619a f214e;

    static {
        new C0624f();
    }

    /* renamed from: a */
    public void mo3878a() {
    }

    /* renamed from: a */
    public boolean mo5473a(AbstractC0619a abstractC0619a) {
        synchronized (this) {
            if (isDone()) {
                return false;
            }
            this.f214e = abstractC0619a;
            return true;
        }
    }

    /* renamed from: b */
    public void m5476b() {
    }

    /* renamed from: c */
    public void m5475c() {
    }

    @Override // p007b.p014c.p015a.p017b.AbstractC0619a
    public boolean cancel() {
        synchronized (this) {
            if (this.f212c) {
                return false;
            }
            if (this.f213d) {
                return true;
            }
            this.f213d = true;
            AbstractC0619a abstractC0619a = this.f214e;
            this.f214e = null;
            if (abstractC0619a != null) {
                abstractC0619a.cancel();
            }
            mo3878a();
            m5476b();
            return true;
        }
    }

    /* renamed from: d */
    public boolean m5474d() {
        synchronized (this) {
            if (this.f213d) {
                return false;
            }
            if (this.f212c) {
                return false;
            }
            this.f212c = true;
            this.f214e = null;
            m5475c();
            m5476b();
            return true;
        }
    }

    @Override // p007b.p014c.p015a.p017b.AbstractC0619a
    public boolean isCancelled() {
        boolean z;
        synchronized (this) {
            if (!this.f213d && (this.f214e == null || !this.f214e.isCancelled())) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    public boolean isDone() {
        return this.f212c;
    }
}
