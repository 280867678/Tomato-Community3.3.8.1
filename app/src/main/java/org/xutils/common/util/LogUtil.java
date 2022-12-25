package org.xutils.common.util;

import android.text.TextUtils;
import android.util.Log;
import org.xutils.C5540x;

/* loaded from: classes4.dex */
public class LogUtil {
    public static String customTagPrefix = "x_log";

    private LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        String className = stackTraceElement.getClassName();
        String format = String.format("%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        if (TextUtils.isEmpty(customTagPrefix)) {
            return format;
        }
        return customTagPrefix + ":" + format;
    }

    /* renamed from: d */
    public static void m46d(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.d(generateTag(), str);
    }

    /* renamed from: d */
    public static void m45d(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.d(generateTag(), str, th);
    }

    /* renamed from: e */
    public static void m44e(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.e(generateTag(), str);
    }

    /* renamed from: e */
    public static void m43e(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.e(generateTag(), str, th);
    }

    /* renamed from: i */
    public static void m42i(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.i(generateTag(), str);
    }

    /* renamed from: i */
    public static void m41i(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.i(generateTag(), str, th);
    }

    /* renamed from: v */
    public static void m40v(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.v(generateTag(), str);
    }

    /* renamed from: v */
    public static void m39v(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.v(generateTag(), str, th);
    }

    /* renamed from: w */
    public static void m38w(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.w(generateTag(), str);
    }

    /* renamed from: w */
    public static void m37w(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.w(generateTag(), str, th);
    }

    /* renamed from: w */
    public static void m36w(Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.w(generateTag(), th);
    }

    public static void wtf(String str) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), str);
    }

    public static void wtf(String str, Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), str, th);
    }

    public static void wtf(Throwable th) {
        if (!C5540x.isDebug()) {
            return;
        }
        Log.wtf(generateTag(), th);
    }
}
