package com.alipay.apmobilesecuritysdk.p045e;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.p043c.C0919a;
import com.alipay.security.mobile.module.p047a.C1037a;
import com.tomatolive.library.utils.DateUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alipay.apmobilesecuritysdk.e.i */
/* loaded from: classes2.dex */
public final class C0935i {

    /* renamed from: a */
    private static String f863a = "";

    /* renamed from: b */
    private static String f864b = "";

    /* renamed from: c */
    private static String f865c = "";

    /* renamed from: d */
    private static String f866d = "";

    /* renamed from: e */
    private static String f867e = "";

    /* renamed from: f */
    private static Map<String, String> f868f = new HashMap();

    /* renamed from: a */
    public static synchronized String m4716a(String str) {
        synchronized (C0935i.class) {
            String str2 = "apdidTokenCache" + str;
            if (f868f.containsKey(str2)) {
                String str3 = f868f.get(str2);
                if (C1037a.m4299b(str3)) {
                    return str3;
                }
            }
            return "";
        }
    }

    /* renamed from: a */
    public static synchronized void m4720a() {
        synchronized (C0935i.class) {
        }
    }

    /* renamed from: a */
    public static synchronized void m4718a(C0928b c0928b) {
        synchronized (C0935i.class) {
            if (c0928b != null) {
                f863a = c0928b.f849a;
                f864b = c0928b.f850b;
                f865c = c0928b.f851c;
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m4717a(C0929c c0929c) {
        synchronized (C0935i.class) {
            if (c0929c != null) {
                f863a = c0929c.f852a;
                f864b = c0929c.f853b;
                f866d = c0929c.f855d;
                f867e = c0929c.f856e;
                f865c = c0929c.f854c;
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m4715a(String str, String str2) {
        synchronized (C0935i.class) {
            String str3 = "apdidTokenCache" + str;
            if (f868f.containsKey(str3)) {
                f868f.remove(str3);
            }
            f868f.put(str3, str2);
        }
    }

    /* renamed from: a */
    public static synchronized boolean m4719a(Context context, String str) {
        boolean z;
        synchronized (C0935i.class) {
            long j = DateUtils.ONE_DAY_MILLIONS;
            try {
                long m4737a = C0934h.m4737a(context);
                if (m4737a >= 0) {
                    j = m4737a;
                }
            } catch (Throwable unused) {
            }
            try {
            } catch (Throwable th) {
                C0919a.m4780a(th);
            }
            if (Math.abs(System.currentTimeMillis() - C0934h.m4721h(context, str)) < j) {
                z = true;
            }
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    public static synchronized String m4714b() {
        String str;
        synchronized (C0935i.class) {
            str = f863a;
        }
        return str;
    }

    /* renamed from: b */
    public static void m4713b(String str) {
        f863a = str;
    }

    /* renamed from: c */
    public static synchronized String m4712c() {
        String str;
        synchronized (C0935i.class) {
            str = f864b;
        }
        return str;
    }

    /* renamed from: c */
    public static void m4711c(String str) {
        f864b = str;
    }

    /* renamed from: d */
    public static synchronized String m4710d() {
        String str;
        synchronized (C0935i.class) {
            str = f866d;
        }
        return str;
    }

    /* renamed from: d */
    public static void m4709d(String str) {
        f865c = str;
    }

    /* renamed from: e */
    public static synchronized String m4708e() {
        String str;
        synchronized (C0935i.class) {
            str = f867e;
        }
        return str;
    }

    /* renamed from: e */
    public static void m4707e(String str) {
        f866d = str;
    }

    /* renamed from: f */
    public static synchronized String m4706f() {
        String str;
        synchronized (C0935i.class) {
            str = f865c;
        }
        return str;
    }

    /* renamed from: f */
    public static void m4705f(String str) {
        f867e = str;
    }

    /* renamed from: g */
    public static synchronized C0929c m4704g() {
        C0929c c0929c;
        synchronized (C0935i.class) {
            c0929c = new C0929c(f863a, f864b, f865c, f866d, f867e);
        }
        return c0929c;
    }

    /* renamed from: h */
    public static void m4703h() {
        f868f.clear();
        f863a = "";
        f864b = "";
        f866d = "";
        f867e = "";
        f865c = "";
    }
}
