package com.one.tomato.mvp.p080ui.post.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: PostSharePicAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.PostSharePicAdapter */
/* loaded from: classes3.dex */
public final class PostSharePicAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {
    public PostSharePicAdapter() {
        super((int) R.layout.post_share_image_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ImageBean imageBean) {
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        ViewGroup.LayoutParams layoutParams = imageView != null ? imageView.getLayoutParams() : null;
        if (getItemCount() >= 4) {
            if (layoutParams != null) {
                layoutParams.height = (int) DisplayMetricsUtils.dp2px(90.0f);
            }
        } else if (layoutParams != null) {
            layoutParams.height = (int) DisplayMetricsUtils.dp2px(180.0f);
        }
        if (imageView != null) {
            imageView.setLayoutParams(layoutParams);
        }
        ImageLoaderUtil.loadViewPagerOriginImage(this.mContext, imageView, null, imageBean, 0);
    }
}
