package p007b.p025d.p026a;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import p007b.p014c.p015a.p018c.C0649b;

/* renamed from: b.d.a.L */
/* loaded from: classes2.dex */
public class C0741L {

    /* renamed from: a */
    public String f419a;

    /* renamed from: b */
    public String f420b;

    /* renamed from: c */
    public Map<String, C0795ha> f421c = new HashMap();

    public C0741L(String str, String str2, String str3) {
        this.f419a = str;
        this.f420b = str3;
    }

    /* renamed from: a */
    public C0649b m5248a(String str, int i, int i2) {
        Date date;
        int i3;
        C0649b c0649b = new C0649b();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
        if (str.toLowerCase().endsWith(".m3u8")) {
            Iterator<Map.Entry<String, C0795ha>> it2 = this.f421c.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    i3 = 0;
                    break;
                }
                Map.Entry<String, C0795ha> next = it2.next();
                C0795ha value = next.getValue();
                if (next.getKey().compareTo(str) == 0) {
                    i3 = value.m4951c().length();
                    break;
                }
            }
            if (i3 == 0) {
                i3 = this.f420b.length();
            }
            String format = String.format("bytes %d-%d/%d", 0, Integer.valueOf(i3 - 1), Integer.valueOf(i3));
            c0649b.m5439a("Content-Length", String.valueOf(i3));
            c0649b.m5439a("Content-Range", format);
            c0649b.m5439a("Accept-Ranges", "bytes");
            date = new Date();
        } else {
            C0797ia m5244c = m5244c(str);
            if (m5244c == null) {
                return null;
            }
            int i4 = i2;
            if (i4 == -1) {
                i4 = m5244c.m4947c() - 1;
            }
            int i5 = (i4 - i) + 1;
            if (i5 < 0) {
                i5 = 0;
            }
            String format2 = String.format("bytes %d-%d/%d", Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(m5244c.m4947c()));
            c0649b.m5439a("Content-Length", String.valueOf(i5));
            c0649b.m5439a("Content-Range", format2);
            c0649b.m5439a("Accept-Ranges", "bytes");
            date = new Date();
        }
        c0649b.m5439a("Date", simpleDateFormat.format(date));
        return c0649b;
    }

    /* renamed from: a */
    public C0797ia m5249a(String str, int i) {
        return this.f421c.get(str).m4957a(i);
    }

    /* renamed from: a */
    public String m5250a(String str) {
        for (Map.Entry<String, C0795ha> entry : this.f421c.entrySet()) {
            C0795ha value = entry.getValue();
            if (entry.getKey().compareTo(str) == 0) {
                return value.m4951c();
            }
        }
        return this.f421c.get(this.f419a).m4951c();
    }

    /* renamed from: a */
    public void m5247a(String str, C0795ha c0795ha) {
        this.f421c.put(c0795ha.m4950d(), c0795ha);
    }

    /* renamed from: b */
    public C0795ha m5246b(String str) {
        return this.f421c.get(str);
    }

    /* renamed from: b */
    public C0797ia m5245b(String str, int i) {
        return this.f421c.get(str).m4956a(i);
    }

    /* renamed from: c */
    public C0797ia m5244c(String str) {
        for (Map.Entry<String, C0795ha> entry : this.f421c.entrySet()) {
            C0797ia m4954a = entry.getValue().m4954a(str);
            if (m4954a != null) {
                return m4954a;
            }
        }
        return null;
    }
}
