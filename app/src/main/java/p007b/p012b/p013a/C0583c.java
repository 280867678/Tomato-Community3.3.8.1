package p007b.p012b.p013a;

import android.text.TextUtils;
import com.gen.p059mh.webapps.listener.WebAppInformation;

/* renamed from: b.b.a.c */
/* loaded from: classes2.dex */
public class C0583c {

    /* renamed from: a */
    public String f126a;

    /* renamed from: b */
    public String f127b;

    /* renamed from: c */
    public long f128c;

    /* renamed from: d */
    public long f129d;

    /* renamed from: e */
    public long f130e;

    /* renamed from: f */
    public long f131f;

    /* renamed from: g */
    public byte[] f132g;

    /* renamed from: h */
    public byte[] f133h;

    /* renamed from: b.b.a.c$a */
    /* loaded from: classes2.dex */
    public static final class C0584a {

        /* renamed from: a */
        public String f134a;

        /* renamed from: b */
        public String f135b;

        /* renamed from: e */
        public byte[] f138e;

        /* renamed from: f */
        public byte[] f139f;

        /* renamed from: c */
        public long f136c = WebAppInformation.maxCacheSize;

        /* renamed from: d */
        public long f137d = 604800000;

        /* renamed from: g */
        public long f140g = 52428800;

        /* renamed from: a */
        public C0584a m5512a(String str) {
            this.f134a = str;
            return this;
        }

        /* renamed from: a */
        public C0584a m5511a(byte[] bArr) {
            this.f139f = bArr;
            return this;
        }

        /* renamed from: a */
        public C0583c m5513a() {
            C0583c c0583c = new C0583c();
            c0583c.m5523a(this.f134a);
            c0583c.m5517b(this.f135b);
            c0583c.m5521b(this.f136c);
            c0583c.m5515c(this.f140g);
            c0583c.m5527a(this.f137d);
            c0583c.m5516b(this.f138e);
            c0583c.m5522a(this.f139f);
            return c0583c;
        }

        /* renamed from: b */
        public C0584a m5510b(String str) {
            this.f135b = str;
            return this;
        }

        /* renamed from: b */
        public C0584a m5509b(byte[] bArr) {
            this.f138e = bArr;
            return this;
        }
    }

    public C0583c() {
        this.f128c = WebAppInformation.maxCacheSize;
        this.f129d = 604800000L;
        this.f130e = 500L;
        this.f131f = 52428800L;
    }

    /* renamed from: a */
    public final void m5527a(long j) {
        this.f129d = j;
    }

    /* renamed from: a */
    public final void m5523a(String str) {
        this.f126a = str;
    }

    /* renamed from: a */
    public final void m5522a(byte[] bArr) {
        this.f133h = bArr;
    }

    /* renamed from: a */
    public boolean m5528a() {
        return !TextUtils.isEmpty(this.f126a) && !TextUtils.isEmpty(this.f127b) && this.f132g != null && this.f133h != null;
    }

    /* renamed from: b */
    public final void m5521b(long j) {
        this.f128c = j;
    }

    /* renamed from: b */
    public final void m5517b(String str) {
        this.f127b = str;
    }

    /* renamed from: b */
    public final void m5516b(byte[] bArr) {
        this.f132g = bArr;
    }

    /* renamed from: c */
    public final void m5515c(long j) {
        this.f131f = j;
    }
}
