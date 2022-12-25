package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.presenter.AnchorGradePresenter;
import com.tomatolive.library.p136ui.view.custom.AnchorGradeView;
import com.tomatolive.library.p136ui.view.iview.IAnchorGradeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.activity.mylive.AnchorGradeActivity */
/* loaded from: classes3.dex */
public class AnchorGradeActivity extends BaseActivity<AnchorGradePresenter> implements IAnchorGradeView {
    private AnchorGradeView currentGradeView;
    private ImageView ivAvatar;
    private AnchorGradeView nextGradeView;
    private TextView tvCurrentLv;
    private TextView tvExperience;
    private TextView tvNextLv;
    private TextView tvNickName;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public AnchorGradePresenter mo6636createPresenter() {
        return new AnchorGradePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_anchor_grade;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_anchor_grade);
        this.tvNickName = (TextView) findViewById(R$id.tv_nick_name);
        this.tvExperience = (TextView) findViewById(R$id.tv_experience);
        this.tvCurrentLv = (TextView) findViewById(R$id.tv_current_lv);
        this.tvNextLv = (TextView) findViewById(R$id.tv_next_lv);
        this.ivAvatar = (ImageView) findViewById(R$id.iv_avatar);
        this.currentGradeView = (AnchorGradeView) findViewById(R$id.current_grade_view);
        this.nextGradeView = (AnchorGradeView) findViewById(R$id.next_grade_view);
        ((AnchorGradePresenter) this.mPresenter).getData(this.mStateView, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AnchorGradeActivity$FptS93L2JXZwuHDBhWY5Vgv_wJs
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                AnchorGradeActivity.this.lambda$initListener$0$AnchorGradeActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$AnchorGradeActivity() {
        ((AnchorGradePresenter) this.mPresenter).getData(this.mStateView, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorGradeView
    public void onDataSuccess(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return;
        }
        Context context = this.mContext;
        GlideUtils.loadAvatar(context, this.ivAvatar, anchorEntity.avatar, 6, ContextCompat.getColor(context, R$color.fq_colorWhite));
        this.tvNickName.setText(anchorEntity.nickname);
        this.tvExperience.setText(getExperienceTips(anchorEntity));
        this.tvCurrentLv.setText(getCurrentLv(anchorEntity));
        this.tvNextLv.setText(getNextLv(anchorEntity));
        if (AppUtils.isMaxGradeForAnchor(anchorEntity.expGrade)) {
            this.nextGradeView.setVisibility(4);
            this.currentGradeView.initUserGrade(getExpGrade(String.valueOf(30)));
        } else {
            this.nextGradeView.setVisibility(0);
            this.currentGradeView.initUserGrade(getExpGrade(anchorEntity.expGrade));
        }
        this.nextGradeView.initUserGrade(getNextExpGrade(anchorEntity.expGrade));
    }

    private int getExpGrade(String str) {
        return NumberUtils.string2int(AppUtils.formatExpGrade(str), 1);
    }

    private int getNextExpGrade(String str) {
        int expGrade = getExpGrade(str) + 1;
        if (expGrade > 30) {
            return 30;
        }
        return expGrade;
    }

    private String getCurrentLv(AnchorEntity anchorEntity) {
        return AppUtils.isMaxGradeForAnchor(anchorEntity.expGrade) ? getString(R$string.fq_my_live_grade_current_exp, new Object[]{String.valueOf(30), AppUtils.formatExp(anchorEntity.exp)}) : getString(R$string.fq_my_live_grade_current_exp, new Object[]{AppUtils.formatExpGrade(anchorEntity.expGrade), AppUtils.formatExp(anchorEntity.exp)});
    }

    private String getNextLv(AnchorEntity anchorEntity) {
        if (AppUtils.isMaxGradeForAnchor(anchorEntity.expGrade)) {
            return "";
        }
        return getString(R$string.fq_my_live_grade_current_exp, new Object[]{String.valueOf(getNextExpGrade(anchorEntity.expGrade)), AppUtils.formatExp(anchorEntity.nextGradeExp)});
    }

    private String getExperienceTips(AnchorEntity anchorEntity) {
        if (AppUtils.isMaxGradeForAnchor(anchorEntity.expGrade)) {
            return getString(R$string.fq_my_live_grade_current_next_exp_tips, new Object[]{String.valueOf(anchorEntity.exp)});
        }
        int string2int = NumberUtils.string2int(anchorEntity.nextGradeExp, 0) - NumberUtils.string2int(anchorEntity.exp, 0);
        if (string2int < 0) {
            string2int = Math.abs(string2int);
        }
        return getString(R$string.fq_my_live_grade_next_exp_tips, new Object[]{String.valueOf(string2int), String.valueOf(getNextExpGrade(anchorEntity.expGrade))});
    }
}
