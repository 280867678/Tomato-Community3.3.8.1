package com.airbnb.lottie.animation.keyframe;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;

/* loaded from: classes2.dex */
public class TransformKeyframeAnimation {
    private final BaseKeyframeAnimation<PointF, PointF> anchorPoint;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> endOpacity;
    private final Matrix matrix = new Matrix();
    private final BaseKeyframeAnimation<Integer, Integer> opacity;
    private final BaseKeyframeAnimation<?, PointF> position;
    private final BaseKeyframeAnimation<Float, Float> rotation;
    private final BaseKeyframeAnimation<ScaleXY, ScaleXY> scale;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> startOpacity;

    public TransformKeyframeAnimation(AnimatableTransform animatableTransform) {
        this.anchorPoint = animatableTransform.getAnchorPoint().mo5808createAnimation();
        this.position = animatableTransform.getPosition().mo5808createAnimation();
        this.scale = animatableTransform.getScale().mo5808createAnimation();
        this.rotation = animatableTransform.getRotation().mo5808createAnimation();
        this.opacity = animatableTransform.getOpacity().mo5808createAnimation();
        if (animatableTransform.getStartOpacity() != null) {
            this.startOpacity = animatableTransform.getStartOpacity().mo5808createAnimation();
        } else {
            this.startOpacity = null;
        }
        if (animatableTransform.getEndOpacity() != null) {
            this.endOpacity = animatableTransform.getEndOpacity().mo5808createAnimation();
        } else {
            this.endOpacity = null;
        }
    }

    public void addAnimationsToLayer(BaseLayer baseLayer) {
        baseLayer.addAnimation(this.anchorPoint);
        baseLayer.addAnimation(this.position);
        baseLayer.addAnimation(this.scale);
        baseLayer.addAnimation(this.rotation);
        baseLayer.addAnimation(this.opacity);
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.startOpacity;
        if (baseKeyframeAnimation != null) {
            baseLayer.addAnimation(baseKeyframeAnimation);
        }
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.endOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseLayer.addAnimation(baseKeyframeAnimation2);
        }
    }

    public void addListener(BaseKeyframeAnimation.AnimationListener animationListener) {
        this.anchorPoint.addUpdateListener(animationListener);
        this.position.addUpdateListener(animationListener);
        this.scale.addUpdateListener(animationListener);
        this.rotation.addUpdateListener(animationListener);
        this.opacity.addUpdateListener(animationListener);
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.startOpacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.addUpdateListener(animationListener);
        }
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.endOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.addUpdateListener(animationListener);
        }
    }

    public void setProgress(float f) {
        this.anchorPoint.setProgress(f);
        this.position.setProgress(f);
        this.scale.setProgress(f);
        this.rotation.setProgress(f);
        this.opacity.setProgress(f);
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation = this.startOpacity;
        if (baseKeyframeAnimation != null) {
            baseKeyframeAnimation.setProgress(f);
        }
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.endOpacity;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.setProgress(f);
        }
    }

    public BaseKeyframeAnimation<?, Integer> getOpacity() {
        return this.opacity;
    }

    @Nullable
    public BaseKeyframeAnimation<?, Float> getStartOpacity() {
        return this.startOpacity;
    }

    @Nullable
    public BaseKeyframeAnimation<?, Float> getEndOpacity() {
        return this.endOpacity;
    }

    public Matrix getMatrix() {
        this.matrix.reset();
        PointF mo5805getValue = this.position.mo5805getValue();
        if (mo5805getValue.x != 0.0f || mo5805getValue.y != 0.0f) {
            this.matrix.preTranslate(mo5805getValue.x, mo5805getValue.y);
        }
        float floatValue = this.rotation.mo5805getValue().floatValue();
        if (floatValue != 0.0f) {
            this.matrix.preRotate(floatValue);
        }
        ScaleXY mo5805getValue2 = this.scale.mo5805getValue();
        if (mo5805getValue2.getScaleX() != 1.0f || mo5805getValue2.getScaleY() != 1.0f) {
            this.matrix.preScale(mo5805getValue2.getScaleX(), mo5805getValue2.getScaleY());
        }
        PointF mo5805getValue3 = this.anchorPoint.mo5805getValue();
        if (mo5805getValue3.x != 0.0f || mo5805getValue3.y != 0.0f) {
            this.matrix.preTranslate(-mo5805getValue3.x, -mo5805getValue3.y);
        }
        return this.matrix;
    }

    public Matrix getMatrixForRepeater(float f) {
        PointF mo5805getValue = this.position.mo5805getValue();
        PointF mo5805getValue2 = this.anchorPoint.mo5805getValue();
        ScaleXY mo5805getValue3 = this.scale.mo5805getValue();
        float floatValue = this.rotation.mo5805getValue().floatValue();
        this.matrix.reset();
        this.matrix.preTranslate(mo5805getValue.x * f, mo5805getValue.y * f);
        double d = f;
        this.matrix.preScale((float) Math.pow(mo5805getValue3.getScaleX(), d), (float) Math.pow(mo5805getValue3.getScaleY(), d));
        this.matrix.preRotate(floatValue * f, mo5805getValue2.x, mo5805getValue2.y);
        return this.matrix;
    }

    public <T> boolean applyValueCallback(T t, @Nullable LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2;
        if (t == LottieProperty.TRANSFORM_ANCHOR_POINT) {
            this.anchorPoint.setValueCallback(lottieValueCallback);
            return true;
        } else if (t == LottieProperty.TRANSFORM_POSITION) {
            this.position.setValueCallback(lottieValueCallback);
            return true;
        } else if (t == LottieProperty.TRANSFORM_SCALE) {
            this.scale.setValueCallback(lottieValueCallback);
            return true;
        } else if (t == LottieProperty.TRANSFORM_ROTATION) {
            this.rotation.setValueCallback(lottieValueCallback);
            return true;
        } else if (t == LottieProperty.TRANSFORM_OPACITY) {
            this.opacity.setValueCallback(lottieValueCallback);
            return true;
        } else if (t == LottieProperty.TRANSFORM_START_OPACITY && (baseKeyframeAnimation2 = this.startOpacity) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return true;
        } else if (t != LottieProperty.TRANSFORM_END_OPACITY || (baseKeyframeAnimation = this.endOpacity) == null) {
            return false;
        } else {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
            return true;
        }
    }
}
