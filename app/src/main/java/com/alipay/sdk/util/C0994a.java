package com.alipay.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;

/* renamed from: com.alipay.sdk.util.a */
/* loaded from: classes2.dex */
public class C0994a {

    /* renamed from: c */
    private static C0994a f1033c;

    /* renamed from: b */
    private String f1034b;

    /* renamed from: a */
    public String m4448a() {
        return "000000000000000";
    }

    /* renamed from: b */
    public String m4446b() {
        return "000000000000000";
    }

    /* renamed from: a */
    public static C0994a m4447a(Context context) {
        if (f1033c == null) {
            f1033c = new C0994a(context);
        }
        return f1033c;
    }

    private C0994a(Context context) {
        try {
            try {
                this.f1034b = ((WifiManager) context.getApplicationContext().getSystemService(AopConstants.WIFI)).getConnectionInfo().getMacAddress();
                if (!TextUtils.isEmpty(this.f1034b)) {
                    return;
                }
            } catch (Exception e) {
                C0996c.m4436a(e);
                if (!TextUtils.isEmpty(this.f1034b)) {
                    return;
                }
            }
            this.f1034b = "00:00:00:00:00:00";
        } catch (Throwable th) {
            if (TextUtils.isEmpty(this.f1034b)) {
                this.f1034b = "00:00:00:00:00:00";
            }
            throw th;
        }
    }

    /* renamed from: c */
    public String m4444c() {
        String str = m4446b() + "|";
        String m4448a = m4448a();
        if (TextUtils.isEmpty(m4448a)) {
            return str + "000000000000000";
        }
        return str + m4448a;
    }

    /* renamed from: d */
    public String m4442d() {
        return this.f1034b;
    }

    /* renamed from: b */
    public static EnumC0997d m4445b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                return EnumC0997d.m4429a(activeNetworkInfo.getSubtype());
            }
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                return EnumC0997d.WIFI;
            }
            return EnumC0997d.NONE;
        } catch (Exception unused) {
            return EnumC0997d.NONE;
        }
    }

    /* renamed from: c */
    public static String m4443c(Context context) {
        return m4447a(context).m4444c().substring(0, 8);
    }

    /* renamed from: d */
    public static String m4441d(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }
}
