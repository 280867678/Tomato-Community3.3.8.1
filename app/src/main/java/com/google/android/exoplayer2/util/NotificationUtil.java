package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.StringRes;

@SuppressLint({"InlinedApi"})
/* loaded from: classes.dex */
public final class NotificationUtil {
    public static void createNotificationChannel(Context context, String str, @StringRes int i, int i2) {
        if (Util.SDK_INT >= 26) {
            ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(new NotificationChannel(str, context.getString(i), i2));
        }
    }
}
