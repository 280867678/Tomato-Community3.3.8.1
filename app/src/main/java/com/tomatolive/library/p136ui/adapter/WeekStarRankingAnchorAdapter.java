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
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.UserInfoManager;

/* renamed from: com.tomatolive.library.ui.adapter.WeekStarRankingAnchorAdapter */
/* loaded from: classes3.dex */
public class WeekStarRankingAnchorAdapter extends BaseQuickAdapter<WeekStarAnchorEntity, BaseViewHolder> {
    private boolean isUserRanking;

    private boolean isShowBrand(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    public WeekStarRankingAnchorAdapter(int i) {
        super(i);
        this.isUserRanking = false;
    }

    public WeekStarRankingAnchorAdapter(int i, boolean z) {
        super(i);
        this.isUserRanking = false;
        this.isUserRanking = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, WeekStarAnchorEntity weekStarAnchorEntity) {
        Drawable colorDrawable;
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 1);
        String sb2 = sb.toString();
        boolean z = this.isUserRanking && weekStarAnchorEntity.isRankHideBoolean();
        baseViewHolder.setText(R$id.tv_index, sb2).setText(R$id.tv_ranking_anchor, AppUtils.formatUserNickName(this.isUserRanking ? weekStarAnchorEntity.name : weekStarAnchorEntity.anchorName)).setText(R$id.tv_receive_count, getReceiveStr(weekStarAnchorEntity)).setText(R$id.tv_contribution_count, this.mContext.getString(R$string.fq_week_star_assists, weekStarAnchorEntity.userStarGiftNum)).setImageResource(R$id.iv_brand, getBrandResId(adapterPosition)).setVisible(R$id.tv_ranking_anchor, !z).setVisible(R$id.iv_brand, isShowBrand(adapterPosition)).setVisible(R$id.tv_index, !isShowBrand(adapterPosition)).setVisible(R$id.ll_mystery_bg, z).setVisible(R$id.tv_me, z && TextUtils.equals(weekStarAnchorEntity.userId, UserInfoManager.getInstance().getUserId())).setGone(R$id.tv_contribution_count, true ^ this.isUserRanking).setVisible(R$id.iv_ranking_live, AppUtils.isLiving(weekStarAnchorEntity.liveStatus));
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_ranking_user);
        textView.setText(AppUtils.formatUserNickName(weekStarAnchorEntity.name));
        if (!this.isUserRanking) {
            textView.setVisibility(0);
        } else if (z) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(8);
        }
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R$id.ll_content_bg);
        if (z) {
            colorDrawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_nobility_stealth_top_bg);
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mContext, R$color.fq_color_transparent));
        }
        linearLayout.setBackground(colorDrawable);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_ranking_avatar);
        if (z) {
            imageView.setImageResource(R$drawable.fq_ic_nobility_top_stealth);
        } else {
            GlideUtils.loadAvatar(this.mContext, imageView, weekStarAnchorEntity.avatar);
        }
        GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_ranking_live));
    }

    private String getReceiveStr(WeekStarAnchorEntity weekStarAnchorEntity) {
        return this.isUserRanking ? this.mContext.getString(R$string.fq_week_star_send, weekStarAnchorEntity.giftNum) : this.mContext.getString(R$string.fq_week_star_receive, weekStarAnchorEntity.anchorStarGiftNum);
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
