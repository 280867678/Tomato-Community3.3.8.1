package com.tomatolive.library.p136ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.LabelMenuEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.p136ui.activity.home.AnchorAuthResultActivity;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.presenter.HomeHotPresenter;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.AnchorAuthDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.headview.HomeHotHeadView;
import com.tomatolive.library.p136ui.view.iview.IHomeHotView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.live.PlayManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.HomeHotFragment */
/* loaded from: classes3.dex */
public class HomeHotFragment extends BaseFragment<HomeHotPresenter> implements IHomeHotView {
    private HomeHotHeadView headView;
    private boolean isRouterFlag = false;
    private ImageView ivStartLive;
    private HomeLiveAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int mScrollThreshold;
    private SmartRefreshLayout mSmartRefreshLayout;
    private PlayManager playManager;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isEnablePageStayReport() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onLiveHelperAppConfigFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    static /* synthetic */ int access$708(HomeHotFragment homeHotFragment) {
        int i = homeHotFragment.pageNum;
        homeHotFragment.pageNum = i + 1;
        return i;
    }

    public static HomeHotFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeHotFragment homeHotFragment = new HomeHotFragment();
        homeHotFragment.setArguments(bundle);
        return homeHotFragment;
    }

    public static HomeHotFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        HomeHotFragment homeHotFragment = new HomeHotFragment();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        homeHotFragment.setArguments(bundle);
        return homeHotFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public HomeHotPresenter mo6641createPresenter() {
        return new HomeHotPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isRouterFlag = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_home_hot;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.ivStartLive = (ImageView) view.findViewById(R$id.fab);
        this.playManager = new PlayManager(this.mContext);
        ((HomeHotPresenter) this.mPresenter).getAllLiveList(bindToLifecycle());
        this.mScrollThreshold = getResources().getDimensionPixelOffset(R$dimen.fq_fab_scroll_threshold);
        initAdapter();
        if (this.isRouterFlag) {
            sendRequest(true);
            this.ivStartLive.setVisibility(4);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z) {
            lambda$onDataListSuccess$4$HomeHotFragment();
        } else {
            pausePlay();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            lambda$onDataListSuccess$4$HomeHotFragment();
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

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.headView = new HomeHotHeadView(this.mContext);
        this.mAdapter = new HomeLiveAdapter(this, R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite, true, true));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.headView);
        this.mAdapter.setEmptyView(R$layout.fq_layout_empty_view_warp, this.mRecyclerView);
        this.mAdapter.setHeaderAndEmpty(true);
        AnimUtils.playLiveScaleAnim(this.ivStartLive);
        this.playManager.initRecyclerViewPlayManager(this.mRecyclerView);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeHotFragment$PL7oyOTnVmToU_uHnYIWcqC6qoE
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                HomeHotFragment.this.lambda$initListener$0$HomeHotFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeHotFragment$KCAfreGBFsuMbke3Ys8tXREJpYo
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                HomeHotFragment.this.lambda$initListener$1$HomeHotFragment(refreshLayout);
            }
        });
        RxView.clicks(this.ivStartLive).throttleFirst(2L, TimeUnit.SECONDS).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.fragment.HomeHotFragment.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (!AppUtils.isLogin(((BaseFragment) HomeHotFragment.this).mContext)) {
                    return;
                }
                ((HomeHotPresenter) ((BaseFragment) HomeHotFragment.this).mPresenter).onAnchorAuth();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeHotFragment$utSlLuYmJ2uLn61VWpuF1hqv4RQ
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeHotFragment.this.lambda$initListener$2$HomeHotFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeHotFragment$wX_-f5rh77f_La470n8QF1CSMpg
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return HomeHotFragment.this.lambda$initListener$3$HomeHotFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.tomatolive.library.ui.fragment.HomeHotFragment.2
            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (!HomeHotFragment.this.isRouterFlag) {
                    if (Math.abs(i2) > HomeHotFragment.this.mScrollThreshold) {
                        if (i2 > 0) {
                            HomeHotFragment.this.ivStartLive.setVisibility(4);
                        } else {
                            HomeHotFragment.this.ivStartLive.setVisibility(0);
                        }
                    }
                }
                super.onScrolled(recyclerView, i, i2);
            }

            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0 && HomeHotFragment.this.playManager != null) {
                    HomeHotFragment.this.playManager.onScrolled();
                    HomeHotFragment.this.playManager.onScrollStateChanged();
                }
                if (HomeHotFragment.this.isAutoPreLoadingMore(recyclerView)) {
                    ((BaseFragment) HomeHotFragment.this).isLoadingMore = true;
                    HomeHotFragment.access$708(HomeHotFragment.this);
                    ((HomeHotPresenter) ((BaseFragment) HomeHotFragment.this).mPresenter).getLiveList(((BaseFragment) HomeHotFragment.this).mStateView, ((BaseFragment) HomeHotFragment.this).pageNum, false, false, HomeHotFragment.this.bindToLifecycle());
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$HomeHotFragment() {
        if (!SPUtils.getInstance().getBoolean(ConstantUtils.LIVE_LABEL_MENU, true)) {
            EventBus.getDefault().post(new LabelMenuEvent());
        } else {
            sendRequest(true);
        }
    }

    public /* synthetic */ void lambda$initListener$1$HomeHotFragment(RefreshLayout refreshLayout) {
        if (!this.isRouterFlag) {
            this.ivStartLive.setVisibility(0);
        }
        if (!SPUtils.getInstance().getBoolean(ConstantUtils.LIVE_LABEL_MENU, true)) {
            EventBus.getDefault().post(new LabelMenuEvent());
            refreshLayout.mo6481finishRefresh();
            return;
        }
        refreshLayout.resetNoMoreData();
        ((HomeHotPresenter) this.mPresenter).getAllLiveList(bindToLifecycle());
        sendRequest(false);
        refreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$2$HomeHotFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        if (liveEntity.isAd) {
            AppUtils.onLiveListClickAdEvent(this.mContext, liveEntity);
        } else {
            AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "1", getString(R$string.fq_hot_list));
        }
    }

    public /* synthetic */ boolean lambda$initListener$3$HomeHotFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        PlayManager playManager;
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity != null && !liveEntity.isAd && (playManager = this.playManager) != null) {
            playManager.playVideoByPosition(i + baseQuickAdapter.getHeaderLayoutCount());
        }
        return true;
    }

    private void sendRequest(boolean z) {
        this.pageNum = 1;
        ((HomeHotPresenter) this.mPresenter).getLiveList(this.mStateView, this.pageNum, z, true, bindToLifecycle());
        ((HomeHotPresenter) this.mPresenter).getBannerList("1");
        ((HomeHotPresenter) this.mPresenter).getTopList();
        CacheUtils.updateCacheVersion();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onAnchorAuthSuccess(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return;
        }
        int i = anchorEntity.isChecked;
        if (i == -2) {
            AnchorAuthDialog.newInstance().show(getChildFragmentManager());
        } else if (i == -1 || i == 0) {
            Intent intent = new Intent(this.mContext, AnchorAuthResultActivity.class);
            intent.putExtra(ConstantUtils.AUTH_TYPE, anchorEntity.isChecked);
            startActivity(intent);
        } else if (i != 1) {
        } else {
            if (anchorEntity.isFrozenFlag()) {
                WarnDialog.newInstance(WarnDialog.FROZEN_TIP).show(getChildFragmentManager());
            } else if (AppUtils.isEnableLiveHelperJump()) {
                ((HomeHotPresenter) this.mPresenter).getLiveHelperAppConfig();
            } else {
                startActivity(PrepareLiveActivity.class);
            }
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
        stopPlay();
        if (z2) {
            this.mRecyclerView.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeHotFragment$3Nk6CG1W-a6aRWdNy70J728tZPg
                @Override // java.lang.Runnable
                public final void run() {
                    HomeHotFragment.this.lambda$onDataListSuccess$4$HomeHotFragment();
                }
            }, 800L);
            this.mAdapter.setNewData(list);
        } else {
            this.isLoadingMore = false;
            Observable.just(AppUtils.removeDuplicateList(this.mAdapter.getData(), list)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<LiveEntity>>(false) { // from class: com.tomatolive.library.ui.fragment.HomeHotFragment.3
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<LiveEntity> list2) {
                    HomeHotFragment.this.mAdapter.addData((Collection) list2);
                }
            });
        }
        this.isNoMoreData = z;
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.isLoadingMore = false;
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        this.pageNum = 1;
        ((HomeHotPresenter) this.mPresenter).getLiveList(this.mStateView, this.pageNum, false, true, bindToLifecycle());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onBannerListSuccess(List<BannerEntity> list) {
        HomeHotHeadView homeHotHeadView = this.headView;
        if (homeHotHeadView != null) {
            homeHotHeadView.initBannerImages(list);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onTopListSuccess(List<IndexRankEntity> list) {
        HomeHotHeadView homeHotHeadView = this.headView;
        if (homeHotHeadView != null) {
            homeHotHeadView.initTopList(list);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeHotView
    public void onLiveHelperAppConfigSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
        if (liveHelperAppConfigEntity == null) {
            return;
        }
        AppUtils.toLiveHelperApp(this.mContext, liveHelperAppConfigEntity.androidPackageName, liveHelperAppConfigEntity.startLiveAppDownloadUrl, getChildFragmentManager());
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof ListDataUpdateEvent) {
            if (((ListDataUpdateEvent) baseEvent).isAutoRefresh) {
                if (!getUserVisibleHint()) {
                    return;
                }
                onAutoRefreshData();
                return;
            }
            sendRequest(true);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public String getPageStayTimerType() {
        return getString(R$string.fq_hot_list);
    }

    private void stopPlay() {
        PlayManager playManager = this.playManager;
        if (playManager != null) {
            playManager.stopPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resumePlay */
    public void lambda$onDataListSuccess$4$HomeHotFragment() {
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
