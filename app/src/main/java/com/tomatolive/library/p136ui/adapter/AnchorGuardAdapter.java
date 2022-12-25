package com.tomatolive.library.p136ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.AnchorGuardAdapter */
/* loaded from: classes3.dex */
public class AnchorGuardAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    public AnchorGuardAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        baseViewHolder.setText(R$id.tv_name, anchorEntity.nickname).setText(R$id.tv_money, this.mContext.getString(R$string.fq_tomato_money_reward, anchorEntity.contribution)).setVisible(R$id.iv_guard_type, !TextUtils.equals(anchorEntity.guardType, "0")).setImageResource(R$id.iv_guard_type, TextUtils.equals(anchorEntity.guardType, "3") ? R$drawable.fq_ic_live_msg_year_guard : R$drawable.fq_ic_live_msg_mouth_guard).setImageResource(R$id.iv_gender, AppUtils.getGenderRes(anchorEntity.sex));
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), anchorEntity.avatar);
        ((UserGradeView) baseViewHolder.getView(R$id.user_grade)).initUserGrade(anchorEntity.expGrade);
    }
}
