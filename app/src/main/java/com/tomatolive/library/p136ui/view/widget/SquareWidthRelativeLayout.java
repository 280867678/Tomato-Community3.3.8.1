package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/* renamed from: com.tomatolive.library.ui.view.widget.SquareWidthRelativeLayout */
/* loaded from: classes4.dex */
public class SquareWidthRelativeLayout extends RelativeLayout {
    public SquareWidthRelativeLayout(Context context) {
        this(context, null);
    }

    public SquareWidthRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SquareWidthRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
    }
}
