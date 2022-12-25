package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class BaseStaggerGridLayoutManager extends StaggeredGridLayoutManager {
    public BaseStaggerGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public BaseStaggerGridLayoutManager(Context context, int i, int i2) {
        super(i, i2);
    }

    @Override // android.support.p005v7.widget.StaggeredGridLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
    }
}
