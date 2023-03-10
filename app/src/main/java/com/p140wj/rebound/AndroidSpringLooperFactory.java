package com.p140wj.rebound;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Choreographer;

/* renamed from: com.wj.rebound.AndroidSpringLooperFactory */
/* loaded from: classes4.dex */
abstract class AndroidSpringLooperFactory {
    public static SpringLooper createSpringLooper() {
        if (Build.VERSION.SDK_INT >= 16) {
            return ChoreographerAndroidSpringLooper.create();
        }
        return LegacyAndroidSpringLooper.create();
    }

    /* renamed from: com.wj.rebound.AndroidSpringLooperFactory$LegacyAndroidSpringLooper */
    /* loaded from: classes4.dex */
    private static class LegacyAndroidSpringLooper extends SpringLooper {
        private final Handler mHandler;
        private long mLastTime;
        private final Runnable mLooperRunnable = new Runnable() { // from class: com.wj.rebound.AndroidSpringLooperFactory.LegacyAndroidSpringLooper.1
            @Override // java.lang.Runnable
            public void run() {
                if (!LegacyAndroidSpringLooper.this.mStarted || LegacyAndroidSpringLooper.this.mSpringSystem == null) {
                    return;
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                LegacyAndroidSpringLooper legacyAndroidSpringLooper = LegacyAndroidSpringLooper.this;
                legacyAndroidSpringLooper.mSpringSystem.loop(uptimeMillis - legacyAndroidSpringLooper.mLastTime);
                LegacyAndroidSpringLooper.this.mLastTime = uptimeMillis;
                LegacyAndroidSpringLooper.this.mHandler.post(LegacyAndroidSpringLooper.this.mLooperRunnable);
            }
        };
        private boolean mStarted;

        public static SpringLooper create() {
            return new LegacyAndroidSpringLooper(new Handler());
        }

        public LegacyAndroidSpringLooper(Handler handler) {
            this.mHandler = handler;
        }

        @Override // com.p140wj.rebound.SpringLooper
        public void start() {
            if (this.mStarted) {
                return;
            }
            this.mStarted = true;
            this.mLastTime = SystemClock.uptimeMillis();
            this.mHandler.removeCallbacks(this.mLooperRunnable);
            this.mHandler.post(this.mLooperRunnable);
        }

        @Override // com.p140wj.rebound.SpringLooper
        public void stop() {
            this.mStarted = false;
            this.mHandler.removeCallbacks(this.mLooperRunnable);
        }
    }

    @TargetApi(16)
    /* renamed from: com.wj.rebound.AndroidSpringLooperFactory$ChoreographerAndroidSpringLooper */
    /* loaded from: classes4.dex */
    private static class ChoreographerAndroidSpringLooper extends SpringLooper {
        private final Choreographer mChoreographer;
        private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() { // from class: com.wj.rebound.AndroidSpringLooperFactory.ChoreographerAndroidSpringLooper.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                if (!ChoreographerAndroidSpringLooper.this.mStarted || ChoreographerAndroidSpringLooper.this.mSpringSystem == null) {
                    return;
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                ChoreographerAndroidSpringLooper choreographerAndroidSpringLooper = ChoreographerAndroidSpringLooper.this;
                choreographerAndroidSpringLooper.mSpringSystem.loop(uptimeMillis - choreographerAndroidSpringLooper.mLastTime);
                ChoreographerAndroidSpringLooper.this.mLastTime = uptimeMillis;
                ChoreographerAndroidSpringLooper.this.mChoreographer.postFrameCallback(ChoreographerAndroidSpringLooper.this.mFrameCallback);
            }
        };
        private long mLastTime;
        private boolean mStarted;

        public static ChoreographerAndroidSpringLooper create() {
            return new ChoreographerAndroidSpringLooper(Choreographer.getInstance());
        }

        public ChoreographerAndroidSpringLooper(Choreographer choreographer) {
            this.mChoreographer = choreographer;
        }

        @Override // com.p140wj.rebound.SpringLooper
        public void start() {
            if (this.mStarted) {
                return;
            }
            this.mStarted = true;
            this.mLastTime = SystemClock.uptimeMillis();
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
            this.mChoreographer.postFrameCallback(this.mFrameCallback);
        }

        @Override // com.p140wj.rebound.SpringLooper
        public void stop() {
            this.mStarted = false;
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
        }
    }
}
