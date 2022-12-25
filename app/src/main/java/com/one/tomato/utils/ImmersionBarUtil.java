package com.one.tomato.utils;

import android.app.Activity;
import android.support.p002v4.app.Fragment;
import android.view.View;
import android.view.Window;
import com.gyf.immersionbar.ImmersionBar;

/* loaded from: classes3.dex */
public class ImmersionBarUtil {
    public static void init(Activity activity) {
        ImmersionBar with = ImmersionBar.with(activity);
        with.statusBarDarkFont(true);
        with.init();
    }

    public static void init(Activity activity, View view) {
        ImmersionBar with = ImmersionBar.with(activity);
        with.statusBarDarkFont(true);
        with.titleBar(view);
        with.init();
    }

    public static void init(Activity activity, int i) {
        ImmersionBar with = ImmersionBar.with(activity);
        with.statusBarDarkFont(true);
        with.titleBar(i);
        with.init();
    }

    public static void setFragmentTitleBar(Fragment fragment, View view) {
        ImmersionBar.setTitleBar(fragment, view);
    }

    public static void hideStatusBar(Window window) {
        ImmersionBar.hideStatusBar(window);
    }
}
