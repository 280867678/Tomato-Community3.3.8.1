package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.model.MessageDetailEntity;
import com.tomatolive.library.model.MyLiveEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.LoginEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.p136ui.activity.home.AnchorAuthActivity;
import com.tomatolive.library.p136ui.activity.home.AnchorAuthResultActivity;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenActivity;
import com.tomatolive.library.p136ui.activity.noble.NobilityPrivilegeActivity;
import com.tomatolive.library.p136ui.presenter.MyLivePresenter;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.iview.IMyLiveView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyLiveActivity */
/* loaded from: classes3.dex */
public class MyLiveActivity extends BaseActivity<MyLivePresenter> implements IMyLiveView {
    private boolean isAuthAnchor = false;
    private ImageView ivAwardsDot;
    private ImageView ivBadge;
    private ImageView ivDot;
    private ImageView ivGiveAwardsRed;
    private LinearLayout llAnchorBg;
    private LinearLayout llAnchorGradeBg;
    private LinearLayout llMyCarBg;
    private LinearLayout llMyTicketBg;
    private LinearLayout llNobilityCarBg;
    private LinearLayout ll_my_clan_bg;
    private FrameLayout rlMyNobility;
    private TextView tvAnchorGrade;
    private TextView tvAuth;
    private TextView tvGrade;
    private TextView tvMyIncome;
    private TextView tvMyNobility;

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onLiveHelperAppConfigFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyLivePresenter mo6636createPresenter() {
        return new MyLivePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_my_live;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live);
        this.tvMyNobility = (TextView) findViewById(R$id.tv_my_nobility);
        this.tvAuth = (TextView) findViewById(R$id.tv_auth);
        this.tvGrade = (TextView) findViewById(R$id.tv_live_grade);
        this.tvAnchorGrade = (TextView) findViewById(R$id.tv_live_anchor_grade);
        this.tvMyIncome = (TextView) findViewById(R$id.tv_my_income);
        this.llMyTicketBg = (LinearLayout) findViewById(R$id.ll_my_ticket_bg);
        this.llAnchorBg = (LinearLayout) findViewById(R$id.ll_anchor_bg);
        this.llAnchorGradeBg = (LinearLayout) findViewById(R$id.ll_anchor_grade_bg);
        this.llMyCarBg = (LinearLayout) findViewById(R$id.ll_my_car);
        this.ivBadge = (ImageView) findViewById(R$id.iv_badge);
        this.ivDot = (ImageView) findViewById(R$id.iv_dot);
        this.llNobilityCarBg = (LinearLayout) findViewById(R$id.ll_nobility_car_bg);
        this.rlMyNobility = (FrameLayout) findViewById(R$id.rl_my_nobility);
        this.ll_my_clan_bg = (LinearLayout) findViewById(R$id.ll_my_clan_bg);
        this.ivAwardsDot = (ImageView) findViewById(R$id.iv_awards_red);
        this.ivGiveAwardsRed = (ImageView) findViewById(R$id.iv_give_awards_red);
        this.ivDot.setSelected(true);
        initDataView();
        lambda$initListener$0$MyLiveActivity();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$dvBANTqcHviqCRngEVveTHs4wqw
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                MyLiveActivity.this.lambda$initListener$0$MyLiveActivity();
            }
        });
        findViewById(R$id.rl_live_grade).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$KD6w_VBgEBHF7RKdljrBGezKRpQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$1$MyLiveActivity(view);
            }
        });
        findViewById(R$id.rl_auth).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$1CpH1vCSVdSHBJ4Z9bzGJRBrmC0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$2$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_income_record).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$0UWsjA2hZXMPl74vZt_UGrRjp5I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$3$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_watch_record).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$O_C3uPKWrrE58k3TQtFP37gYu3Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$4$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_my_title).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$Tpe0Sv9MFq6KlefWfUUUiA_BnNg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$5$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_my_income).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$Jiu7O6AmF7-alalHi6wwqhYiB6Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$6$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_dedicate).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$_ods-C135x2rKgPy8kM32w7KFjg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$7$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_house_setting).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$PT1Nhj2rqO_kU2RjlLc8hKWZ2fg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$8$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_banned_setting).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$UqgI9yLt5mwKDpIsvaOOGWS4KgY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$9$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_live_pre_notice).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$eJ4lIsFHn3BCay8harVcIe-5eZM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$10$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_live_give_awards).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$vi33eetKQexLGhV8bjC8NxbpjR8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$11$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_my_ticket).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$tPdLm07oRpyroz1Ou-AGLfy4NCY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$12$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_awards).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$M6yv36MuoKdFB6NgcRjKqwSuiS4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$13$MyLiveActivity(view);
            }
        });
        findViewById(R$id.rl_live_anchor_grade).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$PniZUjGJWUsk2gh-rrtnerWxv6Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$14$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_my_clan).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$jMiqElzhJaVs9B2lUafqtx_BCY0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$15$MyLiveActivity(view);
            }
        });
        this.llMyCarBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$r6XWrPCvWM10CThkHxl6tpoSJH0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$16$MyLiveActivity(view);
            }
        });
        findViewById(R$id.rl_my_nobility).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyLiveActivity$T31DHQhptXlnABHqXtbnbFFZoX0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyLiveActivity.this.lambda$initListener$17$MyLiveActivity(view);
            }
        });
        findViewById(R$id.tv_anchor_college).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyLiveActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(((BaseActivity) MyLiveActivity.this).mContext, WebViewActivity.class);
                intent.putExtra("url", MyLiveActivity.this.getH5Url());
                intent.putExtra("title", MyLiveActivity.this.getString(R$string.fq_anchor_college));
                MyLiveActivity.this.startActivity(intent);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$MyLiveActivity(View view) {
        startActivityByRestrictionUserLogin(UserGradeActivity.class);
    }

    public /* synthetic */ void lambda$initListener$2$MyLiveActivity(View view) {
        if (!AppUtils.isLogin(this.mContext)) {
            return;
        }
        ((MyLivePresenter) this.mPresenter).onAnchorAuth();
    }

    public /* synthetic */ void lambda$initListener$3$MyLiveActivity(View view) {
        if (!AppUtils.isConsumptionPermissionUser(this.mContext)) {
            return;
        }
        Intent intent = new Intent(this.mContext, MyAccountActivity.class);
        intent.putExtra(ConstantUtils.IS_AUTH, this.isAuthAnchor);
        startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$4$MyLiveActivity(View view) {
        startActivityByRestrictionUserLogin(WatchRecordActivity.class);
    }

    public /* synthetic */ void lambda$initListener$5$MyLiveActivity(View view) {
        startActivityByRestrictionUserLogin(WearCenterActivity.class);
    }

    public /* synthetic */ void lambda$initListener$6$MyLiveActivity(View view) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onIncomeWithdrawalListener(this.mContext);
        }
    }

    public /* synthetic */ void lambda$initListener$7$MyLiveActivity(View view) {
        startActivityByLogin(DedicateTopActivity.class);
    }

    public /* synthetic */ void lambda$initListener$8$MyLiveActivity(View view) {
        startActivityByLogin(HouseSettingActivity.class);
    }

    public /* synthetic */ void lambda$initListener$9$MyLiveActivity(View view) {
        startActivityByLogin(BannedSettingActivity.class);
    }

    public /* synthetic */ void lambda$initListener$10$MyLiveActivity(View view) {
        startActivityByLogin(LivePreNoticeActivity.class);
    }

    public /* synthetic */ void lambda$initListener$11$MyLiveActivity(View view) {
        this.ivGiveAwardsRed.setVisibility(4);
        Intent intent = new Intent(this.mContext, AwardHistoryActivity.class);
        intent.putExtra(ConstantUtils.RESULT_FLAG, false);
        startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$12$MyLiveActivity(View view) {
        startActivityByLogin(MyTicketActivity.class);
    }

    public /* synthetic */ void lambda$initListener$13$MyLiveActivity(View view) {
        this.ivAwardsDot.setVisibility(4);
        Intent intent = new Intent(this.mContext, AwardHistoryActivity.class);
        intent.putExtra(ConstantUtils.RESULT_FLAG, true);
        startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$14$MyLiveActivity(View view) {
        startActivityByLogin(AnchorGradeActivity.class);
    }

    public /* synthetic */ void lambda$initListener$15$MyLiveActivity(View view) {
        startActivityByLogin(MyClanActivity.class);
    }

    public /* synthetic */ void lambda$initListener$16$MyLiveActivity(View view) {
        if (!AppUtils.isConsumptionPermissionUser(this.mContext)) {
            return;
        }
        Intent intent = new Intent(this.mContext, CarMallActivity.class);
        intent.putExtra(ConstantUtils.RESULT_FLAG, false);
        startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$17$MyLiveActivity(View view) {
        if (!AppUtils.isConsumptionPermissionUser(this.mContext)) {
            return;
        }
        if (SysConfigInfoManager.getInstance().isEnableNobilityGuide()) {
            SysConfigInfoManager.getInstance().setEnableNobilityGuide(false);
            this.ivDot.setVisibility(4);
        }
        if (AppUtils.isNobilityUser()) {
            startActivity(NobilityPrivilegeActivity.class);
        } else {
            startActivity(NobilityOpenActivity.class);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onDataSuccess(MyLiveEntity myLiveEntity) {
        if (myLiveEntity == null) {
            return;
        }
        SysConfigInfoManager.getInstance().setEnableCar(myLiveEntity.openCar);
        this.isAuthAnchor = TextUtils.equals("1", myLiveEntity.role);
        this.tvAuth.setText(this.isAuthAnchor ? R$string.fq_already_identy : R$string.fq_no_identy);
        this.tvAuth.setTextColor(ContextCompat.getColor(this.mContext, this.isAuthAnchor ? R$color.fq_text_gray : R$color.fq_colorRed));
        if (this.isAuthAnchor) {
            ((MyLivePresenter) this.mPresenter).getAnchorGradeData();
            ((MyLivePresenter) this.mPresenter).getUnReadFlag(false, new ResultCallBack<MessageDetailEntity>() { // from class: com.tomatolive.library.ui.activity.mylive.MyLiveActivity.2
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(MessageDetailEntity messageDetailEntity) {
                    if (messageDetailEntity != null && messageDetailEntity.isUnReadFlag()) {
                        MyLiveActivity.this.ivGiveAwardsRed.setVisibility(0);
                    }
                }
            });
        }
        this.llAnchorBg.setVisibility(this.isAuthAnchor ? 0 : 8);
        this.llAnchorGradeBg.setVisibility(this.isAuthAnchor ? 0 : 8);
        this.tvMyIncome.setVisibility(this.isAuthAnchor ? 0 : 8);
        this.llMyCarBg.setVisibility(myLiveEntity.openCar ? 0 : 8);
        if (!AppUtils.isEnableNobility() && !myLiveEntity.openCar) {
            this.llNobilityCarBg.setVisibility(8);
        } else {
            this.llNobilityCarBg.setVisibility(0);
        }
        if (!TextUtils.equals(myLiveEntity.role, "4")) {
            return;
        }
        this.llAnchorBg.setVisibility(8);
        this.llAnchorGradeBg.setVisibility(8);
        this.tvMyIncome.setVisibility(0);
        this.ll_my_clan_bg.setVisibility(0);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onAnchorAuthSuccess(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return;
        }
        int i = anchorEntity.isChecked;
        if (i == -2) {
            Intent intent = new Intent(this.mContext, AnchorAuthActivity.class);
            intent.putExtra(ConstantUtils.AUTH_TYPE, anchorEntity.isChecked);
            startActivity(intent);
        } else if (i == -1 || i == 0) {
            Intent intent2 = new Intent(this.mContext, AnchorAuthResultActivity.class);
            intent2.putExtra(ConstantUtils.AUTH_TYPE, anchorEntity.isChecked);
            startActivity(intent2);
        } else if (i != 1) {
        } else {
            if (anchorEntity.isFrozenFlag()) {
                WarnDialog.newInstance(WarnDialog.FROZEN_TIP).show(getSupportFragmentManager());
            } else if (AppUtils.isEnableLiveHelperJump()) {
                ((MyLivePresenter) this.mPresenter).getLiveHelperAppConfig();
            } else {
                startActivity(PrepareLiveActivity.class);
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onUserGradeSuccess(UserEntity userEntity) {
        this.tvGrade.setText(String.format(getString(R$string.fq_lv), AppUtils.formatExpGrade(userEntity.expGrade)));
        UserInfoManager.getInstance().setNobilityType(userEntity.nobilityType);
        UserInfoManager.getInstance().setExpGrade(userEntity.expGrade);
        initShowBadgeImg();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onAnchorGradeSuccess(AnchorEntity anchorEntity) {
        this.tvAnchorGrade.setText(String.format(getString(R$string.fq_lv), AppUtils.formatExpGrade(anchorEntity.expGrade)));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyLiveView
    public void onLiveHelperAppConfigSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
        if (liveHelperAppConfigEntity == null) {
            return;
        }
        AppUtils.toLiveHelperApp(this.mContext, liveHelperAppConfigEntity.androidPackageName, liveHelperAppConfigEntity.startLiveAppDownloadUrl, getSupportFragmentManager());
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof LoginEvent) {
            initDataView();
            lambda$initListener$0$MyLiveActivity();
        } else if (!(baseEvent instanceof NobilityOpenEvent)) {
        } else {
            NobilityOpenEvent nobilityOpenEvent = (NobilityOpenEvent) baseEvent;
            if (nobilityOpenEvent.isOpenSuccess) {
                showToast(nobilityOpenEvent.toastTips);
            }
            initShowBadgeImg();
        }
    }

    private void initDataView() {
        this.tvMyNobility.setText(AppUtils.isNobilityUser() ? R$string.fq_nobility_my : R$string.fq_text_live_nobility);
        int i = 0;
        this.rlMyNobility.setVisibility(AppUtils.isEnableNobility() ? 0 : 8);
        ImageView imageView = this.ivDot;
        if (!SysConfigInfoManager.getInstance().isEnableNobilityGuide()) {
            i = 4;
        }
        imageView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendRequest */
    public void lambda$initListener$0$MyLiveActivity() {
        ((MyLivePresenter) this.mPresenter).initData(this.mStateView, true);
        ((MyLivePresenter) this.mPresenter).getUserGradeData();
        ((MyLivePresenter) this.mPresenter).getUnReadFlag(true, new ResultCallBack<MessageDetailEntity>() { // from class: com.tomatolive.library.ui.activity.mylive.MyLiveActivity.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MessageDetailEntity messageDetailEntity) {
                if (messageDetailEntity != null && messageDetailEntity.isUnReadFlag()) {
                    MyLiveActivity.this.ivAwardsDot.setVisibility(0);
                }
            }
        });
    }

    private void initShowBadgeImg() {
        int nobilityType = UserInfoManager.getInstance().getNobilityType();
        if (!SysConfigInfoManager.getInstance().isEnableNobilityGuide() && AppUtils.isNobilityUser(nobilityType)) {
            this.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(nobilityType));
        } else {
            this.ivBadge.setImageResource(R$drawable.fq_ic_my_live_default_nobility);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getH5Url() {
        return AppUtils.getUploadUrl() + "html/dist/index.html?" + System.currentTimeMillis();
    }
}
