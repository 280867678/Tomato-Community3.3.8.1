package com.alipay.apmobilesecuritysdk.p043c;

import android.content.Context;
import android.os.Build;
import com.alipay.security.mobile.module.p051d.C1052a;
import com.alipay.security.mobile.module.p051d.C1055d;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* renamed from: com.alipay.apmobilesecuritysdk.c.a */
/* loaded from: classes2.dex */
public final class C0919a {
    /* renamed from: a */
    public static synchronized void m4782a(Context context, String str, String str2, String str3) {
        synchronized (C0919a.class) {
            C1052a m4779b = m4779b(context, str, str2, str3);
            C1055d.m4205a(context.getFilesDir().getAbsolutePath() + "/log/ap", new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".log", m4779b.toString());
        }
    }

    /* renamed from: a */
    public static synchronized void m4781a(String str) {
        synchronized (C0919a.class) {
            C1055d.m4206a(str);
        }
    }

    /* renamed from: a */
    public static synchronized void m4780a(Throwable th) {
        synchronized (C0919a.class) {
            C1055d.m4204a(th);
        }
    }

    /* renamed from: b */
    private static C1052a m4779b(Context context, String str, String str2, String str3) {
        String str4;
        try {
            str4 = context.getPackageName();
        } catch (Throwable unused) {
            str4 = "";
        }
        return new C1052a(Build.MODEL, str4, "APPSecuritySDK-ALIPAYSDK", "3.4.0.201910161639", str, str2, str3);
    }
}
