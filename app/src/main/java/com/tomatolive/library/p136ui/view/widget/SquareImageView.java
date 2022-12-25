package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/* renamed from: com.tomatolive.library.ui.view.widget.SquareImageView */
/* loaded from: classes4.dex */
public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
