package p007b.p025d.p026a;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: b.d.a.ga */
/* loaded from: classes2.dex */
public class C0793ga {

    /* renamed from: b */
    public int f673b;

    /* renamed from: c */
    public C0791fa f674c = null;

    /* renamed from: a */
    public LinkedList<C0791fa> f672a = new LinkedList<>();

    public C0793ga(int i) {
        this.f673b = i;
    }

    /* renamed from: a */
    public double m4962a() {
        int i;
        synchronized (this) {
            if (this.f672a.size() == 0) {
                return 0.0d;
            }
            long time = (new Date().getTime() - this.f674c.f625b) / 1000;
            int i2 = 1;
            while (true) {
                i = 0;
                if (i2 >= time) {
                    break;
                }
                C0791fa c0791fa = new C0791fa(0);
                c0791fa.f625b = this.f674c.f625b + (i2 * 1000);
                m4960a(c0791fa);
                i2++;
            }
            Iterator<C0791fa> it2 = this.f672a.iterator();
            double d = 0.0d;
            while (it2.hasNext()) {
                d += Math.pow(it2.next().f624a, 2.0d);
                i++;
            }
            if (i == 0) {
                return 0.0d;
            }
            return Math.sqrt(d / i) / 1024.0d;
        }
    }

    /* renamed from: a */
    public void m4961a(int i) {
        synchronized (this) {
            long time = new Date().getTime();
            if (this.f674c == null) {
                this.f674c = new C0791fa(0);
                m4960a(this.f674c);
            }
            long j = (time - this.f674c.f625b) / 1000;
            if (j == 0) {
                this.f674c.f624a += i;
            } else {
                for (int i2 = 1; i2 < j; i2++) {
                    C0791fa c0791fa = new C0791fa(0);
                    c0791fa.f625b = this.f674c.f625b + (i2 * 1000);
                    m4960a(c0791fa);
                }
                this.f674c = new C0791fa(i);
                m4960a(this.f674c);
            }
        }
    }

    /* renamed from: a */
    public final void m4960a(C0791fa c0791fa) {
        if (this.f672a.size() < this.f673b) {
            this.f672a.add(c0791fa);
        }
        if (this.f672a.size() == this.f673b) {
            this.f672a.remove();
            this.f672a.add(c0791fa);
        }
    }

    /* renamed from: b */
    public void m4959b() {
        synchronized (this) {
            this.f672a.clear();
        }
    }
}
