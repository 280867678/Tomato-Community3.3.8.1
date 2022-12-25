package com.tomatolive.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import com.blankj.utilcode.util.ScreenUtils;
import com.eclipsesource.p056v8.Platform;
import com.tomatolive.library.TomatoLiveSDK;
import java.util.List;

/* loaded from: classes4.dex */
public class SystemUtils {
    private SystemUtils() {
    }

    public static String getResString(@StringRes int i) {
        Application application = TomatoLiveSDK.getSingleton().getApplication();
        return application != null ? application.getResources().getString(i) : "";
    }

    public static Bitmap decodeResource(Resources resources, int i) {
        TypedValue typedValue = new TypedValue();
        resources.openRawResource(i, typedValue);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = typedValue.density;
        return BitmapFactory.decodeResource(resources, i, options);
    }

    public static boolean isApplicationInBackground(Context context) {
        ComponentName componentName;
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        return runningTasks != null && !runningTasks.isEmpty() && (componentName = runningTasks.get(0).topActivity) != null && !componentName.getPackageName().equals(context.getPackageName());
    }

    public static float dp2px(float f) {
        return (f * Resources.getSystem().getDisplayMetrics().density) + 0.5f;
    }

    public static int px2dp(float f) {
        return Math.round(f / (Resources.getSystem().getDisplayMetrics().xdpi / 160.0f));
    }

    public static float sp2px(float f) {
        return f * Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

    public static int getContentViewInvisibleHeight(int i, Activity activity) {
        View findViewById = activity.findViewById(16908290);
        if (findViewById == null) {
            return i;
        }
        Rect rect = new Rect();
        findViewById.getWindowVisibleDisplayFrame(rect);
        int abs = Math.abs(findViewById.getBottom() - rect.bottom);
        if (abs > getStatusBarHeight() + getNavBarHeight()) {
            return abs;
        }
        return 0;
    }

    public static int getStatusBarHeight() {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", Platform.ANDROID));
    }

    public static int getNavBarHeight() {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("navigation_bar_height", "dimen", Platform.ANDROID);
        if (identifier != 0) {
            return system.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getAppScreenHeight(Activity activity) {
        return ScreenUtils.getScreenHeight() - getNavigationBarHeightIfRoom(activity);
    }

    public static int getNavigationBarHeightIfRoom(Activity activity) {
        if (navigationGestureEnabled(activity)) {
            return 0;
        }
        return getCurrentNavigationBarHeight(activity);
    }

    public static boolean navigationGestureEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0) != 0;
    }

    public static String getDeviceInfo() {
        String str = Build.BRAND;
        return (!TextUtils.isEmpty(str) && !str.equalsIgnoreCase("HUAWEI")) ? str.equalsIgnoreCase("XIAOMI") ? "force_fsg_nav_bar" : (!str.equalsIgnoreCase("VIVO") && !str.equalsIgnoreCase("OPPO")) ? "navigationbar_is_min" : "navigation_gesture_on" : "navigationbar_is_min";
    }

    public static int getCurrentNavigationBarHeight(Activity activity) {
        if (isNavigationBarShown(activity)) {
            return getNavBarHeight();
        }
        return 0;
    }

    public static boolean isNavigationBarShown(Activity activity) {
        int visibility;
        if (Build.VERSION.SDK_INT < 21) {
            return true;
        }
        View findViewById = activity.findViewById(16908336);
        return (findViewById == null || (visibility = findViewById.getVisibility()) == 8 || visibility == 4) ? false : true;
    }
}
