package com.p097ta.utdid2.p098a.p099a;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.Random;

/* renamed from: com.ta.utdid2.a.a.e */
/* loaded from: classes3.dex */
public class C3204e {
    /* renamed from: a */
    public static String m3657a() {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nanoTime = (int) System.nanoTime();
        int nextInt = new Random().nextInt();
        int nextInt2 = new Random().nextInt();
        byte[] bytes = C3203d.getBytes(currentTimeMillis);
        byte[] bytes2 = C3203d.getBytes(nanoTime);
        byte[] bytes3 = C3203d.getBytes(nextInt);
        byte[] bytes4 = C3203d.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return C3198b.encodeToString(bArr, 2);
    }

    /* renamed from: a */
    public static String m3656a(Context context) {
        String str = null;
        if (!C3202c.m3658a() && context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
            } catch (Exception unused) {
            }
        }
        if (C3208g.m3647a(str)) {
            str = m3655b();
        }
        if (C3208g.m3647a(str)) {
            str = m3654b(context);
        }
        return C3208g.m3647a(str) ? m3657a() : str;
    }

    /* renamed from: b */
    private static String m3654b(Context context) {
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            try {
                if (!TextUtils.isEmpty(string) && !string.equalsIgnoreCase("a5f5faddde9e9f02") && !string.equalsIgnoreCase("8e17f7422b35fbea")) {
                    if (!string.equalsIgnoreCase("0000000000000000")) {
                        return string;
                    }
                }
                return "";
            } catch (Throwable unused) {
                return string;
            }
        } catch (Throwable unused2) {
            return "";
        }
    }

    /* renamed from: b */
    private static String m3655b() {
        String str = C3209h.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = C3209h.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? m3653c() : str;
    }

    /* renamed from: c */
    private static String m3653c() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: c */
    public static String m3652c(Context context) {
        String str = null;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Exception unused) {
            }
        }
        return C3208g.m3647a(str) ? m3657a() : str;
    }
}
