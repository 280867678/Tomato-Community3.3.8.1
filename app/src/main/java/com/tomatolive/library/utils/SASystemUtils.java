package com.tomatolive.library.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.eclipsesource.p056v8.Platform;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.util.DateFormatUtils;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class SASystemUtils {
    public static String getLibVersion() {
        return "3.1.1";
    }

    private SASystemUtils() {
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Map<String, Object> getDeviceInfo(Context context) {
        int height;
        int i;
        HashMap hashMap = new HashMap();
        hashMap.put("platformType", Platform.ANDROID);
        hashMap.put("os", Platform.ANDROID);
        String str = Build.VERSION.RELEASE;
        if (str == null) {
            str = "UNKNOWN";
        }
        hashMap.put("osVersion", str);
        hashMap.put(LogConstants.MANUFACTURER, SensorsDataUtils.getManufacturer());
        if (TextUtils.isEmpty(Build.MODEL)) {
            hashMap.put("model", "UNKNOWN");
        } else {
            hashMap.put("model", Build.MODEL.trim());
        }
        try {
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            int rotation = defaultDisplay.getRotation();
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= 17) {
                defaultDisplay.getRealSize(point);
                i = point.x;
                height = point.y;
            } else if (Build.VERSION.SDK_INT >= 13) {
                defaultDisplay.getSize(point);
                i = point.x;
                height = point.y;
            } else {
                int width = defaultDisplay.getWidth();
                height = defaultDisplay.getHeight();
                i = width;
            }
            hashMap.put("screenWidth", Integer.valueOf(SensorsDataUtils.getNaturalWidth(rotation, i, height)));
            hashMap.put("screenHeight", Integer.valueOf(SensorsDataUtils.getNaturalHeight(rotation, i, height)));
        } catch (Exception unused) {
            if (context.getResources() != null) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                hashMap.put("screenWidth", Integer.valueOf(displayMetrics.widthPixels));
                hashMap.put("screenHeight", Integer.valueOf(displayMetrics.heightPixels));
            }
        }
        String carrier = SensorsDataUtils.getCarrier(context);
        if (!TextUtils.isEmpty(carrier)) {
            hashMap.put("carrier", carrier);
        }
        hashMap.put(LogConstants.DEVICE_ID, DeviceUtils.getAndroidID());
        return hashMap;
    }

    public static int isFirstDay() {
        long j = SPUtils.getInstance().getLong(AopConstants.IS_FIRST_DAY);
        if (j < 0) {
            SPUtils.getInstance().put(AopConstants.IS_FIRST_DAY, System.currentTimeMillis());
            return 1;
        } else if (TextUtils.equals(DateFormatUtils.formatTime(j, "yyyy-MM-dd"), DateFormatUtils.formatTime(System.currentTimeMillis(), "yyyy-MM-dd"))) {
            return 0;
        } else {
            SPUtils.getInstance().put(AopConstants.IS_FIRST_DAY, System.currentTimeMillis());
            return 1;
        }
    }

    public static String duration(long j, long j2) {
        long j3 = j2 - j;
        try {
            if (j3 < 0 || j3 > DateUtils.ONE_DAY_MILLIONS) {
                return String.valueOf(0);
            }
            float f = ((float) j3) / 1000.0f;
            return f < 0.0f ? String.valueOf(0) : String.format(Locale.CHINA, "%.3f", Float.valueOf(f));
        } catch (Exception unused) {
            return String.valueOf(0);
        }
    }
}
