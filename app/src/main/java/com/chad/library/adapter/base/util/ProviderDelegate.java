package com.chad.library.adapter.base.util;

import android.util.SparseArray;
import com.chad.library.adapter.base.annotation.ItemProviderTag;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/* loaded from: classes2.dex */
public class ProviderDelegate {
    private SparseArray<BaseItemProvider> mItemProviders = new SparseArray<>();

    public void registerProvider(BaseItemProvider baseItemProvider) {
        ItemProviderTag itemProviderTag = (ItemProviderTag) baseItemProvider.getClass().getAnnotation(ItemProviderTag.class);
        if (itemProviderTag == null) {
            throw new ItemProviderAnnotationException("ItemProviderTag not def layout");
        }
        int viewType = itemProviderTag.viewType();
        if (this.mItemProviders.get(viewType) != null) {
            return;
        }
        this.mItemProviders.put(viewType, baseItemProvider);
    }

    public SparseArray<BaseItemProvider> getItemProviders() {
        return this.mItemProviders;
    }
}
