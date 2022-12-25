package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.UpSubscribePayBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;

/* compiled from: UpSubscribePayChooseAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.UpSubscribePayChooseAdapter */
/* loaded from: classes3.dex */
public final class UpSubscribePayChooseAdapter extends BaseRecyclerViewAdapter<UpSubscribePayBean> {
    public UpSubscribePayChooseAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.up_subscribe_pay_choose_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UpSubscribePayBean upSubscribePayBean) {
        super.convert(baseViewHolder, (BaseViewHolder) upSubscribePayBean);
        Integer num = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_pay) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_price) : null;
        TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_give) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_bg) : null;
        if (textView != null) {
            textView.setText(upSubscribePayBean != null ? upSubscribePayBean.getTitle() : null);
        }
        if (textView3 != null) {
            textView3.setText(upSubscribePayBean != null ? upSubscribePayBean.getGift() : null);
        }
        if (textView2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append((char) 165);
            if (upSubscribePayBean != null) {
                num = Integer.valueOf(upSubscribePayBean.getPrice());
            }
            sb.append(num);
            textView2.setText(sb.toString());
        }
        if (upSubscribePayBean == null || !upSubscribePayBean.isSelect()) {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.up_subscribe_pay_choose_normal));
            }
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            if (textView2 != null) {
                textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            }
            if (textView3 != null) {
                textView3.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner4_f5f5f7));
            }
            if (textView3 == null) {
                return;
            }
            textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.text_dark));
            return;
        }
        if (relativeLayout != null) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.up_subscribe_pay_choose_select));
        }
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        if (textView2 != null) {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_FC4C7B));
        }
        if (textView3 != null) {
            textView3.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner4_ffe0e0));
        }
        if (textView3 == null) {
            return;
        }
        textView3.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_FC4C7B));
    }
}
