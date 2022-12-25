package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AppealInfoEntity;
import com.tomatolive.library.p136ui.adapter.AppealHistoryAdapter;
import com.tomatolive.library.p136ui.presenter.AppealDetailPresenter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogBuilder;
import com.tomatolive.library.p136ui.view.iview.IAppealInfoView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.activity.mylive.AppealDetailActivity */
/* loaded from: classes3.dex */
public class AppealDetailActivity extends BaseActivity<AppealDetailPresenter> implements IAppealInfoView {
    private int appealStatus;
    private String mAppealId;
    private ImageView[] mImageView = new ImageView[3];
    private TextView mTvPlaceHolderTip;

    private boolean isShowCancelBtn(int i) {
        return i == 1 || i == 2;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealInfoView
    public void onGetAppealInfoFailure(int i, String str) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public AppealDetailPresenter mo6636createPresenter() {
        return new AppealDetailPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_appeal_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        Intent intent = getIntent();
        this.mAppealId = intent.getStringExtra(ConstantUtils.RESULT_ID);
        this.appealStatus = intent.getIntExtra(ConstantUtils.RESULT_FLAG, 0);
        if (isShowCancelBtn(this.appealStatus)) {
            setActivityRightTitle(R$string.fq_hd_appeal_detail, R$string.fq_hd_cancel_appeal, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AppealDetailActivity$Y-YxqWU7-QgUwpjvA23k3TITIQA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppealDetailActivity.this.lambda$initView$1$AppealDetailActivity(view);
                }
            });
        } else {
            setActivityTitle(R$string.fq_hd_appeal_detail);
        }
        findViewById(R$id.et_appeal_content).setVisibility(8);
        findViewById(R$id.tv_current_max_length).setVisibility(8);
        findViewById(R$id.tv_appeal_tip).setVisibility(8);
        findViewById(R$id.tv_submit_appeal).setVisibility(8);
        this.mTvPlaceHolderTip = (TextView) findViewById(R$id.tv_placeholder_tip);
        this.mTvPlaceHolderTip.setVisibility(0);
        this.mImageView[0] = (ImageView) findViewById(R$id.iv_appeal_img0);
        this.mImageView[0].setImageResource(R$drawable.fq_hd_ic_no_img_placeholder);
        this.mImageView[1] = (ImageView) findViewById(R$id.iv_appeal_img1);
        this.mImageView[2] = (ImageView) findViewById(R$id.iv_appeal_img2);
        sendRequest();
    }

    public /* synthetic */ void lambda$initView$1$AppealDetailActivity(View view) {
        new BaseDialogBuilder().setTitleRes(R$string.fq_hd_cancel_appeal).setContentRes(R$string.fq_hd_cancel_appeal_content).setPositiveBtnRes(R$string.fq_hd_sure_submit).setPositiveBtnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AppealDetailActivity$ZPlYB0G7-sieDEjKOm96-v8py3E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AppealDetailActivity.this.lambda$null$0$AppealDetailActivity(view2);
            }
        }).build().show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$null$0$AppealDetailActivity(View view) {
        ((AppealDetailPresenter) this.mPresenter).cancelAppeal(this.mAppealId);
    }

    private void sendRequest() {
        ((AppealDetailPresenter) this.mPresenter).getAppealInfo(this.mStateView, this.mAppealId, true);
    }

    private String getDrawTypeText(String str) {
        if ("1".equals(str)) {
            return getString(R$string.fq_hd_gift_lottery);
        }
        return getString(R$string.fq_hd_gift_lottery);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealInfoView
    public void onGetAppealInfoSuccess(AppealInfoEntity appealInfoEntity) {
        ((TextView) findViewById(R$id.tv_prize_name)).setText(appealInfoEntity.getPrizeName());
        ((TextView) findViewById(R$id.tv_live_name)).setText(appealInfoEntity.getLiveId());
        ((TextView) findViewById(R$id.tv_winning_time)).setText(DateUtils.getTimeStrFromLongSecond(appealInfoEntity.getWinningTime(), DateUtils.C_TIME_PATTON_DEFAULT_2));
        ((TextView) findViewById(R$id.tv_live_draw_type)).setText(getDrawTypeText(appealInfoEntity.getLiveDrawType()));
        ((TextView) findViewById(R$id.tv_user_name)).setText(getString(R$string.fq_hd_name_id, new Object[]{appealInfoEntity.getAnchorName(), appealInfoEntity.getAnchorOpenId()}));
        TextView textView = (TextView) findViewById(R$id.tv_appeal_status);
        textView.setText(AppealHistoryAdapter.getStatusStr(this.mContext, appealInfoEntity.getAppealStatus()));
        textView.setTextColor(ContextCompat.getColor(this.mContext, AppealHistoryAdapter.getStatusColorRes(appealInfoEntity.getAppealStatus())));
        ((TextView) findViewById(R$id.tv_appeal_desc)).setText(appealInfoEntity.getAppealDesc());
        if (hasImage(appealInfoEntity)) {
            this.mTvPlaceHolderTip.setVisibility(8);
        }
        if (appealInfoEntity.getImage1() != null && !appealInfoEntity.getImage1().isEmpty()) {
            GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) findViewById(R$id.iv_appeal_img0), appealInfoEntity.getImage1(), 4);
        }
        if (appealInfoEntity.getImage2() != null && !appealInfoEntity.getImage2().isEmpty()) {
            GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) findViewById(R$id.iv_appeal_img1), appealInfoEntity.getImage2(), 4);
        }
        if (appealInfoEntity.getImage3() == null || appealInfoEntity.getImage3().isEmpty()) {
            return;
        }
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) findViewById(R$id.iv_appeal_img2), appealInfoEntity.getImage3(), 4);
    }

    private boolean hasImage(AppealInfoEntity appealInfoEntity) {
        return (appealInfoEntity.getImage1() != null && !appealInfoEntity.getImage1().isEmpty()) || (appealInfoEntity.getImage2() != null && !appealInfoEntity.getImage2().isEmpty()) || (appealInfoEntity.getImage3() != null && !appealInfoEntity.getImage3().isEmpty());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealInfoView
    public void onCancelAppealSuccess() {
        showToast(R$string.fq_hd_cancel_appeal_success);
        finish();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealInfoView
    public void onCancelAppealFailure() {
        showToast(R$string.fq_hd_cancel_appeal_failure);
    }
}
