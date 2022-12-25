package com.tomatolive.library.p136ui.view.widget.matisse.internal.utils;

import android.content.Context;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.utils.UIUtils */
/* loaded from: classes4.dex */
public class UIUtils {
    public static int spanCount(Context context, int i) {
        int round = Math.round(context.getResources().getDisplayMetrics().widthPixels / i);
        if (round == 0) {
            return 1;
        }
        return round;
    }
}
