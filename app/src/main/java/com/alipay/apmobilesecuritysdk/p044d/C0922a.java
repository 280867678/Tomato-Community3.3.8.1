package com.alipay.apmobilesecuritysdk.p044d;

import android.content.Context;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p049b.C1042a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.apmobilesecuritysdk.d.a */
/* loaded from: classes2.dex */
public final class C0922a {
    /* renamed from: a */
    public static synchronized Map<String, String> m4776a(Context context, Map<String, String> map) {
        HashMap hashMap;
        synchronized (C0922a.class) {
            String m4300a = C1037a.m4300a(map, "appchannel", "");
            hashMap = new HashMap();
            hashMap.put("AA1", context.getPackageName());
            C1042a.m4283a();
            hashMap.put("AA2", C1042a.m4282a(context));
            hashMap.put("AA3", "APPSecuritySDK-ALIPAYSDK");
            hashMap.put("AA4", "3.4.0.201910161639");
            hashMap.put("AA6", m4300a);
        }
        return hashMap;
    }
}
