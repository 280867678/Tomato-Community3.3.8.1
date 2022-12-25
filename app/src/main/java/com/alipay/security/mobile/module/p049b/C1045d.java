package com.alipay.security.mobile.module.p049b;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.eclipsesource.p056v8.Platform;
import java.io.File;

/* renamed from: com.alipay.security.mobile.module.b.d */
/* loaded from: classes2.dex */
public final class C1045d {

    /* renamed from: a */
    private static C1045d f1123a = new C1045d();

    private C1045d() {
    }

    /* renamed from: a */
    public static C1045d m4236a() {
        return f1123a;
    }

    /* renamed from: a */
    private static String m4234a(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, str, str2);
        } catch (Exception unused) {
            return str2;
        }
    }

    /* renamed from: a */
    public static boolean m4235a(Context context) {
        boolean z;
        int length;
        try {
            if (!Build.HARDWARE.contains("goldfish") && !Build.PRODUCT.contains("sdk") && !Build.FINGERPRINT.contains("generic")) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    String deviceId = telephonyManager.getDeviceId();
                    if (deviceId != null && (length = deviceId.length()) != 0) {
                        for (int i = 0; i < length; i++) {
                            if (!Character.isWhitespace(deviceId.charAt(i)) && deviceId.charAt(i) != '0') {
                                z = false;
                                break;
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        return true;
                    }
                }
                return C1037a.m4303a(Settings.Secure.getString(context.getContentResolver(), "android_id"));
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: b */
    public static String m4233b() {
        return Platform.ANDROID;
    }

    /* renamed from: c */
    public static boolean m4232c() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i = 0; i < 5; i++) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    return true;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: d */
    public static String m4231d() {
        return Build.BOARD;
    }

    /* renamed from: e */
    public static String m4230e() {
        return Build.BRAND;
    }

    /* renamed from: f */
    public static String m4229f() {
        return Build.DEVICE;
    }

    /* renamed from: g */
    public static String m4228g() {
        return Build.DISPLAY;
    }

    /* renamed from: h */
    public static String m4227h() {
        return Build.VERSION.INCREMENTAL;
    }

    /* renamed from: i */
    public static String m4226i() {
        return Build.MANUFACTURER;
    }

    /* renamed from: j */
    public static String m4225j() {
        return Build.MODEL;
    }

    /* renamed from: k */
    public static String m4224k() {
        return Build.PRODUCT;
    }

    /* renamed from: l */
    public static String m4223l() {
        return Build.VERSION.RELEASE;
    }

    /* renamed from: m */
    public static String m4222m() {
        return Build.VERSION.SDK;
    }

    /* renamed from: n */
    public static String m4221n() {
        return Build.TAGS;
    }

    /* renamed from: o */
    public static String m4220o() {
        return m4234a("ro.kernel.qemu", "0");
    }
}
