package com.tomatolive.library.p136ui.view.widget.guideview;

import android.content.Context;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.DimenUtil */
/* loaded from: classes4.dex */
public class DimenUtil {
    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int px2dp(Context context, float f) {
        return (int) ((f / context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }
}
