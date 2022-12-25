package com.sensorsdata.analytics.android.sdk;

import android.util.Log;

/* loaded from: classes3.dex */
public class SALog {
    private static String TAG_I = "SALog_debug";
    private static boolean debug;
    private static boolean enableLog;
    private static boolean isShow;

    /* renamed from: i */
    public static void m3675i(String str) {
        if (isShow) {
            Log.i(TAG_I, str);
        }
    }

    /* renamed from: d */
    public static void m3677d(String str, String str2) {
        if (debug) {
            info(str, str2, null);
        }
    }

    /* renamed from: d */
    public static void m3676d(String str, String str2, Throwable th) {
        if (debug) {
            info(str, str2, th);
        }
    }

    /* renamed from: i */
    public static void m3674i(String str, String str2) {
        if (enableLog) {
            info(str, str2, null);
        }
    }

    /* renamed from: i */
    public static void m3672i(String str, Throwable th) {
        if (enableLog) {
            info(str, "", th);
        }
    }

    /* renamed from: i */
    public static void m3673i(String str, String str2, Throwable th) {
        if (enableLog) {
            info(str, str2, th);
        }
    }

    public static void info(String str, String str2, Throwable th) {
        try {
            Log.i(str, str2, th);
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void printStackTrace(Exception exc) {
        if (!enableLog || exc == null) {
            return;
        }
        exc.printStackTrace();
    }

    static void setDebug(boolean z) {
        debug = z;
    }

    public static void setShow(boolean z) {
        isShow = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setEnableLog(boolean z) {
        enableLog = z;
    }

    public static boolean isLogEnabled() {
        return enableLog;
    }
}
