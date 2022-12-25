package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;

/* loaded from: classes2.dex */
public final class BarUtils {
    public static int getNavBarHeight() {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("navigation_bar_height", "dimen", Platform.ANDROID);
        if (identifier != 0) {
            return system.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static boolean isSupportNavBar() {
        if (Build.VERSION.SDK_INT < 17) {
            return !ViewConfiguration.get(Utils.getApp()).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(4);
        }
        WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        if (windowManager == null) {
            return false;
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point point = new Point();
        Point point2 = new Point();
        defaultDisplay.getSize(point);
        defaultDisplay.getRealSize(point2);
        return (point2.y == point.y && point2.x == point.x) ? false : true;
    }
}
