package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInCircInterpolator */
/* loaded from: classes2.dex */
public class EaseInCircInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) (1.0d - Math.sqrt(1.0f - (f * f)));
    }
}
