package com.gen.p059mh.webapps.utils.interpolator;

import android.animation.TimeInterpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutQuintInterpolator */
/* loaded from: classes2.dex */
public class EaseOutQuintInterpolator implements TimeInterpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }
}
