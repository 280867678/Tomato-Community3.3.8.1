package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.MenuEntity;

/* renamed from: com.tomatolive.library.ui.adapter.NobilityOpenAdapter */
/* loaded from: classes3.dex */
public class NobilityOpenAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
    public NobilityOpenAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_logo);
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_title);
        TextView textView2 = (TextView) baseViewHolder.getView(R$id.tv_desc);
        textView.setText(menuEntity.menuTitle);
        textView2.setText(menuEntity.menuDesc);
        imageView.setImageResource(menuEntity.menuIcon);
        float f = 0.4f;
        textView.setAlpha(menuEntity.isSelected ? 0.4f : 1.0f);
        textView2.setAlpha(menuEntity.isSelected ? 0.4f : 1.0f);
        if (!menuEntity.isSelected) {
            f = 1.0f;
        }
        imageView.setAlpha(f);
    }
}
