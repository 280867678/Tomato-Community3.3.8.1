package com.alipay.security.mobile.module.p049b;

import android.content.Context;

/* renamed from: com.alipay.security.mobile.module.b.a */
/* loaded from: classes2.dex */
public final class C1042a {

    /* renamed from: a */
    private static C1042a f1121a = new C1042a();

    private C1042a() {
    }

    /* renamed from: a */
    public static C1042a m4283a() {
        return f1121a;
    }

    /* renamed from: a */
    public static String m4282a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16).versionName;
        } catch (Exception unused) {
            return "0.0.0";
        }
    }
}
