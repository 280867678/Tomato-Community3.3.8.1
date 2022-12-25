package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInSineInterpolator */
/* loaded from: classes2.dex */
public class EaseInSineInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) (1.0d - Math.cos(f * 1.5707963267948966d));
    }
}
