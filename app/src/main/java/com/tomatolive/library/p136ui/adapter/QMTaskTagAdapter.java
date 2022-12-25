package com.tomatolive.library.p136ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.QMInteractTaskEntity;

/* renamed from: com.tomatolive.library.ui.adapter.QMTaskTagAdapter */
/* loaded from: classes3.dex */
public class QMTaskTagAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    private boolean isEdit = false;

    public QMTaskTagAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        baseViewHolder.setText(R$id.tv_task_name, qMInteractTaskEntity.taskName).setGone(R$id.iv_task_delete, this.isEdit).addOnClickListener(R$id.iv_task_delete);
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
        notifyDataSetChanged();
    }
}
