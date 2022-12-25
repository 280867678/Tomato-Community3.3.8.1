package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.VideoTabStatusChange;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.bus.RxBus;
import com.one.tomato.mvp.base.bus.RxSubscriptions;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.NewPaPaAdapter;
import com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotContact$IPaPaHotView;
import com.one.tomato.mvp.p080ui.papa.presenter.NewPaPaHotPresenter;
import com.one.tomato.thirdpart.recyclerview.DividerItemDecoration;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotHomeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotHomeFragment */
/* loaded from: classes3.dex */
public final class PaPaHotHomeFragment extends MvpBaseRecyclerViewFragment<IPaPaHotContact$IPaPaHotView, NewPaPaHotPresenter, NewPaPaAdapter, PostList> implements IPaPaHotContact$IPaPaHotView {
    private HashMap _$_findViewCache;
    private int adPageIndex;
    private PaPaHotHeadView headView;
    private int insertPageIndex;
    private ArrayList<AdPage> listPages;
    private Disposable refreshBus;
    private int insertLoopNum = 10;
    private final int spanCount = 2;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.new_papa_hot_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        refreshCurrentPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public NewPaPaAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            NewPaPaAdapter newPaPaAdapter = new NewPaPaAdapter(mContext, getRecyclerView());
            addHeaderView(newPaPaAdapter);
            return newPaPaAdapter;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NewPaPaHotPresenter mo6441createPresenter() {
        return new NewPaPaHotPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
        configStaggerLayoutManager(getRecyclerView(), this.spanCount);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getMContext(), 2, 10, -1);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        registerRxBus();
    }

    public final void refreshCurrentPage() {
        NewPaPaHotPresenter newPaPaHotPresenter = (NewPaPaHotPresenter) getMPresenter();
        if (newPaPaHotPresenter != null) {
            newPaPaHotPresenter.requestMostHotVideoList();
        }
        NewPaPaHotPresenter newPaPaHotPresenter2 = (NewPaPaHotPresenter) getMPresenter();
        if (newPaPaHotPresenter2 != null) {
            newPaPaHotPresenter2.requestPostList(getPageNo(), getPageSize());
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        this.insertLoopNum = systemParam.getPaPaFrequencyOnList();
        this.listPages = DBUtil.getAdPage(C2516Ad.TYPE_PAPA);
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<PostList> data;
        NewPaPaAdapter adapter;
        NewPaPaAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        NewPaPaAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh(100);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        LogUtil.m3788d("yan");
        NewPaPaHotPresenter newPaPaHotPresenter = (NewPaPaHotPresenter) getMPresenter();
        if (newPaPaHotPresenter != null) {
            newPaPaHotPresenter.requestPostList(getPageNo(), getPageSize());
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotContact$IPaPaHotView
    public void handlerMostHotVideo(ArrayList<PostList> arrayList) {
        PaPaHotHeadView paPaHotHeadView = this.headView;
        if (paPaHotHeadView != null) {
            paPaHotHeadView.setData(arrayList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.impl.IPaPaHotContact$IPaPaHotView
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        updateData(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void updateData(ArrayList<PostList> arrayList) {
        NewPaPaAdapter adapter;
        List<PostList> data;
        super.updateData(arrayList);
        if (getState() == getGET_LIST_REFRESH()) {
            this.insertPageIndex = 1;
        }
        PostList postList = AdUtil.getPostList(getAdPage());
        if (postList != null) {
            NewPaPaAdapter adapter2 = getAdapter();
            Integer valueOf = (adapter2 == null || (data = adapter2.getData()) == null) ? null : Integer.valueOf(data.size());
            while (true) {
                int i = this.insertLoopNum;
                int i2 = this.insertPageIndex;
                int i3 = ((i * i2) + i2) - 1;
                if (valueOf != null && i3 >= valueOf.intValue() + 1) {
                    return;
                }
                if (postList != null && (adapter = getAdapter()) != null) {
                    adapter.addData(i3, (int) postList);
                }
                this.insertPageIndex++;
            }
        }
    }

    private final AdPage getAdPage() {
        ArrayList<AdPage> arrayList = this.listPages;
        AdPage adPage = null;
        if (arrayList != null) {
            if (arrayList != null && arrayList.isEmpty()) {
                return null;
            }
            ArrayList<AdPage> arrayList2 = this.listPages;
            AdPage adPage2 = arrayList2 != null ? arrayList2.get(0) : null;
            if ((adPage2 != null ? adPage2.getWeight() : 0) > 0) {
                ArrayList<AdPage> arrayList3 = this.listPages;
                if (arrayList3 == null) {
                    return null;
                }
                return arrayList3.get(AdUtil.getListIndexByWeight(arrayList3));
            }
            int i = this.adPageIndex;
            ArrayList<AdPage> arrayList4 = this.listPages;
            if (i >= (arrayList4 != null ? arrayList4.size() : 0)) {
                this.adPageIndex = 0;
            }
            LogUtil.m3785e("papa视频", "adPageIndex = " + this.adPageIndex);
            ArrayList<AdPage> arrayList5 = this.listPages;
            if (arrayList5 != null) {
                adPage = arrayList5.get(this.adPageIndex);
            }
            this.adPageIndex++;
            return adPage;
        }
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unRegisterRxBus();
    }

    private final void registerRxBus() {
        this.refreshBus = RxBus.getDefault().toObservable(VideoTabStatusChange.class).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<VideoTabStatusChange>() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHomeFragment$registerRxBus$1
            @Override // io.reactivex.functions.Consumer
            public void accept(VideoTabStatusChange t) {
                boolean isVisibleToUser;
                Intrinsics.checkParameterIsNotNull(t, "t");
                if (t.refresh) {
                    isVisibleToUser = PaPaHotHomeFragment.this.isVisibleToUser();
                    if (!isVisibleToUser) {
                        return;
                    }
                    PaPaHotHomeFragment.this.setLazyLoad(false);
                    PaPaHotHomeFragment.this.setUserVisibleHint(true);
                    LogUtil.m3787d("yana", "調用怕怕熱");
                }
            }
        });
        RxSubscriptions.add(this.refreshBus);
    }

    private final void unRegisterRxBus() {
        RxSubscriptions.remove(this.refreshBus);
    }

    private final void addHeaderView(NewPaPaAdapter newPaPaAdapter) {
        Context mContext = getMContext();
        if (mContext != null) {
            this.headView = new PaPaHotHeadView(mContext);
            newPaPaAdapter.addHeaderView(this.headView);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }
}
