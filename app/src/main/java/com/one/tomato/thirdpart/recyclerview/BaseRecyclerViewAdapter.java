package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.one.tomato.widget.EmptyViewLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private Context context;
    protected View emptyView;
    protected EmptyViewLayout emptyViewLayout;
    private int emptyViewState;
    private LoadMoreView mLoadMoreView = new RecyclerViewLoadMoreView();
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, T t) {
    }

    public void onEmptyRefresh(int i) {
    }

    public void onLoadMore() {
    }

    public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    public void onRecyclerItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    public void onRecyclerItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    public BaseRecyclerViewAdapter(Context context, int i, RecyclerView recyclerView) {
        super(i);
        this.context = context;
        this.recyclerView = recyclerView;
        init();
    }

    public BaseRecyclerViewAdapter(Context context, int i, List<T> list, RecyclerView recyclerView) {
        super(i, list);
        this.context = context;
        this.recyclerView = recyclerView;
        init();
    }

    private void init() {
        openLoadAnimation(1);
        setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerViewAdapter.this.onRecyclerItemClick(baseQuickAdapter, view, i);
            }
        });
        setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerViewAdapter.this.onRecyclerItemLongClick(baseQuickAdapter, view, i);
                return true;
            }
        });
        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerViewAdapter.this.onRecyclerItemChildClick(baseQuickAdapter, view, i);
            }
        });
        setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerViewAdapter.this.onRecyclerItemChildLongClick(baseQuickAdapter, view, i);
                return true;
            }
        });
        setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public void onLoadMoreRequested() {
                BaseRecyclerViewAdapter.this.onLoadMore();
            }
        }, this.recyclerView);
        setEnableLoadMore(true);
        this.emptyView = LayoutInflater.from(this.context).inflate(R.layout.recyclerview_empty_layout, (ViewGroup) this.recyclerView.getParent(), false);
        this.emptyViewLayout = (EmptyViewLayout) this.emptyView.findViewById(R.id.list_empty);
        this.emptyViewLayout.setButtonClickListener(new EmptyViewLayout.ButtonClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter.6
            @Override // com.one.tomato.widget.EmptyViewLayout.ButtonClickListener
            public void buttonClickListener(View view, int i) {
                BaseRecyclerViewAdapter.this.onEmptyRefresh(i);
            }
        });
        setLoadMoreView(this.mLoadMoreView);
    }

    public void setEmptyViewState(int i, RefreshLayout refreshLayout) {
        this.emptyViewState = i;
        this.emptyViewLayout.setState(i);
        setEmptyView(this.emptyView);
        this.mData.clear();
        notifyDataSetChanged();
        if (refreshLayout != null) {
            refreshLayout.mo6487setEnableLoadMore(false);
        }
    }

    public void setEmptyViewState(int i, String str, RefreshLayout refreshLayout) {
        this.emptyViewState = i;
        this.emptyViewLayout.setState(i, str);
        setEmptyView(this.emptyView);
        this.mData.clear();
        notifyDataSetChanged();
        if (refreshLayout != null) {
            refreshLayout.mo6487setEnableLoadMore(false);
        }
    }

    public EmptyViewLayout getEmptyViewLayout() {
        return this.emptyViewLayout;
    }

    public int getEmptyViewState() {
        return this.emptyViewState;
    }
}
