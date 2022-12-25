package com.tomatolive.library.p136ui.view.sticker.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RadioButton;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGColorRadio */
/* loaded from: classes3.dex */
public class IMGColorRadio extends RadioButton implements ValueAnimator.AnimatorUpdateListener {
    private static final float RADIUS_BALL = 0.72f;
    private static final float RADIUS_BASE = 0.6f;
    private static final float RADIUS_RING = 0.9f;
    private static final String TAG = "IMGColorRadio";
    private ValueAnimator mAnimator;
    private int mColor;
    private Paint mPaint;
    private float mRadiusRatio;
    private int mStrokeColor;

    public IMGColorRadio(Context context) {
        this(context, null, 0);
    }

    public IMGColorRadio(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColor = -1;
        this.mStrokeColor = -1;
        this.mRadiusRatio = 0.0f;
        this.mPaint = new Paint(1);
        initialize(context, attributeSet, 0);
    }

    public IMGColorRadio(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColor = -1;
        this.mStrokeColor = -1;
        this.mRadiusRatio = 0.0f;
        this.mPaint = new Paint(1);
        initialize(context, attributeSet, i);
    }

    private void initialize(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.IMGColorRadio);
        this.mColor = obtainStyledAttributes.getColor(R$styleable.IMGColorRadio_image_color, -1);
        this.mStrokeColor = obtainStyledAttributes.getColor(R$styleable.IMGColorRadio_image_stroke_color, -1);
        obtainStyledAttributes.recycle();
        setButtonDrawable((Drawable) null);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStrokeWidth(5.0f);
    }

    private ValueAnimator getAnimator() {
        if (this.mAnimator == null) {
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.addUpdateListener(this);
            this.mAnimator.setDuration(200L);
            this.mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        return this.mAnimator;
    }

    public void setColor(int i) {
        this.mColor = i;
        this.mPaint.setColor(this.mColor);
    }

    public int getColor() {
        return this.mColor;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        float min = Math.min(width, height);
        canvas.save();
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width, height, getBallRadius(min), this.mPaint);
        this.mPaint.setColor(this.mStrokeColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width, height, getRingRadius(min), this.mPaint);
        canvas.restore();
    }

    private float getBallRadius(float f) {
        return f * ((this.mRadiusRatio * 0.120000005f) + RADIUS_BASE);
    }

    private float getRingRadius(float f) {
        return f * ((this.mRadiusRatio * 0.29999995f) + RADIUS_BASE);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        boolean z2 = z != isChecked();
        super.setChecked(z);
        if (z2) {
            ValueAnimator animator = getAnimator();
            if (z) {
                animator.start();
            } else {
                animator.reverse();
            }
        }
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mRadiusRatio = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }
}
