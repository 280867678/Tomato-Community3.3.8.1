package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.presenter.UserGradePresenter;
import com.tomatolive.library.p136ui.view.iview.IUserGradeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.text.NumberFormat;

/* renamed from: com.tomatolive.library.ui.activity.mylive.UserGradeActivity */
/* loaded from: classes3.dex */
public class UserGradeActivity extends BaseActivity<UserGradePresenter> implements IUserGradeView {
    private ImageView ivAvatar;
    private ImageView ivGrade;
    private ProgressBar progressBar;
    private LinearLayout rlGradeBg;
    private TextView tvExperience;
    private TextView tvGrade;
    private TextView tvLv;
    private TextView tvNickName;
    private TextView tvPercentage;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public UserGradePresenter mo6636createPresenter() {
        return new UserGradePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_user_grade;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_user_grade);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.ivGrade = (ImageView) findViewById(R$id.iv_grade);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvGrade = (TextView) findViewById(R$id.tv_grade);
        this.tvExperience = (TextView) findViewById(R$id.tv_experience);
        this.tvLv = (TextView) findViewById(R$id.tv_lv);
        this.tvPercentage = (TextView) findViewById(R$id.tv_percentage);
        this.rlGradeBg = (LinearLayout) findViewById(R$id.rl_grade_bg);
        this.progressBar = (ProgressBar) findViewById(R$id.progressBar);
        ((TextView) findViewById(R$id.tv_send_gift_tips)).setText(getString(R$string.fq_my_live_send_gift_tips, new Object[]{AppUtils.getLiveMoneyUnitStr(this.mContext)}));
        ((UserGradePresenter) this.mPresenter).getData(this.mStateView, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.UserGradeActivity.1
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                ((UserGradePresenter) ((BaseActivity) UserGradeActivity.this).mPresenter).getData(((BaseActivity) UserGradeActivity.this).mStateView, true);
            }
        });
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IUserGradeView
    public void onDataSuccess(UserEntity userEntity) {
        GlideUtils.loadAvatar(this.mContext, this.ivAvatar, userEntity.avatar, R$drawable.fq_ic_placeholder_avatar_white);
        this.tvNickName.setText(userEntity.name);
        this.tvExperience.setText(Html.fromHtml(getExperienceTips(userEntity)));
        this.tvLv.setText(String.format(getString(R$string.fq_lv), AppUtils.formatExpGrade(userEntity.expGrade)));
        this.progressBar.setMax(NumberUtils.string2int(userEntity.nextGradeExp, 100));
        this.progressBar.setProgress(NumberUtils.string2int(userEntity.exp, 1));
        initUserGrade(NumberUtils.string2int(AppUtils.formatExpGrade(userEntity.expGrade)));
        if (isMaxGrade(userEntity)) {
            this.tvPercentage.setText("∞");
            return;
        }
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMinimumFractionDigits(1);
        double string2Double = NumberUtils.string2Double(userEntity.exp) / NumberUtils.string2Double(userEntity.nextGradeExp);
        if (Double.isNaN(string2Double) || Double.isInfinite(string2Double)) {
            this.tvPercentage.setText("0%");
        } else if (this.progressBar.getProgress() >= this.progressBar.getMax()) {
            this.tvPercentage.setText("100%");
        } else {
            this.tvPercentage.setText(percentInstance.format(string2Double));
        }
    }

    private void initUserGrade(int i) {
        AppUtils.formatTvNumTypeface(this.mContext, this.tvGrade, "");
        this.tvGrade.setText(String.valueOf(i));
        this.rlGradeBg.setBackgroundResource(AppUtils.getUserGradeBackgroundResource(false, i));
        this.ivGrade.setImageResource(AppUtils.getUserGradeIconResource(false, i));
    }

    private String getExperienceTips(UserEntity userEntity) {
        int string2int = NumberUtils.string2int(userEntity.nextGradeExp, 0) - NumberUtils.string2int(userEntity.exp, 0);
        if (string2int < 0) {
            string2int = Math.abs(string2int);
        }
        return isMaxGrade(userEntity) ? getString(R$string.fq_my_live_grade_current_experience_tips, new Object[]{String.valueOf(userEntity.exp)}) : getString(R$string.fq_my_live_grade_experience_tips, new Object[]{String.valueOf(string2int), String.valueOf(getNextExpGrade(userEntity.expGrade))});
    }

    private int getExpGrade(String str) {
        return NumberUtils.string2int(AppUtils.formatExpGrade(str), 1);
    }

    private int getNextExpGrade(String str) {
        int expGrade = getExpGrade(str) + 1;
        int i = AppUtils.USER_GRADE_MAX;
        return expGrade > i ? i : expGrade;
    }

    private boolean isMaxGrade(UserEntity userEntity) {
        return (AppUtils.isUserGradeMax60() && getExpGrade(userEntity.expGrade) >= 60) || (AppUtils.isUserGradeMax120() && getExpGrade(userEntity.expGrade) >= 120);
    }
}
