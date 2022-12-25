package com.tomatolive.library.p136ui.view.headview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ScreenUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.headview.CarMallHeadView */
/* loaded from: classes3.dex */
public class CarMallHeadView extends LinearLayout {
    private AnimatorSet mCarNoticeAnimatorSet;
    private Context mContext;
    private TextView mTvTopNotice;

    private long formatAnimatorSetDuration(long j) {
        return j;
    }

    public CarMallHeadView(Context context) {
        super(context);
        initView(context);
    }

    public CarMallHeadView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(context, R$layout.fq_layout_head_view_car_mall, this);
        this.mContext = context;
        this.mTvTopNotice = (TextView) findViewById(R$id.tv_notice_tips);
        setVisibility(4);
    }

    public void setCarNoticeAnimListener(Animator.AnimatorListener animatorListener) {
        if (animatorListener != null) {
            int screenWidth = ScreenUtils.getScreenWidth();
            this.mCarNoticeAnimatorSet = new AnimatorSet();
            this.mCarNoticeAnimatorSet.play(ObjectAnimator.ofFloat(this.mTvTopNotice, "translationX", screenWidth, -screenWidth));
            this.mCarNoticeAnimatorSet.addListener(animatorListener);
        }
    }

    public void setCarNoticeNoticeAnim(String str, String str2, String str3, long j) {
        this.mTvTopNotice.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_car_top_notice_tips, AppUtils.formatUserNickName(str), str2, str3)));
        AnimatorSet animatorSet = this.mCarNoticeAnimatorSet;
        if (animatorSet != null) {
            animatorSet.setDuration(formatAnimatorSetDuration(j));
            this.mCarNoticeAnimatorSet.start();
        }
    }

    public void onDestroy() {
        AnimatorSet animatorSet = this.mCarNoticeAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mCarNoticeAnimatorSet.removeAllListeners();
            this.mCarNoticeAnimatorSet = null;
        }
    }
}
