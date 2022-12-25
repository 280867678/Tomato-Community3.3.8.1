package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import p007b.p014c.p015a.p018c.AbstractC0690g;
import p007b.p014c.p015a.p018c.C0633a;
import p007b.p014c.p015a.p018c.C0688e;
import p007b.p014c.p015a.p018c.C0689f;

/* loaded from: classes3.dex */
public class Multimap extends LinkedHashMap<String, List<String>> implements Iterable<AbstractC0690g> {

    /* renamed from: a */
    public static final AbstractC2206a f1523a = new C0688e();

    /* renamed from: b */
    public static final AbstractC2206a f1524b = new C0689f();

    /* renamed from: com.koushikdutta.async.http.Multimap$a */
    /* loaded from: classes3.dex */
    public interface AbstractC2206a {
        String decode(String str);
    }

    /* renamed from: a */
    public static Multimap m3870a(String str, String str2, String str3, boolean z, AbstractC2206a abstractC2206a) {
        Multimap multimap = new Multimap();
        if (str == null) {
            return multimap;
        }
        for (String str4 : str.split(str2)) {
            String[] split = str4.split(str3, 2);
            String trim = split[0].trim();
            if (!TextUtils.isEmpty(trim)) {
                String str5 = null;
                if (split.length > 1) {
                    str5 = split[1];
                }
                if (str5 != null && z && str5.endsWith("\"") && str5.startsWith("\"")) {
                    str5 = str5.substring(1, str5.length() - 1);
                }
                if (str5 != null && abstractC2206a != null) {
                    trim = abstractC2206a.decode(trim);
                    str5 = abstractC2206a.decode(str5);
                }
                multimap.m3871a(trim, str5);
            }
        }
        return multimap;
    }

    /* renamed from: a */
    public static Multimap m3869a(String str, String str2, boolean z, AbstractC2206a abstractC2206a) {
        return m3870a(str, str2, SimpleComparison.EQUAL_TO_OPERATION, z, abstractC2206a);
    }

    /* renamed from: c */
    public static Multimap m3866c(String str) {
        return m3869a(str, ";", true, null);
    }

    /* renamed from: d */
    public static Multimap m3865d(String str) {
        return m3869a(str, "&", false, f1524b);
    }

    /* renamed from: a */
    public List<String> mo3873a() {
        return new ArrayList();
    }

    /* renamed from: a */
    public List<String> m3872a(String str) {
        List<String> list = get(str);
        if (list == null) {
            List<String> mo3873a = mo3873a();
            put(str, mo3873a);
            return mo3873a;
        }
        return list;
    }

    /* renamed from: a */
    public void m3871a(String str, String str2) {
        m3872a(str).add(str2);
    }

    /* renamed from: b */
    public String m3868b(String str) {
        List<String> list = get(str);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /* renamed from: b */
    public void m3867b(String str, String str2) {
        List<String> mo3873a = mo3873a();
        mo3873a.add(str2);
        put(str, mo3873a);
    }

    @Override // java.lang.Iterable
    public Iterator<AbstractC0690g> iterator() {
        ArrayList arrayList = new ArrayList();
        for (String str : keySet()) {
            for (String str2 : (List) get(str)) {
                arrayList.add(new C0633a(str, str2));
            }
        }
        return arrayList.iterator();
    }
}
