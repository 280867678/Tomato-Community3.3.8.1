package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.VideoTabStatusChange;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaHotDetailListAdapter;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotListContact$IPaPaHotListView;
import com.one.tomato.mvp.p080ui.papa.presenter.PaPaHotListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotListFragment */
/* loaded from: classes3.dex */
public final class PaPaHotListFragment extends MvpBaseRecyclerViewFragment<IPaPaHotListContact$IPaPaHotListView, PaPaHotListPresenter, PaPaHotDetailListAdapter, PostList> implements IPaPaHotListContact$IPaPaHotListView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int dateType;
    private Disposable refreshBus;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.papa_hot_list_fragment;
    }

    /* compiled from: PaPaHotListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotListFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PaPaHotListFragment initInstance(int i) {
            Bundle bundle = new Bundle();
            PaPaHotListFragment paPaHotListFragment = new PaPaHotListFragment();
            bundle.putInt("dateType", i);
            paPaHotListFragment.setArguments(bundle);
            return paPaHotListFragment;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        requestHotList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        requestHotList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        showDialog();
        requestHotList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public PaPaHotDetailListAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            return new PaPaHotDetailListAdapter(mContext, recyclerView);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public PaPaHotListPresenter mo6441createPresenter() {
        return new PaPaHotListPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        registerRxBus();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.dateType = arguments.getInt("dateType");
        }
        if (this.dateType == 1) {
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListFragment$onLazyLoad$1
                @Override // java.lang.Runnable
                public final void run() {
                    SmartRefreshLayout refreshLayout2;
                    refreshLayout2 = PaPaHotListFragment.this.getRefreshLayout();
                    if (refreshLayout2 != null) {
                        refreshLayout2.autoRefresh();
                    }
                }
            }, 100L);
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<PostList> data;
        PaPaHotDetailListAdapter adapter;
        PaPaHotDetailListAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        dismissDialog();
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        PaPaHotDetailListAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotListContact$IPaPaHotListView
    public void handlerHoutListData(ArrayList<PostList> arrayList) {
        updateData(arrayList);
        PaPaHotDetailListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setPageNumber(getPageNo());
        }
    }

    public final void requestHotList() {
        PaPaHotListPresenter paPaHotListPresenter = (PaPaHotListPresenter) getMPresenter();
        if (paPaHotListPresenter != null) {
            paPaHotListPresenter.requestHoutListData(this.dateType, getPageNo(), getPageSize());
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterRxBus();
        _$_clearFindViewByIdCache();
    }

    private final void registerRxBus() {
        this.refreshBus = RxBus.getDefault().toObservable(VideoTabStatusChange.class).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new PaPaHotListFragment$registerRxBus$1(this));
        RxSubscriptions.add(this.refreshBus);
    }

    private final void unRegisterRxBus() {
        RxSubscriptions.remove(this.refreshBus);
    }
}
