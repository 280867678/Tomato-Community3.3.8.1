package com.gen.p059mh.webapp_extensions.matisse.internal.utils;

import android.content.Context;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.utils.UIUtils */
/* loaded from: classes2.dex */
public class UIUtils {
    public static int spanCount(Context context, int i) {
        int round = Math.round(context.getResources().getDisplayMetrics().widthPixels / i);
        if (round == 0) {
            return 1;
        }
        return round;
    }
}
