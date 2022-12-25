package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class AwardHistoryEntity {
    private String anchorName;
    private String prizeName;
    private String prizeNum;
    private String userAppId;
    private String userName;
    private String userOpenId;
    private String winningRecordId;
    private int winningStatus;
    private String winningTime;

    public AwardHistoryEntity(String str, String str2, int i, long j) {
        this.anchorName = str;
        this.prizeName = str2;
        this.winningStatus = i;
        this.winningTime = String.valueOf(j);
    }

    public String getAnchorName() {
        return this.anchorName;
    }

    public void setAnchorName(String str) {
        this.anchorName = str;
    }

    public String getPrizeName() {
        return this.prizeName;
    }

    public void setPrizeName(String str) {
        this.prizeName = str;
    }

    public String getPrizeNum() {
        return this.prizeNum;
    }

    public void setPrizeNum(String str) {
        this.prizeNum = str;
    }

    public int getWinningStatus() {
        return this.winningStatus;
    }

    public void setWinningStatus(int i) {
        this.winningStatus = i;
    }

    public String getWinningTime() {
        return this.winningTime;
    }

    public void setWinningTime(String str) {
        this.winningTime = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getUserOpenId() {
        return this.userOpenId;
    }

    public void setUserOpenId(String str) {
        this.userOpenId = str;
    }

    public String getUserAppId() {
        return this.userAppId;
    }

    public void setUserAppId(String str) {
        this.userAppId = str;
    }

    public String getWinningRecordId() {
        return this.winningRecordId;
    }

    public void setWinningRecordId(String str) {
        this.winningRecordId = str;
    }
}
