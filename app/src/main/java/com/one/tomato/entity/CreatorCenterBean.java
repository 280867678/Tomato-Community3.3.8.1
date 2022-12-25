package com.one.tomato.entity;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CreatorCenterBean {
    public String countOriginal;
    public String countPublished;
    public String countSerialGroup;
    public String countUpHost;
    public String todayIncome;
    public List<TodayRank> todayTop3;
    public List<ArrayList<TodayRank>> top3List;
    public String topListCfg;
    public String totalIncome;

    /* loaded from: classes3.dex */
    public static class TodayRank {
        public String avatar;
        public String income;
        public String memberId;
        public String memberName;

        public TodayRank(String str, String str2) {
            this.income = str;
            this.memberName = str2;
        }
    }
}
