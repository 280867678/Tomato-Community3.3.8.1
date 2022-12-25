package com.one.tomato.utils;

import android.util.Log;

/* loaded from: classes3.dex */
public class LogUtil {
    private static boolean isShowLog;

    public static void init(boolean z) {
        isShowLog = z;
    }

    /* renamed from: d */
    public static void m3788d(Object obj) {
        log(2, null, obj);
    }

    /* renamed from: d */
    public static void m3787d(String str, Object obj) {
        log(2, str, obj);
    }

    /* renamed from: i */
    public static void m3784i(Object obj) {
        log(3, null, obj);
    }

    /* renamed from: i */
    public static void m3783i(String str, String str2) {
        log(3, str, str2);
    }

    /* renamed from: e */
    public static void m3786e(Object obj) {
        log(5, null, obj);
    }

    /* renamed from: e */
    public static void m3785e(String str, Object obj) {
        log(5, str, obj);
    }

    private static void log(int i, String str, Object obj) {
        if (!isShowLog) {
            return;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fileName = stackTrace[4].getFileName();
        String methodName = stackTrace[4].getMethodName();
        int lineNumber = stackTrace[4].getLineNumber();
        if (str == null) {
            str = fileName;
        }
        String str2 = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        StringBuilder sb = new StringBuilder();
        String obj2 = obj == null ? "Log with null Object" : obj.toString();
        if (obj2 != null) {
            sb.append("(");
            sb.append(fileName);
            sb.append(":");
            sb.append(lineNumber);
            sb.append(")  #");
            sb.append(str2);
            sb.append("\n");
            sb.append(obj2);
            sb.append("\n");
            sb.append("===============================================================================");
        }
        String sb2 = sb.toString();
        if (i == 1) {
            Log.v(str, sb2);
        } else if (i == 2) {
            Log.d(str, sb2);
        } else if (i == 3) {
            Log.i(str, sb2);
        } else if (i == 4) {
            Log.w(str, sb2);
        } else if (i != 5) {
        } else {
            Log.e(str, sb2);
        }
    }
}
