package com.faceunity.beautycontrolview.seekbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.graphics.drawable.DrawableCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p005v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.faceunity.beautycontrolview.R$attr;
import com.faceunity.beautycontrolview.R$style;
import com.faceunity.beautycontrolview.R$styleable;
import com.faceunity.beautycontrolview.seekbar.internal.PopupIndicator;
import com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat;
import com.faceunity.beautycontrolview.seekbar.internal.compat.SeekBarCompat;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.ThumbDrawable;
import com.faceunity.beautycontrolview.seekbar.internal.drawable.TrackRectDrawable;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DiscreteSeekBar extends View {
    private static final boolean isLollipopOrGreater;
    private final int mAddedTouchBounds;
    private boolean mAllowTrackClick;
    private float mAnimationPosition;
    private int mAnimationTarget;
    private float mDownX;
    private int mDragOffset;
    private MarkerDrawable.MarkerAnimationListener mFloaterListener;
    private StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private PopupIndicator mIndicator;
    private String mIndicatorFormatter;
    private boolean mIndicatorPopupEnabled;
    private Rect mInvalidateRect;
    private boolean mIsDragging;
    private int mKeyProgressIncrement;
    private int mMax;
    private int mMin;
    private boolean mMirrorForRtl;
    private NumericTransformer mNumericTransformer;
    private AnimatorCompat mPositionAnimator;
    private OnProgressChangeListener mPublicChangeListener;
    private Drawable mRipple;
    private TrackRectDrawable mScrubber;
    private int mScrubberHeight;
    private Runnable mShowIndicatorRunnable;
    private Rect mTempRect;
    private ThumbDrawable mThumb;
    private float mTouchSlop;
    private TrackRectDrawable mTrack;
    private int mTrackHeight;
    private int mValue;
    private int mValueBase;

    /* loaded from: classes2.dex */
    public interface OnProgressChangeListener {
        void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean z);

        void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar);

        void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar);
    }

    protected void onHideBubble() {
    }

    protected void onShowBubble() {
    }

    protected void onValueChanged(int i) {
    }

    static {
        isLollipopOrGreater = Build.VERSION.SDK_INT >= 21;
    }

    /* loaded from: classes2.dex */
    public static abstract class NumericTransformer {
        public abstract int transform(int i);

        public boolean useStringTransform() {
            return false;
        }

        public String transformToString(int i) {
            return String.valueOf(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class DefaultNumericTransformer extends NumericTransformer {
        @Override // com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.NumericTransformer
        public int transform(int i) {
            return i;
        }

        private DefaultNumericTransformer() {
        }
    }

    public DiscreteSeekBar(Context context) {
        this(context, null);
    }

    public DiscreteSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.discreteSeekBarStyle);
    }

    public DiscreteSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        this.mKeyProgressIncrement = 1;
        this.mMirrorForRtl = false;
        this.mAllowTrackClick = true;
        this.mIndicatorPopupEnabled = true;
        this.mInvalidateRect = new Rect();
        this.mTempRect = new Rect();
        this.mShowIndicatorRunnable = new Runnable() { // from class: com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.2
            @Override // java.lang.Runnable
            public void run() {
                DiscreteSeekBar.this.showFloater();
            }
        };
        this.mFloaterListener = new MarkerDrawable.MarkerAnimationListener() { // from class: com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.3
            @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable.MarkerAnimationListener
            public void onOpeningComplete() {
            }

            @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.MarkerDrawable.MarkerAnimationListener
            public void onClosingComplete() {
                DiscreteSeekBar.this.mThumb.animateToNormal();
            }
        };
        setFocusable(true);
        setWillNotDraw(false);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        float f = context.getResources().getDisplayMetrics().density;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DiscreteSeekBar, i, R$style.Widget_DiscreteSeekBar);
        this.mMirrorForRtl = obtainStyledAttributes.getBoolean(R$styleable.DiscreteSeekBar_dsb_mirrorForRtl, this.mMirrorForRtl);
        this.mAllowTrackClick = obtainStyledAttributes.getBoolean(R$styleable.DiscreteSeekBar_dsb_allowTrackClickToDrag, this.mAllowTrackClick);
        this.mIndicatorPopupEnabled = obtainStyledAttributes.getBoolean(R$styleable.DiscreteSeekBar_dsb_indicatorPopupEnabled, this.mIndicatorPopupEnabled);
        this.mTrackHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.DiscreteSeekBar_dsb_trackHeight, (int) (1.0f * f));
        this.mScrubberHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.DiscreteSeekBar_dsb_scrubberHeight, (int) (4.0f * f));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.DiscreteSeekBar_dsb_thumbSize, (int) (12.0f * f));
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R$styleable.DiscreteSeekBar_dsb_indicatorSeparation, (int) (5.0f * f));
        this.mAddedTouchBounds = Math.max(0, (((int) (f * 32.0f)) - dimensionPixelSize) / 2);
        int i3 = R$styleable.DiscreteSeekBar_dsb_max;
        int i4 = R$styleable.DiscreteSeekBar_dsb_min;
        int i5 = R$styleable.DiscreteSeekBar_dsb_value;
        TypedValue typedValue = new TypedValue();
        int i6 = 100;
        if (obtainStyledAttributes.getValue(i3, typedValue)) {
            if (typedValue.type == 5) {
                i6 = obtainStyledAttributes.getDimensionPixelSize(i3, 100);
            } else {
                i6 = obtainStyledAttributes.getInteger(i3, 100);
            }
        }
        if (!obtainStyledAttributes.getValue(i4, typedValue)) {
            i2 = 0;
        } else if (typedValue.type == 5) {
            i2 = obtainStyledAttributes.getDimensionPixelSize(i4, 0);
        } else {
            i2 = obtainStyledAttributes.getInteger(i4, 0);
        }
        if (obtainStyledAttributes.getValue(i5, typedValue)) {
            if (typedValue.type == 5) {
                this.mValueBase = obtainStyledAttributes.getDimensionPixelSize(i5, this.mValueBase);
            } else {
                this.mValueBase = obtainStyledAttributes.getInteger(i5, this.mValueBase);
            }
        }
        this.mMin = i2;
        this.mMax = Math.max(i2 + 1, i6);
        this.mValue = Math.max(i2, Math.min(i6, this.mValueBase));
        updateKeyboardRange();
        this.mIndicatorFormatter = obtainStyledAttributes.getString(R$styleable.DiscreteSeekBar_dsb_indicatorFormatter);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R$styleable.DiscreteSeekBar_dsb_trackColor);
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(R$styleable.DiscreteSeekBar_dsb_progressColor);
        ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(R$styleable.DiscreteSeekBar_dsb_rippleColor);
        boolean isInEditMode = isInEditMode();
        colorStateList3 = (isInEditMode || colorStateList3 == null) ? new ColorStateList(new int[][]{new int[0]}, new int[]{-12303292}) : colorStateList3;
        colorStateList = (isInEditMode || colorStateList == null) ? new ColorStateList(new int[][]{new int[0]}, new int[]{-7829368}) : colorStateList;
        colorStateList2 = (isInEditMode || colorStateList2 == null) ? new ColorStateList(new int[][]{new int[0]}, new int[]{-16738680}) : colorStateList2;
        this.mRipple = SeekBarCompat.getRipple(colorStateList3);
        if (isLollipopOrGreater) {
            SeekBarCompat.setBackground(this, this.mRipple);
        } else {
            this.mRipple.setCallback(this);
        }
        this.mTrack = new TrackRectDrawable(colorStateList);
        this.mTrack.setCallback(this);
        this.mScrubber = new TrackRectDrawable(colorStateList2);
        this.mScrubber.setCallback(this);
        this.mThumb = new ThumbDrawable(colorStateList2, dimensionPixelSize);
        this.mThumb.setCallback(this);
        ThumbDrawable thumbDrawable = this.mThumb;
        thumbDrawable.setBounds(0, 0, thumbDrawable.getIntrinsicWidth(), this.mThumb.getIntrinsicHeight());
        if (!isInEditMode) {
            this.mIndicator = new PopupIndicator(context, attributeSet, i, convertValueToMessage(this.mMax), dimensionPixelSize, this.mAddedTouchBounds + dimensionPixelSize + dimensionPixelSize2);
            this.mIndicator.setListener(this.mFloaterListener);
        }
        obtainStyledAttributes.recycle();
        setNumericTransformer(new DefaultNumericTransformer());
    }

    public void setIndicatorFormatter(@Nullable String str) {
        this.mIndicatorFormatter = str;
        updateProgressMessage(this.mValue);
    }

    public void setNumericTransformer(@Nullable NumericTransformer numericTransformer) {
        if (numericTransformer == null) {
            numericTransformer = new DefaultNumericTransformer();
        }
        this.mNumericTransformer = numericTransformer;
        updateIndicatorSizes();
        updateProgressMessage(this.mValue);
    }

    public NumericTransformer getNumericTransformer() {
        return this.mNumericTransformer;
    }

    public void setMax(int i) {
        if (this.mMax == i) {
            return;
        }
        this.mMax = i;
        int i2 = this.mMax;
        if (i2 < this.mMin) {
            setMin(i2 - 1);
        }
        updateKeyboardRange();
        updateIndicatorSizes();
    }

    public int getMax() {
        return this.mMax;
    }

    public void setMin(int i) {
        if (this.mMin == i) {
            return;
        }
        this.mMin = i;
        int i2 = this.mMin;
        if (i2 > this.mMax) {
            setMax(i2 + 1);
        }
        updateKeyboardRange();
    }

    public int getMin() {
        return this.mMin;
    }

    public void setProgress(int i) {
        setProgress(i, false);
    }

    private void setProgress(int i, boolean z) {
        int max = Math.max(this.mMin, Math.min(this.mMax, i));
        if (isAnimationRunning()) {
            this.mPositionAnimator.cancel();
        }
        this.mValue = max;
        notifyProgress(max, z);
        updateProgressMessage(max);
        updateThumbPosFromCurrentProgress();
    }

    public int getProgress() {
        return this.mValue;
    }

    public void setOnProgressChangeListener(@Nullable OnProgressChangeListener onProgressChangeListener) {
        this.mPublicChangeListener = onProgressChangeListener;
    }

    public void setThumbColor(int i, int i2) {
        this.mThumb.setColorStateList(ColorStateList.valueOf(i));
        this.mIndicator.setColors(i2, i);
    }

    public void setThumbColor(@NonNull ColorStateList colorStateList, int i) {
        this.mThumb.setColorStateList(colorStateList);
        this.mIndicator.setColors(i, colorStateList.getColorForState(new int[]{16842919}, colorStateList.getDefaultColor()));
    }

    public void setScrubberColor(int i) {
        this.mScrubber.setColorStateList(ColorStateList.valueOf(i));
    }

    public void setScrubberColor(@NonNull ColorStateList colorStateList) {
        this.mScrubber.setColorStateList(colorStateList);
    }

    public void setRippleColor(int i) {
        setRippleColor(new ColorStateList(new int[][]{new int[0]}, new int[]{i}));
    }

    public void setRippleColor(@NonNull ColorStateList colorStateList) {
        SeekBarCompat.setRippleColor(this.mRipple, colorStateList);
    }

    public void setTrackColor(int i) {
        this.mTrack.setColorStateList(ColorStateList.valueOf(i));
    }

    public void setTrackColor(@NonNull ColorStateList colorStateList) {
        this.mTrack.setColorStateList(colorStateList);
    }

    public void setIndicatorPopupEnabled(boolean z) {
        this.mIndicatorPopupEnabled = z;
    }

    private void updateIndicatorSizes() {
        if (!isInEditMode()) {
            if (this.mNumericTransformer.useStringTransform()) {
                this.mIndicator.updateSizes(this.mNumericTransformer.transformToString(this.mMax));
                return;
            }
            PopupIndicator popupIndicator = this.mIndicator;
            NumericTransformer numericTransformer = this.mNumericTransformer;
            int i = this.mMax;
            numericTransformer.transform(i);
            popupIndicator.updateSizes(convertValueToMessage(i));
        }
    }

    private void notifyProgress(int i, boolean z) {
        OnProgressChangeListener onProgressChangeListener = this.mPublicChangeListener;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onProgressChanged(this, i, z);
        }
        onValueChanged(i);
    }

    private void notifyBubble(boolean z) {
        if (z) {
            onShowBubble();
        } else {
            onHideBubble();
        }
    }

    private void updateKeyboardRange() {
        int i = this.mMax - this.mMin;
        int i2 = this.mKeyProgressIncrement;
        if (i2 == 0 || i / i2 > 20) {
            this.mKeyProgressIncrement = Math.max(1, Math.round(i / 20.0f));
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(View.MeasureSpec.getSize(i), this.mThumb.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom() + (this.mAddedTouchBounds * 2));
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            removeCallbacks(this.mShowIndicatorRunnable);
            if (!isInEditMode()) {
                this.mIndicator.dismissComplete();
            }
            updateFromDrawableState();
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        super.scheduleDrawable(drawable, runnable, j);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int intrinsicWidth = this.mThumb.getIntrinsicWidth();
        int intrinsicHeight = this.mThumb.getIntrinsicHeight();
        int i5 = this.mAddedTouchBounds;
        int i6 = intrinsicWidth / 2;
        int paddingLeft = getPaddingLeft() + i5;
        int paddingRight = getPaddingRight();
        int height = (getHeight() - getPaddingBottom()) - i5;
        this.mThumb.setBounds(paddingLeft, height - intrinsicHeight, intrinsicWidth + paddingLeft, height);
        int max = Math.max(this.mTrackHeight / 2, 1);
        int i7 = paddingLeft + i6;
        int i8 = height - i6;
        this.mTrack.setBounds(i7, i8 - max, ((getWidth() - i6) - paddingRight) - i5, max + i8);
        int max2 = Math.max(this.mScrubberHeight / 2, 2);
        this.mScrubber.setBounds(i7, i8 - max2, i7, i8 + max2);
        updateThumbPosFromCurrentProgress();
    }

    @Override // android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        if (!isLollipopOrGreater) {
            this.mRipple.draw(canvas);
        }
        super.onDraw(canvas);
        this.mTrack.draw(canvas);
        this.mScrubber.draw(canvas);
        this.mThumb.draw(canvas);
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateFromDrawableState();
    }

    private void updateFromDrawableState() {
        int[] drawableState = getDrawableState();
        boolean z = false;
        boolean z2 = false;
        for (int i : drawableState) {
            if (i == 16842908) {
                z = true;
            } else if (i == 16842919) {
                z2 = true;
            }
        }
        if (isEnabled() && ((z || z2) && this.mIndicatorPopupEnabled)) {
            removeCallbacks(this.mShowIndicatorRunnable);
            postDelayed(this.mShowIndicatorRunnable, 150L);
        } else {
            hideFloater();
        }
        this.mThumb.setState(drawableState);
        this.mTrack.setState(drawableState);
        this.mScrubber.setState(drawableState);
        this.mRipple.setState(drawableState);
    }

    private void updateProgressMessage(int i) {
        if (!isInEditMode()) {
            if (this.mNumericTransformer.useStringTransform()) {
                this.mIndicator.setValue(this.mNumericTransformer.transformToString(i));
                return;
            }
            PopupIndicator popupIndicator = this.mIndicator;
            this.mNumericTransformer.transform(i);
            popupIndicator.setValue(convertValueToMessage(i));
        }
    }

    private String convertValueToMessage(int i) {
        String str = this.mIndicatorFormatter;
        if (str == null) {
            str = "%d";
        }
        Formatter formatter = this.mFormatter;
        if (formatter == null || !formatter.locale().equals(Locale.getDefault())) {
            int length = str.length() + String.valueOf(this.mMax).length();
            StringBuilder sb = this.mFormatBuilder;
            if (sb == null) {
                this.mFormatBuilder = new StringBuilder(length);
            } else {
                sb.ensureCapacity(length);
            }
            this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        } else {
            this.mFormatBuilder.setLength(0);
        }
        return this.mFormatter.format(str, Integer.valueOf(i)).toString();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mDownX = motionEvent.getX();
            startDragging(motionEvent, isInScrollingContainer());
        } else if (action == 1) {
            if (!isDragging() && this.mAllowTrackClick) {
                startDragging(motionEvent, false);
                updateDragging(motionEvent);
            }
            stopDragging();
        } else if (action != 2) {
            if (action == 3) {
                stopDragging();
            }
        } else if (isDragging()) {
            updateDragging(motionEvent);
        } else if (Math.abs(motionEvent.getX() - this.mDownX) > this.mTouchSlop) {
            startDragging(motionEvent, false);
        }
        return true;
    }

    private boolean isInScrollingContainer() {
        return SeekBarCompat.isInScrollingContainer(getParent());
    }

    private boolean startDragging(MotionEvent motionEvent, boolean z) {
        Rect rect = this.mTempRect;
        this.mThumb.copyBounds(rect);
        int i = this.mAddedTouchBounds;
        rect.inset(-i, -i);
        this.mIsDragging = rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        if (!this.mIsDragging && this.mAllowTrackClick && !z) {
            this.mIsDragging = true;
            this.mDragOffset = (rect.width() / 2) - this.mAddedTouchBounds;
            updateDragging(motionEvent);
            this.mThumb.copyBounds(rect);
            int i2 = this.mAddedTouchBounds;
            rect.inset(-i2, -i2);
        }
        if (this.mIsDragging) {
            setPressed(true);
            attemptClaimDrag();
            setHotspot(motionEvent.getX(), motionEvent.getY());
            this.mDragOffset = (int) ((motionEvent.getX() - rect.left) - this.mAddedTouchBounds);
            OnProgressChangeListener onProgressChangeListener = this.mPublicChangeListener;
            if (onProgressChangeListener != null) {
                onProgressChangeListener.onStartTrackingTouch(this);
            }
        }
        return this.mIsDragging;
    }

    private boolean isDragging() {
        return this.mIsDragging;
    }

    private void stopDragging() {
        OnProgressChangeListener onProgressChangeListener = this.mPublicChangeListener;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onStopTrackingTouch(this);
        }
        this.mIsDragging = false;
        setPressed(false);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z;
        if (isEnabled()) {
            int animatedProgress = getAnimatedProgress();
            if (i != 21) {
                if (i == 22) {
                    if (animatedProgress < this.mMax) {
                        animateSetProgress(animatedProgress + this.mKeyProgressIncrement);
                    }
                }
            } else if (animatedProgress > this.mMin) {
                animateSetProgress(animatedProgress - this.mKeyProgressIncrement);
            }
            z = true;
            return !z || super.onKeyDown(i, keyEvent);
        }
        z = false;
        if (!z) {
        }
    }

    private int getAnimatedProgress() {
        return isAnimationRunning() ? getAnimationTarget() : this.mValue;
    }

    boolean isAnimationRunning() {
        AnimatorCompat animatorCompat = this.mPositionAnimator;
        return animatorCompat != null && animatorCompat.isRunning();
    }

    void animateSetProgress(int i) {
        float animationPosition = isAnimationRunning() ? getAnimationPosition() : getProgress();
        int i2 = this.mMin;
        if (i >= i2 && i <= (i2 = this.mMax)) {
            i2 = i;
        }
        AnimatorCompat animatorCompat = this.mPositionAnimator;
        if (animatorCompat != null) {
            animatorCompat.cancel();
        }
        this.mAnimationTarget = i2;
        this.mPositionAnimator = AnimatorCompat.create(animationPosition, i2, new AnimatorCompat.AnimationFrameUpdateListener() { // from class: com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.1
            @Override // com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat.AnimationFrameUpdateListener
            public void onAnimationFrame(float f) {
                DiscreteSeekBar.this.setAnimationPosition(f);
            }
        });
        this.mPositionAnimator.setDuration(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.mPositionAnimator.start();
    }

    private int getAnimationTarget() {
        return this.mAnimationTarget;
    }

    void setAnimationPosition(float f) {
        this.mAnimationPosition = f;
        int i = this.mMin;
        updateProgressFromAnimation((f - i) / (this.mMax - i));
    }

    float getAnimationPosition() {
        return this.mAnimationPosition;
    }

    private void updateDragging(MotionEvent motionEvent) {
        setHotspot(motionEvent.getX(), motionEvent.getY());
        int width = this.mThumb.getBounds().width() / 2;
        int i = this.mAddedTouchBounds;
        int x = (((int) motionEvent.getX()) - this.mDragOffset) + width;
        int paddingLeft = getPaddingLeft() + width + i;
        int width2 = getWidth() - ((getPaddingRight() + width) + i);
        if (x < paddingLeft) {
            x = paddingLeft;
        } else if (x > width2) {
            x = width2;
        }
        float f = (x - paddingLeft) / (width2 - paddingLeft);
        if (isRtl()) {
            f = 1.0f - f;
        }
        int i2 = this.mMax;
        int i3 = this.mMin;
        setProgress(Math.round((f * (i2 - i3)) + i3), true);
    }

    private void updateProgressFromAnimation(float f) {
        int i;
        int width = this.mThumb.getBounds().width() / 2;
        int i2 = this.mAddedTouchBounds;
        int width2 = (getWidth() - ((getPaddingRight() + width) + i2)) - ((getPaddingLeft() + width) + i2);
        int i3 = this.mMax;
        int round = Math.round(((i3 - i) * f) + this.mMin);
        if (round != getProgress()) {
            this.mValue = round;
            notifyProgress(this.mValue, true);
            updateProgressMessage(round);
        }
        float f2 = width2;
        int i4 = this.mValueBase;
        int i5 = this.mMin;
        updateThumbPos((int) ((((i4 - i5) / (this.mMax - i5)) * f2) + 0.5f), (int) ((f * f2) + 0.5f));
    }

    private void updateThumbPosFromCurrentProgress() {
        int intrinsicWidth = this.mThumb.getIntrinsicWidth();
        int i = this.mAddedTouchBounds;
        int i2 = intrinsicWidth / 2;
        int width = (getWidth() - ((getPaddingRight() + i2) + i)) - ((getPaddingLeft() + i2) + i);
        int i3 = this.mValue;
        int i4 = this.mMin;
        int i5 = this.mMax;
        float f = (i3 - i4) / (i5 - i4);
        float f2 = (this.mValueBase - i4) / (i5 - i4);
        float f3 = width;
        updateThumbPos((int) ((f2 * f3) + 0.5f), (int) ((f * f3) + 0.5f));
    }

    private void updateThumbPos(int i, int i2) {
        int paddingLeft = i + getPaddingLeft() + this.mAddedTouchBounds;
        int paddingLeft2 = i2 + getPaddingLeft() + this.mAddedTouchBounds;
        int min = Math.min(paddingLeft, paddingLeft2);
        int max = Math.max(paddingLeft, paddingLeft2);
        int intrinsicWidth = this.mThumb.getIntrinsicWidth();
        int i3 = intrinsicWidth / 2;
        this.mThumb.copyBounds(this.mInvalidateRect);
        ThumbDrawable thumbDrawable = this.mThumb;
        Rect rect = this.mInvalidateRect;
        thumbDrawable.setBounds(paddingLeft2, rect.top, intrinsicWidth + paddingLeft2, rect.bottom);
        this.mScrubber.getBounds().left = min + i3;
        this.mScrubber.getBounds().right = max + i3;
        Rect rect2 = this.mTempRect;
        this.mThumb.copyBounds(rect2);
        if (!isInEditMode()) {
            this.mIndicator.move(rect2.centerX());
        }
        Rect rect3 = this.mInvalidateRect;
        int i4 = this.mAddedTouchBounds;
        rect3.inset(-i4, -i4);
        int i5 = this.mAddedTouchBounds;
        rect2.inset(-i5, -i5);
        this.mInvalidateRect.union(rect2);
        SeekBarCompat.setHotspotBounds(this.mRipple, rect2.left, rect2.top, rect2.right, rect2.bottom);
        invalidate(this.mInvalidateRect);
    }

    private void setHotspot(float f, float f2) {
        DrawableCompat.setHotspot(this.mRipple, f, f2);
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mThumb || drawable == this.mTrack || drawable == this.mScrubber || drawable == this.mRipple || super.verifyDrawable(drawable);
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFloater() {
        if (!isInEditMode()) {
            this.mThumb.animateToPressed();
            this.mIndicator.showIndicator(this, this.mThumb.getBounds());
            notifyBubble(true);
        }
    }

    private void hideFloater() {
        removeCallbacks(this.mShowIndicatorRunnable);
        if (!isInEditMode()) {
            this.mIndicator.dismiss();
            notifyBubble(false);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowIndicatorRunnable);
        if (!isInEditMode()) {
            this.mIndicator.dismissComplete();
        }
    }

    public boolean isRtl() {
        return ViewCompat.getLayoutDirection(this) == 1 && this.mMirrorForRtl;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        CustomState customState = new CustomState(super.onSaveInstanceState());
        customState.progress = getProgress();
        customState.max = this.mMax;
        customState.min = this.mMin;
        return customState;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(CustomState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        CustomState customState = (CustomState) parcelable;
        setMin(customState.min);
        setMax(customState.max);
        setProgress(customState.progress, false);
        super.onRestoreInstanceState(customState.getSuperState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class CustomState extends View.BaseSavedState {
        public static final Parcelable.Creator<CustomState> CREATOR = new Parcelable.Creator<CustomState>() { // from class: com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.CustomState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public CustomState[] mo5963newArray(int i) {
                return new CustomState[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public CustomState mo5962createFromParcel(Parcel parcel) {
                return new CustomState(parcel);
            }
        };
        private int max;
        private int min;
        private int progress;

        public CustomState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.max = parcel.readInt();
            this.min = parcel.readInt();
        }

        public CustomState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
            parcel.writeInt(this.min);
        }
    }
}
