package com.tomatolive.library.p136ui.view.gift;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.tomatolive.library.R$anim;
import com.tomatolive.library.R$id;

/* renamed from: com.tomatolive.library.ui.view.gift.CustomAnimImpl */
/* loaded from: classes3.dex */
public class CustomAnimImpl implements ICustomAnim {
    private String TAG = "CustomAnimImpl";

    @Override // com.tomatolive.library.p136ui.view.gift.ICustomAnim
    public AnimatorSet startAnim(final GiftFrameLayout giftFrameLayout, View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(view.getContext(), R$anim.anim_jump_enter_left);
        giftFrameLayout.setAnimation(loadAnimation);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.tomatolive.library.ui.view.gift.CustomAnimImpl.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                giftFrameLayout.initLayoutState();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                giftFrameLayout.comboAnimation(true);
            }
        });
        giftFrameLayout.ivSvgaImageView.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R$anim.anim_jump_icon_left));
        return null;
    }

    @Override // com.tomatolive.library.p136ui.view.gift.ICustomAnim
    public AnimatorSet comboAnim(final GiftFrameLayout giftFrameLayout, View view, boolean z) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.ll_gift_num_bg);
        if (z) {
            linearLayout.setVisibility(0);
            giftFrameLayout.initGiftCount(String.valueOf(giftFrameLayout.getCombo()));
            giftFrameLayout.comboEndAnim();
            return null;
        }
        ObjectAnimator scaleGiftNum = GiftAnimationUtil.scaleGiftNum(linearLayout);
        scaleGiftNum.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.gift.CustomAnimImpl.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                giftFrameLayout.comboEndAnim();
            }
        });
        scaleGiftNum.start();
        return null;
    }

    @Override // com.tomatolive.library.p136ui.view.gift.ICustomAnim
    public AnimatorSet endAnim(GiftFrameLayout giftFrameLayout, View view) {
        return GiftAnimationUtil.startAnimation(GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 0.0f, -80.0f, 300, 0), GiftAnimationUtil.createFadeAnimator(giftFrameLayout, 100.0f, 0.0f, 0, 0));
    }
}
