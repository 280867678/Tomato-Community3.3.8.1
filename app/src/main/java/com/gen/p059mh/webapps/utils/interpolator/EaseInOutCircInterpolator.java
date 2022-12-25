package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutCircInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutCircInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f * 2.0f;
        if (f2 < 1.0f) {
            return (float) ((1.0d - Math.sqrt(1.0f - (f2 * f2))) * 0.5d);
        }
        float f3 = f2 - 2.0f;
        return (float) ((Math.sqrt(1.0f - (f3 * f3)) + 1.0d) * 0.5d);
    }
}
