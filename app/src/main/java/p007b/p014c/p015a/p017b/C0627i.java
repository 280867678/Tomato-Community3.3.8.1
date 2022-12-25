package p007b.p014c.p015a.p017b;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import p007b.p014c.p015a.C0699e;
import p007b.p014c.p015a.p017b.C0627i;

/* renamed from: b.c.a.b.i */
/* loaded from: classes2.dex */
public class C0627i<T> extends C0626h implements AbstractC0621c<T> {

    /* renamed from: f */
    public C0699e f215f;

    /* renamed from: g */
    public Exception f216g;

    /* renamed from: h */
    public T f217h;

    /* renamed from: i */
    public boolean f218i;

    /* renamed from: j */
    public AbstractC0628a<T> f219j;

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: b.c.a.b.i$a */
    /* loaded from: classes2.dex */
    public interface AbstractC0628a<T> {
        /* renamed from: a */
        void mo5453a(Exception exc, T t, C0629b c0629b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b.c.a.b.i$b */
    /* loaded from: classes2.dex */
    public static class C0629b {

        /* renamed from: a */
        public Exception f220a;

        /* renamed from: b */
        public Object f221b;

        /* renamed from: c */
        public AbstractC0628a f222c;

        /* JADX WARN: Multi-variable type inference failed */
        /* renamed from: a */
        public void m5452a() {
            while (true) {
                AbstractC0628a abstractC0628a = this.f222c;
                if (abstractC0628a != 0) {
                    Exception exc = this.f220a;
                    Object obj = this.f221b;
                    this.f222c = null;
                    this.f220a = null;
                    this.f221b = null;
                    abstractC0628a.mo5453a(exc, obj, this);
                } else {
                    return;
                }
            }
        }
    }

    public C0627i() {
    }

    public C0627i(T t) {
        m5460a((C0627i<T>) t);
    }

    /* renamed from: a */
    public static /* synthetic */ AbstractFutureC0622d m5464a(AbstractC0630j abstractC0630j, Object obj) {
        return new C0627i(abstractC0630j.mo5347a(obj));
    }

    /* renamed from: a */
    public static /* synthetic */ void m5468a(C0627i c0627i, AbstractC0631k abstractC0631k, Exception exc, Object obj, C0629b c0629b) {
        if (exc != null) {
            c0627i.m5461a(exc, (Exception) null, c0629b);
            return;
        }
        try {
            c0627i.m5472a(abstractC0631k.mo5451a(obj), c0629b);
        } catch (Exception e) {
            c0627i.m5461a(e, (Exception) null, c0629b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    public /* synthetic */ void m5467a(C0627i c0627i, Exception exc, Object obj) {
        CancellationException cancellationException = null;
        if (!m5461a(exc, (Exception) obj, (C0629b) null)) {
            cancellationException = new CancellationException();
        }
        c0627i.m5462a((Exception) cancellationException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    public /* synthetic */ void m5466a(C0627i c0627i, Exception exc, Object obj, C0629b c0629b) {
        c0627i.m5461a((Exception) (m5461a(exc, (Exception) obj, c0629b) ? null : new CancellationException()), (CancellationException) obj, c0629b);
    }

    /* renamed from: a */
    public final AbstractFutureC0622d<T> m5472a(AbstractFutureC0622d<T> abstractFutureC0622d, C0629b c0629b) {
        mo5473a((AbstractC0619a) abstractFutureC0622d);
        final C0627i c0627i = new C0627i();
        if (abstractFutureC0622d instanceof C0627i) {
            ((C0627i) abstractFutureC0622d).m5458b(c0629b, new AbstractC0628a() { // from class: b.c.a.b.-$$Lambda$i$mN9iJxXnsDWwlmIFFPgyCDM91_4
                @Override // p007b.p014c.p015a.p017b.C0627i.AbstractC0628a
                /* renamed from: a */
                public final void mo5453a(Exception exc, Object obj, C0627i.C0629b c0629b2) {
                    C0627i.this.m5466a(c0627i, exc, obj, c0629b2);
                }
            });
        } else {
            abstractFutureC0622d.mo5471a(new AbstractC0623e() { // from class: b.c.a.b.-$$Lambda$i$T7NKeOQHxw0c25BaD2f0-NzHbUM
                @Override // p007b.p014c.p015a.p017b.AbstractC0623e
                /* renamed from: a */
                public final void mo5444a(Exception exc, Object obj) {
                    C0627i.this.m5467a(c0627i, exc, obj);
                }
            });
        }
        return c0627i;
    }

    @Override // p007b.p014c.p015a.p017b.AbstractFutureC0622d
    /* renamed from: a */
    public <R> AbstractFutureC0622d<R> mo5465a(final AbstractC0630j<R, T> abstractC0630j) {
        return m5463a((AbstractC0631k) new AbstractC0631k() { // from class: b.c.a.b.-$$Lambda$Ey6FNJWRdpS3ger8Kg59SRqF4z8
            @Override // p007b.p014c.p015a.p017b.AbstractC0631k
            /* renamed from: a */
            public final AbstractFutureC0622d mo5451a(Object obj) {
                return C0627i.m5464a(AbstractC0630j.this, obj);
            }
        });
    }

    /* renamed from: a */
    public <R> AbstractFutureC0622d<R> m5463a(final AbstractC0631k<R, T> abstractC0631k) {
        final C0627i c0627i = new C0627i();
        c0627i.mo5473a((AbstractC0619a) this);
        m5458b(null, new AbstractC0628a() { // from class: b.c.a.b.-$$Lambda$eCAV3q6ftGHGJhfz56bI34Xv8IM
            @Override // p007b.p014c.p015a.p017b.C0627i.AbstractC0628a
            /* renamed from: a */
            public final void mo5453a(Exception exc, Object obj, C0627i.C0629b c0629b) {
                C0627i.m5468a(C0627i.this, abstractC0631k, exc, obj, c0629b);
            }
        });
        return c0627i;
    }

    @Override // p007b.p014c.p015a.p017b.AbstractFutureC0622d
    /* renamed from: a */
    public void mo5471a(final AbstractC0623e<T> abstractC0623e) {
        if (abstractC0623e == null) {
            m5458b(null, null);
        } else {
            m5458b(null, new AbstractC0628a() { // from class: b.c.a.b.-$$Lambda$dsT2_p7adDW_Rg5aXwJnL6Q52Fg
                @Override // p007b.p014c.p015a.p017b.C0627i.AbstractC0628a
                /* renamed from: a */
                public final void mo5453a(Exception exc, Object obj, C0627i.C0629b c0629b) {
                    AbstractC0623e.this.mo5444a(exc, obj);
                }
            });
        }
    }

    /* renamed from: a */
    public final void m5469a(C0629b c0629b, AbstractC0628a<T> abstractC0628a) {
        if (!this.f218i && abstractC0628a != null) {
            boolean z = false;
            if (c0629b == null) {
                z = true;
                c0629b = new C0629b();
            }
            c0629b.f222c = abstractC0628a;
            c0629b.f220a = this.f216g;
            c0629b.f221b = this.f217h;
            if (!z) {
                return;
            }
            c0629b.m5452a();
        }
    }

    @Override // p007b.p014c.p015a.p017b.C0626h
    /* renamed from: a */
    public boolean mo5473a(AbstractC0619a abstractC0619a) {
        return super.mo5473a(abstractC0619a);
    }

    /* renamed from: a */
    public boolean m5462a(Exception exc) {
        return m5461a(exc, (Exception) null, (C0629b) null);
    }

    /* renamed from: a */
    public final boolean m5461a(Exception exc, T t, C0629b c0629b) {
        synchronized (this) {
            if (!super.m5474d()) {
                return false;
            }
            this.f217h = t;
            this.f216g = exc;
            m5454h();
            m5469a(c0629b, m5455g());
            return true;
        }
    }

    /* renamed from: a */
    public boolean m5460a(T t) {
        return m5461a((Exception) null, (Exception) t, (C0629b) null);
    }

    /* renamed from: a */
    public final boolean m5459a(boolean z) {
        AbstractC0628a<T> m5455g;
        if (!super.cancel()) {
            return false;
        }
        synchronized (this) {
            this.f216g = new CancellationException();
            m5454h();
            m5455g = m5455g();
            this.f218i = z;
        }
        m5469a((C0629b) null, m5455g);
        return true;
    }

    /* renamed from: b */
    public void m5458b(C0629b c0629b, AbstractC0628a<T> abstractC0628a) {
        synchronized (this) {
            this.f219j = abstractC0628a;
            if (isDone() || isCancelled()) {
                m5469a(c0629b, m5455g());
            }
        }
    }

    @Override // p007b.p014c.p015a.p017b.C0626h, p007b.p014c.p015a.p017b.AbstractC0619a
    public boolean cancel() {
        return m5459a(this.f218i);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return cancel();
    }

    /* renamed from: e */
    public C0699e m5457e() {
        if (this.f215f == null) {
            this.f215f = new C0699e();
        }
        return this.f215f;
    }

    /* renamed from: f */
    public final T m5456f() {
        Exception exc = this.f216g;
        if (exc == null) {
            return this.f217h;
        }
        throw new ExecutionException(exc);
    }

    /* renamed from: g */
    public final AbstractC0628a<T> m5455g() {
        AbstractC0628a<T> abstractC0628a = this.f219j;
        this.f219j = null;
        return abstractC0628a;
    }

    @Override // java.util.concurrent.Future
    public T get() {
        synchronized (this) {
            if (!isCancelled() && !isDone()) {
                m5457e().m5342a();
                return m5456f();
            }
            return m5456f();
        }
    }

    @Override // java.util.concurrent.Future
    public T get(long j, TimeUnit timeUnit) {
        synchronized (this) {
            if (!isCancelled() && !isDone()) {
                C0699e m5457e = m5457e();
                if (!m5457e.m5341a(j, timeUnit)) {
                    throw new TimeoutException();
                }
                return m5456f();
            }
            return m5456f();
        }
    }

    /* renamed from: h */
    public void m5454h() {
        C0699e c0699e = this.f215f;
        if (c0699e != null) {
            c0699e.m5340b();
            this.f215f = null;
        }
    }
}
