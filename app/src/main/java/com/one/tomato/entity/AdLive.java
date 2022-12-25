package com.one.tomato.entity;

import com.one.tomato.entity.p079db.AdLiveBean;
import java.util.List;

/* loaded from: classes3.dex */
public class AdLive {
    private List<AdLiveBean> advList;
    private String typeId;

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String str) {
        this.typeId = str;
    }

    public List<AdLiveBean> getAdvList() {
        return this.advList;
    }

    public void setAdvList(List<AdLiveBean> list) {
        this.advList = list;
    }
}
