package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInOutBounceInterpolator */
/* loaded from: classes2.dex */
public class EaseInOutBounceInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1307s = 1.70158f;
    EaseOutBounceInterpolator out = new EaseOutBounceInterpolator();

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f < 0.5f) {
            return (1.0f - this.out.getInterpolation(1.0f - (f * 2.0f))) * 0.5f;
        }
        return (this.out.getInterpolation((f * 2.0f) - 1.0f) + 1.0f) * 0.5f;
    }
}
