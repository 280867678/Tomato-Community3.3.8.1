package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.GiftWallEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.GiftWallAnchorRankingAdapter */
/* loaded from: classes3.dex */
public class GiftWallAnchorRankingAdapter extends BaseQuickAdapter<GiftWallEntity, BaseViewHolder> {
    private boolean isShowBrand(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3 || i == 4;
    }

    public GiftWallAnchorRankingAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, GiftWallEntity giftWallEntity) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 1);
        baseViewHolder.setText(R$id.tv_anchor_name, AppUtils.formatUserNickName(giftWallEntity.name)).setText(R$id.tv_light_count, this.mContext.getString(R$string.fq_achieve_ranking_light_cout, giftWallEntity.num)).setText(R$id.tv_num, sb.toString()).setImageResource(R$id.iv_brand, getBrandResId(adapterPosition)).setVisible(R$id.iv_brand, isShowBrand(adapterPosition)).setVisible(R$id.tv_num, !isShowBrand(adapterPosition));
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), giftWallEntity.avatar);
    }

    @DrawableRes
    private int getBrandResId(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_ic_achieve_rank_num_2;
            }
            if (i == 2) {
                return R$drawable.fq_ic_achieve_rank_num_3;
            }
            if (i == 3) {
                return R$drawable.fq_ic_achieve_rank_num_4;
            }
            if (i == 4) {
                return R$drawable.fq_ic_achieve_rank_num_5;
            }
            return R$drawable.fq_ic_achieve_rank_num_5;
        }
        return R$drawable.fq_ic_achieve_rank_num_1;
    }
}
