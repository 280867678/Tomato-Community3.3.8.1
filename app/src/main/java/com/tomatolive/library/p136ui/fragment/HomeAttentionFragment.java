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
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.AttentionEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.model.event.LoginEvent;
import com.tomatolive.library.model.event.LogoutEvent;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.adapter.RecommendAdapter;
import com.tomatolive.library.p136ui.presenter.HomeAttentionPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.divider.RVDividerRecommendGrid;
import com.tomatolive.library.p136ui.view.emptyview.AttentionEmptyView;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.headview.RecommendHeadView;
import com.tomatolive.library.p136ui.view.iview.IHomeAttentionView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.LogManager;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.HomeAttentionFragment */
/* loaded from: classes3.dex */
public class HomeAttentionFragment extends BaseFragment<HomeAttentionPresenter> implements IHomeAttentionView {
    private boolean isRouterFlag = false;
    private AttentionEmptyView mAttentionEmptyView;
    private HomeLiveAdapter mOpenAdapter;
    private RecommendAdapter mRecommendAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewRecommend;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isEnablePageStayReport() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAttentionView
    public void onAttentionSuccess() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    static /* synthetic */ int access$008(HomeAttentionFragment homeAttentionFragment) {
        int i = homeAttentionFragment.pageNum;
        homeAttentionFragment.pageNum = i + 1;
        return i;
    }

