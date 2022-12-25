package org.xutils.common.util;

import org.xutils.C5540x;

/* loaded from: classes4.dex */
public final class DensityUtil {
    private static float density = -1.0f;
    private static int heightPixels = -1;
    private static int widthPixels = -1;

    private DensityUtil() {
    }

    public static float getDensity() {
        if (density <= 0.0f) {
            density = C5540x.app().getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(float f) {
        return (int) ((f * getDensity()) + 0.5f);
    }

    public static int px2dip(float f) {
        return (int) ((f / getDensity()) + 0.5f);
    }

    public static int getScreenWidth() {
        if (widthPixels <= 0) {
            widthPixels = C5540x.app().getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    public static int getScreenHeight() {
        if (heightPixels <= 0) {
            heightPixels = C5540x.app().getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
