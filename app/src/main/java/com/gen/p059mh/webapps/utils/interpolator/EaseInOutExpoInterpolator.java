package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutExpoInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutExpoInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        double pow;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f == 1.0f) {
            return 1.0f;
        }
        float f2 = f * 2.0f;
        if (f2 < 1.0f) {
            pow = Math.pow(2.0d, (f2 - 1.0f) * 10.0f);
        } else {
            pow = 2.0d - Math.pow(2.0d, (f2 - 1.0f) * (-10.0f));
        }
        return (float) (pow * 0.5d);
    }
}
