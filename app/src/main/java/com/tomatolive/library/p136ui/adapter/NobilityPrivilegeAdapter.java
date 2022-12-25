package com.tomatolive.library.p136ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.MenuEntity;

/* renamed from: com.tomatolive.library.ui.adapter.NobilityPrivilegeAdapter */
/* loaded from: classes3.dex */
public class NobilityPrivilegeAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
    public NobilityPrivilegeAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R$id.rl_item_bg);
        if (adapterPosition == getData().size()) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_bottom_corners_white_bg_2));
        } else {
            relativeLayout.setBackground(new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite)));
        }
        ((TextView) baseViewHolder.getView(R$id.tv_tips)).setText(menuEntity.menuTitle);
        ((ImageView) baseViewHolder.getView(R$id.iv_logo)).setImageResource(menuEntity.menuIcon);
    }
}
