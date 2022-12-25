package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpRanlImageAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.UpRanlImageAdapter */
/* loaded from: classes3.dex */
public final class UpRanlImageAdapter extends BaseRecyclerViewAdapter<UpRankListBean> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpRanlImageAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.up_rank_image_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UpRankListBean upRankListBean) {
        super.convert(baseViewHolder, (BaseViewHolder) upRankListBean);
        String str = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image) : null;
        Context context = this.mContext;
        if (upRankListBean != null) {
            str = upRankListBean.getAvatar();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, roundedImageView, new ImageBean(str));
    }
}
