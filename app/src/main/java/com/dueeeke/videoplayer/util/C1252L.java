package com.dueeeke.videoplayer.util;

import android.util.Log;

/* renamed from: com.dueeeke.videoplayer.util.L */
/* loaded from: classes2.dex */
public class C1252L {
    private static final String TAG = "DKPlayer";
    private static boolean isDebug;

    /* renamed from: d */
    public static void m4172d(String str) {
        if (isDebug) {
            Log.d(TAG, str);
        }
    }

    /* renamed from: e */
    public static void m4171e(String str) {
        if (isDebug) {
            Log.e(TAG, str);
        }
    }

    /* renamed from: i */
    public static void m4170i(String str) {
        if (isDebug) {
            Log.i(TAG, str);
        }
    }

    public static void setDebug(boolean z) {
        isDebug = z;
    }
}
