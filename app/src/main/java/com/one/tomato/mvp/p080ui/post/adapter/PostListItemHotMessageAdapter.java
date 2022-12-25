package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: PostListItemHotMessageAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.PostListItemHotMessageAdapter */
/* loaded from: classes3.dex */
public final class PostListItemHotMessageAdapter extends BaseQuickAdapter<PostHotMessageBean, BaseViewHolder> {
    public PostListItemHotMessageAdapter() {
        super((int) R.layout.item_post_list_hot_message_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostHotMessageBean postHotMessageBean) {
        String str = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_time) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_hot) : null;
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append('#');
            sb.append(postHotMessageBean != null ? postHotMessageBean.getEventName() : null);
            textView.setText(sb.toString());
        }
        String eventStartDate = postHotMessageBean != null ? postHotMessageBean.getEventStartDate() : null;
        if (textView2 != null) {
            if (eventStartDate == null) {
                eventStartDate = "";
            }
            textView2.setText(eventStartDate);
        }
        if (textView3 != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AppUtil.getString(R.string.serach_des_hot));
            sb2.append(' ');
            sb2.append(FormatUtil.formatNumOverTenThousand(String.valueOf(postHotMessageBean != null ? Integer.valueOf(postHotMessageBean.getHotViewNum()) : null)));
            textView3.setText(sb2.toString());
        }
        Context context = this.mContext;
        if (postHotMessageBean != null) {
            str = postHotMessageBean.getEventCoverUrl();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, imageView, new ImageBean(str));
    }
}
