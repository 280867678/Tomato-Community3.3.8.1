package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.RoundedRectangleImageView */
/* loaded from: classes4.dex */
public class RoundedRectangleImageView extends AppCompatImageView {
    private float mRadius;
    private RectF mRectF;
    private Path mRoundedRectPath;

    public RoundedRectangleImageView(Context context) {
        super(context);
        init(context);
    }

    public RoundedRectangleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public RoundedRectangleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mRadius = context.getResources().getDisplayMetrics().density * 2.0f;
        this.mRoundedRectPath = new Path();
        this.mRectF = new RectF();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
        Path path = this.mRoundedRectPath;
        RectF rectF = this.mRectF;
        float f = this.mRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(this.mRoundedRectPath);
        super.onDraw(canvas);
    }
}
