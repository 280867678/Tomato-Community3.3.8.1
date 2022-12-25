package com.tomatolive.library.model;

import java.util.List;

/* loaded from: classes3.dex */
public class LotteryUserRankEntity extends AnchorEntity {
    public List<LotteryPrizeEntity> awardList;

    public List<LotteryPrizeEntity> getPropList() {
        List<LotteryPrizeEntity> list = this.awardList;
        if (list != null && list.size() < 3) {
            this.awardList.add(null);
            this.awardList.add(null);
        }
        List<LotteryPrizeEntity> list2 = this.awardList;
        if (list2 != null && list2.size() > 3) {
            return this.awardList.subList(0, 3);
        }
        return this.awardList;
    }
}
