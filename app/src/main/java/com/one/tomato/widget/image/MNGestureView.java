package com.one.tomato.widget.image;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
import com.one.tomato.utils.LogUtil;

/* loaded from: classes3.dex */
public class MNGestureView extends RelativeLayout {
    public boolean isAppBarEx;
    private float mDisplacementX;
    private float mDisplacementY;
    private float mInitialTx;
    private float mInitialTy;
    private boolean mTracking;
    private OnCanSwipeListener onCanSwipeListener;
    private OnSwipeListener onSwipeListener;

    /* loaded from: classes3.dex */
    public interface OnCanSwipeListener {
        boolean canSwipe();
    }

    /* loaded from: classes3.dex */
    public interface OnSwipeListener {
        void downSwipe();

        void onSwiping(float f);

        void overSwipe();
    }

    public MNGestureView(Context context) {
        this(context, null);
    }

    public MNGestureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MNGestureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAppBarEx = false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1) {
            LogUtil.m3787d("yang", "下h來關閉執行ACTION_UP");
            if (this.mTracking) {
                this.mTracking = false;
                float translationY = getTranslationY();
                LogUtil.m3787d("yang", "currentTranslateY =" + translationY + "mHeight / 3==333");
                if (translationY > 333.0f) {
                    OnSwipeListener onSwipeListener = this.onSwipeListener;
                    if (onSwipeListener != null) {
                        onSwipeListener.downSwipe();
                    }
                }
            }
            setViewDefault();
            OnSwipeListener onSwipeListener2 = this.onSwipeListener;
            if (onSwipeListener2 != null) {
                onSwipeListener2.overSwipe();
            }
        } else if (action == 2) {
            if (this.isAppBarEx) {
                return super.onTouchEvent(motionEvent);
            }
            float rawX = motionEvent.getRawX() - this.mDisplacementX;
            float rawY = motionEvent.getRawY() - this.mDisplacementY;
            OnCanSwipeListener onCanSwipeListener = this.onCanSwipeListener;
            if (onCanSwipeListener == null || onCanSwipeListener.canSwipe()) {
                if ((rawY > 0.0f && Math.abs(rawY) > ViewConfiguration.get(getContext()).getScaledTouchSlop() * 2 && Math.abs(rawX) < Math.abs(rawY) / 2.0f) || this.mTracking) {
                    OnSwipeListener onSwipeListener3 = this.onSwipeListener;
                    if (onSwipeListener3 != null) {
                        onSwipeListener3.onSwiping(rawY);
                    }
                    setBackgroundColor(0);
                    this.mTracking = true;
                    setTranslationY(this.mInitialTy + rawY);
                    setTranslationX(this.mInitialTx + rawX);
                    float f = 1.0f - (rawY / 1000.0f);
                    if (f < 0.3d) {
                        f = 0.3f;
                    }
                    setScaleX(f);
                    setScaleY(f);
                    return true;
                } else if (rawY < 0.0f) {
                    setViewDefault();
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDisplacementX = motionEvent.getRawX();
            this.mDisplacementY = motionEvent.getRawY();
            this.mInitialTy = getTranslationY();
            this.mInitialTx = getTranslationX();
        } else if (actionMasked == 2) {
            int abs = Math.abs((int) (motionEvent.getRawX() - this.mDisplacementX));
            if (((int) (motionEvent.getRawY() - this.mDisplacementY)) > 50 && abs <= 50 && !this.isAppBarEx) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private void setViewDefault() {
        setAlpha(1.0f);
        setTranslationX(0.0f);
        setTranslationY(0.0f);
        setScaleX(1.0f);
        setScaleY(1.0f);
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    public void setOnGestureListener(OnCanSwipeListener onCanSwipeListener) {
        this.onCanSwipeListener = onCanSwipeListener;
    }
}
