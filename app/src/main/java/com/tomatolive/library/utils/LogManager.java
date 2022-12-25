package com.tomatolive.library.utils;

import android.util.Log;

/* loaded from: classes4.dex */
public class LogManager {
    private static final String TAG_D = "debug_d";
    private static final String TAG_G = "debug_gift";
    public static final String TAG_I = "debug_i";
    public static final String TAG_P = "debug_p";
    public static final String TAG_S = "debug_s";
    private static final String TAG_T = "debug_t";
    static boolean isDebug = false;

    /* renamed from: i */
    public static void m238i(String str) {
        if (isDebug) {
            Log.i(TAG_I, str);
        }
    }

    /* renamed from: s */
    public static void m236s(String str) {
        if (isDebug) {
            Log.i(TAG_S, str);
        }
    }

    /* renamed from: p */
    public static void m237p(String str) {
        if (isDebug) {
            Log.i(TAG_P, str);
        }
    }

    /* renamed from: t */
    public static void m235t(String str) {
        if (isDebug) {
            Log.i(TAG_T, str);
        }
    }

    /* renamed from: d */
    public static void m240d(String str) {
        if (isDebug) {
            Log.i(TAG_D, str);
        }
    }

    /* renamed from: g */
    public static void m239g(String str) {
        if (isDebug) {
            Log.i(TAG_G, str);
        }
    }
}
