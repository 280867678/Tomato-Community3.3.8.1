package com.tomatolive.library.p136ui.activity.noble;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.NobilityRecommendHistoryEntity;
import com.tomatolive.library.p136ui.adapter.NobilityRecommendHistoryAdapter;
import com.tomatolive.library.p136ui.presenter.NobilityRecommendHistoryPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerNobilityOpenRecord;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.NobilityPrivilegeFooterView;
import com.tomatolive.library.p136ui.view.iview.INobilityRecommendHistoryView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobilityRecommendHistoryActivity */
/* loaded from: classes3.dex */
public class NobilityRecommendHistoryActivity extends BaseActivity<NobilityRecommendHistoryPresenter> implements INobilityRecommendHistoryView {
    private NobilityRecommendHistoryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NobilityRecommendHistoryPresenter mo6636createPresenter() {
        return new NobilityRecommendHistoryPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_recommend_history;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_noble_recommend_history);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        initAdapter();
        ((NobilityRecommendHistoryPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityRecommendHistoryActivity$-509jphUHsTF94FgmevlKMOnbaE
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                NobilityRecommendHistoryActivity.this.lambda$initListener$0$NobilityRecommendHistoryActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.noble.NobilityRecommendHistoryActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                NobilityRecommendHistoryActivity nobilityRecommendHistoryActivity = NobilityRecommendHistoryActivity.this;
                nobilityRecommendHistoryActivity.pageNum++;
                ((NobilityRecommendHistoryPresenter) ((BaseActivity) nobilityRecommendHistoryActivity).mPresenter).getDataList(((BaseActivity) NobilityRecommendHistoryActivity.this).mStateView, NobilityRecommendHistoryActivity.this.pageNum, false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                NobilityRecommendHistoryActivity nobilityRecommendHistoryActivity = NobilityRecommendHistoryActivity.this;
                nobilityRecommendHistoryActivity.pageNum = 1;
                ((NobilityRecommendHistoryPresenter) ((BaseActivity) nobilityRecommendHistoryActivity).mPresenter).getDataList(((BaseActivity) NobilityRecommendHistoryActivity.this).mStateView, NobilityRecommendHistoryActivity.this.pageNum, false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityRecommendHistoryActivity() {
        this.pageNum = 1;
        ((NobilityRecommendHistoryPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, true, true);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerNobilityOpenRecord(this.mContext, 17170445));
        this.mAdapter = new NobilityRecommendHistoryAdapter(R$layout.fq_item_list_nobility_recommend_history);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext));
        this.mAdapter.addFooterView(new NobilityPrivilegeFooterView(this.mContext));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityRecommendHistoryView
    public void onDataListSuccess(List<NobilityRecommendHistoryEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityRecommendHistoryView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }
}
