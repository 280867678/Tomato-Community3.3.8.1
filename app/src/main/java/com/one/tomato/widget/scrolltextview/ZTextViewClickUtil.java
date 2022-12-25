package com.one.tomato.widget.scrolltextview;

/* loaded from: classes3.dex */
public class ZTextViewClickUtil {
    private static long lastClickTime;

    public static synchronized boolean isFastClick() {
        synchronized (ZTextViewClickUtil.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastClickTime < 500) {
                return true;
            }
            lastClickTime = currentTimeMillis;
            return false;
        }
    }
}
