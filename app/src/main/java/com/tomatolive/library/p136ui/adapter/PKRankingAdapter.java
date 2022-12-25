package com.tomatolive.library.p136ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.UserNickNameGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.adapter.PKRankingAdapter */
/* loaded from: classes3.dex */
public class PKRankingAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    public PKRankingAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        Drawable colorDrawable;
        if (anchorEntity == null) {
            baseViewHolder.itemView.setVisibility(4);
            return;
        }
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 3);
        String sb2 = sb.toString();
        boolean isRankHideBoolean = anchorEntity.isRankHideBoolean();
        int i = 0;
        boolean z = isRankHideBoolean && TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId());
        baseViewHolder.itemView.setVisibility(0);
        baseViewHolder.setText(R$id.tv_diamond, getDiamondStr(anchorEntity)).setText(R$id.tv_num, sb2).setVisible(R$id.ll_mystery_bg, isRankHideBoolean).setVisible(R$id.tv_me, z);
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R$id.ll_content_bg);
        if (isRankHideBoolean) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_nobility_stealth_top_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_color_transparent));
        }
        linearLayout.setBackground(colorDrawable);
        LinearLayout linearLayout2 = (LinearLayout) baseViewHolder.getView(R$id.ll_user_nickname_bg);
        UserNickNameGradeView userNickNameGradeView = (UserNickNameGradeView) baseViewHolder.getView(R$id.user_nickname);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_avatar);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R$id.iv_guard);
        if (isRankHideBoolean) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, anchorEntity.avatar);
        }
        linearLayout2.setVisibility(!isRankHideBoolean ? 0 : 8);
        if (!AppUtils.isGuardUser(NumberUtils.string2int(anchorEntity.guardType))) {
            i = 8;
        }
        imageView2.setVisibility(i);
        imageView2.setImageResource(AppUtils.isYearGuard(anchorEntity.guardType) ? R$drawable.fq_ic_live_msg_year_guard_big : R$drawable.fq_ic_live_msg_mouth_guard_big);
        userNickNameGradeView.initData(anchorEntity.name, R$color.fq_colorBlack, anchorEntity.sex, anchorEntity.expGrade, anchorEntity.nobilityType);
    }

    private String getDiamondStr(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return "";
        }
        return this.mContext.getString(R$string.fq_tomato_money_reward, anchorEntity.f5827fp);
    }
}
