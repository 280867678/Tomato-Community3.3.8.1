package com.one.tomato.mvp.p080ui.post.adapter;

import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.HotMessageBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PreferencesUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotHeadAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.PostHotHeadAdapter */
/* loaded from: classes3.dex */
public final class PostHotHeadAdapter extends BaseQuickAdapter<HotMessageBean, BaseViewHolder> {
    public PostHotHeadAdapter() {
        super((int) R.layout.post_hot_head_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, HotMessageBean hotMessageBean) {
        String str;
        Drawable drawable = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.background_view) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_tag) : null;
        if (hotMessageBean != null) {
            int imageId = hotMessageBean.getImageId();
            if (imageView != null) {
                imageView.setImageResource(imageId);
            }
        }
        if (textView != null) {
            if (hotMessageBean == null || (str = hotMessageBean.getTextTitle()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (relativeLayout != null) {
            if (hotMessageBean != null) {
                drawable = ContextCompat.getDrawable(this.mContext, hotMessageBean.getBackgroundId());
            }
            relativeLayout.setBackground(drawable);
        }
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        String string = PreferencesUtil.getInstance().getString("review_new", "0");
        if (getData().indexOf(hotMessageBean) == 0 && Intrinsics.areEqual(string, "0")) {
            if (textView2 != null) {
                textView2.setText("NEW");
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
        }
        if (getData().indexOf(hotMessageBean) == 3) {
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.post_home_give));
            }
            if (textView2 == null) {
                return;
            }
            textView2.setVisibility(0);
        }
    }
}
