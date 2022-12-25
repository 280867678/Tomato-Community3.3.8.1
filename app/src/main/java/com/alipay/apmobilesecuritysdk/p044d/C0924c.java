package com.alipay.apmobilesecuritysdk.p044d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.apmobilesecuritysdk.p045e.C0931e;
import com.alipay.apmobilesecuritysdk.p045e.C0932f;
import com.alipay.apmobilesecuritysdk.p046f.C0936a;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.alipay.security.mobile.module.p049b.C1043b;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.alipay.apmobilesecuritysdk.d.c */
/* loaded from: classes2.dex */
public final class C0924c {
    /* renamed from: a */
    public static Map<String, String> m4774a(Context context) {
        C1043b m4281a = C1043b.m4281a();
        HashMap hashMap = new HashMap();
        C0932f m4752a = C0931e.m4752a(context);
        String m4280a = C1043b.m4280a(context);
        String m4277b = C1043b.m4277b(context);
        String m4259k = C1043b.m4259k(context);
        String m4255m = C1043b.m4255m(context);
        if (m4752a != null) {
            if (C1037a.m4303a(m4280a)) {
                m4280a = m4752a.m4751a();
            }
            if (C1037a.m4303a(m4277b)) {
                m4277b = m4752a.m4749b();
            }
            if (C1037a.m4303a(m4259k)) {
                m4259k = m4752a.m4747c();
            }
            if (C1037a.m4303a(m4255m)) {
                m4255m = m4752a.m4743e();
            }
        }
        C0932f c0932f = new C0932f(m4280a, m4277b, m4259k, "", m4255m);
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("imei", c0932f.m4751a());
                jSONObject.put("imsi", c0932f.m4749b());
                jSONObject.put("mac", c0932f.m4747c());
                jSONObject.put("bluetoothmac", c0932f.m4745d());
                jSONObject.put("gsi", c0932f.m4743e());
                String jSONObject2 = jSONObject.toString();
                C0936a.m4699a("device_feature_file_name", "device_feature_file_key", jSONObject2);
                C0936a.m4701a(context, "device_feature_prefs_name", "device_feature_prefs_key", jSONObject2);
            } catch (Exception e) {
                C0919a.m4780a(e);
            }
        }
        hashMap.put("AD1", m4280a);
        hashMap.put("AD2", m4277b);
        hashMap.put("AD3", C1043b.m4269f(context));
        hashMap.put("AD5", C1043b.m4265h(context));
        hashMap.put("AD6", C1043b.m4263i(context));
        hashMap.put("AD7", C1043b.m4261j(context));
        hashMap.put("AD8", m4259k);
        hashMap.put("AD9", C1043b.m4257l(context));
        hashMap.put("AD10", m4255m);
        hashMap.put("AD11", C1043b.m4274d());
        hashMap.put("AD12", m4281a.m4272e());
        hashMap.put("AD13", C1043b.m4270f());
        hashMap.put("AD14", C1043b.m4266h());
        hashMap.put("AD15", C1043b.m4264i());
        hashMap.put("AD16", C1043b.m4262j());
        hashMap.put("AD17", "");
        hashMap.put("AD19", C1043b.m4253n(context));
        hashMap.put("AD20", C1043b.m4260k());
        hashMap.put("AD22", "");
        hashMap.put("AD23", C1043b.m4249p(context));
        hashMap.put("AD24", C1037a.m4293g(C1043b.m4267g(context)));
        hashMap.put("AD26", C1043b.m4271e(context));
        hashMap.put("AD27", C1043b.m4250p());
        hashMap.put("AD28", C1043b.m4246r());
        hashMap.put("AD29", C1043b.m4242t());
        hashMap.put("AD30", C1043b.m4248q());
        hashMap.put("AD31", C1043b.m4244s());
        hashMap.put("AD32", C1043b.m4254n());
        hashMap.put("AD33", C1043b.m4252o());
        hashMap.put("AD34", C1043b.m4245r(context));
        hashMap.put("AD35", C1043b.m4243s(context));
        hashMap.put("AD36", C1043b.m4247q(context));
        hashMap.put("AD37", C1043b.m4256m());
        hashMap.put("AD38", C1043b.m4258l());
        hashMap.put("AD39", C1043b.m4275c(context));
        hashMap.put("AD40", C1043b.m4273d(context));
        hashMap.put("AD41", C1043b.m4278b());
        hashMap.put("AD42", C1043b.m4276c());
        hashMap.put("AL3", C1043b.m4251o(context));
        return hashMap;
    }
}
