package p007b.p025d.p026a.p030d;

import java.util.Date;
import java.util.HashMap;

/* renamed from: b.d.a.d.c */
/* loaded from: classes2.dex */
public class C0769c {

    /* renamed from: a */
    public static C0769c f531a;

    /* renamed from: b */
    public String f532b;

    /* renamed from: c */
    public HashMap<String, C0771e> f533c = new HashMap<>();

    /* renamed from: a */
    public static C0769c m5146a() {
        if (f531a == null) {
            synchronized (C0769c.class) {
                f531a = new C0769c();
            }
        }
        return f531a;
    }

    /* renamed from: a */
    public String m5144a(String str) {
        synchronized (this) {
            C0771e c0771e = this.f533c.get(str);
            if (c0771e == null || c0771e.f550a != 1) {
                return null;
            }
            return c0771e.f552c;
        }
    }

    /* renamed from: a */
    public void m5143a(String str, String str2) {
        synchronized (this) {
            C0771e c0771e = this.f533c.get(str);
            if (c0771e == null) {
                c0771e = new C0771e();
                this.f533c.put(str, c0771e);
            }
            c0771e.f550a = 1;
            c0771e.f552c = str2;
            c0771e.f551b = new Date().getTime();
        }
    }

    /* renamed from: b */
    public void m5142b(String str) {
        synchronized (this) {
            C0771e c0771e = this.f533c.get(str);
            if (c0771e != null) {
                if (c0771e.f550a != 1) {
                    return;
                }
                c0771e.f552c = null;
                c0771e.f550a = 0;
            }
            RunnableC0778l.m5061a().m5054a(new RunnableC0768b(this, str));
        }
    }

    /* renamed from: c */
    public void m5141c(String str) {
        this.f532b = str;
    }
}
