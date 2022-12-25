package p007b.p025d.p026a;

/* renamed from: b.d.a.M */
/* loaded from: classes2.dex */
public class C0742M {

    /* renamed from: a */
    public String f422a;

    /* renamed from: b */
    public String f423b;

    /* renamed from: c */
    public int f424c;

    /* renamed from: d */
    public String f425d;

    /* renamed from: e */
    public int f426e;

    /* renamed from: f */
    public String f427f;

    /* renamed from: g */
    public String f428g;

    public C0742M(String str, String str2, int i, String str3, int i2, String str4, String str5) {
        this.f422a = str;
        this.f423b = str2;
        this.f424c = i;
        this.f425d = str3;
        this.f426e = i2;
        this.f428g = str4;
        this.f427f = str5;
    }

    public String toString() {
        return String.format("P2PId:%s localAddr:%s:%d natAddr:%s:%d", this.f422a, this.f423b, Integer.valueOf(this.f424c), this.f425d, Integer.valueOf(this.f426e));
    }
}
