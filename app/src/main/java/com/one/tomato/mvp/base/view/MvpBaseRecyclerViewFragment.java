package com.one.tomato.mvp.base.view;

import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.View;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.thirdpart.recyclerview.BaseGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseStaggerGridLayoutManager;
import com.one.tomato.widget.EmptyViewLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseRecyclerViewFragment.kt */
/* loaded from: classes3.dex */
public abstract class MvpBaseRecyclerViewFragment<V extends IBaseView, P extends MvpBasePresenter<V>, ADAPTER extends BaseRecyclerViewAdapter<DATA>, DATA> extends MvpBaseFragment<V, P> {
    private HashMap _$_findViewCache;
    private ADAPTER adapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private final int GET_LIST_REFRESH = 1;
    private final int GET_LIST_LOAD = 2;
    private int pageNo = 1;
    private int pageSize = 10;
    private int state = this.GET_LIST_REFRESH;
    private final BaseQuickAdapter.RequestLoadMoreListener onLodeMore = new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment$onLodeMore$1
        @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
        public final void onLoadMoreRequested() {
            MvpBaseRecyclerViewFragment mvpBaseRecyclerViewFragment = MvpBaseRecyclerViewFragment.this;
            mvpBaseRecyclerViewFragment.setState(mvpBaseRecyclerViewFragment.getGET_LIST_LOAD());
            MvpBaseRecyclerViewFragment.this.loadMore();
        }
    };
    private final EmptyViewLayout.ButtonClickListener emptyOnClick = new EmptyViewLayout.ButtonClickListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment$emptyOnClick$1
        @Override // com.one.tomato.widget.EmptyViewLayout.ButtonClickListener
        public final void buttonClickListener(View view, int i) {
            MvpBaseRecyclerViewFragment mvpBaseRecyclerViewFragment = MvpBaseRecyclerViewFragment.this;
            Intrinsics.checkExpressionValueIsNotNull(view, "view");
            mvpBaseRecyclerViewFragment.onEmptyRefresh(view, i);
        }
    };

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    /* renamed from: createAdapter */
    protected abstract ADAPTER mo6434createAdapter();

    public int getPreLoadNumber() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void loadMore();

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onEmptyRefresh(View view, int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void refresh();

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getGET_LIST_REFRESH() {
        return this.GET_LIST_REFRESH;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getGET_LIST_LOAD() {
        return this.GET_LIST_LOAD;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getPageNo() {
        return this.pageNo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setPageNo(int i) {
        this.pageNo = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPageSize() {
        return this.pageSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPageSize(int i) {
        this.pageSize = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SmartRefreshLayout getRefreshLayout() {
        return this.refreshLayout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    protected void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ADAPTER getAdapter() {
        return this.adapter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setAdapter(ADAPTER adapter) {
        this.adapter = adapter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getState() {
        return this.state;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setState(int i) {
        this.state = i;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        initRefreshLayout();
        initRecyclerView();
        initAdapter();
    }

    protected final void initRefreshLayout() {
        View layoutView = getLayoutView();
        this.refreshLayout = layoutView != null ? (SmartRefreshLayout) layoutView.findViewById(R.id.refreshLayout) : null;
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableRefresh(true);
        }
        SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
        if (smartRefreshLayout2 != null) {
            smartRefreshLayout2.mo6487setEnableLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout3 = this.refreshLayout;
        if (smartRefreshLayout3 != null) {
            smartRefreshLayout3.mo6486setEnableAutoLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout4 = this.refreshLayout;
        if (smartRefreshLayout4 != null) {
            smartRefreshLayout4.setOnRefreshLoadMoreListener(new MvpBaseRecyclerViewFragment$initRefreshLayout$1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initRecyclerView() {
        View layoutView = getLayoutView();
        RecyclerView.ItemAnimator itemAnimator = null;
        setRecyclerView(layoutView != null ? (RecyclerView) layoutView.findViewById(R.id.recyclerView) : null);
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            itemAnimator = recyclerView2.getItemAnimator();
        }
        SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) itemAnimator;
        if (simpleItemAnimator != null) {
            simpleItemAnimator.setSupportsChangeAnimations(false);
        }
        configLinearLayoutVerticalManager(getRecyclerView());
    }

    protected void initAdapter() {
        EmptyViewLayout emptyViewLayout;
        this.adapter = mo6434createAdapter();
        ADAPTER adapter = this.adapter;
        if (adapter != null) {
            adapter.setPreLoadNumber(getPreLoadNumber());
        }
        ADAPTER adapter2 = this.adapter;
        if (adapter2 != null) {
            adapter2.setOnLoadMoreListener(this.onLodeMore, getRecyclerView());
        }
        ADAPTER adapter3 = this.adapter;
        if (adapter3 != null && (emptyViewLayout = adapter3.getEmptyViewLayout()) != null) {
            emptyViewLayout.setButtonClickListener(this.emptyOnClick);
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final BaseQuickAdapter.RequestLoadMoreListener getOnLodeMore() {
        return this.onLodeMore;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final EmptyViewLayout.ButtonClickListener getEmptyOnClick() {
        return this.emptyOnClick;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateData(ArrayList<DATA> arrayList) {
        ADAPTER adapter;
        ADAPTER adapter2;
        List data;
        ADAPTER adapter3;
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.mo6481finishRefresh();
        }
        if (arrayList == null || arrayList.isEmpty()) {
            if (this.state == this.GET_LIST_REFRESH && (adapter3 = this.adapter) != null) {
                adapter3.setEmptyViewState(2, this.refreshLayout);
            }
            if (this.state == this.GET_LIST_LOAD && (adapter2 = this.adapter) != null && (data = adapter2.getData()) != null && data.size() == 0) {
                ADAPTER adapter4 = this.adapter;
                if (adapter4 != null) {
                    adapter4.setEmptyViewState(2, this.refreshLayout);
                }
                ADAPTER adapter5 = this.adapter;
                if (adapter5 == null) {
                    return;
                }
                adapter5.loadMoreEnd();
                return;
            } else if (this.state != this.GET_LIST_LOAD || (adapter = this.adapter) == null) {
                return;
            } else {
                adapter.loadMoreEnd();
                return;
            }
        }
        boolean z = true;
        if (this.state == this.GET_LIST_REFRESH) {
            this.pageNo = 2;
            ADAPTER adapter6 = this.adapter;
            if (adapter6 != null) {
                adapter6.setNewData(arrayList);
            }
        } else {
            this.pageNo++;
            ADAPTER adapter7 = this.adapter;
            if (adapter7 != null) {
                adapter7.addData(arrayList);
            }
        }
        if (arrayList.size() < getPageSize()) {
            z = false;
        }
        if (z) {
            ADAPTER adapter8 = this.adapter;
            if (adapter8 == null) {
                return;
            }
            adapter8.loadMoreComplete();
            return;
        }
        ADAPTER adapter9 = this.adapter;
        if (adapter9 == null) {
            return;
        }
        adapter9.loadMoreEnd();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateDataFail() {
        List data;
        ADAPTER adapter;
        if (this.state == this.GET_LIST_REFRESH) {
            SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
            if (smartRefreshLayout != null) {
                smartRefreshLayout.mo6481finishRefresh();
            }
            ADAPTER adapter2 = this.adapter;
            if (adapter2 != null) {
                adapter2.setEmptyViewState(1, this.refreshLayout);
            }
        } else {
            ADAPTER adapter3 = this.adapter;
            if (adapter3 != null) {
                adapter3.loadMoreFail();
            }
        }
        ADAPTER adapter4 = this.adapter;
        if (adapter4 == null || (data = adapter4.getData()) == null || data.size() != 0 || (adapter = this.adapter) == null) {
            return;
        }
        adapter.setEmptyViewState(1, this.refreshLayout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 1, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void configLinearLayoutHorizontalManager(RecyclerView recyclerView) {
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 0, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void configStaggerLayoutManager(RecyclerView recyclerView, int i) {
        BaseStaggerGridLayoutManager baseStaggerGridLayoutManager = new BaseStaggerGridLayoutManager(getMContext(), i, 1);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseStaggerGridLayoutManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void configGridLayoutManager(RecyclerView recyclerView, int i) {
        BaseGridLayoutManager baseGridLayoutManager = new BaseGridLayoutManager(getMContext(), i);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseGridLayoutManager);
        }
    }
}
