package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutSineInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutSineInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) ((1.0d - Math.cos(f * 3.141592653589793d)) * 0.5d);
    }
}
