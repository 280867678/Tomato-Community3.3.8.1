package p007b.p014c.p015a;

import com.koushikdutta.async.AsyncServer;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0617f;

/* renamed from: b.c.a.m */
/* loaded from: classes2.dex */
public class C0713m implements AbstractC0719r {

    /* renamed from: a */
    public static final /* synthetic */ boolean f358a = !C0713m.class.desiredAssertionStatus();

    /* renamed from: b */
    public AbstractC0719r f359b;

    /* renamed from: c */
    public boolean f360c;

    /* renamed from: e */
    public AbstractC0617f f362e;

    /* renamed from: g */
    public boolean f364g;

    /* renamed from: d */
    public final C0714n f361d = new C0714n();

    /* renamed from: f */
    public int f363f = Integer.MAX_VALUE;

    public C0713m(AbstractC0719r abstractC0719r) {
        m5334a(abstractC0719r);
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5289a(AbstractC0617f abstractC0617f) {
        this.f362e = abstractC0617f;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5288a(C0714n c0714n) {
        if (mo5283c().m3888b() == Thread.currentThread()) {
            mo5280b(c0714n);
            if (!m5332d()) {
                this.f359b.mo5288a(c0714n);
            }
            synchronized (this.f361d) {
                c0714n.m5328a(this.f361d);
            }
            return;
        }
        synchronized (this.f361d) {
            if (this.f361d.m5303m() >= this.f363f) {
                return;
            }
            mo5280b(c0714n);
            c0714n.m5328a(this.f361d);
            mo5283c().m3893a(new Runnable() { // from class: b.c.a.-$$Lambda$BX_9F6Pgr6ONq24zEpARmFqKqDc
                @Override // java.lang.Runnable
                public final void run() {
                    C0713m.this.m5331e();
                }
            });
        }
    }

    /* renamed from: a */
    public void m5334a(AbstractC0719r abstractC0719r) {
        this.f359b = abstractC0719r;
        this.f359b.mo5289a(new AbstractC0617f() { // from class: b.c.a.-$$Lambda$CF6QUsYFfHqPxmNKdbnHX9nwKxw
            @Override // p007b.p014c.p015a.p016a.AbstractC0617f
            /* renamed from: a */
            public final void mo5215a() {
                C0713m.this.m5331e();
            }
        });
    }

    /* renamed from: b */
    public void m5333b(int i) {
        if (f358a || i >= 0) {
            this.f363f = i;
            return;
        }
        throw new AssertionError();
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: b */
    public void mo5287b(AbstractC0610a abstractC0610a) {
        this.f359b.mo5287b(abstractC0610a);
    }

    /* renamed from: b */
    public void mo5280b(C0714n c0714n) {
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: c */
    public AsyncServer mo5283c() {
        return this.f359b.mo5283c();
    }

    /* renamed from: d */
    public boolean m5332d() {
        return this.f361d.m5308h() || this.f360c;
    }

    /* renamed from: e */
    public final void m5331e() {
        boolean m5307i;
        AbstractC0617f abstractC0617f;
        if (this.f360c) {
            return;
        }
        synchronized (this.f361d) {
            this.f359b.mo5288a(this.f361d);
            m5307i = this.f361d.m5307i();
        }
        if (m5307i && this.f364g) {
            this.f359b.end();
        }
        if (!m5307i || (abstractC0617f = this.f362e) == null) {
            return;
        }
        abstractC0617f.mo5215a();
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    public void end() {
        if (mo5283c().m3888b() != Thread.currentThread()) {
            mo5283c().m3893a(new Runnable() { // from class: b.c.a.-$$Lambda$vfsfQkY11eiLiNh8YyUw4C-TeN4
                @Override // java.lang.Runnable
                public final void run() {
                    C0713m.this.end();
                }
            });
            return;
        }
        synchronized (this.f361d) {
            if (this.f361d.m5308h()) {
                this.f364g = true;
            } else {
                this.f359b.end();
            }
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: h */
    public AbstractC0617f mo5286h() {
        return this.f362e;
    }
}
