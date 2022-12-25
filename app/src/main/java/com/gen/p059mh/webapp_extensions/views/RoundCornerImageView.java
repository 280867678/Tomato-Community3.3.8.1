package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

/* renamed from: com.gen.mh.webapp_extensions.views.RoundCornerImageView */
/* loaded from: classes2.dex */
public class RoundCornerImageView extends AppCompatImageView {
    private float mRadius = 36.0f;
    private Path mClipPath = new Path();
    private RectF mRect = new RectF();

    public RoundCornerImageView(Context context) {
        super(context);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RoundCornerImageView setRadiusDp(float f) {
        this.mRadius = dp2px(f, getResources());
        postInvalidate();
        return this;
    }

    public RoundCornerImageView setRadiusPx(int i) {
        this.mRadius = i;
        postInvalidate();
        return this;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
        this.mClipPath.reset();
        Path path = this.mClipPath;
        RectF rectF = this.mRect;
        float f = this.mRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.mClipPath);
        super.onDraw(canvas);
    }

    private float dp2px(float f, Resources resources) {
        return TypedValue.applyDimension(1, f, resources.getDisplayMetrics());
    }
}
