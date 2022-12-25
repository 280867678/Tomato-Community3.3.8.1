package com.tomatolive.library.p136ui.activity.noble;

import android.os.Bundle;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.model.event.UpdateBalanceEvent;
import com.tomatolive.library.p136ui.adapter.NobilityOpenOrderAdapter;
import com.tomatolive.library.p136ui.presenter.NobilityOpenOrderPresenter;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerNobilityOpenOrderGrid;
import com.tomatolive.library.p136ui.view.headview.NobilityOrderHeadView;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobilityOpenOrderActivity */
/* loaded from: classes3.dex */
public class NobilityOpenOrderActivity extends BaseActivity<NobilityOpenOrderPresenter> implements INobilityOpenOrderView {
    private String anchorId;
    private NobilityEntity currentSelectedItem;
    private String liveCount;
    private NobilityOpenOrderAdapter mAdapter;
    private NobilityOrderHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private TextView tvNowPay;
    private TextView tvOpenTips;
    private String userOver = "0";
    private String currentCarGold = "";

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onNobilityListFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NobilityOpenOrderPresenter mo6636createPresenter() {
        return new NobilityOpenOrderPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_open_order;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.currentSelectedItem = (NobilityEntity) getIntent().getParcelableExtra(ConstantUtils.RESULT_ITEM);
        this.tvOpenTips = (TextView) findViewById(R$id.tv_open_tips);
        this.tvNowPay = (TextView) findViewById(R$id.tv_now_pay);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        setActivityTitle(getString(R$string.fq_nobility_open_order));
        initAdapter();
        this.tvNowPay.setText(getString(R$string.fq_nobility_open_now_pay, new Object[]{"", ""}));
        ((NobilityOpenOrderPresenter) this.mPresenter).getInitData(this.mStateView, true);
        ((NobilityOpenOrderPresenter) this.mPresenter).getUserOver();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenOrderActivity$tsjteVPbXZrhLPkYIqJSDgF1_h8
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                NobilityOpenOrderActivity.this.lambda$initListener$0$NobilityOpenOrderActivity();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenOrderActivity$gUF9UqANzrpTzBgHtos4TJfRFGw
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NobilityOpenOrderActivity.this.lambda$initListener$1$NobilityOpenOrderActivity(baseQuickAdapter, view, i);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvNowPay, 1000, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.noble.NobilityOpenOrderActivity.1
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (!TextUtils.isEmpty(NobilityOpenOrderActivity.this.currentCarGold)) {
                    NobilityOpenOrderActivity.this.currentSelectedItem.isRenew();
                    if (NumberUtils.string2long(NobilityOpenOrderActivity.this.userOver) == 0 || NumberUtils.string2long(NobilityOpenOrderActivity.this.userOver) < NumberUtils.string2long(NobilityOpenOrderActivity.this.currentCarGold)) {
                        NobilityOpenOrderActivity.this.goToRecharge();
                        return;
                    }
                    NobilityOpenOrderActivity.this.tvNowPay.setEnabled(false);
                    ((NobilityOpenOrderPresenter) ((BaseActivity) NobilityOpenOrderActivity.this).mPresenter).getNobilityBuy(NobilityOpenOrderActivity.this.currentSelectedItem.type, NobilityOpenOrderActivity.this.anchorId, NobilityOpenOrderActivity.this.currentSelectedItem.isRenew() ? "2" : "1", NobilityOpenOrderActivity.this.liveCount);
                    return;
                }
                NobilityOpenOrderActivity.this.showToast(R$string.fq_nobility_open_selected_item);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityOpenOrderActivity() {
        ((NobilityOpenOrderPresenter) this.mPresenter).getInitData(this.mStateView, true);
        ((NobilityOpenOrderPresenter) this.mPresenter).getUserOver();
    }

    public /* synthetic */ void lambda$initListener$1$NobilityOpenOrderActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NobilityEntity nobilityEntity = (NobilityEntity) baseQuickAdapter.getItem(i);
        if (nobilityEntity == null) {
            return;
        }
        if (nobilityEntity.isBanBuy()) {
            showToast(R$string.fq_nobility_open_no_tips);
            return;
        }
        this.currentSelectedItem = nobilityEntity;
        initCurrentSelectedItem(nobilityEntity, i);
    }

