package com.alipay.sdk.sys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.app.statistic.C0960c;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1008n;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.sys.a */
/* loaded from: classes2.dex */
public class C0988a {

    /* renamed from: p */
    public final String f1013p;

    /* renamed from: q */
    public final long f1014q = SystemClock.elapsedRealtime();

    /* renamed from: r */
    public final String f1015r;

    /* renamed from: s */
    public final C0960c f1016s;

    /* renamed from: t */
    private String f1017t;

    /* renamed from: u */
    private String f1018u;

    /* renamed from: v */
    private Context f1019v;

    /* renamed from: a */
    public static C0988a m4495a() {
        return null;
    }

    /* renamed from: com.alipay.sdk.sys.a$a */
    /* loaded from: classes2.dex */
    public static final class C0989a {

        /* renamed from: a */
        private static final HashMap<UUID, C0988a> f1020a = new HashMap<>();

        /* renamed from: b */
        private static final HashMap<String, C0988a> f1021b = new HashMap<>();

        /* renamed from: a */
        public static void m4481a(C0988a c0988a, Intent intent) {
            if (c0988a == null || intent == null) {
                return;
            }
            UUID randomUUID = UUID.randomUUID();
            f1020a.put(randomUUID, c0988a);
            intent.putExtra("i_uuid_b_c", randomUUID);
        }

        /* renamed from: a */
        public static C0988a m4482a(Intent intent) {
            if (intent == null) {
                return null;
            }
            Serializable serializableExtra = intent.getSerializableExtra("i_uuid_b_c");
            if (!(serializableExtra instanceof UUID)) {
                return null;
            }
            return f1020a.remove((UUID) serializableExtra);
        }

        /* renamed from: a */
        public static void m4480a(C0988a c0988a, String str) {
            if (c0988a == null || TextUtils.isEmpty(str)) {
                return;
            }
            f1021b.put(str, c0988a);
        }

