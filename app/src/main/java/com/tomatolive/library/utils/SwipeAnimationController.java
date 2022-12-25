package com.tomatolive.library.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import com.tomatolive.library.R$anim;

/* loaded from: classes4.dex */
public class SwipeAnimationController {
    private static final String TAG = "SwipeAnimationController";
    private Animation animation;
    private LayoutAnimationController controller;
    private Context mContext;
    private float mInitX;
    private float mInitY;
    private float mScreenwidth;
    private float mTouchSlop;
    private ViewGroup mViewGroup;
    private boolean isMoving = false;
    private ValueAnimator valueAnimator = new ValueAnimator();

    public SwipeAnimationController(Context context, ViewGroup viewGroup) {
        this.mContext = context;
        this.mViewGroup = viewGroup;
        this.mScreenwidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        this.valueAnimator.setDuration(200L);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.utils.SwipeAnimationController.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeAnimationController.this.mViewGroup.setTranslationX(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.animation = AnimationUtils.loadAnimation(this.mContext, R$anim.fq_anim_slice_in_right);
        this.animation.setDuration(150L);
        this.animation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.controller = new LayoutAnimationController(this.animation);
        this.controller.setOrder(1);
    }

    public void setAnimationView(RelativeLayout relativeLayout) {
        this.mViewGroup = relativeLayout;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
        if (r3 != 3) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean processEvent(MotionEvent motionEvent) {
        if (this.valueAnimator.isRunning()) {
            return true;
        }
        VelocityTracker obtain = VelocityTracker.obtain();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mInitX = motionEvent.getRawX();
            this.mInitY = motionEvent.getRawY();
            obtain.addMovement(motionEvent);
        } else {
            if (action != 1) {
                if (action == 2) {
                    float rawX = motionEvent.getRawX() - this.mInitX;
                    float rawY = motionEvent.getRawY() - this.mInitY;
                    if (!this.isMoving && Math.abs(rawX) > this.mTouchSlop && Math.abs(rawX) > Math.abs(rawY)) {
                        this.isMoving = true;
                    }
                }
            }
            int rawX2 = (int) (motionEvent.getRawX() - this.mInitX);
            obtain.addMovement(motionEvent);
            obtain.computeCurrentVelocity(100);
            float xVelocity = obtain.getXVelocity(-1);
            if (this.isMoving) {
                if (rawX2 >= this.mContext.getResources().getDisplayMetrics().widthPixels / 5 || xVelocity > 1000.0f) {
                    if (this.mViewGroup.getTranslationX() < 0.0f) {
                        this.mViewGroup.setLayoutAnimation(null);
                        this.mViewGroup.setTranslationX((int) this.mScreenwidth);
                        this.mViewGroup.setLayoutAnimation(this.controller);
                        this.mViewGroup.startLayoutAnimation();
                        this.mViewGroup.setTranslationX(0.0f);
                    }
                } else if (rawX2 < 0 - (this.mContext.getResources().getDisplayMetrics().widthPixels / 5) && this.mViewGroup.getTranslationX() == 0.0f) {
                    this.valueAnimator.setIntValues(0, -((int) this.mScreenwidth));
                    this.valueAnimator.start();
                }
                this.isMoving = false;
            }
            this.mInitX = 0.0f;
            this.mInitY = 0.0f;
            obtain.clear();
            obtain.recycle();
        }
        return true;
    }

    public void resetClearScreen() {
        if (this.mViewGroup.getTranslationX() < 0.0f) {
            this.mViewGroup.setLayoutAnimation(null);
            this.mViewGroup.setTranslationX((int) this.mScreenwidth);
            this.mViewGroup.setLayoutAnimation(this.controller);
            this.mViewGroup.startLayoutAnimation();
            this.mViewGroup.setTranslationX(0.0f);
            this.isMoving = false;
            this.mInitX = 0.0f;
            this.mInitY = 0.0f;
        }
    }

    public void onDestroy() {
        ValueAnimator valueAnimator = this.valueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.valueAnimator.removeAllUpdateListeners();
        }
        Animation animation = this.animation;
        if (animation != null) {
            animation.cancel();
        }
    }
}
