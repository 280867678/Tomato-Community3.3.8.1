package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.apmobilesecuritysdk.p046f.C0936a;
import com.alipay.security.mobile.module.p047a.C1037a;
import org.json.JSONObject;

/* renamed from: com.alipay.apmobilesecuritysdk.e.d */
/* loaded from: classes2.dex */
public final class C0930d {
    /* renamed from: a */
    private static C0929c m4756a(String str) {
        try {
            if (C1037a.m4303a(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            return new C0929c(jSONObject.optString("apdid"), jSONObject.optString("deviceInfoHash"), jSONObject.optString("timestamp"), jSONObject.optString("tid"), jSONObject.optString("utdid"));
        } catch (Exception e) {
            C0919a.m4780a(e);
            return null;
        }
    }

    /* renamed from: a */
    public static synchronized void m4759a() {
        synchronized (C0930d.class) {
        }
    }

    /* renamed from: a */
    public static synchronized void m4758a(Context context) {
        synchronized (C0930d.class) {
            C0936a.m4701a(context, "vkeyid_profiles_v4", "key_deviceid_v4", "");
            C0936a.m4699a("wxcasxx_v4", "key_wxcasxx_v4", "");
        }
    }

    /* renamed from: a */
    public static synchronized void m4757a(Context context, C0929c c0929c) {
        synchronized (C0930d.class) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("apdid", c0929c.f852a);
                jSONObject.put("deviceInfoHash", c0929c.f853b);
                jSONObject.put("timestamp", c0929c.f854c);
                jSONObject.put("tid", c0929c.f855d);
                jSONObject.put("utdid", c0929c.f856e);
                String jSONObject2 = jSONObject.toString();
                C0936a.m4701a(context, "vkeyid_profiles_v4", "key_deviceid_v4", jSONObject2);
                C0936a.m4699a("wxcasxx_v4", "key_wxcasxx_v4", jSONObject2);
            } catch (Exception e) {
                C0919a.m4780a(e);
            }
        }
    }

    /* renamed from: b */
    public static synchronized C0929c m4755b() {
        synchronized (C0930d.class) {
            String m4700a = C0936a.m4700a("wxcasxx_v4", "key_wxcasxx_v4");
            if (C1037a.m4303a(m4700a)) {
                return null;
            }
            return m4756a(m4700a);
        }
    }

    /* renamed from: b */
    public static synchronized C0929c m4754b(Context context) {
        C0929c m4756a;
        synchronized (C0930d.class) {
            String m4702a = C0936a.m4702a(context, "vkeyid_profiles_v4", "key_deviceid_v4");
            if (C1037a.m4303a(m4702a)) {
                m4702a = C0936a.m4700a("wxcasxx_v4", "key_wxcasxx_v4");
            }
            m4756a = m4756a(m4702a);
        }
        return m4756a;
    }

    /* renamed from: c */
    public static synchronized C0929c m4753c(Context context) {
        synchronized (C0930d.class) {
            String m4702a = C0936a.m4702a(context, "vkeyid_profiles_v4", "key_deviceid_v4");
            if (C1037a.m4303a(m4702a)) {
                return null;
            }
            return m4756a(m4702a);
        }
    }
}
