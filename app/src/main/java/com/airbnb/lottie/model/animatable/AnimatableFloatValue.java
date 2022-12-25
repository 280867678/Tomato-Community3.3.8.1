package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: classes2.dex */
public class AnimatableFloatValue extends BaseAnimatableValue<Float, Float> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public AnimatableFloatValue() {
        super(Float.valueOf(0.0f));
    }

    public AnimatableFloatValue(List<Keyframe<Float>> list) {
        super((List) list);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    /* renamed from: createAnimation */
    public BaseKeyframeAnimation<Float, Float> mo5808createAnimation() {
        return new FloatKeyframeAnimation(this.keyframes);
    }
}
