package com.alipay.apmobilesecuritysdk.p044d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0920b;
import com.alipay.security.mobile.module.p049b.C1043b;
import com.alipay.security.mobile.module.p049b.C1045d;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.apmobilesecuritysdk.d.d */
/* loaded from: classes2.dex */
public final class C0925d {
    /* renamed from: a */
    public static synchronized Map<String, String> m4773a() {
        HashMap hashMap;
        synchronized (C0925d.class) {
            hashMap = new HashMap();
            try {
                new C0920b();
                hashMap.put("AE16", "");
            } catch (Throwable unused) {
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    public static synchronized Map<String, String> m4772a(Context context) {
        HashMap hashMap;
        synchronized (C0925d.class) {
            C1045d.m4236a();
            C1043b.m4281a();
            hashMap = new HashMap();
            hashMap.put("AE1", C1045d.m4233b());
            StringBuilder sb = new StringBuilder();
            sb.append(C1045d.m4232c() ? "1" : "0");
            hashMap.put("AE2", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(C1045d.m4235a(context) ? "1" : "0");
            hashMap.put("AE3", sb2.toString());
            hashMap.put("AE4", C1045d.m4231d());
            hashMap.put("AE5", C1045d.m4230e());
            hashMap.put("AE6", C1045d.m4229f());
            hashMap.put("AE7", C1045d.m4228g());
            hashMap.put("AE8", C1045d.m4227h());
            hashMap.put("AE9", C1045d.m4226i());
            hashMap.put("AE10", C1045d.m4225j());
            hashMap.put("AE11", C1045d.m4224k());
            hashMap.put("AE12", C1045d.m4223l());
            hashMap.put("AE13", C1045d.m4222m());
            hashMap.put("AE14", C1045d.m4221n());
            hashMap.put("AE15", C1045d.m4220o());
            hashMap.put("AE21", C1043b.m4268g());
        }
        return hashMap;
    }
}
