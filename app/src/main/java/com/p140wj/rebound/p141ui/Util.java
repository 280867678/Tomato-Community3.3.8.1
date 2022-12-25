package com.p140wj.rebound.p141ui;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.FrameLayout;

/* renamed from: com.wj.rebound.ui.Util */
/* loaded from: classes4.dex */
public abstract class Util {
    public static int dpToPx(float f, Resources resources) {
        return (int) TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }

    public static FrameLayout.LayoutParams createLayoutParams(int i, int i2) {
        return new FrameLayout.LayoutParams(i, i2);
    }

    public static FrameLayout.LayoutParams createMatchParams() {
        return createLayoutParams(-1, -1);
    }

    public static FrameLayout.LayoutParams createMatchWrapParams() {
        return createLayoutParams(-1, -2);
    }
}
