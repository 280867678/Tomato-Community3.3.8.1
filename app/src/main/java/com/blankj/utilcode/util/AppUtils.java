package com.blankj.utilcode.util;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public final class AppUtils {
    public static boolean isAppInstalled(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkgName' of type String (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        try {
            return Utils.getApp().getPackageManager().getApplicationInfo(str, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
