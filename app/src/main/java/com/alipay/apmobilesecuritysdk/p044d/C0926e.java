package com.alipay.apmobilesecuritysdk.p044d;

import android.content.Context;
import com.alipay.security.mobile.module.p047a.p048a.C1039b;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/* renamed from: com.alipay.apmobilesecuritysdk.d.e */
/* loaded from: classes2.dex */
public final class C0926e {

    /* renamed from: a */
    private static Map<String, String> f847a;

    /* renamed from: b */
    private static final String[] f848b = {"AD1", "AD2", "AD3", "AD8", "AD9", "AD10", "AD11", "AD12", "AD14", "AD15", "AD16", "AD18", "AD20", "AD21", "AD23", "AD24", "AD26", "AD27", "AD28", "AD29", "AD30", "AD31", "AD34", "AA1", "AA2", "AA3", "AA4", "AC4", "AC10", "AE1", "AE2", "AE3", "AE4", "AE5", "AE6", "AE7", "AE8", "AE9", "AE10", "AE11", "AE12", "AE13", "AE14", "AE15"};

    /* renamed from: a */
    private static String m4769a(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            String str = (String) arrayList.get(i);
            String str2 = map.get(str);
            String str3 = "";
            if (str2 == null) {
                str2 = str3;
            }
            StringBuilder sb = new StringBuilder();
            if (i != 0) {
                str3 = "&";
            }
            sb.append(str3);
            sb.append(str);
            sb.append(SimpleComparison.EQUAL_TO_OPERATION);
            sb.append(str2);
            stringBuffer.append(sb.toString());
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public static synchronized Map<String, String> m4770a(Context context, Map<String, String> map) {
        Map<String, String> map2;
        synchronized (C0926e.class) {
            if (f847a == null) {
                m4767c(context, map);
            }
            f847a.putAll(C0925d.m4773a());
            map2 = f847a;
        }
        return map2;
    }

    /* renamed from: a */
    public static synchronized void m4771a() {
        synchronized (C0926e.class) {
            f847a = null;
        }
    }

    /* renamed from: b */
    public static synchronized String m4768b(Context context, Map<String, String> map) {
        String[] strArr;
        String m4291a;
        synchronized (C0926e.class) {
            m4770a(context, map);
            TreeMap treeMap = new TreeMap();
            for (String str : f848b) {
                if (f847a.containsKey(str)) {
                    treeMap.put(str, f847a.get(str));
                }
            }
            m4291a = C1039b.m4291a(m4769a(treeMap));
        }
        return m4291a;
    }

    /* renamed from: c */
    private static synchronized void m4767c(Context context, Map<String, String> map) {
        synchronized (C0926e.class) {
            TreeMap treeMap = new TreeMap();
            f847a = treeMap;
            treeMap.putAll(C0923b.m4775a(context, map));
            f847a.putAll(C0925d.m4772a(context));
            f847a.putAll(C0924c.m4774a(context));
            f847a.putAll(C0922a.m4776a(context, map));
        }
    }
}
