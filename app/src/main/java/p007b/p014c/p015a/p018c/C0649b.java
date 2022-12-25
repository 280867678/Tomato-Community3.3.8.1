package p007b.p014c.p015a.p018c;

import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.util.TaggedList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* renamed from: b.c.a.c.b */
/* loaded from: classes2.dex */
public class C0649b {

    /* renamed from: a */
    public final Multimap f249a = new Multimap() { // from class: com.koushikdutta.async.http.Headers$1
        @Override // com.koushikdutta.async.http.Multimap
        /* renamed from: a */
        public List<String> mo3873a() {
            return new TaggedList();
        }
    };

    public C0649b() {
    }

    public C0649b(Map<String, List<String>> map) {
        for (String str : map.keySet()) {
            if (str != null) {
                m5438a(str, map.get(str));
            }
        }
    }

    /* renamed from: a */
    public C0649b m5440a(String str) {
        if (str != null) {
            String[] split = str.trim().split(":", 2);
            if (split.length == 2) {
                m5439a(split[0].trim(), split[1].trim());
            } else {
                m5439a(split[0].trim(), "");
            }
        }
        return this;
    }

    /* renamed from: a */
    public C0649b m5439a(String str, String str2) {
        String lowerCase = str.toLowerCase(Locale.US);
        this.f249a.m3871a(lowerCase, str2);
        ((TaggedList) this.f249a.get(lowerCase)).m3848a(str);
        return this;
    }

    /* renamed from: a */
    public C0649b m5438a(String str, List<String> list) {
        for (String str2 : list) {
            m5439a(str, str2);
        }
        return this;
    }

    /* renamed from: a */
    public Multimap m5441a() {
        return this.f249a;
    }

    /* renamed from: b */
    public C0649b m5435b(String str, String str2) {
        if (str2 == null || (!str2.contains("\n") && !str2.contains("\r"))) {
            String lowerCase = str.toLowerCase(Locale.US);
            this.f249a.m3867b(lowerCase, str2);
            ((TaggedList) this.f249a.get(lowerCase)).m3848a(str);
            return this;
        }
        throw new IllegalArgumentException("value must not contain a new line or line feed");
    }

    /* renamed from: b */
    public String m5436b(String str) {
        return this.f249a.m3868b(str.toLowerCase(Locale.US));
    }

    /* renamed from: b */
    public StringBuilder m5437b() {
        StringBuilder sb = new StringBuilder(256);
        for (String str : this.f249a.keySet()) {
            TaggedList taggedList = (TaggedList) this.f249a.get(str);
            Iterator<T> it2 = taggedList.iterator();
            while (it2.hasNext()) {
                sb.append((String) taggedList.m3849a());
                sb.append(": ");
                sb.append((String) it2.next());
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
        return sb;
    }

    /* renamed from: c */
    public String m5434c(String str) {
        List<String> m5433d = m5433d(str.toLowerCase(Locale.US));
        if (m5433d == null || m5433d.size() == 0) {
            return null;
        }
        return m5433d.get(0);
    }

    /* renamed from: d */
    public List<String> m5433d(String str) {
        return this.f249a.remove(str.toLowerCase(Locale.US));
    }

    /* renamed from: e */
    public String m5432e(String str) {
        StringBuilder m5437b = m5437b();
        return m5437b.insert(0, str + "\r\n").toString();
    }

    public String toString() {
        return m5437b().toString();
    }
}
