package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutBackInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutBackInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1306s = 2.5949094f;

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f * 2.0f;
        if (f2 < 1.0f) {
            return f2 * f2 * ((f2 * 3.5949094f) - f1306s) * 0.5f;
        }
        float f3 = f2 - 2.0f;
        return ((f3 * f3 * ((f3 * 3.5949094f) + f1306s)) + 2.0f) * 0.5f;
    }
}
