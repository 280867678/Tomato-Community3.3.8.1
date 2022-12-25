package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.apmobilesecuritysdk.p046f.C0936a;
import com.alipay.security.mobile.module.p047a.C1037a;
import org.json.JSONObject;

/* renamed from: com.alipay.apmobilesecuritysdk.e.e */
/* loaded from: classes2.dex */
public final class C0931e {
    /* renamed from: a */
    public static C0932f m4752a(Context context) {
        if (context == null) {
            return null;
        }
        String m4702a = C0936a.m4702a(context, "device_feature_prefs_name", "device_feature_prefs_key");
        if (C1037a.m4303a(m4702a)) {
            m4702a = C0936a.m4700a("device_feature_file_name", "device_feature_file_key");
        }
        if (C1037a.m4303a(m4702a)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(m4702a);
            C0932f c0932f = new C0932f();
            c0932f.m4750a(jSONObject.getString("imei"));
            c0932f.m4748b(jSONObject.getString("imsi"));
            c0932f.m4746c(jSONObject.getString("mac"));
            c0932f.m4744d(jSONObject.getString("bluetoothmac"));
            c0932f.m4742e(jSONObject.getString("gsi"));
            return c0932f;
        } catch (Exception e) {
            C0919a.m4780a(e);
            return null;
        }
    }
}
