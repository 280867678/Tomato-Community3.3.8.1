package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutElasticInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutElasticInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f * 2.0f;
        if (f2 >= 2.0f) {
            return 1.0f;
        }
        if (f2 < 1.0f) {
            float f3 = f2 - 1.0f;
            return (float) (Math.pow(2.0d, 10.0f * f3) * (-0.5d) * Math.sin(((f3 - 0.112500004f) * 6.283185307179586d) / 0.45000002f));
        }
        float f4 = f2 - 1.0f;
        return ((float) (Math.pow(2.0d, (-10.0f) * f4) * 0.5d * Math.sin(((f4 - 0.112500004f) * 6.283185307179586d) / 0.45000002f))) + 1.0f;
    }
}
