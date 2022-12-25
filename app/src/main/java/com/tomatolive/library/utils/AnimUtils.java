package com.tomatolive.library.utils;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.p140wj.rebound.SimpleSpringListener;
import com.p140wj.rebound.Spring;
import com.p140wj.rebound.SpringConfig;
import com.p140wj.rebound.SpringSystem;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$drawable;

/* loaded from: classes4.dex */
public class AnimUtils {
    public static void playHideAnimation(View view) {
        playHideAnimation(view, 300L);
    }

    public static void playHideAnimation(final View view, long j) {
        if (view != null && view.getVisibility() == 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(j);
            alphaAnimation.setFillAfter(false);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.tomatolive.library.utils.AnimUtils.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(4);
                }
            });
            view.startAnimation(alphaAnimation);
        }
    }

    public static void playScaleAnim(final View view) {
        Spring createSpring = SpringSystem.create().createSpring();
        createSpring.setCurrentValue(1.100000023841858d);
        createSpring.setSpringConfig(new SpringConfig(40.0d, 7.0d));
        createSpring.addListener(new SimpleSpringListener() { // from class: com.tomatolive.library.utils.AnimUtils.2
            @Override // com.p140wj.rebound.SimpleSpringListener, com.p140wj.rebound.SpringListener
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                view.setScaleX(currentValue);
                view.setScaleY(currentValue);
            }
        });
        createSpring.setEndValue(1.0d);
    }

    public static void playLiveScaleAnim(final View view) {
        Spring createSpring = SpringSystem.create().createSpring();
        createSpring.setCurrentValue(1.5d);
        createSpring.setSpringConfig(new SpringConfig(50.0d, 3.0d));
        createSpring.addListener(new SimpleSpringListener() { // from class: com.tomatolive.library.utils.AnimUtils.3
            @Override // com.p140wj.rebound.SimpleSpringListener, com.p140wj.rebound.SpringListener
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                view.setScaleX(currentValue);
                view.setScaleY(currentValue);
            }
        });
        createSpring.setEndValue(1.0d);
    }

    public static void playShakeAnim(View view) {
        tada(view).start();
    }

    public static ObjectAnimator tada(View view) {
        return tada(view, 1.0f);
    }

    public static ObjectAnimator tada(View view, float f) {
        float f2 = (-3.0f) * f;
        float f3 = 3.0f * f;
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.1f, 0.9f), Keyframe.ofFloat(0.2f, 0.9f), Keyframe.ofFloat(0.3f, 1.1f), Keyframe.ofFloat(0.4f, 1.1f), Keyframe.ofFloat(0.5f, 1.1f), Keyframe.ofFloat(0.6f, 1.1f), Keyframe.ofFloat(0.7f, 1.1f), Keyframe.ofFloat(0.8f, 1.1f), Keyframe.ofFloat(0.9f, 1.1f), Keyframe.ofFloat(1.0f, 1.0f)), PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.1f, 0.9f), Keyframe.ofFloat(0.2f, 0.9f), Keyframe.ofFloat(0.3f, 1.1f), Keyframe.ofFloat(0.4f, 1.1f), Keyframe.ofFloat(0.5f, 1.1f), Keyframe.ofFloat(0.6f, 1.1f), Keyframe.ofFloat(0.7f, 1.1f), Keyframe.ofFloat(0.8f, 1.1f), Keyframe.ofFloat(0.9f, 1.1f), Keyframe.ofFloat(1.0f, 1.0f)), PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(0.1f, f2), Keyframe.ofFloat(0.2f, f2), Keyframe.ofFloat(0.3f, f3), Keyframe.ofFloat(0.4f, f2), Keyframe.ofFloat(0.5f, f3), Keyframe.ofFloat(0.6f, f2), Keyframe.ofFloat(0.7f, f3), Keyframe.ofFloat(0.8f, f2), Keyframe.ofFloat(0.9f, f3), Keyframe.ofFloat(1.0f, 0.0f))).setDuration(1000L);
    }

    public static ObjectAnimator nope(View view) {
        int dimensionPixelOffset = view.getResources().getDimensionPixelOffset(R$dimen.spacing_medium);
        float f = -dimensionPixelOffset;
        float f2 = dimensionPixelOffset;
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X, Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(0.1f, f), Keyframe.ofFloat(0.26f, f2), Keyframe.ofFloat(0.42f, f), Keyframe.ofFloat(0.58f, f2), Keyframe.ofFloat(0.74f, f), Keyframe.ofFloat(0.9f, f2), Keyframe.ofFloat(1.0f, 0.0f))).setDuration(500L);
    }

    public static void showBezier(Activity activity, View view, View view2, String str, ViewGroup viewGroup) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationInWindow(iArr);
        view2.getLocationInWindow(iArr2);
        final ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(60, 60));
        GlideUtils.loadImage((Context) activity, imageView, str, R$drawable.fq_ic_gift_default);
        viewGroup.addView(imageView);
        imageView.setX(iArr[0]);
        imageView.setY(iArr[1]);
        Point point = new Point(iArr[0], iArr[1]);
        Point point2 = new Point(iArr2[0], iArr2[1]);
        ValueAnimator ofObject = ValueAnimator.ofObject(new BezierEvaluator(new Point((point.x + point2.x) / 2, (int) ((point.y + point2.y) * 0.8f))), point, point2);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.utils.AnimUtils.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point3 = (Point) valueAnimator.getAnimatedValue();
                imageView.setX(point3.x);
                imageView.setY(point3.y);
            }
        });
        ofObject.setDuration(4000L);
        ofObject.setInterpolator(new AccelerateDecelerateInterpolator());
        ofObject.addListener(new Animator.AnimatorListener() { // from class: com.tomatolive.library.utils.AnimUtils.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewGroup viewGroup2 = (ViewGroup) imageView.getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(imageView);
                }
            }
        });
        ofObject.start();
    }

    /* loaded from: classes4.dex */
    public static class BezierEvaluator implements TypeEvaluator<Point> {
        private Point controllPoint;

        public BezierEvaluator(Point point) {
            this.controllPoint = point;
        }

        @Override // android.animation.TypeEvaluator
        public Point evaluate(float f, Point point, Point point2) {
            float f2 = 1.0f - f;
            float f3 = f2 * f2;
            float f4 = 2.0f * f * f2;
            Point point3 = this.controllPoint;
            float f5 = f * f;
            return new Point((int) ((point.x * f3) + (point3.x * f4) + (point2.x * f5)), (int) ((f3 * point.y) + (f4 * point3.y) + (f5 * point2.y)));
        }
    }
}
