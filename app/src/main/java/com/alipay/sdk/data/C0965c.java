package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobilesecuritysdk.face.SecurityClientMobile;
import com.alipay.sdk.app.C0951i;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.cons.C0961a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.tid.C0992b;
import com.alipay.sdk.util.C0994a;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1008n;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* renamed from: com.alipay.sdk.data.c */
/* loaded from: classes2.dex */
public class C0965c {

    /* renamed from: d */
    private static volatile C0965c f979d;

    /* renamed from: e */
    private String f980e;

    /* renamed from: f */
    private String f981f = "sdk-and-lite";

    /* renamed from: g */
    private String f982g;

    /* renamed from: e */
    private static String m4562e() {
        return "1";
    }

    /* renamed from: f */
    private static String m4561f() {
        return "-1;-1";
    }

    private C0965c() {
        String m4651a = C0951i.m4651a();
        if (!C0951i.m4649b()) {
            this.f981f += '_' + m4651a;
        }
    }

    /* renamed from: b */
    public static synchronized C0965c m4570b() {
        C0965c c0965c;
        synchronized (C0965c.class) {
            if (f979d == null) {
                f979d = new C0965c();
            }
            c0965c = f979d;
        }
        return c0965c;
    }

    /* renamed from: a */
    public static synchronized void m4571a(String str) {
        synchronized (C0965c.class) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            PreferenceManager.getDefaultSharedPreferences(C0990b.m4478a().m4476b()).edit().putString("trideskey", str).apply();
            C0961a.f960e = str;
        }
    }

    /* renamed from: b */
    private static String m4569b(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    /* renamed from: a */
    public String m4572a(C0988a c0988a, C0992b c0992b) {
        Context m4476b = C0990b.m4478a().m4476b();
        C0994a m4447a = C0994a.m4447a(m4476b);
        if (TextUtils.isEmpty(this.f980e)) {
            String m4381b = C1008n.m4381b();
            String m4375c = C1008n.m4375c();
            String m4371d = C1008n.m4371d(m4476b);
            String m4367f = C1008n.m4367f(m4476b);
            String m4369e = C1008n.m4369e(m4476b);
            String m4569b = m4569b(m4476b);
            this.f980e = "Msp/15.7.4 (" + m4381b + ";" + m4375c + ";" + m4371d + ";" + m4367f + ";" + m4369e + ";" + m4569b;
        }
        String m4428b = C0994a.m4445b(m4476b).m4428b();
        String m4365g = C1008n.m4365g(m4476b);
        String m4562e = m4562e();
        String m4448a = m4447a.m4448a();
        String m4446b = m4447a.m4446b();
        String m4564d = m4564d();
        String m4567c = m4567c();
        if (c0992b != null) {
            this.f982g = c0992b.m4464b();
        }
        String replace = Build.MANUFACTURER.replace(";", ConstantUtils.PLACEHOLDER_STR_ONE);
        String replace2 = Build.MODEL.replace(";", ConstantUtils.PLACEHOLDER_STR_ONE);
        boolean m4474d = C0990b.m4474d();
        String m4442d = m4447a.m4442d();
        String m4566c = m4566c(m4476b);
        String m4563d = m4563d(m4476b);
        StringBuilder sb = new StringBuilder();
        sb.append(this.f980e);
        sb.append(";");
        sb.append(m4428b);
        sb.append(";");
        sb.append(m4365g);
        sb.append(";");
        sb.append(m4562e);
        sb.append(";");
        sb.append(m4448a);
        sb.append(";");
        sb.append(m4446b);
        sb.append(";");
        sb.append(this.f982g);
        sb.append(";");
        sb.append(replace);
        sb.append(";");
        sb.append(replace2);
        sb.append(";");
        sb.append(m4474d);
        sb.append(";");
        sb.append(m4442d);
        sb.append(";");
        sb.append(m4561f());
        sb.append(";");
        sb.append(this.f981f);
        sb.append(";");
        sb.append(m4564d);
        sb.append(";");
        sb.append(m4567c);
        sb.append(";");
        sb.append(m4566c);
        sb.append(";");
        sb.append(m4563d);
        if (c0992b != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("tid", C0992b.m4467a(m4476b).m4468a());
            hashMap.put("utdid", C0990b.m4478a().m4473e());
            String m4565c = m4565c(c0988a, m4476b, hashMap);
            if (!TextUtils.isEmpty(m4565c)) {
                sb.append(";");
                sb.append(m4565c);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /* renamed from: c */
    public static String m4567c() {
        String m4446b;
        Context m4476b = C0990b.m4478a().m4476b();
        SharedPreferences sharedPreferences = m4476b.getSharedPreferences("virtualImeiAndImsi", 0);
        String string = sharedPreferences.getString("virtual_imei", null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(C0992b.m4467a(m4476b).m4468a())) {
                m4446b = m4560g();
            } else {
                m4446b = C0994a.m4447a(m4476b).m4446b();
            }
            String str = m4446b;
            sharedPreferences.edit().putString("virtual_imei", str).apply();
            return str;
        }
        return string;
    }

    /* renamed from: d */
    public static String m4564d() {
        String m4448a;
        Context m4476b = C0990b.m4478a().m4476b();
        SharedPreferences sharedPreferences = m4476b.getSharedPreferences("virtualImeiAndImsi", 0);
        String string = sharedPreferences.getString("virtual_imsi", null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(C0992b.m4467a(m4476b).m4468a())) {
                String m4473e = C0990b.m4478a().m4473e();
                if (TextUtils.isEmpty(m4473e)) {
                    m4448a = m4560g();
                } else {
                    m4448a = m4473e.substring(3, 18);
                }
            } else {
                m4448a = C0994a.m4447a(m4476b).m4448a();
            }
            String str = m4448a;
            sharedPreferences.edit().putString("virtual_imsi", str).apply();
            return str;
        }
        return string;
    }

    /* renamed from: g */
    private static String m4560g() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        Random random = new Random();
        return hexString + (random.nextInt(9000) + 1000);
    }

    /* renamed from: c */
    private static String m4566c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(AopConstants.WIFI)).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getSSID() : "-1";
    }

    /* renamed from: d */
    private static String m4563d(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(AopConstants.WIFI)).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getBSSID() : "00";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public static String m4568b(C0988a c0988a, Context context, HashMap<String, String> hashMap) {
        String str;
        try {
            str = SecurityClientMobile.GetApdid(context, hashMap);
        } catch (Throwable th) {
            C0996c.m4436a(th);
            C0954a.m4632a(c0988a, "third", "GetApdidEx", th);
            str = "";
        }
        if (TextUtils.isEmpty(str)) {
            C0954a.m4633a(c0988a, "third", "GetApdidNull", "apdid == null");
        }
        C0996c.m4438a("mspl", "apdid:" + str);
        return str;
    }

    /* renamed from: c */
    private static String m4565c(C0988a c0988a, Context context, HashMap<String, String> hashMap) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new CallableC0966d(c0988a, context, hashMap)).get(3000L, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "third", "GetApdidTimeout", th);
            return "";
        }
    }

    /* renamed from: a */
    public static String m4574a(Context context) {
        if (context != null) {
            try {
                StringBuilder sb = new StringBuilder();
                String packageName = context.getPackageName();
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                sb.append("(");
                sb.append(packageName);
                sb.append(";");
                sb.append(packageInfo.versionCode);
                sb.append(")");
                return sb.toString();
            } catch (Exception unused) {
                return "";
            }
        }
        return "";
    }
}