        /* renamed from: a */
        public static C0988a m4479a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return f1021b.remove(str);
        }
    }

    public C0988a(Context context, String str, String str2) {
        this.f1017t = "";
        this.f1018u = "";
        this.f1019v = null;
        boolean isEmpty = TextUtils.isEmpty(str2);
        this.f1016s = new C0960c(context, isEmpty);
        this.f1013p = m4484c(str, this.f1018u);
        this.f1015r = str2;
        if (!isEmpty) {
            C0954a.m4628b(this, "biz", "eptyp", str2 + "|" + this.f1013p);
        }
        try {
            this.f1019v = context.getApplicationContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.f1017t = packageInfo.versionName;
            this.f1018u = packageInfo.packageName;
        } catch (Exception e) {
            C0996c.m4436a(e);
        }
        if (!isEmpty) {
            C0954a.m4628b(this, "biz", "PgApiInvoke", "" + SystemClock.elapsedRealtime());
            C0954a.m4635a(context, this, str, this.f1013p);
        }
    }

    /* renamed from: b */
    public Context m4489b() {
        return this.f1019v;
    }

    /* renamed from: a */
    public String m4493a(String str) {
        if (!TextUtils.isEmpty(str) && !str.startsWith("new_external_info==")) {
            if (m4488b(str)) {
                return m4485c(str);
            }
            return m4483d(str);
        }
        return str;
    }

    /* renamed from: b */
    private boolean m4488b(String str) {
        return !str.contains("\"&");
    }

    /* renamed from: c */
    private String m4485c(String str) {
        try {
            String m4491a = m4491a(str, "&", "bizcontext=");
            if (TextUtils.isEmpty(m4491a)) {
                str = str + "&" + m4487b("bizcontext=", "");
            } else {
                int indexOf = str.indexOf(m4491a);
                str = str.substring(0, indexOf) + m4490a(m4491a, "bizcontext=", "", true) + str.substring(indexOf + m4491a.length());
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    /* renamed from: d */
    private String m4483d(String str) {
        try {
            String m4491a = m4491a(str, "\"&", "bizcontext=\"");
            if (TextUtils.isEmpty(m4491a)) {
                return str + "&" + m4487b("bizcontext=\"", "\"");
            }
            if (!m4491a.endsWith("\"")) {
                m4491a = m4491a + "\"";
            }
            int indexOf = str.indexOf(m4491a);
            return str.substring(0, indexOf) + m4490a(m4491a, "bizcontext=\"", "\"", false) + str.substring(indexOf + m4491a.length());
        } catch (Throwable unused) {
            return str;
        }
    }

    /* renamed from: a */
    private String m4491a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(str2);
        for (int i = 0; i < split.length; i++) {
            if (!TextUtils.isEmpty(split[i]) && split[i].startsWith(str3)) {
                return split[i];
            }
        }
        return null;
    }

    /* renamed from: b */
    private String m4487b(String str, String str2) throws JSONException, UnsupportedEncodingException {
        String m4492a = m4492a("", "");
        return str + m4492a + str2;
    }

    /* renamed from: a */
    public String m4492a(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", "2014052600006128");
            jSONObject.put("ty", "and_lite");
            jSONObject.put("sv", "h.a.3.7.4");
            if (!this.f1018u.contains("setting") || !C1008n.m4380b(this.f1019v)) {
                jSONObject.put("an", this.f1018u);
            }
            jSONObject.put("av", this.f1017t);
            jSONObject.put("sdk_start_time", System.currentTimeMillis());
            jSONObject.put("extInfo", m4486c());
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put(str, str2);
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            C0996c.m4436a(th);
            return "";
        }
    }

    /* renamed from: a */
    private String m4490a(String str, String str2, String str3, boolean z) throws JSONException, UnsupportedEncodingException {
        JSONObject jSONObject;
        String substring = str.substring(str2.length());
        boolean z2 = false;
        String substring2 = substring.substring(0, substring.length() - str3.length());
        if (substring2.length() >= 2 && substring2.startsWith("\"") && substring2.endsWith("\"")) {
            jSONObject = new JSONObject(substring2.substring(1, substring2.length() - 1));
            z2 = true;
        } else {
            jSONObject = new JSONObject(substring2);
        }
        if (!jSONObject.has("appkey")) {
            jSONObject.put("appkey", "2014052600006128");
        }
        if (!jSONObject.has("ty")) {
            jSONObject.put("ty", "and_lite");
        }
        if (!jSONObject.has("sv")) {
            jSONObject.put("sv", "h.a.3.7.4");
        }
        if (!jSONObject.has("an") && (!this.f1018u.contains("setting") || !C1008n.m4380b(this.f1019v))) {
            jSONObject.put("an", this.f1018u);
        }
        if (!jSONObject.has("av")) {
            jSONObject.put("av", this.f1017t);
        }
        if (!jSONObject.has("sdk_start_time")) {
            jSONObject.put("sdk_start_time", System.currentTimeMillis());
        }
        if (!jSONObject.has("extInfo")) {
            jSONObject.put("extInfo", m4486c());
        }
        String jSONObject2 = jSONObject.toString();
        if (z2) {
            jSONObject2 = "\"" + jSONObject2 + "\"";
        }
        return str2 + jSONObject2 + str3;
    }

    /* renamed from: c */
    private JSONObject m4486c() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ap_link_token", this.f1013p);
        } catch (Throwable unused) {
        }
        return jSONObject;
    }

    /* renamed from: c */
    private static String m4484c(String str, String str2) {
        try {
            Locale locale = Locale.getDefault();
            Object[] objArr = new Object[4];
            if (str == null) {
                str = "";
            }
            objArr[0] = str;
            if (str2 == null) {
                str2 = "";
            }
            objArr[1] = str2;
            objArr[2] = Long.valueOf(System.currentTimeMillis());
            objArr[3] = UUID.randomUUID().toString();
            return String.format("EP%s%s_%s", "1", C1008n.m4366f(String.format(locale, "%s%s%d%s", objArr)), Long.valueOf(System.currentTimeMillis()));
        } catch (Throwable unused) {
            return "-";
        }
    }

    /* renamed from: a */
    public static HashMap<String, String> m4494a(C0988a c0988a) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (c0988a != null) {
            hashMap.put("sdk_ver", "15.7.4");
            hashMap.put("app_name", c0988a.f1018u);
            hashMap.put("token", c0988a.f1013p);
            hashMap.put("call_type", c0988a.f1015r);
            hashMap.put("ts_api_invoke", String.valueOf(c0988a.f1014q));
        }
        return hashMap;
    }
}
