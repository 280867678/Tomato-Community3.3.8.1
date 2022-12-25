package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.QMInteractTaskAdapter */
/* loaded from: classes3.dex */
public class QMInteractTaskAdapter extends BaseQuickAdapter<QMInteractTaskEntity, BaseViewHolder> {
    public QMInteractTaskAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, QMInteractTaskEntity qMInteractTaskEntity) {
        int i = R$id.tv_count;
        baseViewHolder.setText(i, "x" + qMInteractTaskEntity.giftNum).setText(R$id.tv_task_name, qMInteractTaskEntity.taskName).setVisible(R$id.iv_edit, qMInteractTaskEntity.isEdit).setVisible(R$id.iv_delete, qMInteractTaskEntity.isEdit).setVisible(R$id.iv_more, !qMInteractTaskEntity.isEdit).setGone(R$id.iv_check, !qMInteractTaskEntity.isEdit).addOnClickListener(R$id.iv_check).addOnClickListener(R$id.iv_edit).addOnClickListener(R$id.iv_delete).addOnClickListener(R$id.iv_more).getView(R$id.iv_check).setSelected(qMInteractTaskEntity.isSelected);
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift), qMInteractTaskEntity.giftUrl, R$drawable.fq_ic_gift_default);
    }
}
