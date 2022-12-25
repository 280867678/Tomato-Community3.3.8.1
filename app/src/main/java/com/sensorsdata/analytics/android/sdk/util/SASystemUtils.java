package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.BuildConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class SASystemUtils {
    public static String booleanToString(boolean z) {
        return z ? "1" : "0";
    }

    public static String getLibVersion() {
        return BuildConfig.SDK_VERSION;
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
        hashMap.put("lib", "Android");
        hashMap.put("platformType", "Android");
        hashMap.put("libVersion", getLibVersion());
        hashMap.put("os", "Android");
        String str = Build.VERSION.RELEASE;
        if (str == null) {
            str = "UNKNOWN";
        }
        hashMap.put("osVersion", str);
        hashMap.put(AopConstants.MANUFACTURER, SensorsDataUtils.getManufacturer());
        if (TextUtils.isEmpty(Build.MODEL)) {
            hashMap.put("model", "UNKNOWN");
        } else {
            hashMap.put("model", Build.MODEL.trim());
        }
        hashMap.put(AopConstants.APP_VERSION, getAppVersionName(context));
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
        return Collections.unmodifiableMap(hashMap);
    }
}
