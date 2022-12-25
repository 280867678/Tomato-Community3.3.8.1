package com.tomatolive.library.p136ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.MenuEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.ActionSheetDialogAdapter */
/* loaded from: classes3.dex */
public class ActionSheetDialogAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
    public ActionSheetDialogAdapter(@LayoutRes int i, @Nullable List<MenuEntity> list) {
        super(i, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_item_title);
        textView.setText(menuEntity.getMenuTitle());
        textView.setSelected(menuEntity.isSelected);
        if (menuEntity.getMenuType() == 5) {
            textView.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_private_msg_wave_bg));
        } else {
            textView.setBackground(new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_color_transparent)));
        }
    }
}
