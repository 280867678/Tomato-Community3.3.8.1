package com.gen.p059mh.webapps.utils;

import android.util.Log;

/* renamed from: com.gen.mh.webapps.utils.Logger */
/* loaded from: classes2.dex */
public class Logger {
    public static boolean DEBUG = true;
    private static final String TAG = "WebAppSdk";

    /* renamed from: i */
    public static void m4113i(Object obj) {
        if (DEBUG) {
            Log.i(TAG, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: i */
    public static void m4112i(String str, Object obj) {
        if (DEBUG) {
            Log.i("WebAppSdk_" + str, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: d */
    public static void m4117d(Object obj) {
        if (DEBUG) {
            Log.d(TAG, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: d */
    public static void m4116d(String str, Object obj) {
        if (DEBUG) {
            Log.d("WebAppSdk_" + str, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: v */
    public static void m4111v(Object obj) {
        if (DEBUG) {
            Log.v(TAG, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: v */
    public static void m4110v(String str, Object obj) {
        if (DEBUG) {
            Log.v("WebAppSdk_" + str, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: w */
    public static void m4109w(Object obj) {
        if (DEBUG) {
            Log.w(TAG, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: w */
    public static void m4108w(String str, Object obj) {
        if (DEBUG) {
            Log.w("WebAppSdk_" + str, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: e */
    public static void m4115e(Object obj) {
        if (DEBUG) {
            Log.e(TAG, obj == null ? "null" : obj.toString());
        }
    }

    /* renamed from: e */
    public static void m4114e(String str, Object obj) {
        if (DEBUG) {
            Log.e("WebAppSdk_" + str, obj == null ? "null" : obj.toString());
        }
    }
}
