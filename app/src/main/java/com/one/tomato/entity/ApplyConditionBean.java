package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class ApplyConditionBean {
    private long allCount;
    private long currentCount;
    private String titleName;
    private int type;

    public ApplyConditionBean(int i) {
        this.type = i;
    }

    public ApplyConditionBean(long j, String str) {
        this.currentCount = j;
        this.titleName = str;
    }

    public String getTitleName() {
        return this.titleName;
    }

    public void setTitleName(String str) {
        this.titleName = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public long getAllCount() {
        return this.allCount;
    }

    public void setAllCount(long j) {
        this.allCount = j;
    }

    public long getCurrentCount() {
        return this.currentCount;
    }

    public void setCurrentCount(long j) {
        this.currentCount = j;
    }
}
