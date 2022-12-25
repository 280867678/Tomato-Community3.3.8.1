package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.provider.Settings;

/* loaded from: classes2.dex */
public final class DeviceUtils {
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID() {
        String string = Settings.Secure.getString(Utils.getApp().getContentResolver(), "android_id");
        return string == null ? "" : string;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "";
    }
}
