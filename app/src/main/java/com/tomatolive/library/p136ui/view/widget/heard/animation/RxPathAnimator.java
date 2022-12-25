package com.tomatolive.library.p136ui.view.widget.heard.animation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import com.tomatolive.library.p136ui.view.widget.heard.animation.RxAbstractPathAnimator;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tomatolive.library.ui.view.widget.heard.animation.RxPathAnimator */
/* loaded from: classes4.dex */
public class RxPathAnimator extends RxAbstractPathAnimator {
    private final AtomicInteger mCounter = new AtomicInteger(0);

    public RxPathAnimator(RxAbstractPathAnimator.Config config) {
        super(config);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.heard.animation.RxAbstractPathAnimator
    public void start(boolean z, final View view, final ViewGroup viewGroup) {
        RxAbstractPathAnimator.Config config = this.mConfig;
        viewGroup.addView(view, new ViewGroup.LayoutParams(config.heartWidth, config.heartHeight));
        FloatAnimation floatAnimation = new FloatAnimation(z, createPath(this.mCounter, viewGroup, 2), randomRotation(), viewGroup, view);
        floatAnimation.setDuration(this.mConfig.animDuration);
        floatAnimation.setInterpolator(new LinearInterpolator());
        viewGroup.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.heard.animation.RxPathAnimator.1
            @Override // java.lang.Runnable
            public void run() {
                viewGroup.removeView(view);
            }
        }, floatAnimation.getDuration());
        floatAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.tomatolive.library.ui.view.widget.heard.animation.RxPathAnimator.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                RxPathAnimator.this.mCounter.decrementAndGet();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                RxPathAnimator.this.mCounter.incrementAndGet();
            }
        });
        view.startAnimation(floatAnimation);
    }
}
