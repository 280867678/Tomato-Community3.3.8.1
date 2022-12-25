package com.tomatolive.library.p136ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.SearchEvent;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.presenter.SearchAllPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.headview.SearchAllHeadView;
import com.tomatolive.library.p136ui.view.iview.ISearchAllView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.LogEventUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.SearchAllFragment */
/* loaded from: classes3.dex */
public class SearchAllFragment extends BaseFragment<SearchAllPresenter> implements ISearchAllView {
    private boolean hasAnchor;
    private String keyword = "";
    private HomeLiveAdapter mAdapter;
    private SearchAllHeadView mHeadView;
    private RecyclerEmptyView mRecyclerEmptyView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private SearchEvent searchEvent;

    /* renamed from: com.tomatolive.library.ui.fragment.SearchAllFragment$OnFragmentInteractionListener */
    /* loaded from: classes3.dex */
    public interface OnFragmentInteractionListener {
        void onPagerSelectedListener(int i);
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static SearchAllFragment newInstance() {
        Bundle bundle = new Bundle();
        SearchAllFragment searchAllFragment = new SearchAllFragment();
        searchAllFragment.setArguments(bundle);
        return searchAllFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment, android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public SearchAllPresenter mo6641createPresenter() {
        return new SearchAllPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_search_all;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.mRecyclerEmptyView = (RecyclerEmptyView) view.findViewById(R$id.empty_view);
        this.mSmartRefreshLayout.setEnableRefresh(false);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
        initAdapter();
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mHeadView = new SearchAllHeadView(this.mContext);
        this.mAdapter = new HomeLiveAdapter(this, R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite, true, true));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
        this.mAdapter.setHeaderAndEmpty(true);
        this.mHeadView.findViewById(R$id.tv_anchor_all).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$npAHu-Bg9OO2xNjw1S6f1jDE42U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchAllFragment.this.lambda$initAdapter$0$SearchAllFragment(view);
            }
        });
        this.mHeadView.findViewById(R$id.tv_live_all).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$HLNUT4KQRfgiTKVGip4xwFAoZoY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchAllFragment.this.lambda$initAdapter$1$SearchAllFragment(view);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$NXWtz7mX8MFwU5-ton00q6r_V7s
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SearchAllFragment.this.lambda$initAdapter$2$SearchAllFragment(baseQuickAdapter, view, i);
            }
        });
        this.mHeadView.setAnchorItemClickListener(new SearchAllHeadView.AnchorItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$SaF0nKzTV3HYqJr6UgOkrcxiGAE
            @Override // com.tomatolive.library.p136ui.view.headview.SearchAllHeadView.AnchorItemClickListener
            public final void onAnchorItemClickListener(AnchorEntity anchorEntity, int i) {
                SearchAllFragment.this.lambda$initAdapter$3$SearchAllFragment(anchorEntity, i);
            }
        });
    }

    public /* synthetic */ void lambda$initAdapter$0$SearchAllFragment(View view) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.onFragmentInteractionListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onPagerSelectedListener(1);
        }
    }

    public /* synthetic */ void lambda$initAdapter$1$SearchAllFragment(View view) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.onFragmentInteractionListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onPagerSelectedListener(2);
        }
    }

    public /* synthetic */ void lambda$initAdapter$2$SearchAllFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        liveEntity.chargeType = "";
        LogEventUtils.uploadSearchResultEvent(liveEntity.openId, liveEntity.appId, liveEntity.nickname, this.keyword);
        AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "2", getString(R$string.fq_live_enter_source_search));
    }

    public /* synthetic */ void lambda$initAdapter$3$SearchAllFragment(AnchorEntity anchorEntity, int i) {
        if (anchorEntity == null) {
            return;
        }
        LogEventUtils.uploadSearchResultEvent(anchorEntity.openId, anchorEntity.appId, anchorEntity.nickname, this.keyword);
        AppUtils.startTomatoLiveActivity(this.mContext, AppUtils.formatLiveEntity(anchorEntity), "2", getString(R$string.fq_live_enter_source_search));
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$BGfk2P3zj-fYBG6yBVT5h0Ts9GM
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                SearchAllFragment.this.lambda$initListener$4$SearchAllFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$vNhzQNVJQxPu9KXaJ4kv2iN-dDs
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                SearchAllFragment.this.lambda$initListener$5$SearchAllFragment(refreshLayout);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAllFragment$5DMEQswZQXtZW4QfdmKfyGrB8XU
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                SearchAllFragment.this.lambda$initListener$6$SearchAllFragment(refreshLayout);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$4$SearchAllFragment() {
        if (TextUtils.isEmpty(this.keyword)) {
            return;
        }
        ((SearchAllPresenter) this.mPresenter).getAnchorList(this.keyword);
    }

    public /* synthetic */ void lambda$initListener$5$SearchAllFragment(RefreshLayout refreshLayout) {
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$6$SearchAllFragment(RefreshLayout refreshLayout) {
        this.pageNum++;
        ((SearchAllPresenter) this.mPresenter).getLiveList(this.mStateView, this.keyword, this.pageNum, false, false);
        this.mSmartRefreshLayout.finishLoadMore();
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof SearchEvent) {
            this.searchEvent = (SearchEvent) baseEvent;
            this.keyword = this.searchEvent.keyword;
            StateView stateView = this.mStateView;
            if (stateView != null) {
                stateView.showLoading();
            }
            ((SearchAllPresenter) this.mPresenter).getAnchorList(this.keyword);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAllView
    public void onAnchorListSuccess(List<AnchorEntity> list) {
        if (list == null) {
            return;
        }
        this.mHeadView.initData(list, this.keyword);
        boolean z = false;
        this.mHeadView.setHideAnchorBg(list.size() == 0);
        if (list.size() > 0) {
            z = true;
        }
        this.hasAnchor = z;
        this.pageNum = 1;
        ((SearchAllPresenter) this.mPresenter).getLiveList(this.mStateView, this.keyword, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAllView
    public void onAnchorListFail() {
        this.pageNum = 1;
        ((SearchAllPresenter) this.mPresenter).getLiveList(this.mStateView, this.keyword, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAllView
    public void onLiveListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
        boolean z3 = true;
        if (z2) {
            this.mAdapter.setNewData(list);
            this.mHeadView.setHideLiveBg(list.size() == 0);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
        int i = 4;
        this.mRecyclerEmptyView.setVisibility((this.mHeadView.isAnchorListData() || !(list == null || list.size() == 0)) ? 4 : 0);
        RecyclerView recyclerView = this.mRecyclerView;
        if (this.mHeadView.isAnchorListData() || (list != null && list.size() > 0)) {
            i = 0;
        }
        recyclerView.setVisibility(i);
        if (!this.hasAnchor && list.size() <= 0) {
            z3 = false;
        }
        SearchEvent searchEvent = this.searchEvent;
        LogEventUtils.uploadSendSearchRequest(z3, searchEvent.isHistory, searchEvent.isRecommend, this.keyword);
    }
}
