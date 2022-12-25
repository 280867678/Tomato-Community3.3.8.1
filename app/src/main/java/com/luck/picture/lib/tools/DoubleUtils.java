package com.luck.picture.lib.tools;

/* loaded from: classes3.dex */
public class DoubleUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < 800) {
            return true;
        }
        lastClickTime = currentTimeMillis;
        return false;
    }
}
