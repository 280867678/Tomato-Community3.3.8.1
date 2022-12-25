package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.AttentionEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.SearchEvent;
import com.tomatolive.library.p136ui.adapter.SearchAnchorAdapter;
import com.tomatolive.library.p136ui.presenter.SearchAnchorPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.iview.ISearchAnchorView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.LogEventUtils;
import java.util.Collection;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.SearchAnchorFragment */
/* loaded from: classes3.dex */
public class SearchAnchorFragment extends BaseFragment<SearchAnchorPresenter> implements ISearchAnchorView {
    private String keyword = "";
    private SearchAnchorAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    static /* synthetic */ int access$008(SearchAnchorFragment searchAnchorFragment) {
        int i = searchAnchorFragment.pageNum;
        searchAnchorFragment.pageNum = i + 1;
        return i;
    }

    public static SearchAnchorFragment newInstance() {
        Bundle bundle = new Bundle();
        SearchAnchorFragment searchAnchorFragment = new SearchAnchorFragment();
        searchAnchorFragment.setArguments(bundle);
        return searchAnchorFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public SearchAnchorPresenter mo6641createPresenter() {
        return new SearchAnchorPresenter(this.mContext, this);
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
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mAdapter = new SearchAnchorAdapter(this, R$layout.fq_item_list_search_attention);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerEmptyView(this.mContext, 30));
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAnchorFragment$wxAyKb2-kaPy5aU8iVp0DDOd6tc
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                SearchAnchorFragment.this.lambda$initListener$0$SearchAnchorFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.fragment.SearchAnchorFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                SearchAnchorFragment.access$008(SearchAnchorFragment.this);
                ((SearchAnchorPresenter) ((BaseFragment) SearchAnchorFragment.this).mPresenter).getAnchorList(((BaseFragment) SearchAnchorFragment.this).mStateView, SearchAnchorFragment.this.keyword, ((BaseFragment) SearchAnchorFragment.this).pageNum, false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                ((BaseFragment) SearchAnchorFragment.this).pageNum = 1;
                ((SearchAnchorPresenter) ((BaseFragment) SearchAnchorFragment.this).mPresenter).getAnchorList(((BaseFragment) SearchAnchorFragment.this).mStateView, SearchAnchorFragment.this.keyword, ((BaseFragment) SearchAnchorFragment.this).pageNum, false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAnchorFragment$zCAIYeigJnIFBvGCms0xbDhETt4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                SearchAnchorFragment.this.lambda$initListener$1$SearchAnchorFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$SearchAnchorFragment$ahFuN0fJWVIuSN77Jym8wemWM_U
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                SearchAnchorFragment.this.lambda$initListener$2$SearchAnchorFragment(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$SearchAnchorFragment() {
        this.pageNum = 1;
        ((SearchAnchorPresenter) this.mPresenter).getAnchorList(this.mStateView, this.keyword, this.pageNum, true, true);
    }

    public /* synthetic */ void lambda$initListener$1$SearchAnchorFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        LiveEntity formatLiveEntity = AppUtils.formatLiveEntity(anchorEntity);
        if (anchorEntity == null) {
            return;
        }
        LogEventUtils.uploadSearchResultEvent(anchorEntity.openId, anchorEntity.appId, anchorEntity.nickname, this.keyword);
        AppUtils.startTomatoLiveActivity(this.mContext, formatLiveEntity, "2", getString(R$string.fq_live_enter_source_search));
    }

    public /* synthetic */ void lambda$initListener$2$SearchAnchorFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity;
        if (view.getId() != R$id.tv_attention || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null || !AppUtils.isAttentionUser(this.mContext, anchorEntity.userId)) {
            return;
        }
        attentionAnchor(anchorEntity, view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof SearchEvent) {
            this.keyword = ((SearchEvent) baseEvent).keyword;
            if (TextUtils.isEmpty(this.keyword)) {
                return;
            }
            this.mAdapter.setKeyWord(this.keyword);
            this.mSmartRefreshLayout.resetNoMoreData();
            this.pageNum = 1;
            ((SearchAnchorPresenter) this.mPresenter).getAnchorList(this.mStateView, this.keyword, this.pageNum, true, true);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAnchorView
    public void onDataListSuccess(List<AnchorEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAnchorView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISearchAnchorView
    public void onAttentionSuccess() {
        EventBus.getDefault().postSticky(new AttentionEvent());
    }

    private void attentionAnchor(AnchorEntity anchorEntity, View view) {
        boolean z = !AppUtils.isAttentionAnchor(anchorEntity.userId) ? 1 : 0;
        showToast(z ? R$string.fq_text_attention_success : R$string.fq_text_attention_cancel_success);
        view.setSelected(z);
        DBUtils.attentionAnchor(anchorEntity.userId, z);
        ((SearchAnchorPresenter) this.mPresenter).attentionAnchor(anchorEntity.userId, z ? 1 : 0);
        LogEventUtils.uploadFollow(anchorEntity.openId, anchorEntity.appId, getString(R$string.fq_home_hot), anchorEntity.expGrade, anchorEntity.nickname, getString(R$string.fq_live_enter_source_search), z, anchorEntity.liveId);
    }
}
