package com.one.tomato.thirdpart.recyclerview;

import android.support.annotation.IntRange;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerMultiItemAdapter<T extends MultiItemEntity> extends BaseQuickAdapter<T, BaseViewHolder> {
    private SparseIntArray layouts;

    protected void convert(BaseViewHolder baseViewHolder, T t) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, Object obj) {
        convert(baseViewHolder, (BaseViewHolder) ((MultiItemEntity) obj));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
        if (multiItemEntity != null) {
            return multiItemEntity.getItemType();
        }
        return -255;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public BaseViewHolder onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        return createBaseViewHolder(viewGroup, getLayoutId(i));
    }

    private int getLayoutId(int i) {
        return this.layouts.get(i, BaseMultiItemQuickAdapter.TYPE_NOT_FOUND);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void remove(@IntRange(from = 0) int i) {
        List<T> list = this.mData;
        if (list == 0 || i < 0 || i >= list.size()) {
            return;
        }
        MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
        if (multiItemEntity instanceof IExpandable) {
            removeAllChild((IExpandable) multiItemEntity, i);
        }
        removeDataFromParent(multiItemEntity);
        super.remove(i);
    }

    protected void removeAllChild(IExpandable iExpandable, int i) {
        List subItems;
        if (!iExpandable.isExpanded() || (subItems = iExpandable.getSubItems()) == null || subItems.size() == 0) {
            return;
        }
        int size = subItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            remove(i + 1);
        }
    }

    protected void removeDataFromParent(T t) {
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0) {
            ((IExpandable) this.mData.get(parentPosition)).getSubItems().remove(t);
        }
    }
}
