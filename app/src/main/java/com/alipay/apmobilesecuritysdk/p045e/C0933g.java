package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p047a.p048a.C1040c;
import com.alipay.security.mobile.module.p050c.C1050e;

/* renamed from: com.alipay.apmobilesecuritysdk.e.g */
/* loaded from: classes2.dex */
public final class C0933g {
    /* renamed from: a */
    public static synchronized String m4739a(Context context, String str) {
        synchronized (C0933g.class) {
            String m4213a = C1050e.m4213a(context, "openapi_file_pri", "openApi" + str, "");
            if (C1037a.m4303a(m4213a)) {
                return "";
            }
            String m4285b = C1040c.m4285b(C1040c.m4290a(), m4213a);
            return C1037a.m4303a(m4285b) ? "" : m4285b;
        }
    }

    /* renamed from: a */
    public static synchronized void m4741a() {
        synchronized (C0933g.class) {
        }
    }

    /* renamed from: a */
    public static synchronized void m4740a(Context context) {
        synchronized (C0933g.class) {
            SharedPreferences.Editor edit = context.getSharedPreferences("openapi_file_pri", 0).edit();
            if (edit != null) {
                edit.clear();
                edit.commit();
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m4738a(Context context, String str, String str2) {
        synchronized (C0933g.class) {
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences("openapi_file_pri", 0).edit();
                if (edit != null) {
                    edit.putString("openApi" + str, C1040c.m4288a(C1040c.m4290a(), str2));
                    edit.commit();
                }
            } catch (Throwable unused) {
            }
        }
    }
}
