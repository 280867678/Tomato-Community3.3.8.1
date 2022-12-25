package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutQuartInterpolator */
/* loaded from: classes2.dex */
public class EaseOutQuartInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return 1.0f - (((f2 * f2) * f2) * f2);
    }
}
