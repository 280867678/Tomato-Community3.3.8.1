package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

/* loaded from: classes2.dex */
public class ScaleKeyframeAnimation extends KeyframeAnimation<ScaleXY> {
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    /* renamed from: getValue  reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Object mo5807getValue(Keyframe keyframe, float f) {
        return mo5807getValue((Keyframe<ScaleXY>) keyframe, f);
    }

    public ScaleKeyframeAnimation(List<Keyframe<ScaleXY>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    /* renamed from: getValue */
    public ScaleXY mo5807getValue(Keyframe<ScaleXY> keyframe, float f) {
        ScaleXY scaleXY;
        ScaleXY scaleXY2 = keyframe.startValue;
        if (scaleXY2 == null || (scaleXY = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY scaleXY3 = scaleXY2;
        ScaleXY scaleXY4 = scaleXY;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != 0) {
            return (ScaleXY) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), scaleXY3, scaleXY4, f, getLinearCurrentKeyframeProgress(), getProgress());
        }
        return new ScaleXY(MiscUtils.lerp(scaleXY3.getScaleX(), scaleXY4.getScaleX(), f), MiscUtils.lerp(scaleXY3.getScaleY(), scaleXY4.getScaleY(), f));
    }
}
