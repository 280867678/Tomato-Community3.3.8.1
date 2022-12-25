package com.github.ybq.android.spinkit.animation.interpolator;

import android.view.animation.Interpolator;

/* loaded from: classes2.dex */
class PathInterpolatorCompatBase {
    public static Interpolator create(float f, float f2, float f3, float f4) {
        return new PathInterpolatorDonut(f, f2, f3, f4);
    }
}
