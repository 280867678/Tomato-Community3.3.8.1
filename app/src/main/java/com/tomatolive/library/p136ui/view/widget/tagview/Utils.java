package com.tomatolive.library.p136ui.view.widget.tagview;

import android.content.Context;
import android.graphics.Color;

/* renamed from: com.tomatolive.library.ui.view.widget.tagview.Utils */
/* loaded from: classes4.dex */
public class Utils {
    public static float dp2px(Context context, float f) {
        return (f * context.getResources().getDisplayMetrics().density) + 0.5f;
    }

    public static float sp2px(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int manipulateColorBrightness(int i, float f) {
        int alpha = Color.alpha(i);
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        if (red > 127) {
            red = 255 - Math.round((255 - red) * f);
        }
        if (green > 127) {
            green = 255 - Math.round((255 - green) * f);
        }
        if (blue > 127) {
            blue = 255 - Math.round((255 - blue) * f);
        }
        return Color.argb(alpha, Math.min(red, 255), Math.min(green, 255), Math.min(blue, 255));
    }
}
