package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessageAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.PostHotMessageAdapter */
/* loaded from: classes3.dex */
public final class PostHotMessageAdapter extends BaseRecyclerViewAdapter<PostHotMessageBean> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostHotMessageAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.post_hot_message_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostHotMessageBean postHotMessageBean) {
        String eventDesc;
        String str;
        super.convert(baseViewHolder, (BaseViewHolder) postHotMessageBean);
        String str2 = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_time) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_description) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_hot) : null;
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append('#');
            sb.append(postHotMessageBean != null ? postHotMessageBean.getEventName() : null);
            textView.setText(sb.toString());
        }
        String str3 = "";
        if (textView2 != null) {
            if (postHotMessageBean == null || (str = postHotMessageBean.getEventStartDate()) == null) {
                str = str3;
            }
            textView2.setText(str);
        }
        if (textView3 != null) {
            if (postHotMessageBean != null && (eventDesc = postHotMessageBean.getEventDesc()) != null) {
                str3 = eventDesc;
            }
            textView3.setText(str3);
        }
        if (textView4 != null) {
            textView4.setText(FormatUtil.formatNumOverTenThousand(String.valueOf(postHotMessageBean != null ? Integer.valueOf(postHotMessageBean.getHotViewNum()) : null)));
        }
        Context context = this.mContext;
        if (postHotMessageBean != null) {
            str2 = postHotMessageBean.getEventCoverUrl();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, imageView, new ImageBean(str2));
    }
}
