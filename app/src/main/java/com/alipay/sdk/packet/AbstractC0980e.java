package com.alipay.sdk.packet;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.sdk.app.C0951i;
import com.alipay.sdk.data.C0965c;
import com.alipay.sdk.net.C0973a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.tid.C0992b;
import com.alipay.sdk.util.C0995b;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1007m;
import com.alipay.sdk.util.C1008n;
import com.bumptech.glide.BuildConfig;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.packet.e */
/* loaded from: classes2.dex */
public abstract class AbstractC0980e {

    /* renamed from: r */
    protected boolean f1001r = true;

    /* renamed from: s */
    protected boolean f1002s = true;

    /* renamed from: a */
    protected abstract JSONObject mo4503a() throws JSONException;

    /* renamed from: b */
    protected String mo4508b() {
        return BuildConfig.VERSION_NAME;
    }

    /* renamed from: a */
    protected Map<String, String> mo4504a(boolean z, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("msp-gzip", String.valueOf(z));
        hashMap.put("Operation-Type", "alipay.msp.cashier.dispatch.bytes");
        hashMap.put("content-type", "application/octet-stream");
        hashMap.put("Version", "2.0");
        hashMap.put("AppId", "TAOBAO");
        hashMap.put("Msp-Param", C0976a.m4535a(str));
        hashMap.put("des-mode", "CBC");
        return hashMap;
    }

    /* renamed from: c */
    protected String mo4507c() throws JSONException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AopConstants.DEVICE_KEY, Build.MODEL);
        hashMap.put("namespace", "com.alipay.mobilecashier");
        hashMap.put("api_name", "com.alipay.mcpay");
        hashMap.put("api_version", mo4508b());
        return m4509a(hashMap, new HashMap<>());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public static JSONObject m4510a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put("method", str2);
        jSONObject.put(LogConstants.FOLLOW_OPERATION_TYPE, jSONObject2);
        return jSONObject;
    }

    /* renamed from: a */
    protected String mo4505a(C0988a c0988a, String str, JSONObject jSONObject) {
        C0990b m4478a = C0990b.m4478a();
        C0992b m4467a = C0992b.m4467a(m4478a.m4476b());
        JSONObject m4440a = C0995b.m4440a(new JSONObject(), jSONObject);
        try {
            m4440a.put("tid", m4467a.m4468a());
            m4440a.put("user_agent", m4478a.m4475c().m4572a(c0988a, m4467a));
            m4440a.put("has_alipay", C1008n.m4378b(c0988a, m4478a.m4476b(), C0951i.f930a));
            m4440a.put("has_msp_app", C1008n.m4396a(m4478a.m4476b()));
            m4440a.put("external_info", str);
            m4440a.put("app_key", "2014052600006128");
            m4440a.put("utdid", m4478a.m4473e());
            m4440a.put("new_client_key", m4467a.m4464b());
            m4440a.put("pa", C0965c.m4574a(m4478a.m4476b()));
        } catch (Throwable th) {
            C0996c.m4436a(th);
        }
        return m4440a.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public static boolean m4516a(C0973a.C0975b c0975b) {
        return Boolean.valueOf(m4515a(c0975b, "msp-gzip")).booleanValue();
    }

    /* renamed from: a */
    protected static String m4515a(C0973a.C0975b c0975b, String str) {
        Map<String, List<String>> map;
        List<String> list;
        if (c0975b == null || str == null || (map = c0975b.f993a) == null || (list = map.get(str)) == null) {
            return null;
        }
        return TextUtils.join(",", list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public String m4509a(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (hashMap != null) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                jSONObject2.put(entry.getKey(), entry.getValue());
            }
        }
        if (hashMap2 != null) {
            JSONObject jSONObject3 = new JSONObject();
            for (Map.Entry<String, String> entry2 : hashMap2.entrySet()) {
                jSONObject3.put(entry2.getKey(), entry2.getValue());
            }
            jSONObject2.put("params", jSONObject3);
        }
        jSONObject.put(AopConstants.APP_PROPERTIES_KEY, jSONObject2);
        return jSONObject.toString();
    }

    /* renamed from: a */
    private static boolean m4511a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(AopConstants.APP_PROPERTIES_KEY);
            if (!jSONObject.has("params")) {
                return false;
            }
            String optString = jSONObject.getJSONObject("params").optString("public_key", null);
            if (TextUtils.isEmpty(optString)) {
                return false;
            }
            C0965c.m4571a(optString);
            return true;
        } catch (JSONException e) {
            C0996c.m4436a(e);
            return false;
        }
    }

    /* renamed from: a */
    public C0977b m4514a(C0988a c0988a, Context context) throws Throwable {
        return mo4506a(c0988a, context, "");
    }

    /* renamed from: a */
    public C0977b mo4506a(C0988a c0988a, Context context, String str) throws Throwable {
        return m4513a(c0988a, context, str, C1007m.m4399a(context));
    }

    /* renamed from: a */
    public C0977b m4513a(C0988a c0988a, Context context, String str, String str2) throws Throwable {
        return m4512a(c0988a, context, str, str2, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public C0977b m4512a(C0988a c0988a, Context context, String str, String str2, boolean z) throws Throwable {
        C0996c.m4438a("mspl", "Packet: " + str2);
        C0978c c0978c = new C0978c(this.f1002s);
        C0977b c0977b = new C0977b(mo4507c(), mo4505a(c0988a, str, mo4503a()));
        Map<String, String> mo4504a = mo4504a(false, str);
        C0979d m4525a = c0978c.m4525a(c0977b, this.f1001r, mo4504a.get("iSr"));
        C0973a.C0975b m4539a = C0973a.m4539a(context, new C0973a.C0974a(str2, mo4504a(m4525a.m4518a(), str), m4525a.m4517b()));
        if (m4539a == null) {
            throw new RuntimeException("Response is null.");
        }
        C0977b m4524a = c0978c.m4524a(new C0979d(m4516a(m4539a), m4539a.f994c), mo4504a.get("iSr"));
        return (m4524a == null || !m4511a(m4524a.m4529a()) || !z) ? m4524a : m4512a(c0988a, context, str, str2, false);
    }
}
