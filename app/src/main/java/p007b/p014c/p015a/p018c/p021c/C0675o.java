package p007b.p014c.p015a.p018c.p021c;

import android.text.TextUtils;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.Protocol;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import p007b.p014c.p015a.AbstractC0712l;
import p007b.p014c.p015a.AbstractC0719r;
import p007b.p014c.p015a.C0608K;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.p016a.AbstractC0610a;
import p007b.p014c.p015a.p016a.AbstractC0617f;
import p007b.p014c.p015a.p018c.C0649b;
import p007b.p014c.p015a.p018c.C0686d;
import p007b.p014c.p015a.p018c.p020b.C0651b;

/* renamed from: b.c.a.c.c.o */
/* loaded from: classes2.dex */
public class C0675o implements AbstractC0674n {

    /* renamed from: a */
    public static final /* synthetic */ boolean f299a = !C0675o.class.desiredAssertionStatus();

    /* renamed from: d */
    public AbstractC0712l f302d;

    /* renamed from: e */
    public AbstractC0673m f303e;

    /* renamed from: g */
    public AbstractC0719r f305g;

    /* renamed from: h */
    public AbstractC0617f f306h;

    /* renamed from: i */
    public boolean f307i;

    /* renamed from: j */
    public boolean f308j;

    /* renamed from: m */
    public AbstractC0610a f311m;

    /* renamed from: b */
    public C0649b f300b = new C0649b();

    /* renamed from: c */
    public long f301c = -1;

    /* renamed from: f */
    public boolean f304f = false;

    /* renamed from: k */
    public int f309k = 200;

    /* renamed from: l */
    public String f310l = "HTTP/1.1";

