package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.LotteryPrizeEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LotteryPrizeAdapter */
/* loaded from: classes3.dex */
public class LotteryPrizeAdapter extends BaseQuickAdapter<LotteryPrizeEntity, BaseViewHolder> {
    public LotteryPrizeAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LotteryPrizeEntity lotteryPrizeEntity) {
        baseViewHolder.setText(R$id.tv_num, lotteryPrizeEntity.getPropNumStr()).setText(R$id.tv_item_name, StringUtils.formatStrLen(lotteryPrizeEntity.propName, 4)).setBackgroundRes(R$id.rl_item_layout, getBackgroundRes(baseViewHolder.getLayoutPosition()));
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_item_logo), lotteryPrizeEntity.propUrl, R$drawable.fq_ic_lottery_microphone);
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_gold);
        textView.setText(lotteryPrizeEntity.getPropGoldStr());
        AppUtils.setTextViewLeftDrawable(this.mContext, textView, R$drawable.fq_ic_placeholder_gold, 13, 13);
    }

    @DrawableRes
    private int getBackgroundRes(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$drawable.fq_shape_stroke_wining_two_bg;
            }
            if (i == 2) {
                return R$drawable.fq_shape_stroke_wining_three_bg;
            }
            return R$drawable.fq_shape_stroke_wining_other_bg;
        }
        return R$drawable.fq_shape_stroke_wining_one_bg;
    }
}
