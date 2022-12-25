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
import com.tomatolive.library.p136ui.adapter.BannedAdapter;
import com.tomatolive.library.p136ui.adapter.BannedSearchAdapter;
import com.tomatolive.library.p136ui.presenter.BannedPresenter;
import com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils;
import com.tomatolive.library.p136ui.view.dialog.confirm.MyLiveBannedDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IBannedView;
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
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.mylive.BannedSettingActivity */
/* loaded from: classes3.dex */
public class BannedSettingActivity extends BaseActivity<BannedPresenter> implements IBannedView {
    private EditText etSearch;
    private boolean isSearch = false;
    private String keyword = "";
    private BannedAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewSearch;
    private BannedSearchAdapter mSearchAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView tvCancel;
    private TextView tvCurrentCount;

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
    public BannedPresenter mo6636createPresenter() {
        return new BannedPresenter(this.mContext, this);
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
        setActivityRightIconTitle(getString(R$string.fq_my_live_banned_setting), R$drawable.fq_ic_search_gray_dark, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$UoaXWNP6ta1WnvwJTQ8pPez9IK0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BannedSettingActivity.this.lambda$initView$0$BannedSettingActivity(view);
            }
        });
        this.etSearch = (EditText) findViewById(R$id.et_search);
        this.tvCancel = (TextView) findViewById(R$id.tv_cancel);
        this.tvCurrentCount = (TextView) findViewById(R$id.tv_current_count);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mRecyclerViewSearch = (RecyclerView) findViewById(R$id.recycler_search);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(!this.isSearch);
        this.etSearch.setHint(R$string.fq_my_live_banned_search_hint);
        int i2 = 8;
        findViewById(R$id.tb_prepare_title_bar).setVisibility(this.isSearch ? 8 : 0);
        findViewById(R$id.ll_search_bg).setVisibility(this.isSearch ? 0 : 8);
        TextView textView = this.tvCurrentCount;
        if (!this.isSearch) {
            i2 = 0;
        }
        textView.setVisibility(i2);
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
            ((BannedPresenter) this.mPresenter).getDataList(this.mStateView, z, this.keyword, this.pageNum, true, false);
        }
    }

    public /* synthetic */ void lambda$initView$0$BannedSettingActivity(View view) {
        Intent intent = new Intent(this.mContext, BannedSettingActivity.class);
        intent.putExtra(ConstantUtils.SEARCH_RESULT, true);
        startActivity(intent);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mAdapter = new BannedAdapter(R$layout.fq_item_list_banned_setting);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 36));
    }

    private void initSearchAdapter() {
        this.mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerViewSearch.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mSearchAdapter = new BannedSearchAdapter(R$layout.fq_item_list_search_setting);
        this.mRecyclerViewSearch.setAdapter(this.mSearchAdapter);
        this.mSearchAdapter.bindToRecyclerView(this.mRecyclerViewSearch);
    }

    private void sendRequest(boolean z, boolean z2) {
        ((BannedPresenter) this.mPresenter).getDataList(this.mStateView, this.isSearch, this.keyword, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$3ok-tmh2YlZTnhx_v9fGLxu4SBs
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                BannedSettingActivity.this.lambda$initListener$1$BannedSettingActivity();
            }
        });
        this.mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$8gHOT3chhyLqcrSmiHoPz0lO7Ac
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                BannedSettingActivity.this.lambda$initListener$2$BannedSettingActivity(refreshLayout);
            }
        });
        RxTextView.textChanges(this.etSearch).map($$Lambda$o0pFIlsUNXLvEOX1QJRnwdVBJFE.INSTANCE).debounce(300L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<String>() { // from class: com.tomatolive.library.ui.activity.mylive.BannedSettingActivity.1
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
                    BannedSettingActivity.this.mRecyclerViewSearch.setVisibility(0);
                    BannedSettingActivity.this.mRecyclerView.setVisibility(4);
                    BannedSettingActivity.this.mSearchAdapter.setNewData(BannedSettingActivity.this.getMenuList(str));
                    return;
                }
                BannedSettingActivity.this.mRecyclerViewSearch.setVisibility(4);
                BannedSettingActivity.this.mRecyclerView.setVisibility(0);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$2kHgKKNKr63jTMw99etgGbkW30M
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BannedSettingActivity.this.lambda$initListener$5$BannedSettingActivity(baseQuickAdapter, view, i);
            }
        });
        this.mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$hIv-DYzSZh9rjL0pzyMbn3FBBsc
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BannedSettingActivity.this.lambda$initListener$6$BannedSettingActivity(baseQuickAdapter, view, i);
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$tOafUyp3Si6_1aiclyFV30ZRCZs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BannedSettingActivity.this.lambda$initListener$7$BannedSettingActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$BannedSettingActivity() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$2$BannedSettingActivity(RefreshLayout refreshLayout) {
        this.pageNum++;
        sendRequest(false, false);
    }

    public /* synthetic */ void lambda$initListener$5$BannedSettingActivity(final BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        final BannedEntity bannedEntity = (BannedEntity) baseQuickAdapter.getItem(i);
        if (bannedEntity != null && view.getId() == R$id.tv_banned) {
            if (TextUtils.equals(UserInfoManager.getInstance().getUserId(), bannedEntity.userId)) {
                showToast(R$string.fq_dont_setting_bannd_yourself);
            } else if (bannedEntity.isBanned()) {
                MyLiveBannedDialog.newInstance(bannedEntity.name, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$wt_eO_YQve4uTWI_jHda_Lt2u9s
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        BannedSettingActivity.this.lambda$null$3$BannedSettingActivity(bannedEntity, baseQuickAdapter, i, view2);
                    }
                }).show(getSupportFragmentManager());
            } else {
                BottomDialogUtils.showBannedDialog(this.mContext, new BottomDialogUtils.LiveBottomBannedMenuListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$BannedSettingActivity$1wcq_v9QAs-x3DNu8QikBmvM-rk
                    @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.LiveBottomBannedMenuListener
                    public final void onLiveBottomBannedMenuListener(long j) {
                        BannedSettingActivity.this.lambda$null$4$BannedSettingActivity(bannedEntity, baseQuickAdapter, i, j);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$null$3$BannedSettingActivity(BannedEntity bannedEntity, BaseQuickAdapter baseQuickAdapter, int i, View view) {
        bannedEntity.banPostStatus = bannedEntity.isBanned() ? "0" : "1";
        baseQuickAdapter.setData(i, bannedEntity);
        ((BannedPresenter) this.mPresenter).bannedSetting(bannedEntity.userId, bannedEntity.isBanned() ? 1 : 2, bannedEntity.duration, i, bannedEntity);
    }

    public /* synthetic */ void lambda$null$4$BannedSettingActivity(BannedEntity bannedEntity, BaseQuickAdapter baseQuickAdapter, int i, long j) {
        bannedEntity.banPostStatus = bannedEntity.isBanned() ? "0" : "1";
        bannedEntity.duration = String.valueOf(j);
        baseQuickAdapter.setData(i, bannedEntity);
        ((BannedPresenter) this.mPresenter).bannedSetting(bannedEntity.userId, bannedEntity.isBanned() ? 1 : 2, String.valueOf(j), i, bannedEntity);
    }

    public /* synthetic */ void lambda$initListener$6$BannedSettingActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        this.mRecyclerViewSearch.setVisibility(8);
        MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
        if (menuEntity != null) {
            this.keyword = menuEntity.getMenuTitle();
            ((BannedPresenter) this.mPresenter).getSearchUsersList(this.mStateView, menuEntity.getMenuTitle(), true);
        }
    }

    public /* synthetic */ void lambda$initListener$7$BannedSettingActivity(View view) {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        onBackPressed();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IBannedView
    public void onDataListSuccess(int i, List<BannedEntity> list, boolean z, boolean z2) {
        this.tvCurrentCount.setVisibility(i > 0 ? 0 : 4);
        this.tvCurrentCount.setText(getString(R$string.fq_my_live_current_banned_personal, new Object[]{Integer.valueOf(i)}));
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IBannedView
    public void onSearchDataListSuccess(List<BannedEntity> list) {
        if (list == null) {
            return;
        }
        this.etSearch.setText("");
        this.mAdapter.setNewData(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IBannedView
    public void onBannedSettingSuccess(int i, BannedEntity bannedEntity) {
        if (!this.isSearch && !bannedEntity.isBanned()) {
            this.mAdapter.remove(i);
            this.tvCurrentCount.setVisibility(this.mAdapter.getData().size() > 0 ? 0 : 4);
            this.tvCurrentCount.setText(getString(R$string.fq_my_live_current_banned_personal, new Object[]{Integer.valueOf(this.mAdapter.getData().size())}));
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
        ((BannedPresenter) this.mPresenter).getDataList(this.mStateView, false, this.keyword, this.pageNum, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MenuEntity> getMenuList(String str) {
        ArrayList arrayList = new ArrayList();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuTitle(str);
        arrayList.add(menuEntity);
        return arrayList;
    }
}
