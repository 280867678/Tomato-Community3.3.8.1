package com.tencent.rtmp;

import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes3.dex */
public class TXLog {
    /* renamed from: d */
    public static void m391d(String str, String str2) {
        wrietLogMessage(1, str, str2);
    }

    /* renamed from: i */
    public static void m389i(String str, String str2) {
        wrietLogMessage(2, str, str2);
    }

    /* renamed from: w */
    public static void m388w(String str, String str2) {
        wrietLogMessage(3, str, str2);
    }

    /* renamed from: e */
    public static void m390e(String str, String str2) {
        wrietLogMessage(4, str, str2);
    }

    private static void wrietLogMessage(int i, String str, String str2) {
        TXCLog.log(i, str, "thread ID:" + Thread.currentThread().getId() + "|line:-1|" + str2);
    }
}
