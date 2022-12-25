package com.tomatolive.library.p136ui.view.widget.drawabletext;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import java.lang.reflect.Field;

/* renamed from: com.tomatolive.library.ui.view.widget.drawabletext.DrawableCompatHelper */
/* loaded from: classes4.dex */
public class DrawableCompatHelper {
    public static int[] getColors(GradientDrawable gradientDrawable) {
        if (gradientDrawable == null) {
            return new int[0];
        }
        if (Build.VERSION.SDK_INT >= 24) {
            int[] colors = gradientDrawable.getColors();
            if (colors != null) {
                return colors;
            }
            if (gradientDrawable.getColor() == null) {
                return null;
            }
            int defaultColor = gradientDrawable.getColor().getDefaultColor();
            return new int[]{defaultColor, defaultColor};
        }
        try {
            Field declaredField = GradientDrawable.class.getDeclaredField("mGradientState");
            declaredField.setAccessible(true);
            Field declaredField2 = declaredField.get(gradientDrawable).getClass().getDeclaredField("mGradientColors");
            declaredField2.setAccessible(true);
            return (int[]) declaredField2.get(declaredField.get(gradientDrawable));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    public static ColorStateList getColorStateList(GradientDrawable gradientDrawable) {
        if (Build.VERSION.SDK_INT >= 24) {
            return gradientDrawable.getColor();
        }
        try {
            Field declaredField = GradientDrawable.class.getDeclaredField("mGradientState");
            declaredField.setAccessible(true);
            Field declaredField2 = declaredField.get(gradientDrawable).getClass().getDeclaredField("mColorStateList");
            declaredField2.setAccessible(true);
            return (ColorStateList) declaredField2.get(declaredField.get(gradientDrawable));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float[] getPositions(GradientDrawable gradientDrawable, boolean z) {
        if (z) {
            float[] fArr = new float[3];
            fArr[0] = 0.0f;
            float f = 0.5f;
            if (Build.VERSION.SDK_INT >= 24) {
                f = gradientDrawable.getGradientCenterX();
            } else {
                try {
                    Field declaredField = GradientDrawable.class.getDeclaredField("mGradientState");
                    declaredField.setAccessible(true);
                    Field declaredField2 = declaredField.get(gradientDrawable).getClass().getDeclaredField("mCenterX");
                    declaredField2.setAccessible(true);
                    f = ((Float) declaredField2.get(declaredField.get(gradientDrawable))).floatValue();
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            fArr[1] = f;
            fArr[2] = 1.0f;
            return fArr;
        }
        return null;
    }
}
