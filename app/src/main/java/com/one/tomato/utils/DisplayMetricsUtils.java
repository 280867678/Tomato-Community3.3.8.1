package com.one.tomato.utils;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.one.tomato.mvp.base.BaseApplication;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class DisplayMetricsUtils {

    /* renamed from: dm */
    private static DisplayMetrics f1754dm;
    private static String resolution;

    private static void initDis() {
        f1754dm = BaseApplication.getApplication().getResources().getDisplayMetrics();
    }

    public static float getHeight() {
        initDis();
        return f1754dm.heightPixels;
    }

    public static float getWidth() {
        initDis();
        return f1754dm.widthPixels;
    }

    public static float getDensity() {
        initDis();
        return f1754dm.density;
    }

    public static int getDensityDpi() {
        initDis();
        return f1754dm.densityDpi;
    }

    public static float dp2px(float f) {
        initDis();
        return (f * f1754dm.density) + 0.5f;
    }

    public static float sp2px(float f) {
        initDis();
        return (f * f1754dm.scaledDensity) + 0.5f;
    }

    public static String getResolution() {
        if (TextUtils.isEmpty(resolution)) {
            resolution = ((int) getWidth()) + Marker.ANY_MARKER + ((int) getHeight());
        }
        return resolution;
    }

    public static float get16To9Height() {
        return (getWidth() / 16.0f) * 9.0f;
    }
}
