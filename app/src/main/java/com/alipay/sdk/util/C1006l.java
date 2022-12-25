package com.alipay.sdk.util;

import com.alipay.sdk.app.EnumC0953k;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.sdk.util.l */
/* loaded from: classes2.dex */
public class C1006l {
    /* renamed from: a */
    public static Map<String, String> m4402a(C0988a c0988a, String str) {
        Map<String, String> m4403a = m4403a();
        try {
            return m4401a(str);
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "FormatResultEx", th);
            return m4403a;
        }
    }

    /* renamed from: a */
    private static Map<String, String> m4403a() {
        EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.CANCELED.m4640a());
        HashMap hashMap = new HashMap();
        hashMap.put("resultStatus", Integer.toString(m4636b.m4640a()));
        hashMap.put("memo", m4636b.m4637b());
        hashMap.put("result", "");
        return hashMap;
    }

    /* renamed from: a */
    public static Map<String, String> m4401a(String str) {
        String[] split = str.split(";");
        HashMap hashMap = new HashMap();
        for (String str2 : split) {
            String substring = str2.substring(0, str2.indexOf("={"));
            hashMap.put(substring, m4400a(str2, substring));
        }
        return hashMap;
    }

    /* renamed from: a */
    private static String m4400a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf("}"));
    }
}
