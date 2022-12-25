package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.BannedEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.BannedAdapter */
/* loaded from: classes3.dex */
public class BannedAdapter extends BaseQuickAdapter<BannedEntity, BaseViewHolder> {
    public BannedAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, BannedEntity bannedEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, bannedEntity.name).setText(R$id.tv_time, this.mContext.getString(R$string.fq_banned_time_to, DateUtils.getClearTime(bannedEntity.duration))).setText(R$id.tv_banned, this.mContext.getString(bannedEntity.isBanned() ? R$string.btn_cancel_banned : R$string.btn_banned)).setVisible(R$id.tv_time, bannedEntity.isBanned()).addOnClickListener(R$id.tv_banned).getView(R$id.tv_banned).setSelected(bannedEntity.isBanned());
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), bannedEntity.avatar);
        ((UserGradeView) baseViewHolder.getView(R$id.user_grade)).initUserGrade(bannedEntity.expGrade);
    }
}
