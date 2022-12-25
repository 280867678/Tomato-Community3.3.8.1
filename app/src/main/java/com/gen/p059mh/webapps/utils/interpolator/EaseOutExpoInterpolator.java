package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutExpoInterpolator */
/* loaded from: classes2.dex */
public class EaseOutExpoInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 1.0f) {
            return 1.0f;
        }
        return (float) (1.0d - Math.pow(2.0d, f * (-10.0f)));
    }
}
