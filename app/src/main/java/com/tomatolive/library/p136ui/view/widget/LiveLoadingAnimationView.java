package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.tomatolive.library.R$drawable;

/* renamed from: com.tomatolive.library.ui.view.widget.LiveLoadingAnimationView */
/* loaded from: classes4.dex */
public class LiveLoadingAnimationView extends AppCompatImageView {
    private AnimationDrawable frameAnimation;

    public LiveLoadingAnimationView(Context context) {
        super(context);
        initView(context);
    }

    public LiveLoadingAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        setImageDrawable(ContextCompat.getDrawable(context, R$drawable.fq_live_loading_animation_circle));
        this.frameAnimation = (AnimationDrawable) getDrawable();
    }

    public void showLoading() {
        post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$LiveLoadingAnimationView$CYGrhAJayqrKFVSsOBXwP43xwyw
            @Override // java.lang.Runnable
            public final void run() {
                LiveLoadingAnimationView.this.lambda$showLoading$0$LiveLoadingAnimationView();
            }
        });
    }

    public /* synthetic */ void lambda$showLoading$0$LiveLoadingAnimationView() {
        AnimationDrawable animationDrawable = this.frameAnimation;
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    public void stopLoading() {
        AnimationDrawable animationDrawable = this.frameAnimation;
        if (animationDrawable == null || !animationDrawable.isRunning()) {
            return;
        }
        this.frameAnimation.stop();
    }

    public void release() {
        AnimationDrawable animationDrawable = this.frameAnimation;
        if (animationDrawable != null && animationDrawable.isRunning()) {
            this.frameAnimation.stop();
        }
        this.frameAnimation = null;
        setImageDrawable(null);
    }
}
