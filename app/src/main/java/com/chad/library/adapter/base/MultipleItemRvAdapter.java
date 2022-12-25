package com.chad.library.adapter.base;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.annotation.ItemProviderTag;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {
    private SparseArray<BaseItemProvider> mItemProviders;
    protected ProviderDelegate mProviderDelegate;

    protected abstract int getViewType(T t);

    public abstract void registerItemProvider();

    public MultipleItemRvAdapter(@Nullable List<T> list) {
        super(list);
    }

    public void finishInitialize() {
        this.mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<T>() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.1
            @Override // com.chad.library.adapter.base.util.MultiTypeDelegate
            protected int getItemType(T t) {
                return MultipleItemRvAdapter.this.getViewType(t);
            }
        });
        registerItemProvider();
        this.mItemProviders = this.mProviderDelegate.getItemProviders();
        for (int i = 0; i < this.mItemProviders.size(); i++) {
            int keyAt = this.mItemProviders.keyAt(i);
            BaseItemProvider baseItemProvider = this.mItemProviders.get(keyAt);
            baseItemProvider.mData = this.mData;
            getMultiTypeDelegate().registerItemType(keyAt, ((ItemProviderTag) baseItemProvider.getClass().getAnnotation(ItemProviderTag.class)).layout());
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(V v, T t) {
        BaseItemProvider baseItemProvider = this.mItemProviders.get(v.getItemViewType());
        baseItemProvider.mContext = v.itemView.getContext();
        int layoutPosition = v.getLayoutPosition() - getHeaderLayoutCount();
        baseItemProvider.convert(v, t, layoutPosition);
        bindClick(v, t, layoutPosition, baseItemProvider);
    }

    private void bindClick(final V v, final T t, final int i, final BaseItemProvider baseItemProvider) {
        View view = v.itemView;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                baseItemProvider.onClick(v, t, i);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                return baseItemProvider.onLongClick(v, t, i);
            }
        });
    }
}
