package com.one.tomato.widget.mzbanner;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes3.dex */
public class CustomViewPager extends ViewPager {
    private ArrayList<Integer> childCenterXAbs = new ArrayList<>();
    private SparseArray<Integer> childIndex = new SparseArray<>();

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setClipToPadding(false);
        setOverScrollMode(2);
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (i2 == 0 || this.childIndex.size() != i) {
            this.childCenterXAbs.clear();
            this.childIndex.clear();
            int viewCenterX = getViewCenterX(this);
            for (int i3 = 0; i3 < i; i3++) {
                int abs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i3)));
                if (this.childIndex.get(abs) != null) {
                    abs++;
                }
                this.childCenterXAbs.add(Integer.valueOf(abs));
                this.childIndex.append(abs, Integer.valueOf(i3));
            }
            Collections.sort(this.childCenterXAbs);
        }
        return this.childIndex.get(this.childCenterXAbs.get((i - 1) - i2).intValue()).intValue();
    }

    private int getViewCenterX(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[0] + (view.getWidth() / 2);
    }
}
