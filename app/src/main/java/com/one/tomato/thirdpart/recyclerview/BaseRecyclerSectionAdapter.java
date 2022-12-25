package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.one.tomato.widget.EmptyViewLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerSectionAdapter<T extends SectionEntity> extends BaseQuickAdapter<T, BaseViewHolder> {
    private Context context;
    protected View emptyView;
    protected EmptyViewLayout emptyViewLayout;
    private LoadMoreView mLoadMoreView = new RecyclerViewLoadMoreView();
    protected int mSectionHeadResId;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, T t) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convertHead(BaseViewHolder baseViewHolder, T t) {
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, Object obj) {
        convert(baseViewHolder, (BaseViewHolder) ((SectionEntity) obj));
    }

    public BaseRecyclerSectionAdapter(Context context, int i, int i2, RecyclerView recyclerView) {
        super(i);
        this.mSectionHeadResId = i2;
        this.context = context;
        this.recyclerView = recyclerView;
        init();
    }

    public BaseRecyclerSectionAdapter(Context context, int i, int i2, List<T> list, RecyclerView recyclerView) {
        super(i, list);
        this.mSectionHeadResId = i2;
        this.context = context;
        this.recyclerView = recyclerView;
        init();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        return ((SectionEntity) this.mData.get(i)).isHeader ? 1092 : 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public BaseViewHolder onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1092) {
            return createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup));
        }
        return super.onCreateDefViewHolder(viewGroup, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public boolean isFixedViewType(int i) {
        return super.isFixedViewType(i) || i == 1092;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter, android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        if (baseViewHolder.getItemViewType() == 1092) {
            setFullSpan(baseViewHolder);
            convertHead(baseViewHolder, (SectionEntity) getItem(i - getHeaderLayoutCount()));
            return;
        }
        super.onBindViewHolder((BaseRecyclerSectionAdapter<T>) baseViewHolder, i);
    }

    private void init() {
        openLoadAnimation(1);
        setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerSectionAdapter.this.onRecyclerItemClick(baseQuickAdapter, view, i);
            }
        });
        setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerSectionAdapter.this.onRecyclerItemLongClick(baseQuickAdapter, view, i);
                return true;
            }
        });
        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerSectionAdapter.this.onRecyclerItemChildClick(baseQuickAdapter, view, i);
            }
        });
        setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseRecyclerSectionAdapter.this.onRecyclerItemChildLongClick(baseQuickAdapter, view, i);
                return true;
            }
        });
        setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public void onLoadMoreRequested() {
                BaseRecyclerSectionAdapter.this.onLoadMore();
            }
        }, this.recyclerView);
        setEnableLoadMore(true);
        this.emptyView = LayoutInflater.from(this.context).inflate(R.layout.recyclerview_empty_layout, (ViewGroup) this.recyclerView.getParent(), false);
        this.emptyViewLayout = (EmptyViewLayout) this.emptyView.findViewById(R.id.list_empty);
        this.emptyViewLayout.setButtonClickListener(new EmptyViewLayout.ButtonClickListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter.6
            @Override // com.one.tomato.widget.EmptyViewLayout.ButtonClickListener
            public void buttonClickListener(View view, int i) {
                BaseRecyclerSectionAdapter.this.onEmptyRefresh(i);
            }
        });
        setLoadMoreView(this.mLoadMoreView);
    }

    public void setEmptyViewState(int i, RefreshLayout refreshLayout) {
        this.emptyViewLayout.setState(i);
        setEmptyView(this.emptyView);
        this.mData.clear();
        notifyDataSetChanged();
        if (refreshLayout != null) {
            refreshLayout.mo6487setEnableLoadMore(false);
        }
    }
}
