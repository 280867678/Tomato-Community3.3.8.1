package com.tencent.liteav.basic.util;

/* loaded from: classes3.dex */
public class TXCTimeUtil {
    private static native long nativeGetTimeTick();

    private static native long nativeGetUtcTimeTick();

    public static long getTimeTick() {
        return nativeGetTimeTick();
    }

    public static long getUtcTimeTick() {
        return nativeGetUtcTimeTick();
    }

    static {
        TXCSystemUtil.m2873e();
    }
}