    public static HomeAttentionFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeAttentionFragment homeAttentionFragment = new HomeAttentionFragment();
        homeAttentionFragment.setArguments(bundle);
        return homeAttentionFragment;
    }

    public static HomeAttentionFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        HomeAttentionFragment homeAttentionFragment = new HomeAttentionFragment();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        homeAttentionFragment.setArguments(bundle);
        return homeAttentionFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public HomeAttentionPresenter mo6641createPresenter() {
        return new HomeAttentionPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isRouterFlag = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_home_attention;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.mRecyclerViewRecommend = (RecyclerView) view.findViewById(R$id.recycler_view_recommend);
        initOpenAdapter();
        initRecommendAdapter();
        if (this.isRouterFlag) {
            sendRequest(true, true);
        }
    }

    private void initOpenAdapter() {
        this.mOpenAdapter = new HomeLiveAdapter(this, R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite));
        this.mRecyclerView.setAdapter(this.mOpenAdapter);
        this.mOpenAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mOpenAdapter.setEmptyView(new RecyclerEmptyView(this.mContext));
    }

    private void initRecommendAdapter() {
        this.mAttentionEmptyView = new AttentionEmptyView(this.mContext);
        this.mRecyclerViewRecommend.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mRecyclerViewRecommend.addItemDecoration(new RVDividerRecommendGrid(this.mContext, R$color.fq_colorWhite));
        this.mRecommendAdapter = new RecommendAdapter(R$layout.fq_item_list_recommend);
        this.mRecyclerViewRecommend.setAdapter(this.mRecommendAdapter);
        this.mRecommendAdapter.bindToRecyclerView(this.mRecyclerViewRecommend);
        this.mRecommendAdapter.setEmptyView(this.mAttentionEmptyView);
        this.mRecommendAdapter.addHeaderView(new RecommendHeadView(this.mContext));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        if (AppUtils.isConsumptionPermissionUser()) {
            ((HomeAttentionPresenter) this.mPresenter).getAttentionAnchorListList(this.mStateView, this.pageNum, z, z2);
        } else {
            ((HomeAttentionPresenter) this.mPresenter).getRecommendAnchorList(this.mStateView, z);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAttentionFragment$4WUvtwgX_AONLfsIgrjgfVfmP5E
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                HomeAttentionFragment.this.lambda$initListener$0$HomeAttentionFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.fragment.HomeAttentionFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                HomeAttentionFragment.access$008(HomeAttentionFragment.this);
                HomeAttentionFragment.this.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                ((BaseFragment) HomeAttentionFragment.this).pageNum = 1;
                HomeAttentionFragment.this.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mOpenAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAttentionFragment$d3NZ03fpbTCwvqxjI2bySYH7NYc
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeAttentionFragment.this.lambda$initListener$1$HomeAttentionFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mRecommendAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAttentionFragment$uXO0s7rmJpM1Y5j6jBDbx8MqMKc
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeAttentionFragment.this.lambda$initListener$2$HomeAttentionFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAttentionFragment$rs4IRIzZbLgT1xf1iJkJfln-myg
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeAttentionFragment.this.lambda$initListener$3$HomeAttentionFragment(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$HomeAttentionFragment() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$HomeAttentionFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "2", getString(R$string.fq_attention_list));
    }

    public /* synthetic */ void lambda$initListener$2$HomeAttentionFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity;
        if (view.getId() != R$id.tv_attention || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null || !AppUtils.isAttentionUser(this.mContext, anchorEntity.anchor_id)) {
            return;
        }
        boolean z = !view.isSelected() ? 1 : 0;
        view.setSelected(z);
        showToast(z ? R$string.fq_text_attention_success : R$string.fq_text_attention_cancel_success);
        ((HomeAttentionPresenter) this.mPresenter).attentionAnchor(anchorEntity.anchor_id, z ? 1 : 0);
        LogEventUtils.uploadFollow(anchorEntity.openId, anchorEntity.appId, getString(R$string.fq_home_attention), anchorEntity.expGrade, anchorEntity.nickname, getString(R$string.fq_attention_list), z, anchorEntity.liveId);
        DBUtils.attentionAnchor(anchorEntity.anchor_id, z);
    }

    public /* synthetic */ void lambda$initListener$3$HomeAttentionFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (anchorEntity == null) {
            return;
        }
        AppUtils.startTomatoLiveActivity(this.mContext, AppUtils.formatLiveEntity(anchorEntity), "2", getString(R$string.fq_live_enter_source_attention_hot));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAttentionView
    public void onAttentionListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
        if (list == null || list.size() == 0) {
            this.mRecyclerView.setVisibility(4);
            this.mRecyclerViewRecommend.setVisibility(0);
            ((HomeAttentionPresenter) this.mPresenter).getRecommendAnchorList(this.mStateView, false);
            return;
        }
        this.mRecyclerView.setVisibility(0);
        this.mRecyclerViewRecommend.setVisibility(4);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(true);
        if (z2) {
            this.mOpenAdapter.setNewData(list);
        } else {
            this.mOpenAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAttentionView
    public void onAttentionListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
        ((HomeAttentionPresenter) this.mPresenter).getRecommendAnchorList(this.mStateView, false);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAttentionView
    public void onRecommendListSuccess(List<AnchorEntity> list) {
        this.mRecyclerView.setVisibility(4);
        boolean z = false;
        this.mRecyclerViewRecommend.setVisibility(0);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
        if (list == null) {
            return;
        }
        if (list.size() > 0) {
            z = true;
        }
        this.mAttentionEmptyView.hideRecommendTextView(!z);
        this.mRecommendAdapter.setNewData(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAttentionView
    public void onRecommendListFail() {
        this.mRecyclerView.setVisibility(4);
        this.mRecyclerViewRecommend.setVisibility(0);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThread(BaseEvent baseEvent) {
        if (baseEvent instanceof ListDataUpdateEvent) {
            if (((ListDataUpdateEvent) baseEvent).isAutoRefresh) {
                if (!getUserVisibleHint()) {
                    return;
                }
                LogManager.m238i(this.TAG);
                sendRequest(true, true);
                return;
            }
            sendRequest(true, true);
        } else if (baseEvent instanceof AttentionEvent) {
            this.pageNum = 1;
            ((HomeAttentionPresenter) this.mPresenter).getAttentionAnchorListList(this.mStateView, this.pageNum, true, true);
        } else if (baseEvent instanceof LoginEvent) {
            this.pageNum = 1;
            ((HomeAttentionPresenter) this.mPresenter).getAttentionAnchorListList(this.mStateView, this.pageNum, true, true);
        } else if (!(baseEvent instanceof LogoutEvent)) {
        } else {
            this.mRecyclerView.setVisibility(4);
            this.mRecyclerViewRecommend.setVisibility(0);
            this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
            this.pageNum = 1;
            ((HomeAttentionPresenter) this.mPresenter).getRecommendAnchorList(this.mStateView, true);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public String getPageStayTimerType() {
        return getString(R$string.fq_attention_list);
    }
}
