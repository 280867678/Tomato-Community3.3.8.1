package com.p089pm.liquidlink.p092c;

import android.util.Log;

/* renamed from: com.pm.liquidlink.c.c */
/* loaded from: classes3.dex */
public class C3055c {

    /* renamed from: a */
    public static boolean f1826a;

    /* renamed from: a */
    public static void m3735a(String str, Object... objArr) {
        if (!f1826a) {
            return;
        }
        Log.i("LiquidLink", String.format(str, objArr));
    }

    /* renamed from: b */
    public static void m3734b(String str, Object... objArr) {
        if (!f1826a) {
            return;
        }
        Log.d("LiquidLink", String.format(str, objArr));
    }

    /* renamed from: c */
    public static void m3733c(String str, Object... objArr) {
        if (!f1826a) {
            return;
        }
        Log.w("LiquidLink", String.format(str, objArr));
    }

    /* renamed from: d */
    public static void m3732d(String str, Object... objArr) {
        if (!f1826a) {
            return;
        }
        Log.e("LiquidLink", String.format(str, objArr));
    }
}
