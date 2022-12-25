package com.king.zxing.util;

import android.util.Log;

/* loaded from: classes3.dex */
public class LogUtils {
    private static boolean isShowLog = true;
    private static int priority = 1;

    public static boolean isShowLog() {
        return isShowLog;
    }

    private static String generateTag(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String format = String.format("%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        return "ZXingLite|" + format;
    }

    public static StackTraceElement getStackTraceElement(int i) {
        return Thread.currentThread().getStackTrace()[i];
    }

    private static String getCallerStackLogTag() {
        return generateTag(getStackTraceElement(5));
    }

    private static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    /* renamed from: d */
    public static void m3904d(String str) {
        if (!isShowLog || priority > 3) {
            return;
        }
        Log.d(getCallerStackLogTag(), String.valueOf(str));
    }

    /* renamed from: i */
    public static void m3903i(String str) {
        if (!isShowLog || priority > 4) {
            return;
        }
        Log.i(getCallerStackLogTag(), String.valueOf(str));
    }

    /* renamed from: w */
    public static void m3902w(String str) {
        if (!isShowLog || priority > 5) {
            return;
        }
        Log.w(getCallerStackLogTag(), String.valueOf(str));
    }

    /* renamed from: w */
    public static void m3900w(Throwable th) {
        if (!isShowLog || priority > 5) {
            return;
        }
        Log.w(getCallerStackLogTag(), getStackTraceString(th));
    }

    /* renamed from: w */
    public static void m3901w(String str, Throwable th) {
        if (!isShowLog || priority > 5) {
            return;
        }
        Log.w(getCallerStackLogTag(), String.valueOf(str), th);
    }
}
