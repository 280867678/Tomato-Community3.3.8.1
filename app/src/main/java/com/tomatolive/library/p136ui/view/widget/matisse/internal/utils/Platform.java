package com.tomatolive.library.p136ui.view.widget.matisse.internal.utils;

import android.os.Build;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.utils.Platform */
/* loaded from: classes4.dex */
public class Platform {
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }
}
