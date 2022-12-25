package com.one.tomato.thirdpart.video.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import java.util.Random;

/* loaded from: classes3.dex */
public class TouchFlower {
    private Context context;
    private Drawable[] drawables;
    private PointF endPoint;
    float height;
    private Interpolator[] interpolators;
    private FrameLayout rootView;
    private PointF startPoint;
    float width;

    public TouchFlower(FrameLayout frameLayout, float f, float f2, Context context) {
        this.width = f;
        this.height = f2;
        this.rootView = frameLayout;
        this.context = context;
        init();
    }

    public void init() {
        this.drawables = new Drawable[8];
        this.drawables[0] = this.context.getResources().getDrawable(R.drawable.yp_video_like);
        this.drawables[1] = this.context.getResources().getDrawable(R.drawable.yp_video_like);
        this.drawables[2] = this.context.getResources().getDrawable(R.drawable.yp_video_like);
        this.interpolators = new Interpolator[4];
        this.interpolators[0] = new AccelerateDecelerateInterpolator();
        this.interpolators[1] = new AccelerateInterpolator();
        this.interpolators[2] = new DecelerateInterpolator();
        this.interpolators[3] = new LinearInterpolator();
    }

    public void addFlower(float f, float f2) {
        this.startPoint = new PointF(f, f2);
        this.endPoint = new PointF(f, f2);
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, (int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION));
        imageView.setBackground(this.drawables[new Random().nextInt(this.drawables.length)]);
        imageView.setX(f);
        imageView.setY(f2);
        this.rootView.addView(imageView);
        startAnin(imageView);
    }

    private void startAnin(final ImageView imageView) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.0f);
        ofFloat.setDuration(500L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0.2f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(imageView, "scaleY", 0.2f, 1.0f);
        ofFloat2.setDuration(1000L);
        ofFloat3.setDuration(1000L);
        ValueAnimator ofObject = ValueAnimator.ofObject(new MyTypeEvaluator(this, getPoint(0), getPoint(1)), this.startPoint, this.endPoint);
        ofObject.setDuration(4000L);
        ofObject.setInterpolator(new AccelerateDecelerateInterpolator());
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.one.tomato.thirdpart.video.widget.TouchFlower.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        ofObject.start();
        animatorSet.play(ofObject);
        animatorSet.play(ofFloat2).with(ofFloat3).before(ofFloat);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.one.tomato.thirdpart.video.widget.TouchFlower.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                TouchFlower.this.rootView.removeView(imageView);
            }
        });
        animatorSet.start();
    }

    private PointF getPoint(int i) {
        PointF pointF = new PointF();
        pointF.x = new Random().nextFloat() * this.width;
        if (i == 0) {
            pointF.y = new Random().nextFloat() * 0.5f * this.height;
        } else {
            float f = this.height;
            pointF.y = (new Random().nextFloat() * 0.5f * f) + (f * 0.5f);
        }
        return pointF;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class MyTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF pointF1;
        private PointF pointF2;

        public MyTypeEvaluator(TouchFlower touchFlower, PointF pointF, PointF pointF2) {
            this.pointF1 = pointF;
            this.pointF2 = pointF2;
        }

        @Override // android.animation.TypeEvaluator
        public PointF evaluate(float f, PointF pointF, PointF pointF2) {
            float f2 = 1.0f - f;
            PointF pointF3 = new PointF();
            float f3 = f2 * f2 * f2;
            float f4 = 3.0f * f2;
            float f5 = f2 * f4 * f;
            PointF pointF4 = this.pointF1;
            float f6 = (pointF.x * f3) + (pointF4.x * f5);
            float f7 = f4 * f * f;
            PointF pointF5 = this.pointF2;
            float f8 = f * f * f;
            pointF3.x = f6 + (pointF5.x * f7) + (pointF2.x * f8);
            pointF3.y = (f3 * pointF.y) + (f5 * pointF4.y) + (f7 * pointF5.y) + (f8 * pointF2.y);
            return pointF3;
        }
    }
}
