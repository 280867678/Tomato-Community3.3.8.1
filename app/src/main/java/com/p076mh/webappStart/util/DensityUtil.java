package com.p076mh.webappStart.util;

import com.gen.p059mh.webapp_extensions.WebApplication;

/* renamed from: com.mh.webappStart.util.DensityUtil */
/* loaded from: classes3.dex */
public final class DensityUtil {
    private static float density = -1.0f;

    public static float getDensity() {
        if (density <= 0.0f) {
            density = WebApplication.getInstance().getApplication().getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(float f) {
        return (int) ((f * getDensity()) + 0.5f);
    }
}
