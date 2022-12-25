package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.LayoutRes;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.ReportTypeEntity;

/* renamed from: com.tomatolive.library.ui.adapter.ReportTypeAdapter */
/* loaded from: classes3.dex */
public class ReportTypeAdapter extends BaseQuickAdapter<ReportTypeEntity, BaseViewHolder> {
    private int selectedPosition = -1;

    public ReportTypeAdapter(@LayoutRes int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ReportTypeEntity reportTypeEntity) {
        baseViewHolder.setText(R$id.tv_type, reportTypeEntity.desc).getView(R$id.tv_type).setSelected(this.selectedPosition == baseViewHolder.getAdapterPosition());
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }
}
