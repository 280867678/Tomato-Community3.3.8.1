package com.airbnb.lottie.value;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;

/* loaded from: classes2.dex */
public class LottieValueCallback<T> {
    private final LottieFrameInfo<T> frameInfo = new LottieFrameInfo<>();
    @Nullable
    protected T value;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final void setAnimation(@Nullable BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
    }

    public LottieValueCallback(@Nullable T t) {
        this.value = null;
        this.value = t;
    }

    public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
        return this.value;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final T getValueInternal(float f, float f2, T t, T t2, float f3, float f4, float f5) {
        LottieFrameInfo<T> lottieFrameInfo = this.frameInfo;
        lottieFrameInfo.set(f, f2, t, t2, f3, f4, f5);
        return getValue(lottieFrameInfo);
    }
}
