package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.LotteryPrizeEntity;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LotteryRankPrizeAdapter */
/* loaded from: classes3.dex */
public class LotteryRankPrizeAdapter extends BaseQuickAdapter<LotteryPrizeEntity, BaseViewHolder> {
    private boolean isShowTop;

    public LotteryRankPrizeAdapter(boolean z, int i) {
        super(i);
        this.isShowTop = true;
        this.isShowTop = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LotteryPrizeEntity lotteryPrizeEntity) {
        if (lotteryPrizeEntity == null) {
            return;
        }
        String str = lotteryPrizeEntity.propNum;
        boolean z = true;
        BaseViewHolder visible = baseViewHolder.setText(R$id.tv_top_num, str).setText(R$id.tv_bottom_num, str).setBackgroundRes(R$id.tv_top_num, str.length() > 2 ? R$drawable.fq_shape_lottery_red_dot : R$drawable.fq_shape_lottery_red_dot_oval).setBackgroundRes(R$id.tv_bottom_num, str.length() > 2 ? R$drawable.fq_shape_lottery_red_dot : R$drawable.fq_shape_lottery_red_dot_oval).setVisible(R$id.tv_top_num, this.isShowTop && NumberUtils.string2int(str, 0) > 0);
        int i = R$id.tv_bottom_num;
        if (this.isShowTop || NumberUtils.string2int(str, 0) <= 0) {
            z = false;
        }
        visible.setVisible(i, z);
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_prize), lotteryPrizeEntity.propUrl, R$drawable.fq_ic_gift_default);
    }
}
