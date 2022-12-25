package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.UpdateBalanceEvent;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenRecordActivity;
import com.tomatolive.library.p136ui.presenter.MyAccountPresenter;
import com.tomatolive.library.p136ui.view.dialog.NobilityGoldUseDescDialog;
import com.tomatolive.library.p136ui.view.iview.IMyAccountView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.RxViewUtils;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyAccountActivity */
/* loaded from: classes3.dex */
public class MyAccountActivity extends BaseActivity<MyAccountPresenter> implements IMyAccountView {
    private boolean isAuth = false;
    private TextView tvFrozen;
    private TextView tvGold;
    private TextView tvGoldGeneral;
    private TextView tvGoldNobility;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyAccountView
    public void onUserOverFail() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyAccountPresenter mo6636createPresenter() {
        return new MyAccountPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_my_account;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.isAuth = getIntent().getBooleanExtra(ConstantUtils.IS_AUTH, false);
        this.tvGold = (TextView) findViewById(R$id.tv_gold);
        this.tvGoldGeneral = (TextView) findViewById(R$id.tv_gold_general);
        this.tvGoldNobility = (TextView) findViewById(R$id.tv_gold_nobility);
        this.tvFrozen = (TextView) findViewById(R$id.tv_frozen);
        setActivityTitle(getString(R$string.fq_my_account));
        ((MyAccountPresenter) this.mPresenter).getUserOver(this.mStateView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyAccountActivity$akA2kT5ifqPHXZqxKK25LMr0hOg
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                MyAccountActivity.this.lambda$initListener$0$MyAccountActivity();
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_nobility_open), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.MyAccountActivity.1
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                MyAccountActivity.this.startActivity(NobilityOpenRecordActivity.class);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_income_record), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.MyAccountActivity.2
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                Intent intent = new Intent(((BaseActivity) MyAccountActivity.this).mContext, IncomeRecordActivity.class);
                intent.putExtra(ConstantUtils.IS_AUTH, MyAccountActivity.this.isAuth);
                MyAccountActivity.this.startActivity(intent);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_recharge), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.MyAccountActivity.3
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                AppUtils.onRechargeListener(((BaseActivity) MyAccountActivity.this).mContext);
                LogEventUtils.uploadRechargeClick(MyAccountActivity.this.getString(R$string.fq_my_account_recharge_entrance));
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_help), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.MyAccountActivity.4
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                NobilityGoldUseDescDialog.newInstance(NobilityGoldUseDescDialog.TYPE_GOLD).show(MyAccountActivity.this.getSupportFragmentManager());
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$MyAccountActivity() {
        ((MyAccountPresenter) this.mPresenter).getUserOver(this.mStateView);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyAccountView
    public void onUserOverSuccess(MyAccountEntity myAccountEntity) {
        this.tvFrozen.setVisibility(myAccountEntity.isFrozen() ? 0 : 4);
        this.tvGold.setText(AppUtils.formatDisplayPrice(myAccountEntity.getAccountBalance(), true));
        this.tvGoldGeneral.setText(Html.fromHtml(getString(R$string.fq_my_account_virtual_currency_general, new Object[]{AppUtils.formatDisplayPrice(myAccountEntity.price, true)})));
        this.tvGoldNobility.setText(Html.fromHtml(getString(R$string.fq_my_account_virtual_currency_nobility, new Object[]{AppUtils.formatDisplayPrice(myAccountEntity.nobilityPrice, true)})));
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof UpdateBalanceEvent) {
            ((MyAccountPresenter) this.mPresenter).getUserOver(this.mStateView);
        }
    }
}
