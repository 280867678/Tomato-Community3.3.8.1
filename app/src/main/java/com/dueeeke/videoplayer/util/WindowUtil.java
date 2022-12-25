package com.dueeeke.videoplayer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.p005v7.app.ActionBar;
import android.support.p005v7.app.AppCompatActivity;
import android.support.p005v7.view.ContextThemeWrapper;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;

/* loaded from: classes2.dex */
public class WindowUtil {
    private static final int FLAGS = 5638;

    public static double getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
        return identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : 0;
    }

    public static int getNavigationBarHeight(Context context) {
        if (!hasNavigationBar(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", Platform.ANDROID));
    }

    public static boolean hasNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT < 17) {
            return !ViewConfiguration.get(context).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(4);
        }
        Display defaultDisplay = getWindowManager(context).getDefaultDisplay();
        Point point = new Point();
        Point point2 = new Point();
        defaultDisplay.getSize(point);
        defaultDisplay.getRealSize(point2);
        return (point2.x == point.x && point2.y == point.y) ? false : true;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context, boolean z) {
        if (z) {
            return context.getResources().getDisplayMetrics().heightPixels + getNavigationBarHeight(context);
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    @SuppressLint({"RestrictedApi"})
    public static void hideSystemBar(Context context) {
        ActionBar supportActionBar;
        AppCompatActivity appCompActivity = getAppCompActivity(context);
        if (appCompActivity != null && (supportActionBar = appCompActivity.getSupportActionBar()) != null && supportActionBar.isShowing()) {
            supportActionBar.setShowHideAnimationEnabled(false);
            supportActionBar.hide();
        }
        hideNavigationBar(context);
    }

    @SuppressLint({"RestrictedApi"})
    public static void showSystemBar(Context context) {
        ActionBar supportActionBar;
        showNavigationBar(context);
        AppCompatActivity appCompActivity = getAppCompActivity(context);
        if (appCompActivity == null || (supportActionBar = appCompActivity.getSupportActionBar()) == null || supportActionBar.isShowing()) {
            return;
        }
        supportActionBar.setShowHideAnimationEnabled(false);
        supportActionBar.show();
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (!(context instanceof ContextWrapper)) {
            return null;
        }
        return scanForActivity(((ContextWrapper) context).getBaseContext());
    }

    private static void hideNavigationBar(Context context) {
        scanForActivity(context).getWindow().getDecorView().setSystemUiVisibility(FLAGS);
    }

    private static void showNavigationBar(Context context) {
        View decorView = scanForActivity(context).getWindow().getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-5639));
    }

    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        }
        if (!(context instanceof ContextThemeWrapper)) {
            return null;
        }
        return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService("window");
    }

    public static boolean isEdge(Context context, MotionEvent motionEvent) {
        int dp2px = dp2px(context, 50.0f);
        float f = dp2px;
        return motionEvent.getRawX() < f || motionEvent.getRawX() > ((float) (getScreenWidth(context) - dp2px)) || motionEvent.getRawY() < f || motionEvent.getRawY() > ((float) (getScreenHeight(context, true) - dp2px));
    }
}
