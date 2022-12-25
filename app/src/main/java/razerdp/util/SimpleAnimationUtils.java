package razerdp.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/* loaded from: classes4.dex */
public class SimpleAnimationUtils {
    public static Animation getTranslateVerticalAnimation(int i, int i2, int i3) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, i, i2);
        translateAnimation.setDuration(i3);
        return translateAnimation;
    }

    public static Animation getTranslateVerticalAnimation(float f, float f2, int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, f, 2, f2);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static Animation getDefaultScaleAnimation(boolean z) {
        return getScaleAnimation(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f, z ? 0.0f : 1.0f, z ? 1.0f : 0.0f, 1, 0.5f, 1, 0.5f);
    }

    public static Animation getScaleAnimation(float f, float f2, float f3, float f4, int i, float f5, int i2, float f6) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f3, f4, i, f5, i2, f6);
        scaleAnimation.setDuration(360L);
        return scaleAnimation;
    }

    public static Animation getDefaultAlphaAnimation(boolean z) {
        float f = 0.0f;
        float f2 = z ? 0.0f : 1.0f;
        if (z) {
            f = 1.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f);
        alphaAnimation.setDuration(360L);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }

    public static AnimatorSet getDefaultSlideFromBottomAnimationSet(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        if (view != null) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationY", 250.0f, 0.0f).setDuration(400L), ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1.0f).setDuration(375L));
        }
        return animatorSet;
    }
}
