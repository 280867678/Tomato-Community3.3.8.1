package com.one.tomato.mvp.p080ui.papa.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotDetailListAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.adapter.PaPaHotDetailListAdapter */
/* loaded from: classes3.dex */
public final class PaPaHotDetailListAdapter extends BaseRecyclerViewAdapter<PostList> {
    private int pageNumber = 1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaPaHotDetailListAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.papa_hot_list_item, recyclerView);
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
    }

    public final void setPageNumber(int i) {
        this.pageNumber = i;
    }

    public final int getPageNumber() {
        return this.pageNumber;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostList postList) {
        String str;
        String str2;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        String str3 = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.hot_image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.hot_text) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_sort) : null;
        TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_sort) : null;
        if (textView4 != null) {
            textView4.setVisibility(8);
        }
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        ImageLoaderUtil.loadRecyclerThumbSamllImage(this.mContext, roundedImageView, new ImageBean(postList != null ? postList.getSecVideoCover() : null));
        if (textView2 != null) {
            if (!TextUtils.isEmpty(postList != null ? postList.getTitle() : null)) {
                if (postList != null) {
                    str2 = postList.getTitle();
                    textView2.setText(str2);
                }
                str2 = null;
                textView2.setText(str2);
            } else {
                if (!TextUtils.isEmpty(postList != null ? postList.getDescription() : null)) {
                    if (postList != null) {
                        str2 = postList.getDescription();
                    }
                    str2 = null;
                } else {
                    str2 = "";
                }
                textView2.setText(str2);
            }
        }
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(AppUtil.getString(R.string.post_most_video_hot));
            sb.append(' ');
            if (postList != null) {
                postList.getScore();
                str3 = FormatUtil.formatTwo(Double.valueOf(postList.getScore()));
            }
            sb.append(str3);
            textView.setText(sb.toString());
        }
        if (textView3 != null) {
            if (postList == null || (str = postList.getName()) == null) {
                str = "";
            }
            textView3.setText(str);
        }
        if (getData().indexOf(postList) == 0) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView == null) {
                return;
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_one));
        } else if (getData().indexOf(postList) == 1) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView == null) {
                return;
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_two));
        } else if (getData().indexOf(postList) == 2) {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (imageView == null) {
                return;
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.papa_hot_list_item_three));
        } else {
            if (textView4 != null) {
                textView4.setVisibility(0);
            }
            if (textView4 == null) {
                return;
            }
            textView4.setText(String.valueOf(getData().indexOf(postList) + 1));
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
        companion.startAct(mContext, arrayList, null, null, 0, getPageNumber(), i, true);
    }
}
