package com.p076mh.webappStart.util;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.mh.webappStart.util.CZViewUtil */
/* loaded from: classes3.dex */
public class CZViewUtil {
    public static void measureViewWrapWrap(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        view.setLayoutParams(layoutParams);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        Log.d("CZViewUtil", "measureView: w = " + makeMeasureSpec + ",h = " + makeMeasureSpec2);
        view.measure(makeMeasureSpec, makeMeasureSpec2);
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Log.e("CZViewUtil", "measureViewWrapWrap: mWidth = " + measuredWidth + ",mHeight = " + measuredHeight);
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        layoutParams2.width = measuredWidth;
        layoutParams2.height = measuredHeight;
        view.setLayoutParams(layoutParams2);
    }
}
