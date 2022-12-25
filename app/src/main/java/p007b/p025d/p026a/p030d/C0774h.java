package p007b.p025d.p026a.p030d;

/* renamed from: b.d.a.d.h */
/* loaded from: classes2.dex */
public class C0774h extends C0766a {

    /* renamed from: q */
    public long f570q;

    /* renamed from: r */
    public long f571r;

    /* renamed from: s */
    public long f572s;

    /* renamed from: o */
    public int f568o = 1;

    /* renamed from: p */
    public int f569p = 0;

    /* renamed from: k */
    public C0773g f564k = new C0773g();

    /* renamed from: l */
    public C0773g f565l = new C0773g();

    /* renamed from: m */
    public C0773g f566m = new C0773g();

    /* renamed from: n */
    public C0773g f567n = new C0773g();

    public C0774h() {
        this.f519c = 101;
    }

    /* renamed from: a */
    public void m5093a(int i, int i2) {
        C0773g c0773g;
        if (i == 0) {
            c0773g = this.f564k;
        } else if (i == 1) {
            c0773g = this.f566m;
        } else if (i != 2) {
            if (i != 3) {
                return;
            }
            C0773g c0773g2 = this.f567n;
            c0773g2.f563d = c0773g2.f562c - c0773g2.f561b;
            c0773g2.f560a = i2;
            this.f569p = (int) (this.f569p + c0773g2.f563d);
            this.f568o = i2;
            return;
        } else {
            c0773g = this.f565l;
        }
        c0773g.f563d = c0773g.f562c - c0773g.f561b;
        c0773g.f560a = i2;
        this.f569p = (int) (this.f569p + c0773g.f563d);
    }

    /* renamed from: a */
    public void m5092a(int i, long j) {
        C0773g c0773g;
        if (i == 0) {
            c0773g = this.f564k;
        } else if (i == 1) {
            c0773g = this.f566m;
        } else if (i != 2) {
            if (i == 3) {
                c0773g = this.f567n;
            }
            this.f571r = j;
        } else {
            c0773g = this.f565l;
        }
        c0773g.f562c = j;
        this.f571r = j;
    }

    /* renamed from: b */
    public void m5091b(int i, long j) {
        C0773g c0773g;
        if (i == 0) {
            this.f564k.f561b = j;
            this.f570q = j;
            return;
        }
        if (i == 1) {
            c0773g = this.f566m;
        } else if (i == 2) {
            c0773g = this.f565l;
        } else if (i != 3) {
            return;
        } else {
            c0773g = this.f567n;
        }
        c0773g.f561b = j;
    }

    @Override // p007b.p025d.p026a.p030d.C0766a
    /* renamed from: clone  reason: collision with other method in class */
    public C0774h mo5793clone() {
        return (C0774h) super.m5789clone();
    }

    /* renamed from: j */
    public long m5090j() {
        return this.f571r;
    }

    /* renamed from: k */
    public C0773g m5089k() {
        return this.f565l;
    }

    /* renamed from: l */
    public C0773g m5088l() {
        return this.f566m;
    }

    /* renamed from: m */
    public C0773g m5087m() {
        return this.f564k;
    }

    /* renamed from: n */
    public C0773g m5086n() {
        return this.f567n;
    }

    /* renamed from: o */
    public long m5085o() {
        return this.f570q;
    }

    /* renamed from: p */
    public int m5084p() {
        return this.f568o;
    }

    /* renamed from: q */
    public int m5083q() {
        return this.f569p;
    }

    public String toString() {
        return "PreRequstReportItem {phaseQuery=" + this.f564k + ", phaseDLHlsList=" + this.f565l + ", phaseHole=" + this.f566m + ", phaseRequest=" + this.f567n + ", state=" + this.f568o + ", totalConsume=" + this.f569p + ", startTime=" + this.f570q + ", endTime=" + this.f571r + ", timeStamp=" + this.f572s + '}';
    }
}
