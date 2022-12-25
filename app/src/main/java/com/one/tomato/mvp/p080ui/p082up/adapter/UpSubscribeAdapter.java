package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.SubscribeUpList;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: UpSubscribeAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.UpSubscribeAdapter */
/* loaded from: classes3.dex */
public final class UpSubscribeAdapter extends BaseRecyclerViewAdapter<SubscribeUpList> {
    public UpSubscribeAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.up_subscribe_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubscribeUpList subscribeUpList) {
        String str;
        super.convert(baseViewHolder, (BaseViewHolder) subscribeUpList);
        Integer num = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_sort) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_sort) : null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.iv_up_type) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_intimacy) : null;
        ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(subscribeUpList != null ? subscribeUpList.avatar : null));
        if (textView2 != null) {
            if (subscribeUpList == null || (str = subscribeUpList.name) == null) {
                str = "";
            }
            textView2.setText(str);
        }
        Integer valueOf = subscribeUpList != null ? Integer.valueOf(subscribeUpList.type) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.up_valid_month);
            }
        } else if (valueOf != null && valueOf.intValue() == 2) {
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.up_valid_season);
            }
        } else if (valueOf != null && valueOf.intValue() == 3 && imageView2 != null) {
            imageView2.setImageResource(R.drawable.up_valid_year);
        }
        if (textView != null) {
            textView.setVisibility(8);
        }
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (getData().indexOf(subscribeUpList) == 0) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_one));
            }
        } else if (getData().indexOf(subscribeUpList) == 1) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_two));
            }
        } else if (getData().indexOf(subscribeUpList) == 2) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView != null) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_three));
            }
        } else {
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView != null) {
                textView.setText(String.valueOf(getData().indexOf(subscribeUpList) + 1));
            }
        }
        if (textView3 != null) {
            StringBuilder sb = new StringBuilder();
            if (subscribeUpList != null) {
                num = Integer.valueOf(subscribeUpList.closePoint);
            }
            sb.append(num);
            sb.append(AppUtil.getString(R.string.up_subsceribe_close_point));
            textView3.setText(sb.toString());
        }
    }
}
