package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PrivilegeBean;

/* compiled from: PrivilegeAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.PrivilegeAdapter */
/* loaded from: classes3.dex */
public final class PrivilegeAdapter extends BaseQuickAdapter<PrivilegeBean, BaseViewHolder> {
    public PrivilegeAdapter() {
        super((int) R.layout.item_up_privilege);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PrivilegeBean privilegeBean) {
        String str = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.name) : null;
        if (privilegeBean != null) {
            int resId = privilegeBean.getResId();
            if (imageView != null) {
                imageView.setImageResource(resId);
            }
        }
        if (textView != null) {
            if (privilegeBean != null) {
                str = privilegeBean.getName();
            }
            textView.setText(str);
        }
    }
}
