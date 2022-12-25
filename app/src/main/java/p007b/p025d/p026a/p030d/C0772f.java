package p007b.p025d.p026a.p030d;

/* renamed from: b.d.a.d.f */
/* loaded from: classes2.dex */
public class C0772f extends C0766a {

    /* renamed from: k */
    public String f553k;

    /* renamed from: l */
    public String f554l;

    /* renamed from: o */
    public long f557o;

    /* renamed from: m */
    public int f555m = 0;

    /* renamed from: n */
    public long f556n = 0;

    /* renamed from: r */
    public int f559r = 0;

    /* renamed from: p */
    public long f558p = 0;

    public C0772f() {
        this.f519c = 102;
    }

    /* renamed from: a */
    public void m5107a(int i) {
        this.f559r = i;
    }

    /* renamed from: a */
    public void m5106a(long j) {
        this.f556n = j;
    }

    /* renamed from: b */
    public void m5105b(int i) {
        this.f555m = i;
    }

    /* renamed from: b */
    public void m5104b(long j) {
    }

    /* renamed from: c */
    public void m5103c(long j) {
        this.f558p = j;
    }

    @Override // p007b.p025d.p026a.p030d.C0766a
    /* renamed from: clone  reason: collision with other method in class */
    public C0770d mo5793clone() {
        return (C0770d) super.m5789clone();
    }

    /* renamed from: d */
    public void m5102d(long j) {
        this.f557o = j;
    }

    /* renamed from: f */
    public void m5101f(String str) {
        this.f553k = str;
    }

    /* renamed from: g */
    public void m5100g(String str) {
        this.f554l = str;
    }

    /* renamed from: j */
    public int m5099j() {
        return this.f559r;
    }

    /* renamed from: k */
    public long m5098k() {
        return this.f556n;
    }

    /* renamed from: l */
    public String m5097l() {
        return this.f553k;
    }

    /* renamed from: m */
    public String m5096m() {
        return this.f554l;
    }

    /* renamed from: n */
    public long m5095n() {
        return this.f558p;
    }

    /* renamed from: o */
    public int m5094o() {
        return this.f555m;
    }

    public String toString() {
        return "P2PConnReportItem{nodeId='" + this.f553k + "', state=" + this.f555m + ", consume=" + this.f556n + ", timeStamp=" + this.f557o + '}';
    }
}
