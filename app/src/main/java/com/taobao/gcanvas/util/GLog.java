package com.taobao.gcanvas.util;

import android.util.Log;

/* loaded from: classes3.dex */
public class GLog {
    public static int logLevel = 4;

    public static void force(String str, String str2) {
        Log.e(str, str2);
    }

    /* renamed from: d */
    public static void m3565d(String str) {
        m3564d("CANVAS", str);
    }

    /* renamed from: d */
    public static void m3564d(String str, String str2) {
        int i = logLevel;
        if (i == 0) {
            force(str, str2);
        } else if (i > 3) {
        } else {
            Log.i(str, str2);
        }
    }

    /* renamed from: e */
    public static void m3563e(String str, String str2) {
        if (logLevel <= 6) {
            Log.e(str, str2);
        }
    }

    /* renamed from: e */
    public static void m3562e(String str, String str2, Throwable th) {
        if (logLevel <= 6) {
            Log.e(str, str2, th);
        }
    }

    /* renamed from: w */
    public static void m3561w(String str, String str2) {
        int i = logLevel;
        if (i == 0) {
            force(str, str2);
        } else if (i > 5) {
        } else {
            Log.w(str, str2);
        }
    }
}
