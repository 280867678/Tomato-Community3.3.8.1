package com.tencent.rtmp.sharp.jni;

import android.content.Context;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes3.dex */
public class QLog {
    public static final int CLR = 2;
    public static final int DEV = 4;
    public static final String ERR_KEY = "qq_error|";
    public static final int LOG_ITEM_MAX_CACHE_SIZE = 50;
    public static final String TAG_REPORTLEVEL_COLORUSER = "W";
    public static final String TAG_REPORTLEVEL_DEVELOPER = "D";
    public static final String TAG_REPORTLEVEL_USER = "E";
    public static final int USR = 1;
    public static String sBuildNumber = "";

    public static void dumpCacheToFile() {
    }

    public static String getReportLevel(int i) {
        return i != 1 ? i != 2 ? i != 4 ? "E" : TAG_REPORTLEVEL_DEVELOPER : "W" : "E";
    }

    public static void init(Context context) {
    }

    public static boolean isColorLevel() {
        return true;
    }

    public static boolean isDevelopLevel() {
        return true;
    }

    /* renamed from: p */
    public static void m372p(String str, String str2) {
    }

    public static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    /* renamed from: e */
    public static void m376e(String str, int i, String str2) {
        TXCLog.m2914e(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: e */
    public static void m375e(String str, int i, String str2, Throwable th) {
        m376e(str, i, str2);
    }

    /* renamed from: w */
    public static void m371w(String str, int i, String str2) {
        TXCLog.m2911w(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: w */
    public static void m370w(String str, int i, String str2, Throwable th) {
        TXCLog.m2911w(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: i */
    public static void m374i(String str, int i, String str2) {
        TXCLog.m2913i(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: i */
    public static void m373i(String str, int i, String str2, Throwable th) {
        TXCLog.m2913i(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: d */
    public static void m378d(String str, int i, String str2) {
        TXCLog.m2915d(str, "[" + getReportLevel(i) + "]" + str2);
    }

    /* renamed from: d */
    public static void m377d(String str, int i, String str2, Throwable th) {
        TXCLog.m2915d(str, "[" + getReportLevel(i) + "]" + str2);
    }
}
