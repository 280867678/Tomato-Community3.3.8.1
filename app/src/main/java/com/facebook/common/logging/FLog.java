package com.facebook.common.logging;

/* loaded from: classes2.dex */
public class FLog {
    private static LoggingDelegate sHandler = FLogDefaultLoggingDelegate.getInstance();

    public static boolean isLoggable(int i) {
        return sHandler.isLoggable(i);
    }

    /* renamed from: v */
    public static void m4149v(Class<?> cls, String str) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(getTag(cls), str);
        }
    }

    /* renamed from: v */
    public static void m4148v(Class<?> cls, String str, Object obj) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(getTag(cls), formatString(str, obj));
        }
    }

    /* renamed from: v */
    public static void m4147v(Class<?> cls, String str, Object obj, Object obj2) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(getTag(cls), formatString(str, obj, obj2));
        }
    }

    /* renamed from: v */
    public static void m4146v(Class<?> cls, String str, Object obj, Object obj2, Object obj3) {
        if (isLoggable(2)) {
            m4149v(cls, formatString(str, obj, obj2, obj3));
        }
    }

    /* renamed from: v */
    public static void m4145v(Class<?> cls, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(getTag(cls), formatString(str, obj, obj2, obj3, obj4));
        }
    }

    /* renamed from: v */
    public static void m4143v(String str, String str2, Object... objArr) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(str, formatString(str2, objArr));
        }
    }

    /* renamed from: v */
    public static void m4144v(Class<?> cls, String str, Object... objArr) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo4133v(getTag(cls), formatString(str, objArr));
        }
    }

    /* renamed from: d */
    public static void m4158d(String str, String str2) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo4137d(str, str2);
        }
    }

    /* renamed from: d */
    public static void m4159d(Class<?> cls, String str, Object obj) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo4137d(getTag(cls), formatString(str, obj));
        }
    }

    /* renamed from: d */
    public static void m4157d(String str, String str2, Throwable th) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo4136d(str, str2, th);
        }
    }

    /* renamed from: w */
    public static void m4142w(Class<?> cls, String str) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo4132w(getTag(cls), str);
        }
    }

    /* renamed from: w */
    public static void m4138w(String str, String str2, Object... objArr) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo4132w(str, formatString(str2, objArr));
        }
    }

    /* renamed from: w */
    public static void m4140w(Class<?> cls, String str, Object... objArr) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo4132w(getTag(cls), formatString(str, objArr));
        }
    }

    /* renamed from: w */
    public static void m4139w(Class<?> cls, Throwable th, String str, Object... objArr) {
        if (isLoggable(5)) {
            m4141w(cls, formatString(str, objArr), th);
        }
    }

    /* renamed from: w */
    public static void m4141w(Class<?> cls, String str, Throwable th) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo4131w(getTag(cls), str, th);
        }
    }

    /* renamed from: e */
    public static void m4152e(String str, String str2) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4135e(str, str2);
        }
    }

    /* renamed from: e */
    public static void m4156e(Class<?> cls, String str) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4135e(getTag(cls), str);
        }
    }

    /* renamed from: e */
    public static void m4150e(String str, String str2, Object... objArr) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4135e(str, formatString(str2, objArr));
        }
    }

    /* renamed from: e */
    public static void m4154e(Class<?> cls, String str, Object... objArr) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4135e(getTag(cls), formatString(str, objArr));
        }
    }

    /* renamed from: e */
    public static void m4153e(Class<?> cls, Throwable th, String str, Object... objArr) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4134e(getTag(cls), formatString(str, objArr), th);
        }
    }

    /* renamed from: e */
    public static void m4151e(String str, String str2, Throwable th) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4134e(str, str2, th);
        }
    }

    /* renamed from: e */
    public static void m4155e(Class<?> cls, String str, Throwable th) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo4134e(getTag(cls), str, th);
        }
    }

    public static void wtf(String str, String str2, Object... objArr) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(str, formatString(str2, objArr));
        }
    }

    public static void wtf(Class<?> cls, String str, Throwable th) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), str, th);
        }
    }

    private static String formatString(String str, Object... objArr) {
        return String.format(null, str, objArr);
    }

    private static String getTag(Class<?> cls) {
        return cls.getSimpleName();
    }
}
