package com.faceunity.beautycontrolview.seekbar.internal.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public abstract class StateDrawable extends Drawable {
    private int mCurrentColor;
    private ColorStateList mTintStateList;
    private int mAlpha = 255;
    private final Paint mPaint = new Paint(1);

    abstract void doDraw(Canvas canvas, Paint paint);

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public StateDrawable(@NonNull ColorStateList colorStateList) {
        setColorStateList(colorStateList);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mTintStateList.isStateful() || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        return updateTint(iArr) || super.setState(iArr);
    }

    private boolean updateTint(int[] iArr) {
        int colorForState = this.mTintStateList.getColorForState(iArr, this.mCurrentColor);
        if (colorForState != this.mCurrentColor) {
            this.mCurrentColor = colorForState;
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mPaint.setColor(this.mCurrentColor);
        this.mPaint.setAlpha(modulateAlpha(Color.alpha(this.mCurrentColor)));
        doDraw(canvas, this.mPaint);
    }

    public void setColorStateList(@NonNull ColorStateList colorStateList) {
        this.mTintStateList = colorStateList;
        this.mCurrentColor = colorStateList.getDefaultColor();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAlpha = i;
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int modulateAlpha(int i) {
        int i2 = this.mAlpha;
        return (i * (i2 + (i2 >> 7))) >> 8;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
