package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.PlayerAdsSizeLayoutUtils */
/* loaded from: classes2.dex */
public class PlayerAdsSizeLayoutUtils {
    public static void onLayout(boolean z, boolean z2, Context context, View view) {
        int min;
        int i;
        int i2;
        if (z && !z2) {
            min = Math.max((int) DeviceUtils.getScreenWidth(context), (int) DeviceUtils.getScreenHeight(context));
        } else {
            min = Math.min((int) DeviceUtils.getScreenWidth(context), (int) DeviceUtils.getScreenHeight(context));
        }
        int i3 = (min / 16) * 9;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (z) {
            i = (int) ((i3 / 9) * 16 * 0.65d);
            i2 = (int) (i3 * 0.65d);
            if (z2) {
                i = (int) (i * 1.3d);
                i2 = (int) (i2 * 1.3d);
                layoutParams.setMargins(0, 0, 0, ((int) (DeviceUtils.getScreenHeight(context) / 2.0f)) - (i2 / 2));
            } else {
                layoutParams.setMargins(0, 0, 0, (int) DeviceUtils.dpToPixel(context, 20.0f));
            }
        } else {
            i = (int) (min * 0.65d);
            i2 = (i / 16) * 9;
            layoutParams.setMargins(0, 0, 0, 0);
        }
        layoutParams.height = i2;
        layoutParams.width = i;
        view.setLayoutParams(layoutParams);
    }

    public static void onLayoutOtherAds(boolean z, Context context, View view) {
        int dpToPixel;
        int dpToPixel2;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (z) {
            dpToPixel = (int) DeviceUtils.dpToPixel(context, 200.0f);
            dpToPixel2 = (int) DeviceUtils.dpToPixel(context, 112.0f);
            layoutParams.setMargins(0, 0, (int) DeviceUtils.dpToPixel(context, 13.0f), 0);
        } else {
            dpToPixel = (int) DeviceUtils.dpToPixel(context, 147.0f);
            dpToPixel2 = (int) DeviceUtils.dpToPixel(context, 83.0f);
            layoutParams.setMargins(0, 0, (int) DeviceUtils.dpToPixel(context, 13.0f), 0);
        }
        layoutParams.height = dpToPixel2;
        layoutParams.width = dpToPixel;
        view.setLayoutParams(layoutParams);
    }
}