    public C0675o(AbstractC0712l abstractC0712l, AbstractC0673m abstractC0673m) {
        this.f302d = abstractC0712l;
        this.f303e = abstractC0673m;
        if (C0686d.m5363a(Protocol.HTTP_1_1, abstractC0673m.mo5394i())) {
            this.f300b.m5435b("Connection", "Keep-Alive");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m5387a(C0714n c0714n, String str) {
        this.f301c = c0714n.m5303m();
        this.f300b.m5435b("Content-Length", Long.toString(this.f301c));
        if (str != null) {
            this.f300b.m5435b("Content-Type", str);
        }
        C0608K.m5479a(this, c0714n, new AbstractC0610a() { // from class: b.c.a.c.c.-$$Lambda$o$Z-uGantEONjmjljKU20Ewd3GGyw
            @Override // p007b.p014c.p015a.p016a.AbstractC0610a
            /* renamed from: a */
            public final void mo5196a(Exception exc) {
                C0675o.this.m5380c(exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [b.c.a.m, b.c.a.c.b.b] */
    /* renamed from: a */
    public /* synthetic */ void m5383a(boolean z, Exception exc) {
        AbstractC0712l abstractC0712l;
        if (exc != null) {
            mo5381b(exc);
            return;
        }
        if (z) {
            ?? c0651b = new C0651b(this.f302d);
            c0651b.m5333b(0);
            abstractC0712l = c0651b;
        } else {
            abstractC0712l = this.f302d;
        }
        this.f305g = abstractC0712l;
        this.f305g.mo5287b(this.f311m);
        this.f311m = null;
        this.f305g.mo5289a(this.f306h);
        this.f306h = null;
        if (this.f307i) {
            end();
        } else {
            mo5283c().m3893a(new Runnable() { // from class: b.c.a.c.c.-$$Lambda$o$KkCbIgFh1OFBKR85Pj802uwvD44
                @Override // java.lang.Runnable
                public final void run() {
                    C0675o.this.m5375i();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void m5380c(Exception exc) {
        mo5377f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void m5375i() {
        AbstractC0617f mo5286h = mo5286h();
        if (mo5286h != null) {
            mo5286h.mo5215a();
        }
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0674n
    /* renamed from: a */
    public int mo5389a() {
        return this.f309k;
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0674n
    /* renamed from: a */
    public AbstractC0674n mo5388a(int i) {
        this.f309k = i;
        return this;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5289a(AbstractC0617f abstractC0617f) {
        AbstractC0719r abstractC0719r = this.f305g;
        if (abstractC0719r != null) {
            abstractC0719r.mo5289a(abstractC0617f);
        } else {
            this.f306h = abstractC0617f;
        }
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: a */
    public void mo5288a(C0714n c0714n) {
        AbstractC0719r abstractC0719r;
        if (f299a || !this.f308j) {
            if (!this.f304f) {
                m5378e();
            }
            if (c0714n.m5303m() == 0 || (abstractC0719r = this.f305g) == null) {
                return;
            }
            abstractC0719r.mo5288a(c0714n);
            return;
        }
        throw new AssertionError();
    }

    @Override // p007b.p014c.p015a.p016a.AbstractC0610a
    /* renamed from: a */
    public void mo5196a(Exception exc) {
        end();
    }

    /* renamed from: a */
    public void m5386a(final String str, final C0714n c0714n) {
        mo5283c().m3893a(new Runnable() { // from class: b.c.a.c.c.-$$Lambda$o$hMufg15E_ove8oj6fqOWQo8J5ow
            @Override // java.lang.Runnable
            public final void run() {
                C0675o.this.m5387a(c0714n, str);
            }
        });
    }

    /* renamed from: a */
    public void m5385a(String str, String str2) {
        try {
            m5384a(str, str2.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: a */
    public void m5384a(String str, byte[] bArr) {
        m5386a(str, new C0714n(bArr));
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0674n
    /* renamed from: b */
    public String mo5382b() {
        return this.f310l;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: b */
    public void mo5287b(AbstractC0610a abstractC0610a) {
        AbstractC0719r abstractC0719r = this.f305g;
        if (abstractC0719r != null) {
            abstractC0719r.mo5287b(abstractC0610a);
        } else {
            this.f311m = abstractC0610a;
        }
    }

    /* renamed from: b */
    public void mo5381b(Exception exc) {
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: c */
    public AsyncServer mo5283c() {
        return this.f302d.mo5283c();
    }

    /* renamed from: d */
    public AbstractC0670j m5379d() {
        return this.f303e;
    }

    /* renamed from: e */
    public void m5378e() {
        final boolean z;
        if (this.f304f) {
            return;
        }
        this.f304f = true;
        String m5436b = this.f300b.m5436b("Transfer-Encoding");
        if ("".equals(m5436b)) {
            this.f300b.m5433d("Transfer-Encoding");
        }
        boolean z2 = ("Chunked".equalsIgnoreCase(m5436b) || m5436b == null) && !MainFragment.CLOSE_EVENT.equalsIgnoreCase(this.f300b.m5436b("Connection"));
        if (this.f301c < 0) {
            String m5436b2 = this.f300b.m5436b("Content-Length");
            if (!TextUtils.isEmpty(m5436b2)) {
                this.f301c = Long.valueOf(m5436b2).longValue();
            }
        }
        if (this.f301c >= 0 || !z2) {
            z = false;
        } else {
            this.f300b.m5435b("Transfer-Encoding", "Chunked");
            z = true;
        }
        C0608K.m5478a(this.f302d, this.f300b.m5432e(String.format(Locale.ENGLISH, "%s %s %s", this.f310l, Integer.valueOf(this.f309k), C0669i.m5414a(this.f309k))).getBytes(), new AbstractC0610a() { // from class: b.c.a.c.c.-$$Lambda$o$oXX-rG54sYG95wjjnO7u2NrTxaI
            @Override // p007b.p014c.p015a.p016a.AbstractC0610a
            /* renamed from: a */
            public final void mo5196a(Exception exc) {
                C0675o.this.m5383a(z, exc);
            }
        });
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0674n, p007b.p014c.p015a.AbstractC0719r
    public void end() {
        if (this.f307i) {
            return;
        }
        this.f307i = true;
        if (this.f304f && this.f305g == null) {
            return;
        }
        if (!this.f304f) {
            this.f300b.m5434c("Transfer-Encoding");
        }
        AbstractC0719r abstractC0719r = this.f305g;
        if (abstractC0719r instanceof C0651b) {
            abstractC0719r.end();
            return;
        }
        if (!this.f304f) {
            if (!this.f303e.getMethod().equalsIgnoreCase("HEAD")) {
                m5385a("text/html", "");
                return;
            }
            m5376g();
        }
        mo5377f();
    }

    /* renamed from: f */
    public void mo5377f() {
        this.f308j = true;
    }

    /* renamed from: g */
    public void m5376g() {
        m5378e();
    }

    @Override // p007b.p014c.p015a.p018c.p021c.AbstractC0674n
    public AbstractC0712l getSocket() {
        return this.f302d;
    }

    @Override // p007b.p014c.p015a.AbstractC0719r
    /* renamed from: h */
    public AbstractC0617f mo5286h() {
        AbstractC0719r abstractC0719r = this.f305g;
        return abstractC0719r != null ? abstractC0719r.mo5286h() : this.f306h;
    }

    public String toString() {
        return this.f300b == null ? super.toString() : this.f300b.m5432e(String.format(Locale.ENGLISH, "%s %s %s", this.f310l, Integer.valueOf(this.f309k), C0669i.m5414a(this.f309k)));
    }
}
