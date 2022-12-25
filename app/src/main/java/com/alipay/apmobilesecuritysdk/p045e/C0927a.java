package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.apmobilesecuritysdk.p046f.C0936a;
import com.alipay.security.mobile.module.p047a.C1037a;
import org.json.JSONObject;

/* renamed from: com.alipay.apmobilesecuritysdk.e.a */
/* loaded from: classes2.dex */
public final class C0927a {
    /* renamed from: a */
    private static C0928b m4763a(String str) {
        try {
            if (C1037a.m4303a(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            return new C0928b(jSONObject.optString("apdid"), jSONObject.optString("deviceInfoHash"), jSONObject.optString("timestamp"));
        } catch (Exception e) {
            C0919a.m4780a(e);
            return null;
        }
    }

    /* renamed from: a */
    public static synchronized void m4766a() {
        synchronized (C0927a.class) {
        }
    }

    /* renamed from: a */
    public static synchronized void m4765a(Context context) {
        synchronized (C0927a.class) {
            C0936a.m4701a(context, "vkeyid_profiles_v3", "deviceid", "");
            C0936a.m4699a("wxcasxx_v3", "wxcasxx", "");
        }
    }

    /* renamed from: a */
    public static synchronized void m4764a(Context context, C0928b c0928b) {
        synchronized (C0927a.class) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("apdid", c0928b.f849a);
                jSONObject.put("deviceInfoHash", c0928b.f850b);
                jSONObject.put("timestamp", c0928b.f851c);
                String jSONObject2 = jSONObject.toString();
                C0936a.m4701a(context, "vkeyid_profiles_v3", "deviceid", jSONObject2);
                C0936a.m4699a("wxcasxx_v3", "wxcasxx", jSONObject2);
            } catch (Exception e) {
                C0919a.m4780a(e);
            }
        }
    }

    /* renamed from: b */
    public static synchronized C0928b m4762b() {
        synchronized (C0927a.class) {
            String m4700a = C0936a.m4700a("wxcasxx_v3", "wxcasxx");
            if (C1037a.m4303a(m4700a)) {
                return null;
            }
            return m4763a(m4700a);
        }
    }

    /* renamed from: b */
    public static synchronized C0928b m4761b(Context context) {
        C0928b m4763a;
        synchronized (C0927a.class) {
            String m4702a = C0936a.m4702a(context, "vkeyid_profiles_v3", "deviceid");
            if (C1037a.m4303a(m4702a)) {
                m4702a = C0936a.m4700a("wxcasxx_v3", "wxcasxx");
            }
            m4763a = m4763a(m4702a);
        }
        return m4763a;
    }

    /* renamed from: c */
    public static synchronized C0928b m4760c(Context context) {
        synchronized (C0927a.class) {
            String m4702a = C0936a.m4702a(context, "vkeyid_profiles_v3", "deviceid");
            if (C1037a.m4303a(m4702a)) {
                return null;
            }
            return m4763a(m4702a);
        }
    }
}
