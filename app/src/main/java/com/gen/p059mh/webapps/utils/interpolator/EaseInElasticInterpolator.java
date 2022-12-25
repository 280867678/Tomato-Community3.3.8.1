package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInElasticInterpolator */
/* loaded from: classes2.dex */
public class EaseInElasticInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f == 1.0f) {
            return 1.0f;
        }
        float f2 = f - 1.0f;
        return (float) ((-Math.pow(2.0d, 10.0f * f2)) * Math.sin((f2 - 0.075f) * (6.283185307179586d / 0.3f)));
    }
}
