package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.p135db.ShortcutItemEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.ShortcutItemAdapter */
/* loaded from: classes3.dex */
public class ShortcutItemAdapter extends BaseQuickAdapter<ShortcutItemEntity, BaseViewHolder> {
    public ShortcutItemAdapter(int i) {
        super(i);
    }

    public ShortcutItemAdapter(@LayoutRes int i, @Nullable List<ShortcutItemEntity> list) {
        super(i, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ShortcutItemEntity shortcutItemEntity) {
        baseViewHolder.setText(R$id.tv_shortcut_content, shortcutItemEntity == null ? "" : shortcutItemEntity.content);
    }

    public void addMsg(ShortcutItemEntity shortcutItemEntity) {
        this.mData.add(0, shortcutItemEntity);
        notifyItemInserted(0);
    }
}
