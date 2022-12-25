package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.MaxHeightViewPager */
/* loaded from: classes4.dex */
public class MaxHeightViewPager extends ViewPager {
    public MaxHeightViewPager(Context context) {
        super(context);
    }

    public MaxHeightViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.view.ViewPager, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            childAt.measure(i, View.MeasureSpec.makeMeasureSpec(0, 0));
            if (childAt.getMeasuredHeight() > i3) {
                i3 = childAt.getMeasuredHeight();
            }
        }
        if (i3 > 0) {
            setMeasuredDimension(getMeasuredWidth(), i3);
        }
    }
}