    private void initAdapter() {
        this.mHeadView = new NobilityOrderHeadView(this.mContext);
        this.mAdapter = new NobilityOpenOrderAdapter(R$layout.fq_item_grid_nobility_open_order);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mRecyclerView.addItemDecoration(new RVDividerNobilityOpenOrderGrid(this.mContext, R$color.fq_colorWhite));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToRecharge() {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            SureCancelDialog.newInstance(getString(R$string.fq_over_insufficient), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenOrderActivity$O-8Rsb7MhUaeXlj-Yyu-YKnSryI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NobilityOpenOrderActivity.this.lambda$goToRecharge$2$NobilityOpenOrderActivity(view);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$goToRecharge$2$NobilityOpenOrderActivity(View view) {
        AppUtils.onRechargeListener(this.mContext);
        LogEventUtils.uploadRechargeClick(getString(R$string.fq_open_nobility_recharge_entrance));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onNobilityListSuccess(List<NobilityEntity> list) {
        this.mAdapter.setNewData(formatList(list));
        NobilityEntity nobilityEntity = this.currentSelectedItem;
        if (nobilityEntity != null) {
            AnchorEntity anchorEntity = nobilityEntity.anchorInfoItem;
            if (anchorEntity != null) {
                this.anchorId = anchorEntity.userId;
                this.liveCount = anchorEntity.liveCount;
                this.mHeadView.initData(anchorEntity.nickname, anchorEntity.liveId, nobilityEntity.isOpen());
            }
            NobilityEntity nobilityEntity2 = this.currentSelectedItem;
            initCurrentSelectedItem(nobilityEntity2, getSelectedPosition(NumberUtils.string2int(nobilityEntity2.type)));
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onUserOverSuccess(MyAccountEntity myAccountEntity) {
        this.userOver = myAccountEntity.price;
        this.mHeadView.updateOverPay(this.userOver);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onUserOverFail() {
        this.mHeadView.updateOverPayFail();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onBuySuccess(String str) {
        String string = getString(this.currentSelectedItem.isRenew() ? R$string.fq_nobility_renewal_success_tips : R$string.fq_nobility_open_success_tips);
        UserInfoManager.getInstance().setNobilityType(NumberUtils.string2int(this.currentSelectedItem.type));
        EventBus.getDefault().post(new NobilityOpenEvent(true, string, str));
        finish();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView
    public void onBuyFail(int i) {
        this.tvNowPay.setEnabled(true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof UpdateBalanceEvent) {
            ((NobilityOpenOrderPresenter) this.mPresenter).getUserOver();
        }
    }

    private List<NobilityEntity> formatList(List<NobilityEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (NobilityEntity nobilityEntity : list) {
            if (!nobilityEntity.isBanBuy()) {
                arrayList.add(nobilityEntity);
            }
        }
        return arrayList;
    }

    private int getSelectedPosition(int i) {
        List<NobilityEntity> data = this.mAdapter.getData();
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (NumberUtils.string2int(data.get(i2).type) == i) {
                return i2;
            }
        }
        return 0;
    }

    private void initCurrentSelectedItem(NobilityEntity nobilityEntity, int i) {
        this.currentCarGold = nobilityEntity.isRenew() ? nobilityEntity.getRenewPrice() : nobilityEntity.getOpenPrice();
        String str = this.currentCarGold;
        this.mAdapter.setSelectedPosition(i);
        this.tvNowPay.setText(getString(R$string.fq_nobility_open_now_pay, new Object[]{AppUtils.getLiveMoneyUnitStr(this.mContext), formatNobilityPrice(str)}));
        this.tvOpenTips.setText(getString(R$string.fq_nobility_open_reward_tips, new Object[]{formatNobilityPrice(nobilityEntity.getRebatePrice()), DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DATE_CHINA_6)}));
        this.mHeadView.updateBadgeIcon(NumberUtils.string2int(nobilityEntity.type));
        this.mHeadView.isHideBenefitAnchor(nobilityEntity.isOpen());
    }

    private String formatNobilityPrice(String str) {
        String formatStarNum = NumberUtils.formatStarNum(new Double(AppUtils.formatPriceExchangeProportion(str)).longValue(), true, true);
        return formatStarNum.endsWith(".00") ? formatStarNum.substring(0, formatStarNum.length() - 3) : formatStarNum;
    }
}
