package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.QMInteractTaskEntity;

/* renamed from: com.tomatolive.library.ui.adapter.QMTagAdapter */
/* loaded from: classes3.dex */
public class QMTagAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    private int selectedPosition = -1;

    public QMTagAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        boolean z = this.selectedPosition == baseViewHolder.getAdapterPosition();
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_tag_name);
        textView.setText(qMInteractTaskEntity.taskName);
        textView.setTextColor(ContextCompat.getColor(this.mContext, z ? R$color.fq_qm_primary : R$color.fq_qm_gray));
        textView.setSelected(z);
    }

    public void setCheckItem(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }
}
