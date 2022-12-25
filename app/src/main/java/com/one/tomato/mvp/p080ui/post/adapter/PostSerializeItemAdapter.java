package com.one.tomato.mvp.p080ui.post.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PostSerializeItemAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.PostSerializeItemAdapter */
/* loaded from: classes3.dex */
public final class PostSerializeItemAdapter extends BaseRecyclerViewAdapter<PostList> {
    public PostSerializeItemAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.layout_item_post_serialize_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PostList postList) {
        int indexOf$default;
        int indexOf$default2;
        super.convert(baseViewHolder, (BaseViewHolder) postList);
        String str = null;
        Integer num = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.round_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_num_time) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_read) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        if (textView != null) {
            textView.setVisibility(0);
        }
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        if (roundedImageView != null) {
            roundedImageView.setImageBitmap(null);
        }
        Integer valueOf = postList != null ? Integer.valueOf(postList.getPostType()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            if (textView != null) {
                StringBuilder sb = new StringBuilder();
                if (postList != null) {
                    num = Integer.valueOf(postList.getPicNum());
                }
                sb.append(num.intValue());
                sb.append(AppUtil.getString(R.string.circle_post_img_num));
                textView.setText(sb.toString());
            }
            ArrayList<ImageBean> createImageBeanList = PostUtils.INSTANCE.createImageBeanList(postList);
            if (!(createImageBeanList == null || createImageBeanList.isEmpty()) && createImageBeanList.size() > 0) {
                ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, createImageBeanList.get(0));
            }
        } else if (valueOf != null && valueOf.intValue() == 2) {
            if (textView != null) {
                if (postList != null) {
                    str = postList.getVideoTime();
                }
                textView.setText(String.valueOf(str));
            }
            ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(postList.getSecVideoCover()));
        } else if (valueOf != null && valueOf.intValue() == 3) {
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            if (textView != null) {
                textView.setVisibility(8);
            }
            String contentText = !TextUtils.isEmpty(postList.getDescription()) ? postList.getDescription() : postList.getTitle();
            Intrinsics.checkExpressionValueIsNotNull(contentText, "contentText");
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) contentText, "</", 0, false, 6, (Object) null);
            if (indexOf$default != -1) {
                RichText.fromHtml(contentText).into(textView2);
            } else if (textView2 != null) {
                textView2.setText(contentText);
            }
            if (roundedImageView != null) {
                roundedImageView.setImageResource(R.drawable.mine_post_publish_read);
            }
        }
        if (postList != null) {
            String title = !TextUtils.isEmpty(postList.getTitle()) ? postList.getTitle() : postList.getDescription();
            Intrinsics.checkExpressionValueIsNotNull(title, "title");
            indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) title, "</", 0, false, 6, (Object) null);
            if (indexOf$default2 != -1) {
                RichTextConfig.RichTextConfigBuild fromHtml = RichText.fromHtml(title);
                fromHtml.clickable(true);
                fromHtml.into(textView3);
            } else if (textView3 != null) {
                textView3.setText(title);
            }
            if (postList.isSelectMinePostDelete()) {
                if (textView3 == null) {
                    return;
                }
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorAccent));
            } else if (textView3 == null) {
            } else {
                textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
        }
    }
}
