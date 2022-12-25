package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ApplyConditionBean;
import com.one.tomato.utils.AppUtil;

/* compiled from: ApplyUpConditionAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.ApplyUpConditionAdapter */
/* loaded from: classes3.dex */
public final class ApplyUpConditionAdapter extends BaseQuickAdapter<ApplyConditionBean, BaseViewHolder> {
    public ApplyUpConditionAdapter() {
        super((int) R.layout.item_up_apply_conditions);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ApplyConditionBean applyConditionBean) {
        Integer num = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_red) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_post_publish_num) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_post_all_num) : null;
        if (applyConditionBean != null) {
            num = Integer.valueOf(applyConditionBean.getType());
        }
        if (num != null && num.intValue() == 1) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.up_image_01);
            }
            String string = AppUtil.getString(R.string.up_apply_post_publish_all_num);
            if (textView != null) {
                textView.setText(string);
            }
            String string2 = AppUtil.getString(R.string.up_apply_post_publish_curr_num);
            if (textView2 == null) {
                return;
            }
            textView2.setText(string2);
        } else if (num != null && num.intValue() == 2) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.up_image_02);
            }
            String string3 = AppUtil.getString(R.string.up_apply_post_browse_all_num);
            if (textView != null) {
                textView.setText(string3);
            }
            String string4 = AppUtil.getString(R.string.up_apply_post_browse_curr_num);
            if (textView2 == null) {
                return;
            }
            textView2.setText(string4);
        } else if (num != null && num.intValue() == 3) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.up_image_03);
            }
            String string5 = AppUtil.getString(R.string.up_apply_post_tumb_all_num);
            if (textView != null) {
                textView.setText(string5);
            }
            String string6 = AppUtil.getString(R.string.up_apply_post_tumb_curr_num);
            if (textView2 == null) {
                return;
            }
            textView2.setText(string6);
        } else if (num == null || num.intValue() != 4) {
        } else {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.up_image_04);
            }
            String string7 = AppUtil.getString(R.string.up_apply_fans_all_num);
            if (textView != null) {
                textView.setText(string7);
            }
            String string8 = AppUtil.getString(R.string.up_apply_fans_curr_num);
            if (textView2 == null) {
                return;
            }
            textView2.setText(string8);
        }
    }
}
