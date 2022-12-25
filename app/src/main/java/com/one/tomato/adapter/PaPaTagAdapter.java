package com.one.tomato.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.Tag;

/* compiled from: PaPaTagAdapter.kt */
/* loaded from: classes3.dex */
public final class PaPaTagAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {
    public PaPaTagAdapter() {
        super((int) R.layout.papa_tag_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Tag tag) {
        String str;
        ImageView imageView = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_content) : null;
        if (baseViewHolder != null) {
            imageView = (ImageView) baseViewHolder.getView(R.id.image_add);
        }
        if (tag == null || tag.getTagId() != -10) {
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
        } else {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (textView != null) {
                textView.setVisibility(8);
            }
        }
        if (textView != null) {
            if (tag == null || (str = tag.getTagName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
    }
}
