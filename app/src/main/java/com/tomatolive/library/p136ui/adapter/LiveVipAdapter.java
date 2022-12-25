package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LiveVipAdapter */
/* loaded from: classes3.dex */
public class LiveVipAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    public LiveVipAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UserEntity userEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, userEntity.getName()).setImageResource(R$id.iv_badge, AppUtils.getNobilityBadgeDrawableRes(userEntity.getNobilityType())).setImageResource(R$id.iv_guard, AppUtils.isYearGuard(userEntity.getGuardType()) ? R$drawable.fq_ic_live_msg_year_guard_big : R$drawable.fq_ic_live_msg_mouth_guard_big);
        int i = 0;
        ((ImageView) baseViewHolder.getView(R$id.iv_badge)).setVisibility(AppUtils.isNobilityUser(userEntity.getNobilityType()) ? 0 : 8);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_guard);
        if (!AppUtils.isGuardUser(userEntity.getGuardType())) {
            i = 8;
        }
        imageView.setVisibility(i);
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), userEntity.getAvatar(), 3, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        ((UserGradeView) baseViewHolder.getView(R$id.grade_view)).initUserGradeMsg(userEntity.getExpGrade(), true);
    }
}
