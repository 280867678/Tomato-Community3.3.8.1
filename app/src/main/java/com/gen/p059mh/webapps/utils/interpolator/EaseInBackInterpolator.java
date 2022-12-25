package com.gen.p059mh.webapps.utils.interpolator;

import android.view.animation.Interpolator;

/* renamed from: com.gen.mh.webapps.utils.interpolator.EaseInBackInterpolator */
/* loaded from: classes2.dex */
public class EaseInBackInterpolator implements Interpolator {

    /* renamed from: s */
    private static final float f1304s = 1.70158f;

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return f * f * ((f * 2.70158f) - f1304s);
    }
}
