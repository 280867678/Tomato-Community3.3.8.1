package com.luck.picture.lib.immersive;

import android.app.Activity;
import android.os.Build;
import android.view.Window;

/* loaded from: classes3.dex */
public class ImmersiveManage {
    public static void immersiveAboveAPI23(Activity activity, int i, int i2, boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            immersiveAboveAPI23(activity, false, false, i, i2, z);
        }
    }

    public static void immersiveAboveAPI23(Activity activity, boolean z, boolean z2, int i, int i2, boolean z3) {
        try {
            Window window = activity.getWindow();
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                window.setFlags(67108864, 67108864);
            } else if (Build.VERSION.SDK_INT < 21) {
            } else {
                boolean z4 = false;
                if (z && z2) {
                    window.clearFlags(201326592);
                    if (i == 0) {
                        z4 = true;
                    }
                    LightStatusBarUtils.setLightStatusBar(activity, z, z2, z4, z3);
                    window.addFlags(Integer.MIN_VALUE);
                } else if (!z && !z2) {
                    window.requestFeature(1);
                    window.clearFlags(201326592);
                    if (i == 0) {
                        z4 = true;
                    }
                    LightStatusBarUtils.setLightStatusBar(activity, z, z2, z4, z3);
                    window.addFlags(Integer.MIN_VALUE);
                } else if (z || !z2) {
                    return;
                } else {
                    window.requestFeature(1);
                    window.clearFlags(201326592);
                    if (i == 0) {
                        z4 = true;
                    }
                    LightStatusBarUtils.setLightStatusBar(activity, z, z2, z4, z3);
                    window.addFlags(Integer.MIN_VALUE);
                }
                window.setStatusBarColor(i);
                window.setNavigationBarColor(i2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
