package com.one.tomato.thirdpart.recyclerview;

import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerViewFragment extends BaseFragment {
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;
    private View view;

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadMore() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refresh() {
    }

    @Override // com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        initRecyclerView();
        initRefreshLayout();
        return this.view;
    }

    protected void initRefreshLayout() {
        this.refreshLayout = (SmartRefreshLayout) this.view.findViewById(R.id.refreshLayout);
        this.refreshLayout.setEnableRefresh(true);
        this.refreshLayout.mo6487setEnableLoadMore(false);
        this.refreshLayout.mo6486setEnableAutoLoadMore(false);
        this.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable(this) { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                }, 500L);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseRecyclerViewFragment.this.refresh();
                    }
                }, 500L);
            }
        });
    }

    protected void initRecyclerView() {
        this.recyclerView = (RecyclerView) this.view.findViewById(R.id.recyclerView);
        this.recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) this.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        configLinearLayoutVerticalManager(this.recyclerView);
    }

    protected final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new BaseLinearLayoutManager(this.mContext, 1, false));
    }
}
