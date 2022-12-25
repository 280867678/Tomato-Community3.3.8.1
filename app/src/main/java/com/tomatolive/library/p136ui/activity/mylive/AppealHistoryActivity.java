package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AppealHistoryEntity;
import com.tomatolive.library.p136ui.adapter.AppealHistoryAdapter;
import com.tomatolive.library.p136ui.presenter.AppealHistoryPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IAppealListView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.AppealHistoryActivity */
/* loaded from: classes3.dex */
public class AppealHistoryActivity extends BaseActivity<AppealHistoryPresenter> implements IAppealListView {
    private AppealHistoryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public AppealHistoryPresenter mo6636createPresenter() {
        return new AppealHistoryPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_awards_history;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_hd_appeal_history);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        initAdapter();
        sendRequest(true, true);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new AppealHistoryAdapter(R$layout.fq_item_list_awards_history);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 55));
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AppealHistoryActivity$evRUMvNGy_1WxZ4l_qUt6MNqw34
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AppealHistoryActivity.this.lambda$initAdapter$0$AppealHistoryActivity(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initAdapter$0$AppealHistoryActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AppealHistoryEntity appealHistoryEntity = (AppealHistoryEntity) baseQuickAdapter.getItem(i);
        if (appealHistoryEntity == null) {
            return;
        }
        Intent intent = new Intent(this.mContext, AppealDetailActivity.class);
        intent.putExtra(ConstantUtils.RESULT_ID, appealHistoryEntity.getId());
        intent.putExtra(ConstantUtils.RESULT_FLAG, appealHistoryEntity.getAppealStatus());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        ((AppealHistoryPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AppealHistoryActivity$ed4wtLSCmZkI6Z9k7d_JgQRHzvU
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                AppealHistoryActivity.this.lambda$initListener$1$AppealHistoryActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.AppealHistoryActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                AppealHistoryActivity appealHistoryActivity = AppealHistoryActivity.this;
                appealHistoryActivity.pageNum++;
                appealHistoryActivity.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                AppealHistoryActivity appealHistoryActivity = AppealHistoryActivity.this;
                appealHistoryActivity.pageNum = 1;
                appealHistoryActivity.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$AppealHistoryActivity() {
        sendRequest(true, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealListView
    public void onDataListSuccess(List<AppealHistoryEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAppealListView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }
}
