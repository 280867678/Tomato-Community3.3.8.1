package com.alipay.apmobilesecuritysdk.p046f;

import android.content.Context;
import android.os.Environment;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p047a.p048a.C1040c;
import com.alipay.security.mobile.module.p050c.C1047b;
import com.alipay.security.mobile.module.p050c.C1048c;
import com.alipay.security.mobile.module.p050c.C1050e;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

/* renamed from: com.alipay.apmobilesecuritysdk.f.a */
/* loaded from: classes2.dex */
public class C0936a {
    /* renamed from: a */
    public static String m4702a(Context context, String str, String str2) {
        String m4213a;
        if (context == null || C1037a.m4303a(str)) {
            return null;
        }
        if (!C1037a.m4303a(str2)) {
            try {
                m4213a = C1050e.m4213a(context, str, str2, "");
                if (C1037a.m4303a(m4213a)) {
                    return null;
                }
            } catch (Throwable unused) {
                return null;
            }
        }
        return C1040c.m4285b(C1040c.m4290a(), m4213a);
    }

    /* renamed from: a */
    public static String m4700a(String str, String str2) {
        synchronized (C0936a.class) {
            if (C1037a.m4303a(str) || C1037a.m4303a(str2)) {
                return null;
            }
            String m4217a = C1047b.m4217a(str);
            if (C1037a.m4303a(m4217a)) {
                return null;
            }
            String string = new JSONObject(m4217a).getString(str2);
            if (C1037a.m4303a(string)) {
                return null;
            }
            return C1040c.m4285b(C1040c.m4290a(), string);
        }
    }

    /* renamed from: a */
    public static void m4701a(Context context, String str, String str2, String str3) {
        if (!C1037a.m4303a(str) && !C1037a.m4303a(str2) && context != null) {
            try {
                String m4288a = C1040c.m4288a(C1040c.m4290a(), str3);
                HashMap hashMap = new HashMap();
                hashMap.put(str2, m4288a);
                C1050e.m4212a(context, str, hashMap);
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: a */
    public static void m4699a(String str, String str2, String str3) {
        synchronized (C0936a.class) {
            if (C1037a.m4303a(str) || C1037a.m4303a(str2)) {
                return;
            }
            try {
                String m4217a = C1047b.m4217a(str);
                JSONObject jSONObject = new JSONObject();
                if (C1037a.m4299b(m4217a)) {
                    try {
                        jSONObject = new JSONObject(m4217a);
                    } catch (Exception unused) {
                        jSONObject = new JSONObject();
                    }
                }
                jSONObject.put(str2, C1040c.m4288a(C1040c.m4290a(), str3));
                jSONObject.toString();
                try {
                    System.clearProperty(str);
                } catch (Throwable unused2) {
                }
                if (C1048c.m4216a()) {
                    String str4 = ".SystemConfig" + File.separator + str;
                    if (C1048c.m4216a()) {
                        File file = new File(Environment.getExternalStorageDirectory(), str4);
                        if (file.exists() && file.isFile()) {
                            file.delete();
                        }
                    }
                }
            } catch (Throwable unused3) {
            }
        }
    }
}
