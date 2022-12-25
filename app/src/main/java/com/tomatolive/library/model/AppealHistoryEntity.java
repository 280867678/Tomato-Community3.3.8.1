package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class AppealHistoryEntity {
    private String anchorName;
    private int appealStatus;
    private String createTime;

    /* renamed from: id */
    private String f5828id;
    private String prizeName;

    public AppealHistoryEntity(String str, String str2, int i, long j) {
        this.anchorName = str;
        this.prizeName = str2;
        this.appealStatus = i;
        this.createTime = String.valueOf(j);
    }

    public String getId() {
        return this.f5828id;
    }

    public void setId(String str) {
        this.f5828id = str;
    }

    public String getPrizeName() {
        return this.prizeName;
    }

    public void setPrizeName(String str) {
        this.prizeName = str;
    }

    public String getAnchorName() {
        return this.anchorName;
    }

    public void setAnchorName(String str) {
        this.anchorName = str;
    }

    public int getAppealStatus() {
        return this.appealStatus;
    }

    public void setAppealStatus(int i) {
        this.appealStatus = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }
}
