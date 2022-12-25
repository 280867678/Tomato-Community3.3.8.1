package com.one.tomato.mvp.base.view;

import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.View;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.widget.EmptyViewLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseRecyclerViewActivity.kt */
/* loaded from: classes3.dex */
public abstract class MvpBaseRecyclerViewActivity<V extends IBaseView, P extends MvpBasePresenter<V>, ADAPTER extends BaseRecyclerViewAdapter<DATA>, DATA> extends MvpBaseActivity<V, P> {
    private ADAPTER adapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private final int GET_LIST_REFRESH = 1;
    private final int GET_LIST_LOAD = 2;
    private int pageNo = 1;
    private int pageSize = 10;
    private int state = this.GET_LIST_REFRESH;
    private final BaseQuickAdapter.RequestLoadMoreListener onLodeMore = new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity$onLodeMore$1
        @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
        public final void onLoadMoreRequested() {
            MvpBaseRecyclerViewActivity mvpBaseRecyclerViewActivity = MvpBaseRecyclerViewActivity.this;
            mvpBaseRecyclerViewActivity.setState(mvpBaseRecyclerViewActivity.getGET_LIST_LOAD());
            MvpBaseRecyclerViewActivity.this.loadMore();
        }
    };
    private final EmptyViewLayout.ButtonClickListener emptyOnClick = new EmptyViewLayout.ButtonClickListener() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity$emptyOnClick$1
        @Override // com.one.tomato.widget.EmptyViewLayout.ButtonClickListener
        public final void buttonClickListener(View view, int i) {
            MvpBaseRecyclerViewActivity mvpBaseRecyclerViewActivity = MvpBaseRecyclerViewActivity.this;
            Intrinsics.checkExpressionValueIsNotNull(view, "view");
            mvpBaseRecyclerViewActivity.onEmptyRefresh(view, i);
        }
    };

    /* renamed from: createAdapter */
    public abstract ADAPTER mo6446createAdapter();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void loadMore();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void onEmptyRefresh(View view, int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void refresh();

    public final int getGET_LIST_REFRESH() {
        return this.GET_LIST_REFRESH;
    }

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
    public final int getPageSize() {
        return this.pageSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SmartRefreshLayout getRefreshLayout() {
        return this.refreshLayout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ADAPTER getAdapter() {
        return this.adapter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getState() {
        return this.state;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setState(int i) {
        this.state = i;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initRefreshLayout();
        initRecyclerView();
        initAdapter();
    }

    protected void initRefreshLayout() {
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
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
            smartRefreshLayout4.setOnRefreshLoadMoreListener(new MvpBaseRecyclerViewActivity$initRefreshLayout$1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initRecyclerView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        RecyclerView.ItemAnimator itemAnimator = recyclerView2 != null ? recyclerView2.getItemAnimator() : null;
        if (itemAnimator == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.SimpleItemAnimator");
        }
        ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        configLinearLayoutVerticalManager(this.recyclerView);
    }

    protected void initAdapter() {
        EmptyViewLayout emptyViewLayout;
        this.adapter = mo6446createAdapter();
        ADAPTER adapter = this.adapter;
        if (adapter != null) {
            adapter.setOnLoadMoreListener(this.onLodeMore, this.recyclerView);
        }
        ADAPTER adapter2 = this.adapter;
        if (adapter2 != null && (emptyViewLayout = adapter2.getEmptyViewLayout()) != null) {
            emptyViewLayout.setButtonClickListener(this.emptyOnClick);
        }
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateData(int i, ArrayList<DATA> arrayList) {
        ADAPTER adapter;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == this.GET_LIST_REFRESH) {
                SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                ADAPTER adapter2 = this.adapter;
                if (adapter2 != null) {
                    adapter2.setEmptyViewState(2, this.refreshLayout);
                }
            }
            if (i != this.GET_LIST_LOAD || (adapter = this.adapter) == null) {
                return;
            }
            adapter.loadMoreEnd();
            return;
        }
        boolean z = true;
        if (i == this.GET_LIST_REFRESH) {
            SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
            if (smartRefreshLayout2 != null) {
                smartRefreshLayout2.mo6481finishRefresh();
            }
            this.pageNo = 2;
            ADAPTER adapter3 = this.adapter;
            if (adapter3 != null) {
                adapter3.setNewData(arrayList);
            }
        } else {
            this.pageNo++;
            ADAPTER adapter4 = this.adapter;
            if (adapter4 != null) {
                adapter4.addData(arrayList);
            }
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            ADAPTER adapter5 = this.adapter;
            if (adapter5 == null) {
                return;
            }
            adapter5.loadMoreComplete();
            return;
        }
        ADAPTER adapter6 = this.adapter;
        if (adapter6 == null) {
            return;
        }
        adapter6.loadMoreEnd();
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

    protected final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(this, 1, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
    }
}
