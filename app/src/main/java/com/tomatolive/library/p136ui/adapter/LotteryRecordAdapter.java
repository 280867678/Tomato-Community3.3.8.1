package com.tomatolive.library.p136ui.adapter;

import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LotteryRecordEntity;
import com.tomatolive.library.utils.DateUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LotteryRecordAdapter */
/* loaded from: classes3.dex */
public class LotteryRecordAdapter extends BaseQuickAdapter<LotteryRecordEntity, BaseViewHolder> {
    public LotteryRecordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LotteryRecordEntity lotteryRecordEntity) {
        if (lotteryRecordEntity == null) {
            return;
        }
        baseViewHolder.setText(R$id.tv_lottery_tips, getTitleTips(lotteryRecordEntity)).setText(R$id.tv_lottery_date, DateUtils.getTimeStrFromLongSecond(lotteryRecordEntity.createTime, DateUtils.C_TIME_PATTON_DEFAULT)).setVisible(R$id.recycle_prize, lotteryRecordEntity.isLuckFlag()).setVisible(R$id.tv_not_won, !lotteryRecordEntity.isLuckFlag());
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R$id.recycle_prize);
        LotteryRankPrizeAdapter lotteryRankPrizeAdapter = new LotteryRankPrizeAdapter(false, R$layout.fq_layout_lottery_prize_mark_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        recyclerView.setAdapter(lotteryRankPrizeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        lotteryRankPrizeAdapter.bindToRecyclerView(recyclerView);
        lotteryRankPrizeAdapter.setNewData(lotteryRecordEntity.getPropList());
    }

    private String getTitleTips(LotteryRecordEntity lotteryRecordEntity) {
        if (lotteryRecordEntity.isLuckFlag()) {
            return lotteryRecordEntity.isGeneralRoulette() ? this.mContext.getString(R$string.fq_lottery_general_roulette_tips, lotteryRecordEntity.drawTimes) : this.mContext.getString(R$string.fq_lottery_luxury_roulette_tips, lotteryRecordEntity.drawTimes);
        }
        return this.mContext.getString(lotteryRecordEntity.isGeneralRoulette() ? R$string.fq_lottery_general_roulette : R$string.fq_lottery_luxury_roulette);
    }
}
