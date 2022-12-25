package com.youdao.sdk.app.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.youdao.sdk.common.YouDaoLog;

/* renamed from: com.youdao.sdk.app.other.h */
/* loaded from: classes4.dex */
public class C5165h {

    /* renamed from: a */
    private static volatile C5165h f5914a;

    /* renamed from: b */
    private String f5915b;

    /* renamed from: c */
    private String f5916c;

    /* renamed from: d */
    private String f5917d;

    /* renamed from: e */
    private String f5918e;

    /* renamed from: f */
    private String f5919f;

    /* renamed from: g */
    private String f5920g;

    /* renamed from: h */
    private final String f5921h = Build.MANUFACTURER;

    /* renamed from: i */
    private final String f5922i = Build.MODEL;

    /* renamed from: j */
    private final String f5923j = Build.PRODUCT;

    /* renamed from: k */
    private final String f5924k = "1.7.3";

    /* renamed from: l */
    private final String f5925l = "v1";

    /* renamed from: m */
    private final String f5926m;

    /* renamed from: p */
    private final Context f5927p;

    /* renamed from: q */
    private final ConnectivityManager f5928q;

    /* renamed from: r */
    private final String f5929r;

    /* renamed from: a */
    public static C5165h m208a(Context context) {
        C5165h c5165h = f5914a;
        if (c5165h == null) {
            synchronized (C5165h.class) {
                c5165h = f5914a;
                if (c5165h == null) {
                    c5165h = new C5165h(context);
                    f5914a = c5165h;
                }
            }
        }
        return c5165h;
    }

    /* renamed from: com.youdao.sdk.app.other.h$a */
    /* loaded from: classes4.dex */
    public enum EnumC5166a {
        UNKNOWN(0),
        ETHERNET(1),
        WIFI(2),
        MOBILE(3);
        
        private final int mId;

        EnumC5166a(int i) {
            this.mId = i;
        }

        @Override // java.lang.Enum
        public String toString() {
            return Integer.toString(this.mId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static EnumC5166a fromAndroidNetworkType(int i) {
            if (i != 0) {
                if (i == 1) {
                    return WIFI;
                }
                if (i != 2 && i != 3 && i != 4 && i != 5) {
                    if (i == 9) {
                        return ETHERNET;
                    }
                    return UNKNOWN;
                }
            }
            return MOBILE;
        }
    }

    private C5165h(Context context) {
        this.f5927p = context.getApplicationContext();
        this.f5928q = (ConnectivityManager) this.f5927p.getSystemService("connectivity");
        String str = Build.VERSION.RELEASE;
        String str2 = Build.VERSION.SDK;
        this.f5926m = m202d(this.f5927p);
        this.f5929r = m204c(this.f5927p);
        TelephonyManager telephonyManager = (TelephonyManager) this.f5927p.getSystemService("phone");
        this.f5915b = telephonyManager.getNetworkOperator();
        if (telephonyManager.getPhoneType() == 2 && telephonyManager.getSimState() == 5) {
            this.f5915b = telephonyManager.getSimOperator();
        }
        this.f5916c = telephonyManager.getNetworkCountryIso();
        try {
            this.f5917d = telephonyManager.getNetworkOperatorName();
        } catch (SecurityException unused) {
            this.f5917d = null;
        }
        this.f5918e = m200e(this.f5927p);
        this.f5919f = Settings.Secure.getString(context.getContentResolver(), "android_id");
        this.f5920g = m206b(this.f5927p);
    }

    /* renamed from: b */
    private static String m206b(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }

    /* renamed from: c */
    private static String m204c(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception unused) {
            YouDaoLog.m169d("Failed to retrieve PackageInfo#versionName.");
            return null;
        }
    }

    /* renamed from: d */
    private static String m202d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            YouDaoLog.m169d("Failed to retrieve PackageInfo#versionName.");
            return null;
        }
    }

    /* renamed from: e */
    private static String m200e(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        String m170a = string == null ? "" : C5177z.m170a(string);
        return "sha:" + m170a;
    }

    /* renamed from: a */
    public String m209a() {
        int i = this.f5927p.getResources().getConfiguration().orientation;
        return i == 1 ? "p" : i == 2 ? "l" : i == 3 ? "s" : "u";
    }

    /* renamed from: b */
    public EnumC5166a m207b() {
        NetworkInfo activeNetworkInfo;
        int i = -1;
        if (this.f5927p.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0 && (activeNetworkInfo = this.f5928q.getActiveNetworkInfo()) != null) {
            i = activeNetworkInfo.getType();
        }
        return EnumC5166a.fromAndroidNetworkType(i);
    }

    /* renamed from: c */
    public String m205c() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.f5927p.getSystemService("phone");
            return telephonyManager != null ? telephonyManager.getDeviceId() : "";
        } catch (SecurityException unused) {
            return "";
        }
    }

    /* renamed from: d */
    public int m203d() {
        if (this.f5927p.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
            NetworkInfo activeNetworkInfo = this.f5928q.getActiveNetworkInfo();
            if (EnumC5166a.fromAndroidNetworkType(activeNetworkInfo != null ? activeNetworkInfo.getType() : -1) != EnumC5166a.MOBILE) {
                return -1;
            }
            return activeNetworkInfo.getSubtype();
        }
        return -1;
    }

    /* renamed from: e */
    public float m201e() {
        return this.f5927p.getResources().getDisplayMetrics().density;
    }

    /* renamed from: f */
    public String m199f() {
        return this.f5915b;
    }

    /* renamed from: g */
    public String m198g() {
        return this.f5916c;
    }

    /* renamed from: h */
    public String m197h() {
        return this.f5917d;
    }

    /* renamed from: i */
    public String m196i() {
        return this.f5918e;
    }

    /* renamed from: j */
    public String m195j() {
        return this.f5919f;
    }

    /* renamed from: k */
    public String m194k() {
        return this.f5920g;
    }

    /* renamed from: l */
    public String m193l() {
        return this.f5921h;
    }

    /* renamed from: m */
    public String m192m() {
        return this.f5922i;
    }

    /* renamed from: n */
    public String m191n() {
        return this.f5923j;
    }

    /* renamed from: o */
    public String m190o() {
        return this.f5924k;
    }

    /* renamed from: p */
    public String m189p() {
        return this.f5925l;
    }

    /* renamed from: q */
    public String m188q() {
        return this.f5926m;
    }

    /* renamed from: r */
    public String m187r() {
        return this.f5929r;
    }
}
