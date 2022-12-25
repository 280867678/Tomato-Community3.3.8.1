package com.blankj.utilcode.util;

import android.content.res.Resources;

/* loaded from: classes2.dex */
public final class ConvertUtils {
    public static int dp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
