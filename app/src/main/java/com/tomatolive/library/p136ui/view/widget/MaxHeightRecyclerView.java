package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.widget.MaxHeightRecyclerView */
/* loaded from: classes4.dex */
public class MaxHeightRecyclerView extends RecyclerView {
    private int mMaxHeight;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context, attributeSet);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize(context, attributeSet);
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MaxHeightRecyclerView);
        this.mMaxHeight = obtainStyledAttributes.getLayoutDimension(R$styleable.MaxHeightRecyclerView_maxHeight, this.mMaxHeight);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.RecyclerView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = this.mMaxHeight;
        if (i3 > 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }
}
