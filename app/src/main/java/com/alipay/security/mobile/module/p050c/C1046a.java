package com.alipay.security.mobile.module.p050c;

import android.content.Context;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p047a.p048a.C1040c;
import java.util.HashMap;

/* renamed from: com.alipay.security.mobile.module.c.a */
/* loaded from: classes2.dex */
public class C1046a {
    /* renamed from: a */
    public static String m4219a(Context context, String str, String str2) {
        String m4213a;
        synchronized (C1046a.class) {
            String str3 = null;
            if (context != null) {
                if (!C1037a.m4303a(str) && !C1037a.m4303a(str2)) {
                    try {
                        m4213a = C1050e.m4213a(context, str, str2, "");
                    } catch (Throwable unused) {
                    }
                    if (C1037a.m4303a(m4213a)) {
                        return null;
                    }
                    str3 = C1040c.m4285b(C1040c.m4290a(), m4213a);
                    return str3;
                }
            }
            return null;
        }
    }

    /* renamed from: a */
    public static void m4218a(Context context, String str, String str2, String str3) {
        synchronized (C1046a.class) {
            if (C1037a.m4303a(str) || C1037a.m4303a(str2) || context == null) {
                return;
            }
            try {
                String m4288a = C1040c.m4288a(C1040c.m4290a(), str3);
                HashMap hashMap = new HashMap();
                hashMap.put(str2, m4288a);
                C1050e.m4212a(context, str, hashMap);
            } catch (Throwable unused) {
            }
        }
    }
}
