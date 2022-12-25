package com.gen.p059mh.webapp_extensions.matisse.internal.utils;

import android.os.Build;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.utils.Platform */
/* loaded from: classes2.dex */
public class Platform {
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }
}
