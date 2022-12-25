package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.tomatolive.library.R$drawable;

/* renamed from: com.tomatolive.library.ui.view.widget.LoadingView */
/* loaded from: classes4.dex */
public class LoadingView extends AppCompatImageView {
    private AnimationDrawable frameAnimation;

    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        setImageDrawable(ContextCompat.getDrawable(context, R$drawable.fq_live_loading_animation));
        this.frameAnimation = (AnimationDrawable) getDrawable();
    }

    public void showLoading() {
        post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.-$$Lambda$LoadingView$EbJr9NVMUkQvKh3fl5kkr7xWhZs
            @Override // java.lang.Runnable
            public final void run() {
                LoadingView.this.lambda$showLoading$0$LoadingView();
            }
        });
    }

    public /* synthetic */ void lambda$showLoading$0$LoadingView() {
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
