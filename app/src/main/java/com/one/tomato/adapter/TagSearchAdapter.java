package com.one.tomato.adapter;

import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.Tag;

/* compiled from: TagSearchAdapter.kt */
/* loaded from: classes3.dex */
public final class TagSearchAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {
    public TagSearchAdapter() {
        super((int) R.layout.item_tag_search);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Tag tag) {
        String str;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        if (textView != null) {
            if (tag == null || (str = tag.getTagName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
    }
}
