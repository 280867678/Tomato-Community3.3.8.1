package com.tomatolive.library.p136ui.view.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LeftAnimEntity;
import com.tomatolive.library.p136ui.view.widget.drawabletext.DrawableTextView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.zzz.ipfssdk.callback.exception.CodeState;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.NobilityEnterDanmuView */
/* loaded from: classes3.dex */
public class NobilityEnterDanmuView extends LeftAnimView {
    private RelativeLayout alAvatarBg;
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private View rootView;
    private TextView tvName;
    private DrawableTextView tvTips;

    private int getAnimDuration(int i) {
        if (i == 5 || i == 6 || i != 7) {
        }
        return CodeState.CODES.CODE_CDN_PROBE;
    }

    private int getAvatarBgTopPadding(int i) {
        if (i != 6) {
            return i != 7 ? 17 : 23;
        }
        return 20;
    }

    public NobilityEnterDanmuView(Context context) {
        super(context);
    }

    public NobilityEnterDanmuView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void initView(Context context) {
        this.animType = ConstantUtils.LEFT_ANIM_ENTER_NOBILITY_TYPE;
        this.rootView = RelativeLayout.inflate(context, R$layout.fq_layout_nobility_enter_anim_view, this);
        this.alAvatarBg = (RelativeLayout) findViewById(R$id.rl_nobility_avatar_bg);
        this.tvName = (TextView) findViewById(R$id.tv_name);
        this.tvTips = (DrawableTextView) findViewById(R$id.tv_tips);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        setRootViewVisibility(4);
        this.tvName.setSelected(true);
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void addItemInfo(final LeftAnimEntity leftAnimEntity) {
        int i;
        if (leftAnimEntity == null) {
            return;
        }
        this.leftAnimEntity = leftAnimEntity;
        leftAnimEntity.leftAnimType = this.animType;
        this.tvName.setText(leftAnimEntity.userName);
        this.alAvatarBg.setBackgroundResource(getAvatarBgDrawableRes(leftAnimEntity.nobilityType));
        this.ivBadge.setImageResource(AppUtils.getNobilityEnterBadgeDrawableRes(leftAnimEntity.nobilityType));
        initViewTopPaddingLayoutParams(this.alAvatarBg, leftAnimEntity.nobilityType);
        this.tvName.setTextColor(ContextCompat.getColor(this.mContext, leftAnimEntity.nobilityType == 7 ? R$color.fq_nobility_red : R$color.fq_text_white_color));
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, leftAnimEntity.avatar);
        DrawableTextView drawableTextView = this.tvTips;
        Context context = this.mContext;
        drawableTextView.setText(context.getString(R$string.fq_nobility_open_left_anim_enter_tips, AppUtils.getNobilityBadgeName(context, leftAnimEntity.nobilityType)));
        int i2 = leftAnimEntity.nobilityType;
        if (i2 == 5) {
            i = R$drawable.fq_gradient_gj_shape;
        } else if (i2 != 6) {
            i = i2 != 7 ? -1 : R$drawable.fq_gradient_hd_shape;
        } else {
            i = R$drawable.fq_gradient_gw_shape;
        }
        if (i != -1) {
            this.tvTips.setTextDrawable(ContextCompat.getDrawable(this.mContext, i));
        }
        Observable.timer(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.view.custom.NobilityEnterDanmuView.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                NobilityEnterDanmuView nobilityEnterDanmuView = NobilityEnterDanmuView.this;
                nobilityEnterDanmuView.isShowing = true;
                nobilityEnterDanmuView.startAnim(nobilityEnterDanmuView.rootView, leftAnimEntity.nobilityType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnim(View view, int i) {
        float f = -view.getWidth();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, this.animPropertyName, f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, this.animPropertyName, 0.0f, f);
        ofFloat2.setStartDelay(getAnimDuration(i) - 2000);
        this.animatorSet.play(ofFloat).before(ofFloat2);
        this.animatorSet.setDuration(2000L);
        this.animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.NobilityEnterDanmuView.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                NobilityEnterDanmuView.this.startAnim();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                NobilityEnterDanmuView.this.endAnim();
            }
        });
        this.animatorSet.start();
    }

    @DrawableRes
    private int getAvatarBgDrawableRes(int i) {
        if (i != 5) {
            if (i == 6) {
                return R$drawable.fq_ic_nobility_open_avatar_bg_6;
            }
            if (i == 7) {
                return R$drawable.fq_ic_nobility_open_avatar_bg_7;
            }
            return R$drawable.fq_ic_nobility_open_avatar_bg_1;
        }
        return R$drawable.fq_ic_nobility_open_avatar_bg_5;
    }

    private void initViewTopPaddingLayoutParams(View view, int i) {
        view.setPadding(0, ConvertUtils.dp2px(getAvatarBgTopPadding(i)), 0, 0);
    }
}
