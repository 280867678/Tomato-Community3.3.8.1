package com.faceunity.beautycontrolview.seekbar.internal.compat;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.p002v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.AlmostRippleDrawable;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable;

/* loaded from: classes2.dex */
public class SeekBarCompat {
    public static void setOutlineProvider(View view, MarkerDrawable markerDrawable) {
        if (Build.VERSION.SDK_INT >= 21) {
            SeekBarCompatDontCrash.setOutlineProvider(view, markerDrawable);
        }
    }

    public static Drawable getRipple(ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            return SeekBarCompatDontCrash.getRipple(colorStateList);
        }
        return new AlmostRippleDrawable(colorStateList);
    }

    public static void setRippleColor(@NonNull Drawable drawable, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT >= 21) {
            ((RippleDrawable) drawable).setColor(colorStateList);
        } else {
            ((AlmostRippleDrawable) drawable).setColor(colorStateList);
        }
    }

    public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 21) {
            int i5 = (i3 - i) / 8;
            DrawableCompat.setHotspotBounds(drawable, i + i5, i2 + i5, i3 - i5, i4 - i5);
            return;
        }
        drawable.setBounds(i, i2, i3, i4);
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            SeekBarCompatDontCrash.setBackground(view, drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void setTextDirection(TextView textView, int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            SeekBarCompatDontCrash.setTextDirection(textView, i);
        }
    }

    public static boolean isInScrollingContainer(ViewParent viewParent) {
        if (Build.VERSION.SDK_INT >= 14) {
            return SeekBarCompatDontCrash.isInScrollingContainer(viewParent);
        }
        return false;
    }

    public static boolean isHardwareAccelerated(View view) {
        if (Build.VERSION.SDK_INT >= 11) {
            return SeekBarCompatDontCrash.isHardwareAccelerated(view);
        }
        return false;
    }
}
