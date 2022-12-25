package com.tomatolive.library.utils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes4.dex */
public class ReSizeUtils {
    public static void reSizeAnchorPkViewSmall(View view, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth() / 2) - 2;
        layoutParams.height = getPKVideoViewHeight();
        layoutParams.topMargin = i;
        view.setLayoutParams(layoutParams);
    }

    public static void reSizeAudienceViewSmall(View view, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth();
        layoutParams.height = getPKVideoViewHeight();
        layoutParams.topMargin = i;
        view.setLayoutParams(layoutParams);
    }

    public static void reSizeRemoteViewSmall(View view, int i) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((ScreenUtils.getScreenWidth() / 2) - 2, getPKVideoViewHeight());
        layoutParams.addRule(11);
        layoutParams.topMargin = i;
        view.setLayoutParams(layoutParams);
    }

    public static void reSizeViewBig(View view) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    }

    public static void reAudienceSizeViewBig(View view) {
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    }

    public static int getPKVideoViewHeight() {
        return (int) ((ScreenUtils.getScreenWidth() * 2) / 3.0f);
    }
}
