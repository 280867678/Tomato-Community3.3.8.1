package com.tomatolive.library.p136ui.activity.noble;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.NobilityOpenRecordEntity;
import com.tomatolive.library.p136ui.adapter.NobilityOpenRecordAdapter;
import com.tomatolive.library.p136ui.presenter.NobilityOpenRecordPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerNobilityOpenRecord;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.NobilityPrivilegeFooterView;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenRecordView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobilityOpenRecordActivity */
/* loaded from: classes3.dex */
public class NobilityOpenRecordActivity extends BaseActivity<NobilityOpenRecordPresenter> implements INobilityOpenRecordView {
    private NobilityOpenRecordAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

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
    public NobilityOpenRecordPresenter mo6636createPresenter() {
        return new NobilityOpenRecordPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_open_record;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_nobility_open_record);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        initAdapter();
        ((NobilityOpenRecordPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenRecordActivity$cZGCbTjbma4DmHRnLEw_LxKr5RQ
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                NobilityOpenRecordActivity.this.lambda$initListener$0$NobilityOpenRecordActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.noble.NobilityOpenRecordActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                NobilityOpenRecordActivity nobilityOpenRecordActivity = NobilityOpenRecordActivity.this;
                nobilityOpenRecordActivity.pageNum++;
                ((NobilityOpenRecordPresenter) ((BaseActivity) nobilityOpenRecordActivity).mPresenter).getDataList(((BaseActivity) NobilityOpenRecordActivity.this).mStateView, NobilityOpenRecordActivity.this.pageNum, false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                NobilityOpenRecordActivity nobilityOpenRecordActivity = NobilityOpenRecordActivity.this;
                nobilityOpenRecordActivity.pageNum = 1;
                ((NobilityOpenRecordPresenter) ((BaseActivity) nobilityOpenRecordActivity).mPresenter).getDataList(((BaseActivity) NobilityOpenRecordActivity.this).mStateView, NobilityOpenRecordActivity.this.pageNum, false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityOpenRecordActivity() {
        this.pageNum = 1;
        ((NobilityOpenRecordPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        this.pageNum = 1;
        ((NobilityOpenRecordPresenter) this.mPresenter).getDataList(this.mStateView, this.pageNum, false, true);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerNobilityOpenRecord(this.mContext, 17170445));
        this.mAdapter = new NobilityOpenRecordAdapter(R$layout.fq_item_list_nobility_open_record);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext));
        this.mAdapter.addFooterView(new NobilityPrivilegeFooterView(this.mContext));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenRecordView
    public void onDataListSuccess(List<NobilityOpenRecordEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenRecordView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }
}
