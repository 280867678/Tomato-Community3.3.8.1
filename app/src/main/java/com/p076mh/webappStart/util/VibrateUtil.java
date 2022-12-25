package com.p076mh.webappStart.util;

import android.content.Context;
import android.os.Vibrator;

/* renamed from: com.mh.webappStart.util.VibrateUtil */
/* loaded from: classes3.dex */
public class VibrateUtil {
    public static boolean vibrate(Context context, long j) {
        ((Vibrator) context.getSystemService("vibrator")).vibrate(new long[]{0, j}, -1);
        return true;
    }
}
