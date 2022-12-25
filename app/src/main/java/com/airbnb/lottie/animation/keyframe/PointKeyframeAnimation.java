package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes2.dex */
public class PointKeyframeAnimation extends KeyframeAnimation<PointF> {
    private final PointF point = new PointF();

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    /* renamed from: getValue  reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Object mo5807getValue(Keyframe keyframe, float f) {
        return mo5807getValue((Keyframe<PointF>) keyframe, f);
    }

    public PointKeyframeAnimation(List<Keyframe<PointF>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    /* renamed from: getValue */
    public PointF mo5807getValue(Keyframe<PointF> keyframe, float f) {
        PointF pointF;
        PointF pointF2 = keyframe.startValue;
        if (pointF2 == null || (pointF = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF pointF3 = pointF2;
        PointF pointF4 = pointF;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != 0) {
            return (PointF) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), pointF3, pointF4, f, getLinearCurrentKeyframeProgress(), getProgress());
        }
        PointF pointF5 = this.point;
        float f2 = pointF3.x;
        float f3 = f2 + ((pointF4.x - f2) * f);
        float f4 = pointF3.y;
        pointF5.set(f3, f4 + (f * (pointF4.y - f4)));
        return this.point;
    }
}
