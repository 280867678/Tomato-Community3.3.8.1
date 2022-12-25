package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.BannedEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.event.BannedEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.p136ui.adapter.BannedSearchAdapter;
import com.tomatolive.library.p136ui.adapter.HouseAdapter;
import com.tomatolive.library.p136ui.presenter.HouseSettingPresenter;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IHouseSettingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.UserInfoManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.mylive.HouseSettingActivity */
/* loaded from: classes3.dex */
public class HouseSettingActivity extends BaseActivity<HouseSettingPresenter> implements IHouseSettingView {
    private EditText etSearch;
    private HouseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewSearch;
    private BannedSearchAdapter mSearchAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView tvCancel;
    private TextView tvCurrentCount;
    private boolean isSearch = false;
    private String keyword = "";
    private AtomicInteger atomicIntegerTotalCount = new AtomicInteger(0);

    @Override // com.tomatolive.library.base.BaseActivity
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public HouseSettingPresenter mo6636createPresenter() {
        return new HouseSettingPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_banned_setting;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        int i = 0;
        this.isSearch = getIntent().getBooleanExtra(ConstantUtils.SEARCH_RESULT, false);
        setActivityRightIconTitle(getString(R$string.fq_my_live_house_setting), R$drawable.fq_ic_search_gray_dark, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$VsdLFqxR57umRrlp-QMdNf0Rcjs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HouseSettingActivity.this.lambda$initView$0$HouseSettingActivity(view);
            }
        });
        this.etSearch = (EditText) findViewById(R$id.et_search);
        this.tvCancel = (TextView) findViewById(R$id.tv_cancel);
        this.tvCurrentCount = (TextView) findViewById(R$id.tv_current_count);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mRecyclerViewSearch = (RecyclerView) findViewById(R$id.recycler_search);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(!this.isSearch);
        this.etSearch.setHint(R$string.fq_my_live_house_manager_search_hint);
        int i2 = 8;
        findViewById(R$id.tb_prepare_title_bar).setVisibility(this.isSearch ? 8 : 0);
        this.tvCurrentCount.setVisibility(this.isSearch ? 8 : 0);
        View findViewById = findViewById(R$id.ll_search_bg);
        if (this.isSearch) {
            i2 = 0;
        }
        findViewById.setVisibility(i2);
        RecyclerView recyclerView = this.mRecyclerViewSearch;
        if (!this.isSearch) {
            i = 4;
        }
        recyclerView.setVisibility(i);
        findViewById(R$id.rl_content).setBackgroundColor(ContextCompat.getColor(this.mContext, this.isSearch ? R$color.fq_colorWhite : 17170445));
        initAdapter();
        initSearchAdapter();
        boolean z = this.isSearch;
        if (z) {
            this.mStateView.showContent();
        } else {
            ((HouseSettingPresenter) this.mPresenter).getDataList(this.mStateView, z, this.keyword, this.pageNum, true, false);
        }
    }

    public /* synthetic */ void lambda$initView$0$HouseSettingActivity(View view) {
        Intent intent = new Intent(this.mContext, HouseSettingActivity.class);
        intent.putExtra(ConstantUtils.SEARCH_RESULT, true);
        startActivity(intent);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mAdapter = new HouseAdapter(R$layout.fq_item_list_house_setting, this.isSearch);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 35));
    }

    private void initSearchAdapter() {
        this.mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerViewSearch.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mSearchAdapter = new BannedSearchAdapter(R$layout.fq_item_list_search_setting);
        this.mRecyclerViewSearch.setAdapter(this.mSearchAdapter);
        this.mSearchAdapter.bindToRecyclerView(this.mRecyclerViewSearch);
    }

    private void sendRequest(boolean z, boolean z2) {
        ((HouseSettingPresenter) this.mPresenter).getDataList(this.mStateView, this.isSearch, this.keyword, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$TQn1X1I_2inBAORMgSPy54bA35c
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                HouseSettingActivity.this.lambda$initListener$1$HouseSettingActivity();
            }
        });
        this.mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$ej4ML8IqlLzZQpy-vNlx8X2ob1Q
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                HouseSettingActivity.this.lambda$initListener$2$HouseSettingActivity(refreshLayout);
            }
        });
        RxTextView.textChanges(this.etSearch).map($$Lambda$o0pFIlsUNXLvEOX1QJRnwdVBJFE.INSTANCE).debounce(300L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<String>() { // from class: com.tomatolive.library.ui.activity.mylive.HouseSettingActivity.1
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
            public void onNext(String str) {
                if (!TextUtils.isEmpty(str)) {
                    HouseSettingActivity.this.mRecyclerViewSearch.setVisibility(0);
                    HouseSettingActivity.this.mRecyclerView.setVisibility(4);
                    HouseSettingActivity.this.mSearchAdapter.setNewData(HouseSettingActivity.this.getMenuList(str));
                    return;
                }
                HouseSettingActivity.this.mRecyclerViewSearch.setVisibility(4);
                HouseSettingActivity.this.mRecyclerView.setVisibility(0);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$BcIHKw_Gp3V45lO1qmdJlmQX_24
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HouseSettingActivity.this.lambda$initListener$4$HouseSettingActivity(baseQuickAdapter, view, i);
            }
        });
        this.mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$ixx9XftoPOixGm-vpnD2lrUpRJo
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HouseSettingActivity.this.lambda$initListener$5$HouseSettingActivity(baseQuickAdapter, view, i);
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$d0aOPB_0zCBgJWlf9YAP4kr5Mbo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HouseSettingActivity.this.lambda$initListener$6$HouseSettingActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$HouseSettingActivity() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$2$HouseSettingActivity(RefreshLayout refreshLayout) {
        this.pageNum++;
        sendRequest(false, false);
    }

    public /* synthetic */ void lambda$initListener$4$HouseSettingActivity(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        final BannedEntity bannedEntity = (BannedEntity) baseQuickAdapter.getItem(i);
        if (bannedEntity != null && view.getId() == R$id.tv_banned) {
            if (TextUtils.equals(UserInfoManager.getInstance().getUserId(), bannedEntity.userId)) {
                showToast(R$string.fq_dont_setting_house_manager_yourself);
            } else if (bannedEntity.isHouseManager()) {
                SureCancelDialog.newInstance(getString(R$string.fq_sure_cancel_house_manager), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$HouseSettingActivity$59lqnn5tuylAI8og1-MqjOSbTjA
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        HouseSettingActivity.this.lambda$null$3$HouseSettingActivity(bannedEntity, i, view2);
                    }
                }).show(getSupportFragmentManager());
            } else {
                ((HouseSettingPresenter) this.mPresenter).houseSetting(bannedEntity.userId, bannedEntity.isHouseManager() ? 2 : 1, i, bannedEntity);
            }
        }
    }

    public /* synthetic */ void lambda$null$3$HouseSettingActivity(BannedEntity bannedEntity, int i, View view) {
        ((HouseSettingPresenter) this.mPresenter).houseSetting(bannedEntity.userId, bannedEntity.isHouseManager() ? 2 : 1, i, bannedEntity);
    }

    public /* synthetic */ void lambda$initListener$5$HouseSettingActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        this.mRecyclerViewSearch.setVisibility(8);
        MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
        this.keyword = menuEntity != null ? menuEntity.getMenuTitle() : "";
        ((HouseSettingPresenter) this.mPresenter).getSearchUsersList(this.mStateView, this.keyword, true);
    }

    public /* synthetic */ void lambda$initListener$6$HouseSettingActivity(View view) {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        onBackPressed();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHouseSettingView
    public void onDataListSuccess(int i, List<BannedEntity> list, boolean z, boolean z2) {
        this.atomicIntegerTotalCount.set(i);
        setCurrentCount(i);
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHouseSettingView
    public void onSearchDataListSuccess(List<BannedEntity> list) {
        if (list == null) {
            return;
        }
        this.etSearch.setText("");
        this.mAdapter.setNewData(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHouseSettingView
    public void onHouseSettingSuccess(int i, BannedEntity bannedEntity) {
        bannedEntity.managerStatus = bannedEntity.isHouseManager() ? "0" : "1";
        this.mAdapter.setData(i, bannedEntity);
        showToast(bannedEntity.isHouseManager() ? R$string.fq_setting_house_manager_suc : R$string.fq_cancel_house_manager_suc);
        if (!this.isSearch && !bannedEntity.isHouseManager()) {
            this.mAdapter.remove(i);
            this.atomicIntegerTotalCount.decrementAndGet();
            setCurrentCount(this.atomicIntegerTotalCount.get());
        }
        if (this.isSearch) {
            EventBus.getDefault().post(new BannedEvent());
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (!(baseEvent instanceof BannedEvent) || this.isSearch) {
            return;
        }
        this.pageNum = 1;
        ((HouseSettingPresenter) this.mPresenter).getDataList(this.mStateView, false, this.keyword, this.pageNum, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MenuEntity> getMenuList(String str) {
        ArrayList arrayList = new ArrayList();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuTitle(str);
        arrayList.add(menuEntity);
        return arrayList;
    }

    private void setCurrentCount(int i) {
        if (i > 200) {
            i = 200;
        }
        this.tvCurrentCount.setVisibility(i > 0 ? 0 : 4);
        this.tvCurrentCount.setText(getString(R$string.fq_my_live_current_house_personal, new Object[]{Integer.valueOf(i), 200}));
    }
}
