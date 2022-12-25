package p007b.p025d.p026a.p030d;

import java.util.ArrayList;
import java.util.List;

/* renamed from: b.d.a.d.d */
/* loaded from: classes2.dex */
public class C0770d extends C0766a {

    /* renamed from: s */
    public int f542s;

    /* renamed from: t */
    public int f543t;

    /* renamed from: u */
    public long f544u;

    /* renamed from: v */
    public long f545v;

    /* renamed from: w */
    public long f546w;

    /* renamed from: x */
    public int f547x;

    /* renamed from: y */
    public int f548y;

    /* renamed from: z */
    public int f549z;

    /* renamed from: o */
    public List<String> f538o = new ArrayList();

    /* renamed from: r */
    public List<String> f541r = new ArrayList();

    /* renamed from: k */
    public int f534k = 0;

    /* renamed from: l */
    public long f535l = 0;

    /* renamed from: m */
    public long f536m = 0;

    /* renamed from: n */
    public long f537n = 0;

    /* renamed from: p */
    public int f539p = 0;

    /* renamed from: q */
    public int f540q = 0;

    public C0770d() {
        this.f543t = -1;
        this.f547x = 0;
        this.f548y = -1;
        this.f549z = -1;
        this.f519c = 100;
        this.f525i = 0;
        this.f543t = -1;
        this.f547x = 0;
        this.f548y = -1;
        this.f549z = -1;
    }

    /* renamed from: a */
    public void m5140a(int i) {
        this.f540q = i;
    }

    /* renamed from: a */
    public void m5139a(long j) {
        this.f536m = j;
    }

    /* renamed from: a */
    public void m5138a(List<String> list) {
        this.f538o = list;
    }

    /* renamed from: b */
    public void m5137b(int i) {
        this.f534k = i;
    }

    /* renamed from: b */
    public void m5136b(long j) {
        this.f544u = j;
    }

    /* renamed from: b */
    public void m5135b(List<String> list) {
        this.f541r = list;
    }

    /* renamed from: c */
    public void m5134c(int i) {
        this.f543t = i;
    }

    /* renamed from: c */
    public void m5133c(long j) {
        this.f546w = j;
    }

    @Override // p007b.p025d.p026a.p030d.C0766a
    /* renamed from: clone  reason: collision with other method in class */
    public C0770d mo5793clone() {
        return (C0770d) super.m5789clone();
    }

    /* renamed from: d */
    public void m5132d(int i) {
        this.f547x = i;
    }

    /* renamed from: d */
    public void m5131d(long j) {
        this.f537n = j;
    }

    /* renamed from: e */
    public void m5130e(int i) {
        this.f549z = i;
    }

    /* renamed from: e */
    public void m5129e(long j) {
        this.f535l = j;
    }

    /* renamed from: f */
    public void m5128f(int i) {
        this.f548y = i;
    }

    /* renamed from: f */
    public void m5127f(long j) {
        this.f545v = j;
    }

    /* renamed from: g */
    public void m5126g(int i) {
        this.f539p = i;
    }

    /* renamed from: h */
    public void m5125h(int i) {
        this.f542s = i;
    }

    /* renamed from: j */
    public long m5124j() {
        return this.f536m;
    }

    /* renamed from: k */
    public int m5123k() {
        return this.f540q;
    }

    /* renamed from: l */
    public int m5122l() {
        return this.f534k;
    }

    /* renamed from: m */
    public int m5121m() {
        return this.f543t;
    }

    /* renamed from: n */
    public long m5120n() {
        return this.f544u;
    }

    /* renamed from: o */
    public long m5119o() {
        return this.f546w;
    }

    /* renamed from: p */
    public int m5118p() {
        return this.f547x;
    }

    /* renamed from: q */
    public int m5117q() {
        return this.f549z;
    }

    /* renamed from: r */
    public int m5116r() {
        return this.f548y;
    }

    /* renamed from: s */
    public List<String> m5115s() {
        return this.f538o;
    }

    /* renamed from: t */
    public long m5114t() {
        return this.f537n;
    }

    public String toString() {
        return "FlowReportItem {id=" + this.f517a + ", taskId='" + this.f520d + "', chanId='" + this.f521e + "', domain='" + this.f522f + "', uri='" + this.f523g + "', urlName='" + this.f524h + "', CDNFlow=" + this.f536m + ", P2PFlow=" + this.f537n + ", nodeList=" + this.f538o + ", P2PSwitchCount=" + this.f539p + ", CDNSwitchCount=" + this.f540q + ", SDKIPList=" + this.f541r + ", sdkType=" + this.f525i + ", sdkNetType=" + this.f542s + ", curAccelState=" + this.f543t + ", curTimeStamp=" + this.f544u + ", startTimeStamp=" + this.f545v + ", endTimeStamp=" + this.f546w + ", exceptionCount=" + this.f547x + ", exceptionType=" + this.f548y + ", exceptionStatus=" + this.f549z + '}';
    }

    /* renamed from: u */
    public int m5113u() {
        return this.f539p;
    }

    /* renamed from: v */
    public long m5112v() {
        return this.f535l;
    }

    /* renamed from: w */
    public List<String> m5111w() {
        return this.f541r;
    }

    /* renamed from: x */
    public int m5110x() {
        return this.f542s;
    }

    /* renamed from: y */
    public long m5109y() {
        return this.f545v;
    }

    /* renamed from: z */
    public void m5108z() {
        this.f535l = 0L;
        this.f536m = 0L;
        this.f537n = 0L;
        this.f539p = 0;
        this.f540q = 0;
        this.f547x = 0;
        this.f548y = -1;
        this.f549z = -1;
        m5127f(System.currentTimeMillis());
    }
}
