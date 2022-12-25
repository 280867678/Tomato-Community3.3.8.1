package com.tomatolive.library.p136ui.adapter;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.BannedEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.HouseAdapter */
/* loaded from: classes3.dex */
public class HouseAdapter extends BaseQuickAdapter<BannedEntity, BaseViewHolder> {
    private boolean isSearch;

    public HouseAdapter(int i, boolean z) {
        super(i);
        this.isSearch = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, BannedEntity bannedEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, bannedEntity.name).setText(R$id.tv_banned, this.mContext.getString(bannedEntity.isHouseManager() ? R$string.fq_my_live_house_manager_cancel : R$string.fq_my_live_house_manager)).setText(R$id.tv_time, this.mContext.getString(R$string.fq_appointment_time, bannedEntity.createTime)).setText(R$id.tv_last_time, getLastTime(bannedEntity)).setVisible(R$id.tv_time, !this.isSearch).setVisible(R$id.tv_last_time, !this.isSearch).addOnClickListener(R$id.tv_banned).getView(R$id.tv_banned).setSelected(bannedEntity.isHouseManager());
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), bannedEntity.avatar);
        ((UserGradeView) baseViewHolder.getView(R$id.user_grade)).initUserGrade(bannedEntity.expGrade);
    }

    private Spanned getLastTime(BannedEntity bannedEntity) {
        return TextUtils.equals(bannedEntity.lastEnterTime, "不活跃") ? Html.fromHtml(this.mContext.getString(R$string.fq_last_join_live_time_2, bannedEntity.lastEnterTime)) : Html.fromHtml(this.mContext.getString(R$string.fq_last_join_live_time, bannedEntity.lastEnterTime));
    }
}
