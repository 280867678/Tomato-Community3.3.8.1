package com.tomatolive.library.p136ui.view.gift;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

/* renamed from: com.tomatolive.library.ui.view.gift.GiftAnimationUtil */
/* loaded from: classes3.dex */
public class GiftAnimationUtil {
    public static ObjectAnimator createFlyFromLtoR(View view, float f, float f2, int i, TimeInterpolator timeInterpolator) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2);
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.setDuration(i);
        return ofFloat;
    }

    public static ObjectAnimator createFlyFromRtoL(View view, float f, float f2, int i, TimeInterpolator timeInterpolator) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2);
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.setDuration(i);
        return ofFloat;
    }

    public static AnimationDrawable startAnimationDrawable(ImageView imageView) {
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        if (animationDrawable != null) {
            imageView.setVisibility(0);
            animationDrawable.start();
        }
        return animationDrawable;
    }

    public static void setAnimationDrawable(ImageView imageView, AnimationDrawable animationDrawable) {
        imageView.setBackground(animationDrawable);
    }

    public static ObjectAnimator scaleGiftNum(View view) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", 1.4f, 0.7f, 1.0f), PropertyValuesHolder.ofFloat("scaleY", 1.4f, 0.7f, 1.0f), PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f, 1.0f)).setDuration(200L);
    }

    public static ObjectAnimator createFadeAnimator(View view, float f, float f2, int i, int i2) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("translationY", f, f2), PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f));
        ofPropertyValuesHolder.setStartDelay(i2);
        ofPropertyValuesHolder.setDuration(i);
        return ofPropertyValuesHolder;
    }

    public static AnimatorSet startAnimation(ObjectAnimator objectAnimator, ObjectAnimator objectAnimator2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).before(objectAnimator2);
        animatorSet.start();
        return animatorSet;
    }

    public static AnimatorSet startAnimation(ObjectAnimator objectAnimator, ObjectAnimator objectAnimator2, ObjectAnimator objectAnimator3, ObjectAnimator objectAnimator4, ObjectAnimator objectAnimator5) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).before(objectAnimator2);
        animatorSet.play(objectAnimator3).after(objectAnimator2);
        animatorSet.play(objectAnimator4).after(objectAnimator3);
        animatorSet.play(objectAnimator5).after(objectAnimator4);
        animatorSet.start();
        return animatorSet;
    }
}
