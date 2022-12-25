package com.one.tomato.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.Tag;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PublishHotTagAdapter.kt */
/* loaded from: classes3.dex */
public final class PublishHotTagAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {
    private String businessType;

    public PublishHotTagAdapter(String str) {
        super((int) R.layout.publish_post_hot_tag_item);
        this.businessType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Tag tag) {
        String str;
        ImageView imageView = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_content) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_tag) : null;
        ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_clear) : null;
        if (baseViewHolder != null) {
            imageView = (ImageView) baseViewHolder.getView(R.id.image_tag);
        }
        if (textView != null) {
            if (tag == null || (str = tag.getTagName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (tag == null || !tag.isSelect()) {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner4_f5f5f7));
            }
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
        } else {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent));
            }
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
            }
        }
        if (tag != null && tag.getTagId() == -10) {
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(8);
        } else if (!Intrinsics.areEqual(this.businessType, "review_post_pre") && !Intrinsics.areEqual(this.businessType, "review_post")) {
            if (imageView2 != null) {
                imageView2.setVisibility(8);
            }
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(8);
        } else {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent));
            }
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
            }
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(0);
        }
    }
}
