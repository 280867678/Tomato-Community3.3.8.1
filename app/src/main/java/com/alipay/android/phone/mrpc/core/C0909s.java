package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* renamed from: com.alipay.android.phone.mrpc.core.s */
/* loaded from: classes2.dex */
public final class C0909s {

    /* renamed from: a */
    private static Boolean f827a;

    /* renamed from: a */
    public static final boolean m4804a(Context context) {
        Boolean bool = f827a;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0);
            f827a = valueOf;
            return valueOf.booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }
}
