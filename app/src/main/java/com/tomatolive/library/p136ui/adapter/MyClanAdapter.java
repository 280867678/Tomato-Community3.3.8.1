package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MyClanEntity;

/* renamed from: com.tomatolive.library.ui.adapter.MyClanAdapter */
/* loaded from: classes3.dex */
public class MyClanAdapter extends BaseQuickAdapter<MyClanEntity, BaseViewHolder> {
    private int liveStatus;

    public MyClanAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MyClanEntity myClanEntity) {
        baseViewHolder.setText(R$id.tv_anchor_name, myClanEntity.anchorName).setText(R$id.tv_time, this.mContext.getString(R$string.fq_last_start_time, myClanEntity.formatLiveTime(), myClanEntity.getMaxPopularity())).setText(R$id.tv_status, getLiveStatusStr(myClanEntity)).setTextColor(R$id.tv_status, ContextCompat.getColor(this.mContext, myClanEntity.liveStatus == 1 ? R$color.fq_colorRed : R$color.fq_text_gray));
    }

    private String getLiveStatusStr(MyClanEntity myClanEntity) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_live_status_menu);
        int i = myClanEntity.liveStatus;
        if (i != 0) {
            return i != 1 ? "" : stringArray[1];
        }
        return stringArray[2];
    }

    public int getLiveStatus() {
        return this.liveStatus;
    }

    public void setLiveStatus(int i) {
        this.liveStatus = i;
    }
}
