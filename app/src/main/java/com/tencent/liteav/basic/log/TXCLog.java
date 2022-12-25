package com.tencent.liteav.basic.log;

import android.os.Environment;
import com.tencent.liteav.basic.util.TXCSystemUtil;

/* loaded from: classes3.dex */
public class TXCLog {
    public static final int LOG_ASYNC = 0;
    public static final int LOG_DEBUG = 1;
    public static final int LOG_ERROR = 4;
    public static final int LOG_FATAL = 5;
    public static final int LOG_INFO = 2;
    public static final int LOG_NONE = 6;
    public static final int LOG_SYNC = 1;
    public static final int LOG_VERBOSE = 0;
    public static final int LOG_WARNING = 3;
    private static boolean mHasInit;
    private static AbstractC3363a mListener;
    private static final Object mLogLock = new Object();

    /* renamed from: com.tencent.liteav.basic.log.TXCLog$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3363a {
        /* renamed from: a */
        void mo392a(int i, String str, String str2);
    }

    private static native void nativeLog(int i, String str, String str2, int i2, String str3, String str4);

    private static native void nativeLogClose();

    private static native void nativeLogInit();

    private static native void nativeLogOpen(int i, String str, String str2);

    private static native void nativeLogSetConsole(boolean z);

    private static native void nativeLogSetLevel(int i);

    static {
        TXCSystemUtil.m2873e();
    }

    public static void init() {
        synchronized (mLogLock) {
            if (!mHasInit) {
                nativeLogInit();
                nativeLogSetLevel(0);
                nativeLogSetConsole(true);
                nativeLogOpen(0, Environment.getExternalStorageDirectory().getAbsolutePath() + "/log/tencent/liteav", "LiteAV");
                mHasInit = true;
            }
        }
    }

    public static void log(int i, String str, String str2) {
        init();
        nativeLog(i, str, "", 0, "", str2);
        log_callback(i, str, str2);
    }

    public static void setLevel(int i) {
        init();
        nativeLogSetLevel(i);
    }

    public static void setConsoleEnabled(boolean z) {
        init();
        nativeLogSetConsole(z);
    }

    public static void setListener(AbstractC3363a abstractC3363a) {
        mListener = abstractC3363a;
    }

    /* renamed from: v */
    public static void m2912v(String str, String str2) {
        log(0, str, str2);
    }

    /* renamed from: d */
    public static void m2915d(String str, String str2) {
        log(1, str, str2);
    }

    /* renamed from: i */
    public static void m2913i(String str, String str2) {
        log(2, str, str2);
    }

    /* renamed from: w */
    public static void m2911w(String str, String str2) {
        log(3, str, str2);
    }

    /* renamed from: e */
    public static void m2914e(String str, String str2) {
        log(4, str, str2);
    }

    private static void log_callback(int i, String str, String str2) {
        AbstractC3363a abstractC3363a = mListener;
        if (abstractC3363a != null) {
            abstractC3363a.mo392a(i, str, str2);
        }
    }
}
