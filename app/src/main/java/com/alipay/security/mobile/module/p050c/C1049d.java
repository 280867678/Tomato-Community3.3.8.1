package com.alipay.security.mobile.module.p050c;

import android.content.Context;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p047a.p048a.C1040c;
import java.util.HashMap;

/* renamed from: com.alipay.security.mobile.module.c.d */
/* loaded from: classes2.dex */
public final class C1049d {
    /* renamed from: a */
    public static synchronized void m4214a(Context context, String str, String str2, String str3) {
        synchronized (C1049d.class) {
            if (!C1037a.m4303a(str)) {
                if (!C1037a.m4303a(str2) && context != null) {
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
    }
}
