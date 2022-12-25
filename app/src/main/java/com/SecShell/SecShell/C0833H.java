package com.SecShell.SecShell;

import android.app.Application;
import android.content.Context;
import java.io.IOException;
import java.util.List;

/* renamed from: com.SecShell.SecShell.H */
/* loaded from: classes5.dex */
public class C0833H {
    public static String APPNAME = "com.one.tomato.mvp.base.BaseApplication";
    public static String ISMPASS = "###MPASS###";
    public static String PKGNAME = "com.broccoli.bh";

    /* renamed from: cl */
    public static ClassLoader f734cl;

    public static native void attach(Application application, Context context);

    /* renamed from: b */
    public static native void m4922b(Context context, Application application);

    /* renamed from: c */
    public static native void m4921c();

    /* renamed from: d */
    public static native String m4920d(String str);

    /* renamed from: e */
    public static native Object[] m4919e(Object obj, List<IOException> list, String str);

    /* renamed from: f */
    public static void m4917f(ClassLoader classLoader, String str, String str2) {
        C0834a.m4903a(classLoader, str, str2);
    }

    /* renamed from: f */
    public static final native String[] m4918f();

    /* renamed from: g */
    public static void m4915g(Object obj) {
        C0834a.m4902a(obj);
    }

    /* renamed from: g */
    public static final native String[] m4916g();

    /* renamed from: h */
    public static Object m4913h(ClassLoader classLoader) {
        return new C0838b(":", classLoader);
    }

    /* renamed from: h */
    public static final native String[] m4914h();

    /* renamed from: i */
    public static native void m4912i();

    /* renamed from: j */
    public static boolean m4910j(Context context) {
        return new com.SecShell.SecShell.r.b(context).h();
    }

    /* renamed from: j */
    public static final native String[] m4911j();

    /* renamed from: k */
    public static final native String m4909k();

    /* renamed from: l */
    public static final native String m4908l();

    /* renamed from: m */
    public static final native String m4907m();

    /* renamed from: n */
    public static final native String[] m4906n();

    public static void stub() {
    }
}
