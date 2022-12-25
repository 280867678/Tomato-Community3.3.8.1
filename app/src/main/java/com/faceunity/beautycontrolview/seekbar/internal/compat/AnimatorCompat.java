package com.faceunity.beautycontrolview.seekbar.internal.compat;

/* loaded from: classes2.dex */
public abstract class AnimatorCompat {

    /* loaded from: classes2.dex */
    public interface AnimationFrameUpdateListener {
        void onAnimationFrame(float f);
    }

    public abstract void cancel();

    public abstract boolean isRunning();

    public abstract void setDuration(int i);

    public abstract void start();

    AnimatorCompat() {
    }

    public static final AnimatorCompat create(float f, float f2, AnimationFrameUpdateListener animationFrameUpdateListener) {
        return new AnimatorCompatBase(f, f2, animationFrameUpdateListener);
    }

    /* loaded from: classes2.dex */
    private static class AnimatorCompatBase extends AnimatorCompat {
        private final float mEndValue;
        private final AnimationFrameUpdateListener mListener;

        @Override // com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat
        public void cancel() {
        }

        @Override // com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat
        public boolean isRunning() {
            return false;
        }

        @Override // com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat
        public void setDuration(int i) {
        }

        public AnimatorCompatBase(float f, float f2, AnimationFrameUpdateListener animationFrameUpdateListener) {
            this.mListener = animationFrameUpdateListener;
            this.mEndValue = f2;
        }

        @Override // com.faceunity.beautycontrolview.seekbar.internal.compat.AnimatorCompat
        public void start() {
            this.mListener.onAnimationFrame(this.mEndValue);
        }
    }
}
