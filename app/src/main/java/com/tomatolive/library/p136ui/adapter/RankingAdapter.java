package com.tomatolive.library.p136ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.adapter.RankingAdapter */
/* loaded from: classes3.dex */
public class RankingAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    private boolean isDialog;
    private int type;

    private boolean isShowBrand(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    public RankingAdapter(int i, int i2) {
        super(i);
        this.isDialog = false;
        this.type = i2;
    }

    public RankingAdapter(int i, int i2, boolean z) {
        super(i);
        this.isDialog = false;
        this.type = i2;
        this.isDialog = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        Drawable colorDrawable;
        if (anchorEntity == null || TextUtils.isEmpty(anchorEntity.nickname)) {
            baseViewHolder.itemView.setVisibility(4);
            return;
        }
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 1);
        String sb2 = sb.toString();
        int i = 0;
        boolean z = this.type == 5 && anchorEntity.isRankHideBoolean();
        boolean z2 = z && TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId());
        baseViewHolder.itemView.setVisibility(0);
        baseViewHolder.setText(R$id.tv_diamond, getDiamondStr(anchorEntity)).setVisible(R$id.tv_attention, this.type == 4).setVisible(R$id.iv_live, AppUtils.isLiving(anchorEntity.liveStatus)).setBackgroundRes(R$id.fl_avatar_bg, getAvatarBgResId(adapterPosition)).setImageResource(R$id.iv_brand, getBrandResId(adapterPosition)).setVisible(R$id.iv_brand, isShowBrand(adapterPosition)).setVisible(R$id.tv_num, !isShowBrand(adapterPosition)).setVisible(R$id.iv_live, AppUtils.isLiving(anchorEntity.liveStatus)).setVisible(R$id.ll_mystery_bg, z).setVisible(R$id.tv_me, z2).addOnClickListener(R$id.tv_attention).getView(R$id.tv_attention).setSelected(anchorEntity.isAttention());
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R$id.ll_content_bg);
        if (z) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_nobility_stealth_top_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_color_transparent));
        }
        linearLayout.setBackground(colorDrawable);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_avatar);
        if (z) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, anchorEntity.avatar);
        }
        GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_live));
        AppUtils.formatTvNumTypeface(this.mContext, (TextView) baseViewHolder.getView(R$id.tv_num), sb2);
        UserNickNameGradeView userNickNameGradeView = (UserNickNameGradeView) baseViewHolder.getView(R$id.user_nickname);
        if (z) {
            i = 8;
        }
        userNickNameGradeView.setVisibility(i);
        String str = anchorEntity.nickname;
        if (this.type == 4) {
            userNickNameGradeView.initAnchorData(str, this.isDialog ? R$color.fq_colorWhite : R$color.fq_colorBlack, anchorEntity.sex, anchorEntity.expGrade, anchorEntity.nobilityType);
        } else {
            userNickNameGradeView.initData(str, this.isDialog ? R$color.fq_colorWhite : R$color.fq_colorBlack, anchorEntity.sex, anchorEntity.expGrade, anchorEntity.nobilityType);
        }
    }

    public void setType(int i) {
        this.type = i;
        notifyDataSetChanged();
    }

    private String getDiamondStr(AnchorEntity anchorEntity) {
        if (anchorEntity == null) {
            return "";
        }
        String str = this.type == 4 ? anchorEntity.income : anchorEntity.expend;
        return this.type == 4 ? this.mContext.getString(R$string.fq_tomato_money_gain, str) : this.mContext.getString(R$string.fq_tomato_money_reward, str);
    }

    private int getAvatarBgResId(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_shape_top_tag_silver_circle;
            }
            if (i == 2) {
                return R$drawable.fq_shape_top_tag_copper_circle;
            }
            return R$drawable.fq_shape_top_tag_gray_circle;
        }
        return R$drawable.fq_shape_top_tag_gold_circle;
    }

    private int getBrandResId(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_ic_top_brand_no_2;
            }
            if (i == 2) {
                return R$drawable.fq_ic_top_brand_no_3;
            }
            return R$drawable.fq_ic_top_brand_no_3;
        }
        return R$drawable.fq_ic_top_brand_no_1;
    }
}
