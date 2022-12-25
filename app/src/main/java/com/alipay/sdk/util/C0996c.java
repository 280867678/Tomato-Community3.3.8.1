package com.alipay.sdk.util;

import com.alipay.sdk.interior.Log$ISdkLogCallback;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.alipay.sdk.util.c */
/* loaded from: classes2.dex */
public class C0996c {

    /* renamed from: a */
    private static Log$ISdkLogCallback f1035a;

    /* renamed from: a */
    private static void m4439a(String str) {
        try {
            Log$ISdkLogCallback log$ISdkLogCallback = f1035a;
            if (log$ISdkLogCallback == null) {
                return;
            }
            log$ISdkLogCallback.onLogLine(String.format("[AlipaySDK] %s %s", new SimpleDateFormat("hh:mm:ss.SSS", Locale.getDefault()).format(new Date()), str));
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public static void m4438a(String str, String str2) {
        m4439a(m4431e(str, str2));
    }

    /* renamed from: b */
    public static void m4435b(String str, String str2) {
        m4439a(m4431e(str, str2));
    }

    /* renamed from: c */
    public static void m4433c(String str, String str2) {
        m4439a(m4431e(str, str2));
    }

    /* renamed from: d */
    public static void m4432d(String str, String str2) {
        m4439a(m4431e(str, str2));
    }

    /* renamed from: a */
    public static void m4437a(String str, String str2, Throwable th) {
        String m4431e = m4431e(str, str2);
        m4439a(m4431e + ConstantUtils.PLACEHOLDER_STR_ONE + m4434b(th));
    }

    /* renamed from: a */
    public static void m4436a(Throwable th) {
        if (th == null) {
            return;
        }
        try {
            m4439a(m4434b(th));
        } catch (Throwable unused) {
        }
    }

    /* renamed from: e */
    private static String m4431e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        return String.format("[%s][%s]", str, str2);
    }

    /* renamed from: b */
    private static String m4434b(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
