package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.SearchEvent;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.presenter.SearchLivePresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.iview.ISearchLiveView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.LogEventUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.SearchLiveFragment */
/* loaded from: classes3.dex */
public class SearchLiveFragment extends BaseFragment<SearchLivePresenter> implements ISearchLiveView {
    private String keyword = "";
    private HomeLiveAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    static /* synthetic */ int access$008(SearchLiveFragment searchLiveFragment) {
        int i = searchLiveFragment.pageNum;
        searchLiveFragment.pageNum = i + 1;
        return i;
    }

    public static SearchLiveFragment newInstance() {
        Bundle bundle = new Bundle();
        SearchLiveFragment searchLiveFragment = new SearchLiveFragment();
        searchLiveFragment.setArguments(bundle);
        return searchLiveFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public SearchLivePresenter mo6641createPresenter() {
        return new SearchLivePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_home_sort;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        initAdapter();
    }

    private void initAdapter() {
        this.mAdapter = new HomeLiveAdapter(this, R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerEmptyView(this.mContext, 31));
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchLiveFragment$RIEXcMJuAaAyul-hmvZF5m_HqIs
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                SearchLiveFragment.this.lambda$initListener$0$SearchLiveFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.fragment.SearchLiveFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                SearchLiveFragment.access$008(SearchLiveFragment.this);
                ((SearchLivePresenter) ((BaseFragment) SearchLiveFragment.this).mPresenter).getLiveList(((BaseFragment) SearchLiveFragment.this).mStateView, SearchLiveFragment.this.keyword, ((BaseFragment) SearchLiveFragment.this).pageNum, false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                ((BaseFragment) SearchLiveFragment.this).pageNum = 1;
                ((SearchLivePresenter) ((BaseFragment) SearchLiveFragment.this).mPresenter).getLiveList(((BaseFragment) SearchLiveFragment.this).mStateView, SearchLiveFragment.this.keyword, ((BaseFragment) SearchLiveFragment.this).pageNum, false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchLiveFragment$998kQVbTZbQv97d8BYwDedLJ-PU
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                SearchLiveFragment.this.lambda$initListener$1$SearchLiveFragment(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$SearchLiveFragment() {
        this.pageNum = 1;
        ((SearchLivePresenter) this.mPresenter).getLiveList(this.mStateView, this.keyword, this.pageNum, true, true);
    }

    public /* synthetic */ void lambda$initListener$1$SearchLiveFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        liveEntity.chargeType = "";
        LogEventUtils.uploadSearchResultEvent(liveEntity.openId, liveEntity.appId, liveEntity.nickname, this.keyword);
        AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "2", getString(R$string.fq_live_enter_source_search));
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof SearchEvent) {
            this.keyword = ((SearchEvent) baseEvent).keyword;
            this.mSmartRefreshLayout.resetNoMoreData();
            this.pageNum = 1;
            ((SearchLivePresenter) this.mPresenter).getLiveList(this.mStateView, this.keyword, this.pageNum, true, true);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchLiveView
    public void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchLiveView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }
}
