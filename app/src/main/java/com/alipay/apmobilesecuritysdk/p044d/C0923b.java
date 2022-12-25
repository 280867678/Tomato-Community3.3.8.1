package com.alipay.apmobilesecuritysdk.p044d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p045e.C0934h;
import com.alipay.security.mobile.module.p047a.C1037a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.apmobilesecuritysdk.d.b */
/* loaded from: classes2.dex */
public final class C0923b {
    /* renamed from: a */
    public static synchronized Map<String, String> m4775a(Context context, Map<String, String> map) {
        HashMap hashMap;
        synchronized (C0923b.class) {
            hashMap = new HashMap();
            String m4300a = C1037a.m4300a(map, "tid", "");
            String m4300a2 = C1037a.m4300a(map, "utdid", "");
            String m4300a3 = C1037a.m4300a(map, "userId", "");
            String m4300a4 = C1037a.m4300a(map, "appName", "");
            String m4300a5 = C1037a.m4300a(map, "appKeyClient", "");
            String m4300a6 = C1037a.m4300a(map, "tmxSessionId", "");
            String m4724f = C0934h.m4724f(context);
            String m4300a7 = C1037a.m4300a(map, "sessionId", "");
            hashMap.put("AC1", m4300a);
            hashMap.put("AC2", m4300a2);
            hashMap.put("AC3", "");
            hashMap.put("AC4", m4724f);
            hashMap.put("AC5", m4300a3);
            hashMap.put("AC6", m4300a6);
            hashMap.put("AC7", "");
            hashMap.put("AC8", m4300a4);
            hashMap.put("AC9", m4300a5);
            if (C1037a.m4299b(m4300a7)) {
                hashMap.put("AC10", m4300a7);
            }
        }
        return hashMap;
    }
}
