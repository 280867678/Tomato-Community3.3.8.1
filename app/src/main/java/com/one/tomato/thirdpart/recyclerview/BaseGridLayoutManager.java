package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class BaseGridLayoutManager extends GridLayoutManager {
    public BaseGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public BaseGridLayoutManager(Context context, int i) {
        super(context, i);
    }

    @Override // android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
    }
}
