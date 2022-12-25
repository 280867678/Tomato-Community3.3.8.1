package com.facebook.drawee.drawable;

import android.graphics.drawable.Drawable;
import android.support.p002v4.view.ViewCompat;

/* loaded from: classes2.dex */
public class DrawableUtils {
    public static int getOpacityFromColor(int i) {
        int i2 = i >>> 24;
        if (i2 == 255) {
            return -1;
        }
        return i2 == 0 ? -2 : -3;
    }

    public static int multiplyColorAlpha(int i, int i2) {
        if (i2 == 255) {
            return i;
        }
        if (i2 == 0) {
            return i & ViewCompat.MEASURED_SIZE_MASK;
        }
        int i3 = i2 + (i2 >> 7);
        return (i & ViewCompat.MEASURED_SIZE_MASK) | ((((i >>> 24) * i3) >> 8) << 24);
    }

    public static void copyProperties(Drawable drawable, Drawable drawable2) {
        if (drawable2 == null || drawable == null || drawable == drawable2) {
            return;
        }
        drawable.setBounds(drawable2.getBounds());
        drawable.setChangingConfigurations(drawable2.getChangingConfigurations());
        drawable.setLevel(drawable2.getLevel());
        drawable.setVisible(drawable2.isVisible(), false);
        drawable.setState(drawable2.getState());
    }

    public static void setDrawableProperties(Drawable drawable, DrawableProperties drawableProperties) {
        if (drawable == null || drawableProperties == null) {
            return;
        }
        drawableProperties.applyTo(drawable);
    }

    public static void setCallbacks(Drawable drawable, Drawable.Callback callback, TransformCallback transformCallback) {
        if (drawable != null) {
            drawable.setCallback(callback);
            if (!(drawable instanceof TransformAwareDrawable)) {
                return;
            }
            ((TransformAwareDrawable) drawable).setTransformCallback(transformCallback);
        }
    }
}
