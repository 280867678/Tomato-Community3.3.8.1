package p007b.p025d.p026a;

import java.util.HashMap;
import java.util.Map;

/* renamed from: b.d.a.ha */
/* loaded from: classes2.dex */
public class C0795ha {

    /* renamed from: a */
    public String f685a;

    /* renamed from: b */
    public String f686b;

    /* renamed from: c */
    public int f687c;

    /* renamed from: d */
    public Map<String, C0797ia> f688d = new HashMap();

    public C0795ha(String str, String str2) {
        this.f685a = str;
        this.f686b = str2;
    }

    /* renamed from: a */
    public int m4958a() {
        return this.f688d.size();
    }

    /* renamed from: a */
    public C0797ia m4957a(int i) {
        for (Map.Entry<String, C0797ia> entry : this.f688d.entrySet()) {
            C0797ia value = entry.getValue();
            if (value.m4949a() == i) {
                return value;
            }
        }
        return null;
    }

    /* renamed from: a */
    public C0797ia m4956a(long j) {
        for (Map.Entry<String, C0797ia> entry : this.f688d.entrySet()) {
            C0797ia value = entry.getValue();
            int m4948b = value.m4948b();
            int m4947c = value.m4947c();
            if (j >= m4948b && j < m4948b + m4947c) {
                return value;
            }
        }
        return null;
    }

    /* renamed from: a */
    public C0797ia m4954a(String str) {
        return this.f688d.get(str);
    }

    /* renamed from: a */
    public void m4955a(C0797ia c0797ia) {
        this.f688d.put(c0797ia.m4945e(), c0797ia);
    }

    /* renamed from: b */
    public int m4953b() {
        return this.f687c;
    }

    /* renamed from: b */
    public void m4952b(int i) {
        this.f687c = i;
    }

    /* renamed from: c */
    public String m4951c() {
        return this.f686b;
    }

    /* renamed from: d */
    public String m4950d() {
        return this.f685a;
    }
}
