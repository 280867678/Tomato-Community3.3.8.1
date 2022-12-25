package com.alipay.sdk.tid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.sdk.encrypt.C0968b;
import com.alipay.sdk.util.C0994a;
import com.alipay.sdk.util.C0996c;
import java.util.Random;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.tid.b */
/* loaded from: classes2.dex */
public class C0992b {

    /* renamed from: i */
    private static Context f1025i;

    /* renamed from: o */
    private static C0992b f1026o;

    /* renamed from: j */
    private String f1027j;

    /* renamed from: k */
    private String f1028k;

    /* renamed from: l */
    private long f1029l;

    /* renamed from: m */
    private String f1030m;

    /* renamed from: n */
    private String f1031n;

    /* renamed from: p */
    private boolean f1032p = false;

    /* renamed from: o */
    private void m4456o() {
    }

    /* renamed from: a */
    public static synchronized C0992b m4467a(Context context) {
        C0992b c0992b;
        synchronized (C0992b.class) {
            if (f1026o == null) {
                f1026o = new C0992b();
            }
            if (f1025i == null) {
                f1026o.m4463b(context);
            }
            c0992b = f1026o;
        }
        return c0992b;
    }

    /* renamed from: b */
    private void m4463b(Context context) {
        if (context != null) {
            f1025i = context.getApplicationContext();
        }
        if (this.f1032p) {
            return;
        }
        this.f1032p = true;
        m4460k();
        m4459l();
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x005b, code lost:
        if (r5 == null) goto L20;
     */
    /* renamed from: k */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void m4460k() {
        C0991a c0991a;
        Throwable th;
        Context context = f1025i;
        if (context == null) {
            return;
        }
        if (C0993a.m4449d("alipay_tid_storage", "upgraded_from_db")) {
            C0996c.m4438a("mspl", "tid_mig: pass");
            return;
        }
        try {
            C0996c.m4438a("mspl", "tid_mig: from db");
            c0991a = new C0991a(context);
            try {
                String m4446b = C0994a.m4447a(context).m4446b();
                String m4448a = C0994a.m4447a(context).m4448a();
                String m4471a = c0991a.m4471a(m4446b, m4448a);
                String m4470b = c0991a.m4470b(m4446b, m4448a);
                if (!TextUtils.isEmpty(m4471a) && !TextUtils.isEmpty(m4470b)) {
                    m4466a(m4471a, m4470b);
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    C0996c.m4436a(th);
                } finally {
                    if (c0991a != null) {
                        c0991a.close();
                    }
                }
            }
        } catch (Throwable th3) {
            c0991a = null;
            th = th3;
        }
        c0991a.close();
        try {
            C0996c.m4438a("mspl", "tid_mig: db purge");
            C0991a c0991a2 = new C0991a(context);
            try {
                c0991a2.m4472a();
                c0991a2.close();
            } catch (Throwable th4) {
                th = th4;
                c0991a = c0991a2;
                try {
                    C0996c.m4436a(th);
                    C0993a.m4453a("alipay_tid_storage", "upgraded_from_db", "updated", false);
                } finally {
                    if (c0991a != null) {
                        c0991a.close();
                    }
                }
            }
        } catch (Throwable th5) {
            th = th5;
        }
        C0993a.m4453a("alipay_tid_storage", "upgraded_from_db", "updated", false);
    }

    /* renamed from: a */
    public String m4468a() {
        return this.f1027j;
    }

    /* renamed from: b */
    public String m4464b() {
        return this.f1028k;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0076  */
    /* renamed from: l */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void m4459l() {
        String str;
        String str2;
        String str3;
        String str4;
        String m4452a;
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        String str5 = null;
        try {
            m4452a = C0993a.m4452a("alipay_tid_storage", "tidinfo", true);
        } catch (Exception e) {
            e = e;
            str = null;
            str2 = null;
        }
        if (!TextUtils.isEmpty(m4452a)) {
            JSONObject jSONObject = new JSONObject(m4452a);
            str = jSONObject.optString("tid", "");
            try {
                str2 = jSONObject.optString("client_key", "");
                try {
                    valueOf = Long.valueOf(jSONObject.optLong("timestamp", System.currentTimeMillis()));
                    str3 = jSONObject.optString("vimei", "");
                } catch (Exception e2) {
                    e = e2;
                    str3 = null;
                }
                try {
                    str4 = jSONObject.optString("vimsi", "");
                } catch (Exception e3) {
                    e = e3;
                    C0996c.m4436a(e);
                    str4 = null;
                    str5 = str;
                    C0996c.m4438a("mspl", "tid_str: load");
                    if (!m4465a(str5, str2, str3, str4)) {
                    }
                }
            } catch (Exception e4) {
                e = e4;
                str2 = null;
                str3 = str2;
                C0996c.m4436a(e);
                str4 = null;
                str5 = str;
                C0996c.m4438a("mspl", "tid_str: load");
                if (!m4465a(str5, str2, str3, str4)) {
                }
            }
            str5 = str;
            C0996c.m4438a("mspl", "tid_str: load");
            if (!m4465a(str5, str2, str3, str4)) {
                m4458m();
                return;
            }
            this.f1027j = str5;
            this.f1028k = str2;
            this.f1029l = valueOf.longValue();
            this.f1030m = str3;
            this.f1031n = str4;
            return;
        }
        str4 = null;
        str2 = null;
        str3 = null;
        C0996c.m4438a("mspl", "tid_str: load");
        if (!m4465a(str5, str2, str3, str4)) {
        }
    }

    /* renamed from: a */
    private boolean m4465a(String str, String str2, String str3, String str4) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4);
    }

