package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import java.text.DecimalFormat;

/* loaded from: classes3.dex */
public class CircleProgressBar extends View {
    private DecimalFormat decimalFormat;
    private Paint mArcPaint;
    private RectF mArcRectF;
    private int mCircleBackground;
    private Paint mCirclePaint;
    private int mCircleX;
    private int mCircleY;
    private float mCurrentAngle;
    private float mCurrentPercent;
    private int mRadius;
    private int mRingColor;
    private float mStartSweepValue;
    private int mTextColor;
    private Paint mTextPaint;
    private int mTextSize;
    private int roundColor;
    private Paint roundColorPaint;
    private boolean showText;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CircleProgressBar);
        this.showText = obtainStyledAttributes.getBoolean(4, false);
        this.mCircleBackground = obtainStyledAttributes.getColor(0, context.getResources().getColor(R.color.transparent));
        this.roundColor = obtainStyledAttributes.getColor(1, context.getResources().getColor(R.color.color_666666));
        this.mRingColor = obtainStyledAttributes.getColor(3, context.getResources().getColor(R.color.white));
        this.mRadius = obtainStyledAttributes.getInt(2, 50);
        this.mTextColor = obtainStyledAttributes.getColor(5, context.getResources().getColor(R.color.white));
        obtainStyledAttributes.recycle();
        init(context);
        this.decimalFormat = new DecimalFormat("#");
    }

    private void init(Context context) {
        this.mStartSweepValue = -90.0f;
        this.mCurrentAngle = 0.0f;
        this.mCurrentPercent = 0.0f;
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setColor(this.mCircleBackground);
        this.mCirclePaint.setStyle(Paint.Style.FILL);
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setStrokeWidth((float) (this.mRadius * 0.025d));
        this.mTextPaint.setTextSize(this.mRadius / 2);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mArcPaint = new Paint();
        this.roundColorPaint = new Paint();
        this.roundColorPaint.setColor(this.roundColor);
        this.mArcPaint.setAntiAlias(true);
        this.mArcPaint.setColor(this.mRingColor);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth((float) (this.mRadius * 0.075d));
        this.mTextSize = (int) this.mTextPaint.getTextSize();
        int i = this.mCircleX;
        int i2 = this.mRadius;
        int i3 = this.mCircleY;
        this.mArcRectF = new RectF(i - i2, i3 - i2, i + i2, i3 + i2);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(measure(i), measure(i));
        this.mCircleX = getMeasuredWidth() / 2;
        this.mCircleY = getMeasuredHeight() / 2;
        int i3 = this.mRadius;
        int i4 = this.mCircleX;
        if (i3 > i4) {
            this.mRadius = i4;
            this.mRadius = (int) (i4 - (this.mRadius * 0.075d));
            this.mTextPaint.setStrokeWidth((float) (this.mRadius * 0.025d));
            this.mTextPaint.setTextSize(this.mRadius / 2);
            this.mArcPaint.setStrokeWidth((float) (this.mRadius * 0.075d));
            this.mTextSize = (int) this.mTextPaint.getTextSize();
        }
        int i5 = this.mCircleX;
        int i6 = this.mRadius;
        int i7 = this.mCircleY;
        this.mArcRectF = new RectF(i5 - i6, i7 - i6, i5 + i6, i7 + i6);
    }

    private int measure(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int i2 = (int) (this.mRadius * 1.075d * 2.0d);
        return mode == Integer.MIN_VALUE ? Math.min(i2, size) : i2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(this.mCircleX, this.mCircleY, this.mRadius, this.mCirclePaint);
        this.roundColorPaint.setStyle(Paint.Style.STROKE);
        this.roundColorPaint.setStrokeWidth((float) (this.mRadius * 0.075d));
        canvas.drawCircle(this.mCircleX, this.mCircleY, this.mRadius, this.roundColorPaint);
        canvas.drawArc(this.mArcRectF, this.mStartSweepValue, this.mCurrentAngle, false, this.mArcPaint);
        if (this.showText) {
            canvas.drawText(this.decimalFormat.format(this.mCurrentPercent) + "%", this.mCircleX, this.mCircleY + (this.mTextSize / 4), this.mTextPaint);
        }
    }

    public void setCurrentPercent(long j, long j2) {
        if (j2 <= j && j > 0) {
            this.mCurrentPercent = (((float) j2) * 100.0f) / ((float) j);
            this.mCurrentAngle = (this.mCurrentPercent * 360.0f) / 100.0f;
            invalidate();
            return;
        }
        this.mCurrentPercent = 0.0f;
        this.mCurrentAngle = 0.0f;
        invalidate();
    }

    public String getCurrentPercent() {
        return this.decimalFormat.format(this.mCurrentPercent) + "%";
    }
}
