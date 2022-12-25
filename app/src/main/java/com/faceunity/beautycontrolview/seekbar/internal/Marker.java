package com.faceunity.beautycontrolview.seekbar.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.faceunity.beautycontrolview.R$attr;
import com.faceunity.beautycontrolview.R$style;
import com.faceunity.beautycontrolview.R$styleable;
import com.faceunity.beautycontrolview.seekbar.internal.compat.SeekBarCompat;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable;

/* loaded from: classes2.dex */
public class Marker extends ViewGroup implements MarkerDrawable.MarkerAnimationListener {
    MarkerDrawable mMarkerDrawable;
    private TextView mNumber;
    private int mSeparation;
    private int mWidth;

    public Marker(Context context, AttributeSet attributeSet, int i, String str, int i2, int i3) {
        super(context, attributeSet, i);
        setVisibility(0);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DiscreteSeekBar, R$attr.discreteSeekBarStyle, R$style.Widget_DiscreteSeekBar);
        int i4 = ((int) (displayMetrics.density * 4.0f)) * 2;
        int resourceId = obtainStyledAttributes.getResourceId(R$styleable.DiscreteSeekBar_dsb_indicatorTextAppearance, R$style.Widget_DiscreteIndicatorTextAppearance);
        this.mNumber = new TextView(context);
        this.mNumber.setPadding(i4, 0, i4, 0);
        this.mNumber.setTextAppearance(context, resourceId);
        this.mNumber.setGravity(17);
        this.mNumber.setText(str);
        this.mNumber.setMaxLines(1);
        this.mNumber.setSingleLine(true);
        SeekBarCompat.setTextDirection(this.mNumber, 5);
        this.mNumber.setVisibility(4);
        setPadding(i4, i4, i4, i4);
        resetSizes(str);
        this.mSeparation = i3;
        this.mMarkerDrawable = new MarkerDrawable(obtainStyledAttributes.getColorStateList(R$styleable.DiscreteSeekBar_dsb_indicatorColor), i2);
        this.mMarkerDrawable.setCallback(this);
        this.mMarkerDrawable.setMarkerListener(this);
        this.mMarkerDrawable.setExternalOffset(i4);
        ViewCompat.setElevation(this, obtainStyledAttributes.getDimension(R$styleable.DiscreteSeekBar_dsb_indicatorElevation, displayMetrics.density * 8.0f));
        if (Build.VERSION.SDK_INT >= 21) {
            SeekBarCompat.setOutlineProvider(this, this.mMarkerDrawable);
        }
        obtainStyledAttributes.recycle();
    }

    public void resetSizes(String str) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        TextView textView = this.mNumber;
        textView.setText("-" + str);
        this.mNumber.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE));
        this.mWidth = Math.max(this.mNumber.getMeasuredWidth(), this.mNumber.getMeasuredHeight());
        removeView(this.mNumber);
        TextView textView2 = this.mNumber;
        int i = this.mWidth;
        addView(textView2, new FrameLayout.LayoutParams(i, i, 51));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        this.mMarkerDrawable.draw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        int paddingLeft = this.mWidth + getPaddingLeft() + getPaddingRight();
        int paddingTop = this.mWidth + getPaddingTop() + getPaddingBottom();
        int i3 = this.mWidth;
        setMeasuredDimension(paddingLeft, paddingTop + (((int) ((i3 * 1.41f) - i3)) / 2) + this.mSeparation);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width = getWidth() - getPaddingRight();
        int height = getHeight() - getPaddingBottom();
        TextView textView = this.mNumber;
        int i5 = this.mWidth;
        textView.layout(paddingLeft, paddingTop, paddingLeft + i5, i5 + paddingTop);
        this.mMarkerDrawable.setBounds(paddingLeft, paddingTop, width, height);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mMarkerDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animateOpen();
    }

    public void setValue(CharSequence charSequence) {
        this.mNumber.setText(charSequence);
    }

    public CharSequence getValue() {
        return this.mNumber.getText();
    }

    public void animateOpen() {
        this.mMarkerDrawable.stop();
        this.mMarkerDrawable.animateToPressed();
    }

    public void animateClose() {
        this.mMarkerDrawable.stop();
        this.mNumber.setVisibility(4);
        this.mMarkerDrawable.animateToNormal();
    }

    @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable.MarkerAnimationListener
    public void onOpeningComplete() {
        this.mNumber.setVisibility(0);
        if (getParent() instanceof MarkerDrawable.MarkerAnimationListener) {
            ((MarkerDrawable.MarkerAnimationListener) getParent()).onOpeningComplete();
        }
    }

    @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable.MarkerAnimationListener
    public void onClosingComplete() {
        if (getParent() instanceof MarkerDrawable.MarkerAnimationListener) {
            ((MarkerDrawable.MarkerAnimationListener) getParent()).onClosingComplete();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mMarkerDrawable.stop();
    }

    public void setColors(int i, int i2) {
        this.mMarkerDrawable.setColors(i, i2);
    }
}
