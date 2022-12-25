package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.JAVDBBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: JavDBAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.JavDBAdapter */
/* loaded from: classes3.dex */
public final class JavDBAdapter extends BaseRecyclerViewAdapter<JAVDBBean.ActorsBean> {
    public JavDBAdapter(RecyclerView recyclerView, Context context) {
        super(context, R.layout.javdb_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, JAVDBBean.ActorsBean actorsBean) {
        String str;
        String avatar_url;
        super.convert(baseViewHolder, (BaseViewHolder) actorsBean);
        TextView textView = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        if (baseViewHolder != null) {
            textView = (TextView) baseViewHolder.getView(R.id.text_name);
        }
        if (actorsBean != null && (avatar_url = actorsBean.getAvatar_url()) != null) {
            ImageLoaderUtil.loadSecAbsoluteImage(this.mContext, roundedImageView, avatar_url, ImageLoaderUtil.getCenterInsideImageOption(roundedImageView));
        }
        if (textView != null) {
            if (actorsBean == null || (str = actorsBean.getName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
    }
}
