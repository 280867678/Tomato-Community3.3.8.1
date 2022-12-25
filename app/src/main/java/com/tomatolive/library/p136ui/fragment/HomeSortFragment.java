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
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.p136ui.adapter.HomeLiveAdapter;
import com.tomatolive.library.p136ui.presenter.HomeSortPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLive;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerEmptyView;
import com.tomatolive.library.p136ui.view.iview.IHomeSortView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.live.PlayManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.HomeSortFragment */
/* loaded from: classes3.dex */
public class HomeSortFragment extends BaseFragment<HomeSortPresenter> implements IHomeSortView {
    private boolean isFeeTag = false;
    private HomeLiveAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private PlayManager playManager;
    private String tagId;

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

    static /* synthetic */ int access$408(HomeSortFragment homeSortFragment) {
        int i = homeSortFragment.pageNum;
        homeSortFragment.pageNum = i + 1;
        return i;
    }

    public static HomeSortFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        HomeSortFragment homeSortFragment = new HomeSortFragment();
        bundle.putString(ConstantUtils.TAB_TAG_ID, str);
        homeSortFragment.setArguments(bundle);
        return homeSortFragment;
    }

    public static HomeSortFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        HomeSortFragment homeSortFragment = new HomeSortFragment();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        homeSortFragment.setArguments(bundle);
        return homeSortFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public HomeSortPresenter mo6641createPresenter() {
        return new HomeSortPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.tagId = bundle.getString(ConstantUtils.TAB_TAG_ID);
        this.isFeeTag = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
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
        this.mAdapter = new HomeLiveAdapter(this, R$layout.fq_item_list_live_view_new);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLive(this.mContext, R$color.fq_colorWhite));
        this.mRecyclerView.setAdapter(this.mAdapter);
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
            lambda$onDataListSuccess$3$HomeSortFragment();
        } else {
            pausePlay();
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            lambda$onDataListSuccess$3$HomeSortFragment();
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
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeSortFragment$O26izLz0AQXdlyZLpi59ucXhDLw
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                HomeSortFragment.this.lambda$initListener$0$HomeSortFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.HomeSortFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                ((BaseFragment) HomeSortFragment.this).pageNum = 1;
                HomeSortFragment.this.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeSortFragment$ql33dOlRqUayOiVaQjzAkQLzIKk
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                HomeSortFragment.this.lambda$initListener$1$HomeSortFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeSortFragment$e5QWtJ4Wt1wY01zST5-kYZE-k4Q
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return HomeSortFragment.this.lambda$initListener$2$HomeSortFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.tomatolive.library.ui.fragment.HomeSortFragment.2
            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }

            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0 && HomeSortFragment.this.playManager != null) {
                    HomeSortFragment.this.playManager.onScrolled();
                    HomeSortFragment.this.playManager.onScrollStateChanged();
                }
                if (HomeSortFragment.this.isAutoPreLoadingMore(recyclerView)) {
                    ((BaseFragment) HomeSortFragment.this).isLoadingMore = true;
                    HomeSortFragment.access$408(HomeSortFragment.this);
                    HomeSortFragment.this.sendRequest(false, false);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$HomeSortFragment() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$HomeSortFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity == null) {
            return;
        }
        AppUtils.startTomatoLiveActivity(this.mContext, liveEntity, "1", getString(R$string.fq_other_list));
    }

    public /* synthetic */ boolean lambda$initListener$2$HomeSortFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        PlayManager playManager;
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getItem(i);
        if (liveEntity != null && !liveEntity.isAd && (playManager = this.playManager) != null) {
            playManager.playVideoByPosition(i + baseQuickAdapter.getHeaderLayoutCount());
        }
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeSortView
    public void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
        stopPlay();
        if (z2) {
            this.mRecyclerView.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$HomeSortFragment$nSKfwESLfE640oymSjwejVn9UFg
                @Override // java.lang.Runnable
                public final void run() {
                    HomeSortFragment.this.lambda$onDataListSuccess$3$HomeSortFragment();
                }
            }, 800L);
            this.mAdapter.setNewData(list);
        } else {
            this.isLoadingMore = false;
            Observable.just(AppUtils.removeDuplicateList(this.mAdapter.getData(), list)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<LiveEntity>>(false) { // from class: com.tomatolive.library.ui.fragment.HomeSortFragment.3
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<LiveEntity> list2) {
                    HomeSortFragment.this.mAdapter.addData((Collection) list2);
                }
            });
        }
        this.isNoMoreData = z;
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeSortView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.isLoadingMore = false;
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
        return this.tagId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        if (this.isFeeTag) {
            ((HomeSortPresenter) this.mPresenter).getFeeLiveList(this.mStateView, this.tagId, this.pageNum, z, z2);
        } else {
            ((HomeSortPresenter) this.mPresenter).getLiveList(this.mStateView, this.tagId, this.pageNum, z, z2);
        }
    }

    private void stopPlay() {
        PlayManager playManager;
        if (!this.isFeeTag && (playManager = this.playManager) != null) {
            playManager.stopPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resumePlay */
    public void lambda$onDataListSuccess$3$HomeSortFragment() {
        PlayManager playManager;
        if (!this.isFeeTag && (playManager = this.playManager) != null) {
            playManager.onRecyclerViewResume();
        }
    }

    private void pausePlay() {
        PlayManager playManager;
        if (!this.isFeeTag && (playManager = this.playManager) != null) {
            playManager.onRecyclerViewPause();
        }
    }

    private void onDestroyPlay() {
        PlayManager playManager;
        if (!this.isFeeTag && (playManager = this.playManager) != null) {
            playManager.onDestroyPlay();
        }
    }
}
