package com.faceunity.beautycontrolview.seekbar.internal.compat;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.widget.TextView;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable;

@TargetApi(21)
/* loaded from: classes2.dex */
class SeekBarCompatDontCrash {
    public static void setOutlineProvider(View view, final MarkerDrawable markerDrawable) {
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.faceunity.beautycontrolview.seekbar.internal.compat.SeekBarCompatDontCrash.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setConvexPath(MarkerDrawable.this.getPath());
            }
        });
    }

    public static Drawable getRipple(ColorStateList colorStateList) {
        return new RippleDrawable(colorStateList, null, null);
    }

    public static void setBackground(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    public static void setTextDirection(TextView textView, int i) {
        textView.setTextDirection(i);
    }

    public static boolean isInScrollingContainer(ViewParent viewParent) {
        while (viewParent != null && (viewParent instanceof ViewGroup)) {
            if (((ViewGroup) viewParent).shouldDelayChildPressedState()) {
                return true;
            }
            viewParent = viewParent.getParent();
        }
        return false;
    }

    public static boolean isHardwareAccelerated(View view) {
        return view.isHardwareAccelerated();
    }
}
