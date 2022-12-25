package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p047a.p048a.C1039b;
import com.alipay.security.mobile.module.p050c.C1046a;
import com.alipay.security.mobile.module.p050c.C1050e;
import com.tomatolive.library.utils.DateUtils;
import java.util.UUID;

/* renamed from: com.alipay.apmobilesecuritysdk.e.h */
/* loaded from: classes2.dex */
public class C0934h {

    /* renamed from: a */
    private static String f862a = "";

    /* renamed from: a */
    public static long m4737a(Context context) {
        String m4219a = C1046a.m4219a(context, "vkeyid_settings", "update_time_interval");
        if (C1037a.m4299b(m4219a)) {
            try {
                return Long.parseLong(m4219a);
            } catch (Exception unused) {
                return DateUtils.ONE_DAY_MILLIONS;
            }
        }
        return DateUtils.ONE_DAY_MILLIONS;
    }

    /* renamed from: a */
    public static void m4736a(Context context, String str) {
        m4734a(context, "update_time_interval", str);
    }

    /* renamed from: a */
    public static void m4735a(Context context, String str, long j) {
        C1046a.m4218a(context, "vkeyid_settings", "vkey_valid" + str, String.valueOf(j));
    }

    /* renamed from: a */
    private static void m4734a(Context context, String str, String str2) {
        C1046a.m4218a(context, "vkeyid_settings", str, str2);
    }

    /* renamed from: a */
    public static void m4733a(Context context, boolean z) {
        m4734a(context, "log_switch", z ? "1" : "0");
    }

    /* renamed from: b */
    public static String m4732b(Context context) {
        return C1046a.m4219a(context, "vkeyid_settings", "last_apdid_env");
    }

    /* renamed from: b */
    public static void m4731b(Context context, String str) {
        m4734a(context, "last_machine_boot_time", str);
    }

    /* renamed from: c */
    public static void m4729c(Context context, String str) {
        m4734a(context, "last_apdid_env", str);
    }

    /* renamed from: c */
    public static boolean m4730c(Context context) {
        String m4219a = C1046a.m4219a(context, "vkeyid_settings", "log_switch");
        return m4219a != null && "1".equals(m4219a);
    }

    /* renamed from: d */
    public static String m4728d(Context context) {
        return C1046a.m4219a(context, "vkeyid_settings", "dynamic_key");
    }

    /* renamed from: d */
    public static void m4727d(Context context, String str) {
        m4734a(context, "agent_switch", str);
    }

    /* renamed from: e */
    public static String m4726e(Context context) {
        return C1046a.m4219a(context, "vkeyid_settings", "apse_degrade");
    }

    /* renamed from: e */
    public static void m4725e(Context context, String str) {
        m4734a(context, "dynamic_key", str);
    }

    /* renamed from: f */
    public static String m4724f(Context context) {
        String str;
        SharedPreferences.Editor edit;
        synchronized (C0934h.class) {
            if (C1037a.m4303a(f862a)) {
                String m4213a = C1050e.m4213a(context, "alipay_vkey_random", "random", "");
                f862a = m4213a;
                if (C1037a.m4303a(m4213a)) {
                    f862a = C1039b.m4291a(UUID.randomUUID().toString());
                    String str2 = f862a;
                    if (str2 != null && (edit = context.getSharedPreferences("alipay_vkey_random", 0).edit()) != null) {
                        edit.putString("random", str2);
                        edit.commit();
                    }
                }
            }
            str = f862a;
        }
        return str;
    }

    /* renamed from: f */
    public static void m4723f(Context context, String str) {
        m4734a(context, "webrtc_url", str);
    }

    /* renamed from: g */
    public static void m4722g(Context context, String str) {
        m4734a(context, "apse_degrade", str);
    }

    /* renamed from: h */
    public static long m4721h(Context context, String str) {
        try {
            String m4219a = C1046a.m4219a(context, "vkeyid_settings", "vkey_valid" + str);
            if (!C1037a.m4303a(m4219a)) {
                return Long.parseLong(m4219a);
            }
            return 0L;
        } catch (Throwable unused) {
            return 0L;
        }
    }
}
