package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.LabelEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.LabelMenuAdapter */
/* loaded from: classes3.dex */
public class LabelMenuAdapter extends BaseQuickAdapter<LabelEntity, BaseViewHolder> {
    private int checkItemPosition = 0;

    public LabelMenuAdapter(int i) {
        super(i);
    }

    public LabelMenuAdapter(int i, @Nullable List<LabelEntity> list) {
        super(i, list);
    }

    public void setCheckItem(int i) {
        this.checkItemPosition = i;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LabelEntity labelEntity) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        baseViewHolder.setText(R$id.tv_menu_title, labelEntity.name);
        fillValue(adapterPosition, (TextView) baseViewHolder.getView(R$id.tv_menu_title));
    }

    private void fillValue(int i, TextView textView) {
        int i2 = this.checkItemPosition;
        if (i2 != -1) {
            textView.setSelected(i2 == i);
        }
    }
}
