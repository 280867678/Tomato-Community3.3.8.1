package com.gen.p059mh.webapp_extensions.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

@TargetApi(14)
/* renamed from: com.gen.mh.webapp_extensions.utils.DeviceUtils */
/* loaded from: classes2.dex */
public class DeviceUtils {
    static {
        int i = Build.VERSION.SDK_INT;
        int i2 = Build.VERSION.SDK_INT;
        int i3 = Build.VERSION.SDK_INT;
    }

    public static float dpToPixel(Context context, float f) {
        return f * (getDisplayMetrics(context).densityDpi / 160.0f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static float getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.flags |= 1024;
        activity.getWindow().setAttributes(attributes);
        activity.getWindow().addFlags(512);
    }

    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.flags &= -1025;
        activity.getWindow().setAttributes(attributes);
        activity.getWindow().clearFlags(512);
    }
}
