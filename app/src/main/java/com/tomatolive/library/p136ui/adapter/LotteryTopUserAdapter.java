package com.tomatolive.library.p136ui.adapter;

import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.LotteryUserRankEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LotteryTopUserAdapter */
/* loaded from: classes3.dex */
public class LotteryTopUserAdapter extends BaseQuickAdapter<LotteryUserRankEntity, BaseViewHolder> {
    public LotteryTopUserAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LotteryUserRankEntity lotteryUserRankEntity) {
        if (lotteryUserRankEntity == null) {
            return;
        }
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 1);
        String sb2 = sb.toString();
        baseViewHolder.setText(R$id.tv_anchor_name, AppUtils.formatUserNickName(lotteryUserRankEntity.nickname)).addOnClickListener(R$id.iv_avatar).addOnClickListener(R$id.tv_anchor_name);
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), lotteryUserRankEntity.avatar);
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_top_number);
        AppUtils.formatTvNumTypeface(this.mContext, textView, sb2);
        textView.setTextColor(ContextCompat.getColor(this.mContext, getNumColorRes(adapterPosition)));
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R$id.recycle_prize);
        LotteryRankPrizeAdapter lotteryRankPrizeAdapter = new LotteryRankPrizeAdapter(true, R$layout.fq_layout_lottery_prize_mark_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        recyclerView.setAdapter(lotteryRankPrizeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        lotteryRankPrizeAdapter.bindToRecyclerView(recyclerView);
        lotteryRankPrizeAdapter.setNewData(lotteryUserRankEntity.getPropList());
    }

    @ColorRes
    private int getNumColorRes(int i) {
        if (i != 0) {
            if (i == 1) {
                return R$color.fq_lottery_top_2;
            }
            if (i == 2) {
                return R$color.fq_lottery_top_3;
            }
            return R$color.fq_lottery_top_normal;
        }
        return R$color.fq_lottery_top_1;
    }
}
