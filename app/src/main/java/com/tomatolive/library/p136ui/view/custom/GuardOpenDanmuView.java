package com.tomatolive.library.p136ui.view.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LeftAnimEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.GuardOpenDanmuView */
/* loaded from: classes3.dex */
public class GuardOpenDanmuView extends LeftAnimView {
    private long animDuration;
    private ImageView ivAvatar;
    private View rlBg;
    private View rootView;
    private TextView tvName;
    private TextView tvTip;

    public GuardOpenDanmuView(Context context) {
        this(context, null);
    }

    public GuardOpenDanmuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GuardOpenDanmuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.animDuration = 6000L;
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void initView(Context context) {
        this.animType = ConstantUtils.LEFT_ANIM_OPEN_GUARD_TYPE;
        this.rootView = RelativeLayout.inflate(context, R$layout.fq_layout_open_guard_anim_view, this);
        this.rlBg = findViewById(R$id.fq_rl_name_bg);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.tvName = (TextView) findViewById(R$id.fq_name);
        this.tvTip = (TextView) findViewById(R$id.fq_tv_open_guard_tip);
        setRootViewVisibility(4);
        this.tvName.setSelected(true);
    }

    @Override // com.tomatolive.library.p136ui.view.custom.LeftAnimView
    public void addItemInfo(LeftAnimEntity leftAnimEntity) {
        String string;
        if (leftAnimEntity == null) {
            return;
        }
        this.leftAnimEntity = leftAnimEntity;
        leftAnimEntity.leftAnimType = this.animType;
        this.tvName.setText(leftAnimEntity.userName);
        int i = R$color.fq_guard_mouth_text_bg;
        int i2 = R$drawable.fq_ic_guard_mouth_open_tip;
        String str = leftAnimEntity.guardType;
        char c = 65535;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                break;
        }
        if (c == 0) {
            string = this.mContext.getString(R$string.fq_guard_week);
        } else if (c == 1) {
            string = this.mContext.getString(R$string.fq_guard_month);
        } else if (c != 2) {
            return;
        } else {
            String string2 = this.mContext.getString(R$string.fq_guard_year);
            this.animDuration = 10000L;
            string = string2;
            i = R$color.fq_guard_year_text_bg;
            i2 = R$drawable.fq_ic_guard_year_open_tip;
        }
        String string3 = this.mContext.getString(R$string.fq_open_guard_tip);
        SpannableString spannableString = new SpannableString(string3 + string);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, i)), string3.length(), spannableString.length(), 33);
        this.tvTip.setText(spannableString);
        this.rlBg.setBackgroundResource(i2);
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, leftAnimEntity.avatar, R$drawable.fq_ic_placeholder_avatar);
        Observable.timer(500L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.view.custom.GuardOpenDanmuView.1
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
                GuardOpenDanmuView guardOpenDanmuView = GuardOpenDanmuView.this;
                guardOpenDanmuView.isShowing = false;
                guardOpenDanmuView.startAnim(guardOpenDanmuView.rootView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnim(View view) {
        float f = -view.getWidth();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, this.animPropertyName, f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, this.animPropertyName, 0.0f, f);
        ofFloat2.setStartDelay(this.animDuration - 2000);
        this.animatorSet.play(ofFloat).before(ofFloat2);
        this.animatorSet.setDuration(2000L);
        this.animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.tomatolive.library.ui.view.custom.GuardOpenDanmuView.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                GuardOpenDanmuView.this.startAnim();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                GuardOpenDanmuView.this.endAnim();
            }
        });
        this.animatorSet.start();
    }
}
