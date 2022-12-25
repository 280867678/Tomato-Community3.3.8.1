package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseOutBackInterpolator */
/* loaded from: classes2.dex */
public class EaseOutBackInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1308s = 1.70158f;

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * ((f2 * 2.70158f) + f1308s)) + 1.0f;
    }
}
