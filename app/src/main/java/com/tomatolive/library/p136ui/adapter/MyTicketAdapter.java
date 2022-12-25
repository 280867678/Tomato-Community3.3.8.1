package com.tomatolive.library.p136ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MyTicketEntity;
import com.tomatolive.library.utils.DateUtils;

/* renamed from: com.tomatolive.library.ui.adapter.MyTicketAdapter */
/* loaded from: classes3.dex */
public class MyTicketAdapter extends BaseQuickAdapter<MyTicketEntity, BaseViewHolder> {
    private boolean isGetRecord;

    public MyTicketAdapter(int i, boolean z) {
        super(i);
        this.isGetRecord = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MyTicketEntity myTicketEntity) {
        baseViewHolder.setText(R$id.tv_title, myTicketEntity.content).setText(R$id.tv_date, DateUtils.getTimeStrFromLongSecond(myTicketEntity.createTime, DateUtils.C_TIME_PATTON_DEFAULT)).setText(R$id.tv_count, this.isGetRecord ? this.mContext.getString(R$string.fq_ticket_add_tips, String.valueOf(myTicketEntity.lotteryTicketNum)) : this.mContext.getString(R$string.fq_ticket_sub_tips, String.valueOf(myTicketEntity.lotteryTicketNum)));
    }

    public boolean isGetRecord() {
        return this.isGetRecord;
    }

    public void setGetRecord(boolean z) {
        this.isGetRecord = z;
    }
}
