package com.github.ybq.android.spinkit.animation.interpolator;

import android.annotation.TargetApi;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes2.dex */
class PathInterpolatorCompatApi21 {
    @TargetApi(21)
    public static Interpolator create(float f, float f2, float f3, float f4) {
        return new PathInterpolator(f, f2, f3, f4);
    }
}
