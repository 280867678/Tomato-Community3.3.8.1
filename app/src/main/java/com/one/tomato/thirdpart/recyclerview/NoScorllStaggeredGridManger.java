package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class NoScorllStaggeredGridManger extends StaggeredGridLayoutManager {
    @Override // android.support.p005v7.widget.StaggeredGridLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override // android.support.p005v7.widget.StaggeredGridLayoutManager, android.support.p005v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return false;
    }

    public NoScorllStaggeredGridManger(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
