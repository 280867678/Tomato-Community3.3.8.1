package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutBounceInterpolator */
/* loaded from: classes2.dex */
public class EaseOutBounceInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1309s = 1.70158f;

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f < 0.36363637f) {
            return 7.5625f * f * f;
        }
        if (f < 0.72727275f) {
            float f2 = f - 0.54545456f;
            return (7.5625f * f2 * f2) + 0.75f;
        } else if (f < 0.90909094f) {
            float f3 = f - 0.8181818f;
            return (7.5625f * f3 * f3) + 0.9375f;
        } else {
            float f4 = f - 0.95454544f;
            return (7.5625f * f4 * f4) + 0.984375f;
        }
    }
}
