package com.one.tomato.entity;

import com.one.tomato.entity.CreatorCenterBean;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class CreatorCenterHomeBean {
    public ArrayList<CreatorCenterBean.TodayRank> top3List;
    public String topListType;

    public CreatorCenterHomeBean(String str, ArrayList<CreatorCenterBean.TodayRank> arrayList) {
        this.topListType = str;
        this.top3List = arrayList;
    }
}
