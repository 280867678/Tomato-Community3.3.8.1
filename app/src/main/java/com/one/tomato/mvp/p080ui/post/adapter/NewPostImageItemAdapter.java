package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewPostImageItemAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostImageItemAdapter */
/* loaded from: classes3.dex */
public final class NewPostImageItemAdapter extends BaseRecyclerViewAdapter<ImageBean> {
    private boolean isReward;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostImageItemAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_new_post_item_image, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    public final void isReward(boolean z) {
        this.isReward = z;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ImageBean imageBean) {
        super.convert(baseViewHolder, (BaseViewHolder) imageBean);
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.new_image) : null;
        ProgressBar progressBar = baseViewHolder != null ? (ProgressBar) baseViewHolder.getView(R.id.loading) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_image_layer) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_show_long) : null;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        if (this.mContext != null) {
            if (imageView != null) {
                imageView.setImageBitmap(null);
            }
            if (this.isReward) {
                if (getItemCount() > 1 && getData().indexOf(imageBean) == 0) {
                    ImageLoaderUtil.loadViewPagerOriginImage(this.mContext, imageView, progressBar, imageBean, 0);
                    return;
                }
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
                ImageLoaderUtil.loadViewPagerOriginImageBlurs(this.mContext, imageView, null, imageBean, new NewPostImageItemAdapter$convert$$inlined$let$lambda$1(this, imageView, imageBean, progressBar, relativeLayout));
            } else {
                ImageLoaderUtil.loadViewPagerOriginImage(this.mContext, imageView, progressBar, imageBean, 0);
            }
        }
        if (imageBean == null || imageBean.getImage() == null) {
            return;
        }
        if (imageBean.isLongImg()) {
            if (textView == null) {
                return;
            }
            textView.setVisibility(0);
        } else if (textView == null) {
        } else {
            textView.setVisibility(8);
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder holder) {
        ImageView imageView;
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        if (this.mContext != null && (imageView = (ImageView) holder.itemView.findViewById(R.id.new_image)) != null) {
            Glide.with(this.mContext).clear(imageView);
        }
        super.onViewRecycled((NewPostImageItemAdapter) holder);
    }
}