    /* renamed from: m */
    private void m4458m() {
        this.f1027j = "";
        this.f1028k = m4462f();
        this.f1029l = System.currentTimeMillis();
        this.f1030m = m4457n();
        this.f1031n = m4457n();
        C0993a.m4450b("alipay_tid_storage", "tidinfo");
    }

    /* renamed from: n */
    private String m4457n() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        Random random = new Random();
        return hexString + (random.nextInt(9000) + 1000);
    }

    /* renamed from: f */
    public String m4462f() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }

    /* renamed from: a */
    public void m4466a(String str, String str2) {
        C0996c.m4438a("mspl", "tid_str: save");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.f1027j = str;
        this.f1028k = str2;
        this.f1029l = System.currentTimeMillis();
        m4455p();
        m4456o();
    }

    /* renamed from: p */
    private void m4455p() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("tid", this.f1027j);
            jSONObject.put("client_key", this.f1028k);
            jSONObject.put("timestamp", this.f1029l);
            jSONObject.put("vimei", this.f1030m);
            jSONObject.put("vimsi", this.f1031n);
            C0993a.m4453a("alipay_tid_storage", "tidinfo", jSONObject.toString(), true);
        } catch (Exception e) {
            C0996c.m4436a(e);
        }
    }

    /* renamed from: com.alipay.sdk.tid.b$a */
    /* loaded from: classes2.dex */
    public static class C0993a {
        /* renamed from: a */
        private static String m4454a() {
            return "!@#23457";
        }

        /* renamed from: b */
        public static void m4450b(String str, String str2) {
            if (C0992b.f1025i == null) {
                return;
            }
            C0992b.f1025i.getSharedPreferences(str, 0).edit().remove(str2).apply();
        }

        /* renamed from: d */
        public static boolean m4449d(String str, String str2) {
            if (C0992b.f1025i == null) {
                return false;
            }
            return C0992b.f1025i.getSharedPreferences(str, 0).contains(str2);
        }

        /* renamed from: a */
        public static String m4452a(String str, String str2, boolean z) {
            if (C0992b.f1025i == null) {
                return null;
            }
            String string = C0992b.f1025i.getSharedPreferences(str, 0).getString(str2, null);
            if (!TextUtils.isEmpty(string) && z) {
                String m4550b = C0968b.m4550b(string, m4451b());
                if (TextUtils.isEmpty(m4550b)) {
                    m4550b = C0968b.m4550b(string, m4454a());
                    if (!TextUtils.isEmpty(m4550b)) {
                        m4453a(str, str2, m4550b, true);
                    }
                }
                string = m4550b;
                if (TextUtils.isEmpty(string)) {
                    C0996c.m4438a("mspl", "tid_str: pref failed");
                }
            }
            C0996c.m4438a("mspl", "tid_str: from local");
            return string;
        }

        /* renamed from: a */
        public static void m4453a(String str, String str2, String str3, boolean z) {
            if (C0992b.f1025i == null) {
                return;
            }
            SharedPreferences sharedPreferences = C0992b.f1025i.getSharedPreferences(str, 0);
            if (z) {
                String m4451b = m4451b();
                String m4551a = C0968b.m4551a(str3, m4451b);
                if (TextUtils.isEmpty(m4551a)) {
                    String.format("LocalPreference::putLocalPreferences failed %s，%s", str3, m4451b);
                }
                str3 = m4551a;
            }
            sharedPreferences.edit().putString(str2, str3).apply();
        }

        /* renamed from: b */
        private static String m4451b() {
            String str;
            try {
                str = C0992b.f1025i.getApplicationContext().getPackageName();
            } catch (Throwable th) {
                C0996c.m4436a(th);
                str = "";
            }
            if (TextUtils.isEmpty(str)) {
                str = "unknow";
            }
            return (str + "00000000").substring(0, 8);
        }
    }
}
