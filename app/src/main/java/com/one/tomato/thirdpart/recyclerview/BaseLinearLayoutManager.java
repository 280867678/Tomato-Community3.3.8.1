package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class BaseLinearLayoutManager extends LinearLayoutManager {
    public BaseLinearLayoutManager(Context context) {
        super(context);
    }

    public BaseLinearLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public BaseLinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.support.p005v7.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
    }
}
