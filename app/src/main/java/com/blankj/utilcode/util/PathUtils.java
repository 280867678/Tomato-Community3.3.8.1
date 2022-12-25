package com.blankj.utilcode.util;

import android.os.Environment;
import java.io.File;

/* loaded from: classes2.dex */
public class PathUtils {
    public static String getExternalAppCachePath() {
        return isExternalStorageDisable() ? "" : getAbsolutePath(Utils.getApp().getExternalCacheDir());
    }

    public static String getExternalAppFilesPath() {
        return isExternalStorageDisable() ? "" : getAbsolutePath(Utils.getApp().getExternalFilesDir(null));
    }

    private static boolean isExternalStorageDisable() {
        return !"mounted".equals(Environment.getExternalStorageState());
    }

    private static String getAbsolutePath(File file) {
        return file == null ? "" : file.getAbsolutePath();
    }
}
