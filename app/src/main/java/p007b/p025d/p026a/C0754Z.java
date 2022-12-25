package p007b.p025d.p026a;

import com.zzz.ipfssdk.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import p007b.p014c.p015a.p018c.p021c.AbstractC0683s;
import p007b.p014c.p015a.p018c.p021c.C0669i;
import p007b.p025d.p026a.p028b.C0759a;
import p007b.p025d.p026a.p029c.C0762a;

/* renamed from: b.d.a.Z */
/* loaded from: classes2.dex */
public class C0754Z {

    /* renamed from: a */
    public static int f480a = C0759a.f500b;

    /* renamed from: b */
    public static C0754Z f481b = null;

    /* renamed from: c */
    public C0669i f482c = new C0669i();

    /* renamed from: d */
    public C0752X[] f483d;

    /* renamed from: e */
    public int[] f484e;

    /* renamed from: f */
    public Map<String, C0752X> f485f;

    public C0754Z() {
        C0762a.m5166b().m5167a();
        m5195a();
    }

    /* renamed from: d */
    public static C0754Z m5186d() {
        C0754Z c0754z;
        synchronized (C0754Z.class) {
            if (f481b == null) {
                f481b = new C0754Z();
            }
            c0754z = f481b;
        }
        return c0754z;
    }

    /* renamed from: a */
    public final void m5195a() {
        this.f483d = new C0752X[f480a];
        int i = 0;
        while (true) {
            int i2 = f480a;
            if (i >= i2) {
                this.f484e = new int[i2];
                this.f485f = new HashMap();
                return;
            }
            this.f483d[i] = new C0752X();
            i++;
        }
    }

    /* renamed from: a */
    public void m5194a(int i) {
        LogUtil.m119i(LogUtil.TAG_IPFS_SDK, "ProxyHttpServer init!");
        this.f482c.m5405b(i);
        this.f482c.m5413a(new C0753Y(this));
    }

    /* renamed from: a */
    public final void m5193a(int i, int i2) {
        C0752X[] c0752xArr = new C0752X[i2];
        C0752X[] c0752xArr2 = this.f483d;
        System.arraycopy(c0752xArr2, 0, c0752xArr, 0, c0752xArr2.length);
        for (int i3 = 0; i3 < i2 - i; i3++) {
            c0752xArr[this.f483d.length + i3] = new C0752X();
        }
        this.f483d = c0752xArr;
        this.f484e = new int[i2];
    }

    /* renamed from: a */
    public void m5192a(String str) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "deRegisterUri:" + str);
        C0752X c0752x = this.f485f.get(str);
        if (c0752x == null) {
            return;
        }
        c0752x.m5199c();
        synchronized (this) {
            for (int i = 0; i < f480a; i++) {
                if (this.f483d[i] == c0752x) {
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "requestHandlerUsing set 0");
                    this.f484e[i] = 0;
                }
            }
        }
        this.f485f.remove(str);
    }

    /* renamed from: a */
    public void m5191a(String str, AbstractC0683s abstractC0683s) {
        this.f482c.m5374a(str, abstractC0683s);
    }

    /* renamed from: b */
    public C0752X m5189b(String str) {
        C0752X c0752x;
        synchronized (this) {
            c0752x = this.f485f.get(str);
        }
        return c0752x;
    }

    /* renamed from: b */
    public synchronized void m5190b() {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "deRegisterUriAll" + this.f485f.size());
        if (this.f485f.size() > 0) {
            int i = 0;
            for (Map.Entry<String, C0752X> entry : this.f485f.entrySet()) {
                C0752X value = entry.getValue();
                if (value != null) {
                    i++;
                    value.m5199c();
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "deRegisterUriAll stopIndex " + i);
                    for (int i2 = 0; i2 < f480a; i2++) {
                        if (this.f483d[i2] == value) {
                            LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "requestHandlerUsing set 0");
                            this.f484e[i2] = 0;
                        }
                    }
                }
            }
            this.f485f.clear();
        }
    }

    /* renamed from: c */
    public C0752X m5187c(String str) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, "registerUri:" + str);
        C0752X m5189b = m5189b(str);
        if (m5189b != null) {
            return m5189b;
        }
        C0752X m5183g = m5183g();
        if (m5183g != null) {
            this.f485f.put(str, m5183g);
            m5191a(str, m5183g);
        }
        return m5183g;
    }

    /* renamed from: c */
    public ArrayList<C0752X> m5188c() {
        ArrayList<C0752X> arrayList;
        synchronized (this) {
            arrayList = new ArrayList<>();
            for (Map.Entry<String, C0752X> entry : this.f485f.entrySet()) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList;
    }

    /* renamed from: e */
    public void m5185e() {
        synchronized (this) {
            for (int i = 0; i < f480a; i++) {
                if (this.f484e[i] == 1) {
                    this.f483d[i].m5203b();
                }
            }
        }
    }

    /* renamed from: f */
    public void m5184f() {
        int i = f480a;
        f480a = C0759a.f500b;
        int i2 = f480a;
        if (i2 > i) {
            m5193a(i, i2);
        }
    }

    /* renamed from: g */
    public C0752X m5183g() {
        synchronized (this) {
            for (int i = 0; i < f480a; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("get handler at index ");
                sb.append(i);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK_CZ, sb.toString());
                if (this.f484e[i] == 0) {
                    this.f484e[i] = 1;
                    return this.f483d[i];
                }
            }
            return null;
        }
    }
}
