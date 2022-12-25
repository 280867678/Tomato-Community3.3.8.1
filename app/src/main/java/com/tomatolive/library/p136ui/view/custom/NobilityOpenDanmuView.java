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
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.NobilityOpenDanmuView */
/* loaded from: classes3.dex */
public class NobilityOpenDanmuView extends LeftAnimView {
    private RelativeLayout alAvatarBg;
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private View rootView;
    private TextView tvName;
    private TextView tvTips;

    private long getAnimDuration(int i) {
        switch (i) {
            case 1:
                return 6000L;
            case 2:
                return 10000L;
            case 3:
                return 12000L;
            case 4:
                return 14000L;
            case 5:
                return 16000L;
            case 6:
                return 18000L;
            case 7:
                return 20000L;
            default:
                return 6000L;
        }
    }

    private int getAvatarBgTopPadding(int i) {
        if (i != 6) {
            return i != 7 ? 17 : 23;
        }
        return 20;
    }

    public NobilityOpenDanmuView(Context context) {
        super(context);
    }

    public NobilityOpenDanmuView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void initView(Context context) {
        this.animType = ConstantUtils.LEFT_ANIM_OPEN_NOBILITY_TYPE;
        this.rootView = RelativeLayout.inflate(context, R$layout.fq_layout_open_nobility_anim_view, this);
        this.alAvatarBg = (RelativeLayout) findViewById(R$id.rl_nobility_avatar_bg);
        this.tvName = (TextView) findViewById(R$id.tv_name);
        this.tvTips = (TextView) findViewById(R$id.tv_tips);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        setRootViewVisibility(4);
        this.tvName.setSelected(true);
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void addItemInfo(final LeftAnimEntity leftAnimEntity) {
        if (leftAnimEntity == null) {
            return;
        }
        this.leftAnimEntity = leftAnimEntity;
        leftAnimEntity.leftAnimType = this.animType;
        this.tvName.setText(leftAnimEntity.userName);
        this.alAvatarBg.setBackgroundResource(getAvatarBgDrawableRes(leftAnimEntity.nobilityType));
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(leftAnimEntity.nobilityType));
        initViewTopPaddingLayoutParams(this.alAvatarBg, leftAnimEntity.nobilityType);
        this.tvName.setTextColor(ContextCompat.getColor(this.mContext, leftAnimEntity.nobilityType == 7 ? R$color.fq_nobility_red : R$color.fq_text_white_color));
        this.tvTips.setTextColor(ContextCompat.getColor(this.mContext, leftAnimEntity.nobilityType == 7 ? R$color.fq_nobility_red : R$color.fq_text_white_color));
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, leftAnimEntity.avatar);
        TextView textView = this.tvTips;
        Context context = this.mContext;
        textView.setText(context.getString(R$string.fq_nobility_open_left_anim_tips, AppUtils.getNobilityBadgeName(context, leftAnimEntity.nobilityType)));
        Observable.timer(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.view.custom.NobilityOpenDanmuView.1
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
                NobilityOpenDanmuView nobilityOpenDanmuView = NobilityOpenDanmuView.this;
                nobilityOpenDanmuView.isShowing = true;
                nobilityOpenDanmuView.startAnim(nobilityOpenDanmuView.rootView, leftAnimEntity.nobilityType);
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
        this.animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.NobilityOpenDanmuView.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                NobilityOpenDanmuView.this.startAnim();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                NobilityOpenDanmuView.this.endAnim();
            }
        });
        this.animatorSet.start();
    }

    @DrawableRes
    private int getAvatarBgDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_open_avatar_bg_1;
            case 2:
                return R$drawable.fq_ic_nobility_open_avatar_bg_2;
            case 3:
                return R$drawable.fq_ic_nobility_open_avatar_bg_3;
            case 4:
                return R$drawable.fq_ic_nobility_open_avatar_bg_4;
            case 5:
                return R$drawable.fq_ic_nobility_open_avatar_bg_5;
            case 6:
                return R$drawable.fq_ic_nobility_open_avatar_bg_6;
            case 7:
                return R$drawable.fq_ic_nobility_open_avatar_bg_7;
            default:
                return R$drawable.fq_ic_nobility_open_avatar_bg_1;
        }
    }

    private void initViewTopPaddingLayoutParams(View view, int i) {
        view.setPadding(0, ConvertUtils.dp2px(getAvatarBgTopPadding(i)), 0, 0);
    }
}
