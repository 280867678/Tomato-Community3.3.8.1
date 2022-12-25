package com.tomatolive.library.p136ui.activity.noble;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.MyNobilityEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.adapter.NobilityPrivilegeAdapter;
import com.tomatolive.library.p136ui.presenter.NobilityPrivilegePresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.headview.NobilityPrivilegeFooterView;
import com.tomatolive.library.p136ui.view.headview.NobilityPrivilegeHeadView;
import com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobilityPrivilegeActivity */
/* loaded from: classes3.dex */
public class NobilityPrivilegeActivity extends BaseActivity<NobilityPrivilegePresenter> implements INobilityPrivilegeView {
    private BGATitleBar bgaTitleBar;
    private int bgaTitleBarHeight;
    private LinearLayout llTitleBarBg;
    private NobilityPrivilegeAdapter mAdapter;
    private NobilityPrivilegeHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private int nobleGrade = -1;

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView
    public void onDataFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NobilityPrivilegePresenter mo6636createPresenter() {
        return new NobilityPrivilegePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_privilege;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.statusBarDarkFont(false);
        immersionBar.init();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.llTitleBarBg = (LinearLayout) findViewById(R$id.ll_title_bar_bg);
        this.bgaTitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.bgaTitleBarHeight = ConvertUtils.dp2px(50.0f);
        this.bgaTitleBar.setLeftDrawable(R$drawable.fq_ic_title_back_white);
        this.bgaTitleBar.setRightDrawable(R$drawable.fq_ic_my_live_car_help_white);
        initAdapter();
        ((NobilityPrivilegePresenter) this.mPresenter).getInitData(this.mStateView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityPrivilegeActivity$xV3TepKCVJ4HndiQA4m9lVUYHLU
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                NobilityPrivilegeActivity.this.lambda$initListener$0$NobilityPrivilegeActivity();
            }
        });
        this.bgaTitleBar.setDelegate(new BGATitleBar.Delegate() { // from class: com.tomatolive.library.ui.activity.noble.NobilityPrivilegeActivity.1
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickRightSecondaryCtv() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickTitleCtv() {
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickLeftCtv() {
                NobilityPrivilegeActivity.this.onBackPressed();
            }

            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
            public void onClickRightCtv() {
                Intent intent = new Intent(((BaseActivity) NobilityPrivilegeActivity.this).mContext, WebViewActivity.class);
                intent.putExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, true);
                intent.putExtra("url", ConstantUtils.APP_PARAM_NOBLE_DESC);
                intent.putExtra("title", NobilityPrivilegeActivity.this.getString(R$string.fq_nobility_desc_faq));
                NobilityPrivilegeActivity.this.startActivity(intent);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.tomatolive.library.ui.activity.noble.NobilityPrivilegeActivity.2
            private int totalDy = 0;

            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                this.totalDy += i2;
                if (this.totalDy < 0) {
                    this.totalDy = 0;
                }
                if (this.totalDy < NobilityPrivilegeActivity.this.bgaTitleBarHeight) {
                    NobilityPrivilegeActivity.this.llTitleBarBg.setBackgroundColor(Color.argb((int) ((this.totalDy / NobilityPrivilegeActivity.this.bgaTitleBarHeight) * 255.0f), 255, 255, 255));
                    NobilityPrivilegeActivity.this.bgaTitleBar.setLeftDrawable(R$drawable.fq_ic_title_back_white);
                    NobilityPrivilegeActivity.this.bgaTitleBar.setRightDrawable(R$drawable.fq_ic_my_live_car_help_white);
                    NobilityPrivilegeActivity.this.bgaTitleBar.setTitleText("");
                    ImmersionBar immersionBar = ((BaseActivity) NobilityPrivilegeActivity.this).mImmersionBar;
                    immersionBar.statusBarDarkFont(false);
                    immersionBar.init();
                    return;
                }
                NobilityPrivilegeActivity.this.llTitleBarBg.setBackgroundColor(Color.argb(255, 255, 255, 255));
                NobilityPrivilegeActivity.this.bgaTitleBar.setLeftDrawable(R$drawable.fq_ic_title_back);
                NobilityPrivilegeActivity.this.bgaTitleBar.setRightDrawable(R$drawable.fq_ic_my_live_car_help);
                NobilityPrivilegeActivity.this.bgaTitleBar.setTitleText(NobilityPrivilegeActivity.this.getString(R$string.fq_nobility_my));
                ImmersionBar immersionBar2 = ((BaseActivity) NobilityPrivilegeActivity.this).mImmersionBar;
                immersionBar2.statusBarDarkFont(true);
                immersionBar2.init();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.noble.NobilityPrivilegeActivity.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
                if (menuEntity == null) {
                    return;
                }
                Intent intent = new Intent(((BaseActivity) NobilityPrivilegeActivity.this).mContext, WebViewActivity.class);
                intent.putExtra("url", NobilityPrivilegeActivity.this.getH5Url(i));
                intent.putExtra("title", menuEntity.menuTitle);
                ((BaseActivity) NobilityPrivilegeActivity.this).mContext.startActivity(intent);
            }
        });
        this.mHeadView.setOnEnterHideListener(new NobilityPrivilegeHeadView.OnEnterHideListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityPrivilegeActivity$k2mKOG3TgOp7SVUkIVYGghKiToI
            @Override // com.tomatolive.library.p136ui.view.headview.NobilityPrivilegeHeadView.OnEnterHideListener
            public final void onEnterHideListener(boolean z) {
                NobilityPrivilegeActivity.this.lambda$initListener$1$NobilityPrivilegeActivity(z);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityPrivilegeActivity() {
        ((NobilityPrivilegePresenter) this.mPresenter).getInitData(this.mStateView);
    }

    public /* synthetic */ void lambda$initListener$1$NobilityPrivilegeActivity(boolean z) {
        if (this.nobleGrade > 4) {
            ((NobilityPrivilegePresenter) this.mPresenter).setEnterHide(z);
        }
    }

    private void initAdapter() {
        this.mHeadView = new NobilityPrivilegeHeadView(this.mContext);
        this.mAdapter = new NobilityPrivilegeAdapter(R$layout.fq_item_list_nobility_privilege);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
        this.mAdapter.addFooterView(new NobilityPrivilegeFooterView(this.mContext));
    }

    private List<MenuEntity> getList() {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = getResources().getStringArray(R$array.fq_nobility_privilege_tips);
        int[] iArr = {R$drawable.fq_ic_nobility_privilege_label_1, R$drawable.fq_ic_nobility_privilege_label_2, R$drawable.fq_ic_nobility_privilege_label_3, R$drawable.fq_ic_nobility_privilege_label_4, R$drawable.fq_ic_nobility_privilege_label_5, R$drawable.fq_ic_nobility_privilege_label_6, R$drawable.fq_ic_nobility_privilege_label_7, R$drawable.fq_ic_nobility_privilege_label_8, R$drawable.fq_ic_nobility_privilege_label_9, R$drawable.fq_ic_nobility_privilege_label_10, R$drawable.fq_ic_nobility_privilege_label_11};
        for (int i = 0; i < getListLen(iArr); i++) {
            arrayList.add(new MenuEntity(stringArray[i], iArr[i]));
        }
        return arrayList;
    }

    private int getListLen(int[] iArr) {
        switch (this.nobleGrade) {
            case 1:
                return 5;
            case 2:
            case 3:
                return 6;
            case 4:
            case 5:
            case 6:
                return 8;
            case 7:
                return iArr.length;
            default:
                return 5;
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView
    public void onDataSuccess(MyNobilityEntity myNobilityEntity) {
        this.nobleGrade = NumberUtils.string2int(myNobilityEntity.nobilityType);
        this.mHeadView.initData(myNobilityEntity);
        this.mAdapter.setNewData(getList());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView
    public void onEnterHideSuccess(boolean z) {
        UserInfoManager.getInstance().setEnterHide(z);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView
    public void onEnterHideFail(String str) {
        showToast(str);
        this.mHeadView.toggleEnterHideCheckBox();
        UserInfoManager.getInstance().setEnterHide(this.mHeadView.isEnterHide());
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof NobilityOpenEvent) {
            NobilityOpenEvent nobilityOpenEvent = (NobilityOpenEvent) baseEvent;
            if (nobilityOpenEvent.isOpenSuccess) {
                showToast(nobilityOpenEvent.toastTips);
            }
            ((NobilityPrivilegePresenter) this.mPresenter).getInitData(this.mStateView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getH5Url(int i) {
        String str = getResources().getStringArray(R$array.fq_nobility_h5_url)[i];
        return AppUtils.getUploadUrl() + "html/" + str + "?" + System.currentTimeMillis();
    }
}
