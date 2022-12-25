package com.luck.picture.lib.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/* loaded from: classes3.dex */
public class AttrsUtils {
    public static int getTypeValueColor(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new TypedValue().resourceId, new int[]{i});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static boolean getTypeValueBoolean(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new TypedValue().resourceId, new int[]{i});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    public static Drawable getTypeValuePopWindowImg(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new TypedValue().resourceId, new int[]{i});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }
}
