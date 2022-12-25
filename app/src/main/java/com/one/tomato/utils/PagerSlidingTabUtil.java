package com.one.tomato.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.p002v4.content.ContextCompat;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.widget.PagerSlidingTabStrip;

/* loaded from: classes3.dex */
public class PagerSlidingTabUtil {
    public static void setAllTabsValue(Context context, PagerSlidingTabStrip pagerSlidingTabStrip, boolean z) {
        if (PreferencesUtil.getInstance().getString("language_country").equals("US") || !z) {
            pagerSlidingTabStrip.setIsShowAllTabs(false);
        } else {
            pagerSlidingTabStrip.setIsShowAllTabs(true);
        }
        pagerSlidingTabStrip.setTextColor(context.getResources().getColor(R.color.text_middle));
        pagerSlidingTabStrip.setSelectedTextColor(context.getResources().getColor(R.color.colorAccent));
        pagerSlidingTabStrip.setCustomIndicatorWith(false, 0);
        pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#00000000"));
        pagerSlidingTabStrip.setIndicatorColor(R.color.transparent);
        pagerSlidingTabStrip.setIndicatorHeight(0);
        pagerSlidingTabStrip.setBoldText(true);
        pagerSlidingTabStrip.setSelectedTextSize((int) DisplayMetricsUtils.sp2px(22.0f));
    }

    public static void setAllTabsValue(Context context, PagerSlidingTabStrip pagerSlidingTabStrip, boolean z, boolean z2) {
        if (PreferencesUtil.getInstance().getString("language_country").equals("US") || !z) {
            pagerSlidingTabStrip.setIsShowAllTabs(false);
        } else {
            pagerSlidingTabStrip.setIsShowAllTabs(true);
        }
        pagerSlidingTabStrip.setTextColor(context.getResources().getColor(R.color.text_light));
        pagerSlidingTabStrip.setSelectedTextColor(context.getResources().getColor(R.color.text_dark));
        pagerSlidingTabStrip.setTextSize((int) DisplayMetricsUtils.sp2px(14.0f));
        pagerSlidingTabStrip.setCustomIndicatorWith(false, 0);
        if (z2) {
            pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#FC4C7B"));
            pagerSlidingTabStrip.setIndicatorColor(ContextCompat.getColor(context, R.color.colorAccent));
            pagerSlidingTabStrip.setIndicatorHeight((int) DisplayMetricsUtils.dp2px(2.0f));
            pagerSlidingTabStrip.setCustomIndicatorWith(true, (int) DisplayMetricsUtils.dp2px(20.0f));
        } else {
            pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#00000000"));
            pagerSlidingTabStrip.setIndicatorColor(R.color.transparent);
            pagerSlidingTabStrip.setIndicatorHeight(0);
        }
        pagerSlidingTabStrip.setBoldText(true);
        pagerSlidingTabStrip.setSelectedTextSize((int) DisplayMetricsUtils.sp2px(18.0f));
    }

    public static void setExpandTabsValue(Context context, PagerSlidingTabStrip pagerSlidingTabStrip) {
        if (BaseApplication.getApplication().isChess()) {
            pagerSlidingTabStrip.setTextColor(Color.parseColor("#63638B"));
            pagerSlidingTabStrip.setSelectedTextColor(Color.parseColor("#A5A5DA"));
        } else {
            pagerSlidingTabStrip.setTextColor(context.getResources().getColor(R.color.text_middle));
            pagerSlidingTabStrip.setSelectedTextColor(context.getResources().getColor(R.color.colorAccent));
        }
        pagerSlidingTabStrip.setCustomIndicatorWith(true, 20);
        pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#e5e5e5"));
        pagerSlidingTabStrip.setBoldText(true);
    }

    public static void setGameTabValue(Context context, PagerSlidingTabStrip pagerSlidingTabStrip) {
        pagerSlidingTabStrip.setIsShowAllTabs(false);
        pagerSlidingTabStrip.setTextColor(context.getResources().getColor(R.color.text_light));
        pagerSlidingTabStrip.setSelectedTextColor(context.getResources().getColor(R.color.colorAccent));
        pagerSlidingTabStrip.setTextSize((int) DisplayMetricsUtils.sp2px(12.0f));
        pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#00000000"));
        pagerSlidingTabStrip.setIndicatorColor(ContextCompat.getColor(context, R.color.colorAccent));
        pagerSlidingTabStrip.setIndicatorHeight((int) DisplayMetricsUtils.dp2px(2.0f));
        pagerSlidingTabStrip.setCustomIndicatorWith(true, (int) DisplayMetricsUtils.dp2px(12.0f));
        pagerSlidingTabStrip.setSelectedTextSize((int) DisplayMetricsUtils.sp2px(12.0f));
    }

    public static void setPostRankAllTabsValue(Context context, PagerSlidingTabStrip pagerSlidingTabStrip, boolean z) {
        if (PreferencesUtil.getInstance().getString("language_country").equals("US") || !z) {
            pagerSlidingTabStrip.setIsShowAllTabs(false);
        } else {
            pagerSlidingTabStrip.setIsShowAllTabs(true);
        }
        pagerSlidingTabStrip.setTextColor(context.getResources().getColor(R.color.white));
        pagerSlidingTabStrip.setSelectedTextColor(context.getResources().getColor(R.color.white));
        pagerSlidingTabStrip.setCustomIndicatorWith(false, 0);
        pagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#00000000"));
        pagerSlidingTabStrip.setIndicatorColor(R.color.transparent);
        pagerSlidingTabStrip.setIndicatorHeight(0);
        pagerSlidingTabStrip.setBoldText(true);
        pagerSlidingTabStrip.setSelectedTextSize((int) DisplayMetricsUtils.sp2px(22.0f));
    }
}
