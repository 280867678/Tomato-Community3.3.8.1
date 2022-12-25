package com.alipay.security.mobile.module.p050c;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* renamed from: com.alipay.security.mobile.module.c.e */
/* loaded from: classes2.dex */
public final class C1050e {
    /* renamed from: a */
    public static String m4213a(Context context, String str, String str2, String str3) {
        return context.getSharedPreferences(str, 0).getString(str2, str3);
    }

    /* renamed from: a */
    public static void m4212a(Context context, String str, Map<String, String> map) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        if (edit != null) {
            for (String str2 : map.keySet()) {
                edit.putString(str2, map.get(str2));
            }
            edit.commit();
        }
    }
}
