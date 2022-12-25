package com.one.tomato.mvp.p080ui.circle.adapter;

import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.CardView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleItemAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.adapter.CircleItemAdapter */
/* loaded from: classes3.dex */
public final class CircleItemAdapter extends BaseQuickAdapter<PostList, BaseViewHolder> {
    public CircleItemAdapter() {
        super((int) R.layout.item_circle_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostList item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        CardView cardView = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_bar) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_top_bar) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_top_bar) : null;
        RelativeLayout relativeLayout2 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_text) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_des) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_click) : null;
        RelativeLayout relativeLayout3 = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_content) : null;
        if (baseViewHolder != null) {
            cardView = (CardView) baseViewHolder.getView(R.id.cardView);
        }
        if (relativeLayout2 != null) {
            relativeLayout2.setVisibility(8);
        }
        if (roundedImageView != null) {
            roundedImageView.setVisibility(8);
        }
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        if (relativeLayout3 != null) {
            relativeLayout3.setVisibility(0);
        }
        if (textView4 != null) {
            textView4.setVisibility(8);
        }
        String str = "";
        if (textView3 != null) {
            textView3.setText(TextUtils.isEmpty(item.getTitle()) ? str : item.getTitle());
        }
        if (item.getId() == -100) {
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(8);
            }
            if (textView4 != null) {
                textView4.setVisibility(0);
            }
            if (cardView == null) {
                return;
            }
            cardView.setCardBackgroundColor(ContextCompat.getColor(this.mContext, R.color.transparent));
            return;
        }
        if (cardView != null) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(this.mContext, R.color.color_white_al_60));
        }
        int postType = item.getPostType();
        boolean z = true;
        if (postType == 1) {
            if (roundedImageView != null) {
                roundedImageView.setVisibility(0);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_image));
            }
            ArrayList<ImageBean> createImageBeanList = PostUtils.INSTANCE.createImageBeanList(item);
            if (createImageBeanList != null && !createImageBeanList.isEmpty()) {
                z = false;
            }
            if (z) {
                return;
            }
            if (textView != null) {
                textView.setText(createImageBeanList.size() + AppUtil.getString(R.string.circle_post_img_num) + ' ');
            }
            ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView, createImageBeanList.get(0));
        } else if (postType != 2) {
            if (postType != 3) {
                return;
            }
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            if (textView2 == null) {
                return;
            }
            if (!TextUtils.isEmpty(item.getDescription())) {
                str = item.getDescription();
            }
            textView2.setText(str);
        } else {
            if (roundedImageView != null) {
                roundedImageView.setVisibility(0);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_video));
            }
            if (textView != null) {
                textView.setText(item.getVideoTime());
            }
            ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView, new ImageBean(item.getSecVideoCover()));
        }
    }
}
