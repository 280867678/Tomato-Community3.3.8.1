package com.security.sdk.p095c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/* renamed from: com.security.sdk.c.b */
/* loaded from: classes3.dex */
public class C3085b {

    /* renamed from: a */
    public static final int f1871a = 0;

    /* renamed from: b */
    public static final int f1872b = 1;

    /* renamed from: c */
    public static final int f1873c = 2;

    /* renamed from: d */
    public static final int f1874d = 3;

    /* renamed from: e */
    public static final int f1875e = 4;

    /* renamed from: f */
    public static final int f1876f = 5;

    /* renamed from: a */
    public static String m3688a(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    /* renamed from: b */
    public static int m3687b(Context context) {
        NetworkInfo activeNetworkInfo;
        NetworkInfo.State state;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable()) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                if (networkInfo != null && (state = networkInfo.getState()) != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)) {
                    return 1;
                }
                switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 2;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 3;
                    case 13:
                        return 4;
                    default:
                        return 5;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 5;
        }
    }

    /* renamed from: c */
    public static boolean m3686c(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /* renamed from: d */
    public static synchronized boolean m3685d(Context context) {
        NetworkInfo activeNetworkInfo;
        int type;
        synchronized (C3085b.class) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !((type = activeNetworkInfo.getType()) == 1 || type == 9)) {
                return false;
            }
            return activeNetworkInfo.isConnected();
        }
    }
}
