package com.p076mh.webappStart.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/* renamed from: com.mh.webappStart.util.GlobalMagicJavaUtil */
/* loaded from: classes3.dex */
public class GlobalMagicJavaUtil {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static int getSystemBrightness() {
        try {
            return Settings.System.getInt(mContext.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setWindowBrightnessForWx(Activity activity, float f) {
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.screenBrightness = f;
        activity.getWindow().setAttributes(attributes);
    }

    public static float getWindowBrightnessForWx(Activity activity) {
        float f = activity.getWindow().getAttributes().screenBrightness;
        return f <= -1.0f ? getSystemBrightness() / 255.0f : f;
    }
}
