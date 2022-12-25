package com.tomatolive.library.utils.litepal.util;

import android.util.Log;

/* loaded from: classes4.dex */
public final class LitePalLog {
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static int level = 5;

    /* renamed from: d */
    public static void m234d(String str, String str2) {
        if (level <= 2) {
            Log.d(str, str2);
        }
    }

    /* renamed from: e */
    public static void m233e(String str, Exception exc) {
        if (level <= 5) {
            Log.e(str, exc.getMessage(), exc);
        }
    }
}
