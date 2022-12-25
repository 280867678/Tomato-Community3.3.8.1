package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutCircInterpolator */
/* loaded from: classes2.dex */
public class EaseOutCircInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (float) Math.sqrt(1.0f - (f2 * f2));
    }
}
