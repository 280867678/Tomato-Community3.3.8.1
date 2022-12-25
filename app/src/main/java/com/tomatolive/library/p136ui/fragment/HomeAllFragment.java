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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.p136ui.adapter.HomeLiveAllAdapter;
import com.tomatolive.library.p136ui.presenter.HomeAllPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLiveAll;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.iview.IHomeAllView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.live.PlayManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.HomeAllFragment */
/* loaded from: classes3.dex */
public class HomeAllFragment extends BaseFragment<HomeAllPresenter> implements IHomeAllView {
    private RVDividerLiveAll itemDecoration;
    private HomeLiveAllAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private PlayManager playManager;
    private List<LiveEntity> mListData = new ArrayList();
    private int bannerSpanPosition = 6;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isEnablePageStayReport() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isLazyLoad() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    static /* synthetic */ int access$408(HomeAllFragment homeAllFragment) {
        int i = homeAllFragment.pageNum;
        homeAllFragment.pageNum = i + 1;
        return i;
    }

    public static HomeAllFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeAllFragment homeAllFragment = new HomeAllFragment();
        homeAllFragment.setArguments(bundle);
        return homeAllFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public HomeAllPresenter mo6641createPresenter() {
        return new HomeAllPresenter(this.mContext, this);
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
        this.playManager = new PlayManager(this.mContext);
        initAdapter();
    }

    private void initAdapter() {
        this.mAdapter = new HomeLiveAllAdapter(this, this.mListData);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerEmptyView(this.mContext));
        this.playManager.initRecyclerViewPlayManager(this.mRecyclerView);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onLazyLoad() {
        sendRequest(true, true);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onFragmentVisible(boolean z) {
        super.onFragmentVisible(z);
        if (z) {
            lambda$onDataListSuccess$3$HomeAllFragment();
        } else {
            pausePlay();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            lambda$onDataListSuccess$3$HomeAllFragment();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        onDestroyPlay();
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAllFragment$l_NEtI3kLxdJxdvMxBb_D-h7knM
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                HomeAllFragment.this.lambda$initListener$0$HomeAllFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.HomeAllFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                ((BaseFragment) HomeAllFragment.this).pageNum = 1;
                HomeAllFragment.this.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAllFragment$rl_WGy_y62MFkV_u6gySsq1UB5E
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeAllFragment.this.lambda$initListener$1$HomeAllFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAllFragment$_avMFt1nYR_-y0g1SIbb-Gues5s
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return HomeAllFragment.this.lambda$initListener$2$HomeAllFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.tomatolive.library.ui.fragment.HomeAllFragment.2
            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }

            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0 && HomeAllFragment.this.playManager != null) {
                    HomeAllFragment.this.playManager.onScrolled();
                    HomeAllFragment.this.playManager.onScrollStateChanged();
                }
                if (HomeAllFragment.this.isAutoPreLoadingMore(recyclerView)) {
                    ((BaseFragment) HomeAllFragment.this).isLoadingMore = true;
                    HomeAllFragment.access$408(HomeAllFragment.this);
                    ((HomeAllPresenter) ((BaseFragment) HomeAllFragment.this).mPresenter).getLiveList(((BaseFragment) HomeAllFragment.this).mStateView, ((BaseFragment) HomeAllFragment.this).pageNum, false, false, HomeAllFragment.this.bindToLifecycle());
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$HomeAllFragment() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$HomeAllFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        if (liveEntity.isAd) {
            AppUtils.onLiveListClickAdEvent(this.mContext, liveEntity);
        } else if (liveEntity.itemType == 2) {
        } else {
            AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "1", getString(R$string.fq_all_list));
        }
    }

    public /* synthetic */ boolean lambda$initListener$2$HomeAllFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        PlayManager playManager;
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity != null && !liveEntity.isAd && liveEntity.itemType != 2 && (playManager = this.playManager) != null) {
            playManager.playVideoByPosition(i + baseQuickAdapter.getHeaderLayoutCount());
        }
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAllView
    public void onDataListSuccess(List<LiveEntity> list, final boolean z, boolean z2, boolean z3) {
        stopPlay();
        if (z3) {
            this.mRecyclerView.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAllFragment$ax-ui2XSNKt1u1VgS3s9d75zqhs
                @Override // java.lang.Runnable
                public final void run() {
                    HomeAllFragment.this.lambda$onDataListSuccess$3$HomeAllFragment();
                }
            }, 800L);
            if (this.itemDecoration == null) {
                this.itemDecoration = new RVDividerLiveAll(this.mContext, R$color.fq_colorWhite);
            }
            this.itemDecoration.setHasBanner(z);
            this.itemDecoration.setBannerSpanPosition(this.bannerSpanPosition);
            if (this.mRecyclerView.getItemDecorationCount() > 0) {
                this.mRecyclerView.removeItemDecoration(this.itemDecoration);
            }
            this.mRecyclerView.addItemDecoration(this.itemDecoration);
            this.mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeAllFragment$oGa7Vjp85R-uVLKljrHumDiqSDQ
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.SpanSizeLookup
                public final int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                    return HomeAllFragment.this.lambda$onDataListSuccess$4$HomeAllFragment(z, gridLayoutManager, i);
                }
            });
            this.mAdapter.setNewData(list);
        } else {
            this.isLoadingMore = false;
            Observable.just(AppUtils.removeDuplicateList(this.mAdapter.getData(), list)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<LiveEntity>>(false) { // from class: com.tomatolive.library.ui.fragment.HomeAllFragment.3
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<LiveEntity> list2) {
                    HomeAllFragment.this.mAdapter.addData((Collection) list2);
                }
            });
        }
        this.isNoMoreData = z2;
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z2, z3);
    }

    public /* synthetic */ int lambda$onDataListSuccess$4$HomeAllFragment(boolean z, GridLayoutManager gridLayoutManager, int i) {
        return (!z || i != this.bannerSpanPosition) ? 1 : 2;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeAllView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.isLoadingMore = false;
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (!(baseEvent instanceof ListDataUpdateEvent) || !((ListDataUpdateEvent) baseEvent).isAutoRefresh || !getUserVisibleHint()) {
            return;
        }
        onAutoRefreshData();
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        this.pageNum = 1;
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public String getPageStayTimerType() {
        return getString(R$string.fq_all_list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        if (z2) {
            TomatoLiveSDK.getSingleton().onAllLiveListUpdate(bindToLifecycle());
            ((HomeAllPresenter) this.mPresenter).getLiveListFirst(this.mStateView, this.pageNum, z, true, bindToLifecycle());
            return;
        }
        ((HomeAllPresenter) this.mPresenter).getLiveList(this.mStateView, this.pageNum, z, false, bindToLifecycle());
    }

    private void stopPlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.stopPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resumePlay */
    public void lambda$onDataListSuccess$3$HomeAllFragment() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.onRecyclerViewResume();
        }
    }

    private void pausePlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.onRecyclerViewPause();
        }
    }

    private void onDestroyPlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.onDestroyPlay();
        }
    }
}
