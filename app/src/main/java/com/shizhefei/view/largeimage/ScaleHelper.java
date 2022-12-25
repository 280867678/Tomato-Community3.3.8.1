package com.shizhefei.view.largeimage;

import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
class ScaleHelper {
    private int mDuration;
    private boolean mFinished = true;
    private Interpolator mInterpolator;
    private float mScale;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mToScale;

    public void startScale(float f, float f2, int i, int i2, Interpolator interpolator) {
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mInterpolator = interpolator;
        this.mScale = f;
        this.mToScale = f2;
        this.mStartX = i;
        this.mStartY = i2;
        float f3 = f2 > f ? f2 / f : f / f2;
        float f4 = 4.0f;
        if (f3 <= 4.0f) {
            f4 = f3;
        }
        this.mDuration = (int) (Math.sqrt(f4 * 3600.0f) + 220.0d);
        this.mFinished = false;
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
        int i = this.mDuration;
        if (currentAnimationTimeMillis < i) {
            float interpolation = this.mInterpolator.getInterpolation(((float) currentAnimationTimeMillis) / i);
            float f = this.mScale;
            this.mScale = f + (interpolation * (this.mToScale - f));
        } else {
            this.mScale = this.mToScale;
            this.mFinished = true;
        }
        return true;
    }

    private boolean isFinished() {
        return this.mFinished;
    }

    public float getCurScale() {
        return this.mScale;
    }

    public int getStartX() {
        return this.mStartX;
    }

    public int getStartY() {
        return this.mStartY;
    }
}
