package p007b.p025d.p026a;

/* renamed from: b.d.a.V */
/* loaded from: classes2.dex */
public class C0750V {

    /* renamed from: a */
    public C0779da f460a = new C0779da();

    /* renamed from: b */
    public int f461b = 0;

    /* renamed from: c */
    public int f462c = 0;

    /* renamed from: a */
    public int m5222a(byte[] bArr, int i) {
        int m5042a;
        synchronized (this) {
            m5042a = this.f460a.m5042a(bArr, i);
            this.f461b += m5042a;
        }
        return m5042a;
    }

    /* renamed from: a */
    public void m5223a(int i) {
        synchronized (this) {
            this.f460a.m5039c();
            this.f461b = i;
            this.f462c = i;
        }
    }

    /* renamed from: a */
    public boolean m5224a() {
        boolean z;
        synchronized (this) {
            z = this.f460a.m5041b() == 0;
        }
        return z;
    }

    /* renamed from: b */
    public int m5221b() {
        int i;
        synchronized (this) {
            i = this.f461b;
        }
        return i;
    }

    /* renamed from: b */
    public int m5219b(byte[] bArr, int i) {
        int m5040b;
        synchronized (this) {
            m5040b = this.f460a.m5040b(bArr, i);
            this.f462c += m5040b;
        }
        return m5040b;
    }

    /* renamed from: b */
    public boolean m5220b(int i) {
        boolean m5043a;
        synchronized (this) {
            m5043a = this.f460a.m5043a(i - this.f461b);
            if (m5043a) {
                this.f461b = i;
            }
        }
        return m5043a;
    }

    /* renamed from: c */
    public int m5218c() {
        int i;
        synchronized (this) {
            i = this.f462c;
        }
        return i;
    }

    /* renamed from: d */
    public int m5217d() {
        int m5044a;
        synchronized (this) {
            m5044a = this.f460a.m5044a();
        }
        return m5044a;
    }

    /* renamed from: e */
    public int m5216e() {
        int m5041b;
        synchronized (this) {
            m5041b = this.f460a.m5041b();
        }
        return m5041b;
    }
}
