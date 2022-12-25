package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInBounceInterpolator */
/* loaded from: classes2.dex */
public class EaseInBounceInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1305s = 1.70158f;
    EaseOutBounceInterpolator out = new EaseOutBounceInterpolator();

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return 1.0f - this.out.getInterpolation(1.0f - f);
    }
}
