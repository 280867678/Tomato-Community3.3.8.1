package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* renamed from: com.tomatolive.library.ui.view.widget.WrapContentHeightViewPager */
/* loaded from: classes4.dex */
public class WrapContentHeightViewPager extends ViewPager {
    private int currentIndex;
    private int height = 0;
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap();

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.view.ViewPager, android.view.View
    public void onMeasure(int i, int i2) {
        int size = this.mChildrenViews.size();
        int i3 = this.currentIndex;
        if (size > i3) {
            View view = this.mChildrenViews.get(Integer.valueOf(i3));
            view.measure(i, View.MeasureSpec.makeMeasureSpec(0, 0));
            this.height = view.getMeasuredHeight();
        }
        if (this.mChildrenViews.size() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.height, 1073741824);
        }
        super.onMeasure(i, i2);
    }

    public void resetHeight(int i) {
        this.currentIndex = i;
        if (this.mChildrenViews.size() > i) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-1, this.height);
            } else {
                layoutParams.height = this.height;
            }
            setLayoutParams(layoutParams);
        }
    }

    public void setViewForPosition(View view, int i) {
        this.mChildrenViews.put(Integer.valueOf(i), view);
    }
}
