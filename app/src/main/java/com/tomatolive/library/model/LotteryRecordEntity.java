package com.tomatolive.library.model;

import android.text.TextUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class LotteryRecordEntity {
    public String createTime;
    public String drawTimes;
    public String isLuck;
    public List<LotteryPrizeEntity> propList;
    public int turntableType;

    public boolean isLuckFlag() {
        return TextUtils.equals(this.isLuck, "1");
    }

    public boolean isGeneralRoulette() {
        return this.turntableType == 1;
    }

    public List<LotteryPrizeEntity> getPropList() {
        return this.propList;
    }
}
