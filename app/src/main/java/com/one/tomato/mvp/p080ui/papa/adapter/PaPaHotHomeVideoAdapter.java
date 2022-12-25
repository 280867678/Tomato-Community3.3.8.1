package com.one.tomato.mvp.p080ui.papa.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotHomeVideoAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.adapter.PaPaHotHomeVideoAdapter */
/* loaded from: classes3.dex */
public final class PaPaHotHomeVideoAdapter extends BaseRecyclerViewAdapter<PostList> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaPaHotHomeVideoAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.papa_most_hot_video_home_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostList postList) {
        View view;
        View view2;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        String str = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.hot_image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.hot_text) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.hot_percent_text) : null;
        if (textView != null) {
            textView.setVisibility(8);
        }
        int indexOf = getData().indexOf(postList);
        if (indexOf >= 0 && 1 >= indexOf) {
            if (getData().indexOf(postList) == 0) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) ((baseViewHolder == null || (view2 = baseViewHolder.itemView) == null) ? null : view2.getLayoutParams());
                if (layoutParams != null) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = (int) DisplayMetricsUtils.dp2px(16.0f);
                }
                if (baseViewHolder != null && (view = baseViewHolder.itemView) != null) {
                    view.setLayoutParams(layoutParams);
                }
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_most_video_text_today_hot));
            }
        }
        int indexOf2 = getData().indexOf(postList);
        if (2 <= indexOf2 && 3 >= indexOf2) {
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_most_video_text_week_hot));
            }
        }
        int indexOf3 = getData().indexOf(postList);
        if (4 <= indexOf3 && 5 >= indexOf3) {
            if (textView != null) {
                textView.setVisibility(0);
            }
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_most_video_text_month_hot));
            }
        }
        ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView, new ImageBean(postList != null ? postList.getSecVideoCover() : null));
        String title = postList != null ? postList.getTitle() : null;
        String description = postList != null ? postList.getDescription() : null;
        if (textView2 != null) {
            if (TextUtils.isEmpty(title)) {
                title = !TextUtils.isEmpty(description) ? description : "";
            }
            textView2.setText(title);
        }
        if (textView3 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getString(R.string.post_most_video_hot));
            sb.append(' ');
            if (postList != null) {
                postList.getScore();
                str = FormatUtil.formatTwo(Double.valueOf(postList.getScore()));
            }
            sb.append(str);
            textView3.setText(sb.toString());
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        LogUtil.m3785e("yan", "点击跳转拍拍视频页面");
        List subList = this.mData.subList(i, getData().size());
        ArrayList<PostList> arrayList = new ArrayList<>();
        arrayList.addAll(subList);
        NewPaPaListVideoActivity.Companion companion = NewPaPaListVideoActivity.Companion;
        Context mContext = this.mContext;
        Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
        companion.startAct(mContext, arrayList, null, null, 0, 0, i, false);
    }
}
