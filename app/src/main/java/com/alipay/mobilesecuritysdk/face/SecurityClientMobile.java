package com.alipay.mobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.p041a.C0917a;
import com.alipay.security.mobile.module.p047a.C1037a;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class SecurityClientMobile {
    public static synchronized String GetApdid(Context context, Map<String, String> map) {
        String m4792a;
        synchronized (SecurityClientMobile.class) {
            HashMap hashMap = new HashMap();
            hashMap.put("utdid", C1037a.m4300a(map, "utdid", ""));
            hashMap.put("tid", C1037a.m4300a(map, "tid", ""));
            hashMap.put("userId", C1037a.m4300a(map, "userId", ""));
            APSecuritySdk.getInstance(context).initToken(0, hashMap, null);
            m4792a = C0917a.m4792a(context);
        }
        return m4792a;
    }
}
