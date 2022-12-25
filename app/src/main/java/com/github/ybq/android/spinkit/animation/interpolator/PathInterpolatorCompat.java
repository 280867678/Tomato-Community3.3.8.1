package com.github.ybq.android.spinkit.animation.interpolator;

import android.os.Build;
import android.view.animation.Interpolator;

/* loaded from: classes2.dex */
public class PathInterpolatorCompat {
    public static Interpolator create(float f, float f2, float f3, float f4) {
        if (Build.VERSION.SDK_INT >= 21) {
            return PathInterpolatorCompatApi21.create(f, f2, f3, f4);
        }
        return PathInterpolatorCompatBase.create(f, f2, f3, f4);
    }
}
