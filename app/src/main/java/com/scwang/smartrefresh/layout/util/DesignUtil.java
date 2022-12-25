package com.scwang.smartrefresh.layout.util;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.listener.CoordinatorLayoutListener;

/* loaded from: classes3.dex */
public class DesignUtil {
    public static void checkCoordinatorLayout(View view, RefreshKernel refreshKernel, CoordinatorLayoutListener coordinatorLayoutListener) {
        try {
            if (!(view instanceof CoordinatorLayout)) {
                return;
            }
            refreshKernel.getRefreshLayout().setEnableNestedScroll(false);
            wrapperCoordinatorLayout((ViewGroup) view, coordinatorLayoutListener);
        } catch (Throwable unused) {
        }
    }

    private static void wrapperCoordinatorLayout(ViewGroup viewGroup, final CoordinatorLayoutListener coordinatorLayoutListener) {
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt instanceof AppBarLayout) {
                ((AppBarLayout) childAt).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.scwang.smartrefresh.layout.util.DesignUtil.1
                    @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
                    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                        CoordinatorLayoutListener coordinatorLayoutListener2 = CoordinatorLayoutListener.this;
                        boolean z = true;
                        boolean z2 = i >= 0;
                        if (appBarLayout.getTotalScrollRange() + i > 0) {
                            z = false;
                        }
                        coordinatorLayoutListener2.onCoordinatorUpdate(z2, z);
                    }
                });
            }
        }
    }
}
