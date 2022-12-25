package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class AwardDetailEntity {
    private String address;
    private String anchorAvatar;
    private String anchorId;
    private String anchorName;
    private String anchorOpenId;
    private String drawId;
    private String drawType;
    private String drawTypeDesc;
    private String liveId;
    private String partDetail;
    private String prizeName;
    private String prizeNum;
    private String saveAddressTime;
    private String sendPrizeTime;
    private String userAppId;
    private String userAvatar;
    private String userId;
    private String userName;
    private String userOpenId;
    private String winningRecordId;
    private String winningStatus;
    private String winningTime;

    public AwardDetailEntity(String str, String str2, String str3, long j) {
        this.anchorName = str;
        this.prizeName = str2;
        this.winningStatus = str3;
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

    public String getWinningStatus() {
        return this.winningStatus;
    }

    public void setWinningStatus(String str) {
        this.winningStatus = str;
    }

    public String getWinningRecordId() {
        return this.winningRecordId;
    }

    public void setWinningRecordId(String str) {
        this.winningRecordId = str;
    }

    public String getAnchorOpenId() {
        return this.anchorOpenId;
    }

    public void setAnchorOpenId(String str) {
        this.anchorOpenId = str;
    }

    public String getLiveId() {
        return this.liveId;
    }

    public void setLiveId(String str) {
        this.liveId = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getDrawTypeDesc() {
        return this.drawTypeDesc;
    }

    public void setDrawTypeDesc(String str) {
        this.drawTypeDesc = str;
    }

    public String getPartDetail() {
        return this.partDetail;
    }

    public void setPartDetail(String str) {
        this.partDetail = str;
    }

    public String getWinningTime() {
        return this.winningTime;
    }

    public void setWinningTime(String str) {
        this.winningTime = str;
    }

    public String getSaveAddressTime() {
        return this.saveAddressTime;
    }

    public void setSaveAddressTime(String str) {
        this.saveAddressTime = str;
    }

    public String getSendPrizeTime() {
        return this.sendPrizeTime;
    }

    public void setSendPrizeTime(String str) {
        this.sendPrizeTime = str;
    }

    public String getAnchorId() {
        return this.anchorId;
    }

    public void setAnchorId(String str) {
        this.anchorId = str;
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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getUserAppId() {
        return this.userAppId;
    }

    public void setUserAppId(String str) {
        this.userAppId = str;
    }

    public String getDrawId() {
        return this.drawId;
    }

    public void setDrawId(String str) {
        this.drawId = str;
    }

    public String getDrawType() {
        return this.drawType;
    }

    public void setDrawType(String str) {
        this.drawType = str;
    }

    public String getAnchorAvatar() {
        return this.anchorAvatar;
    }

    public void setAnchorAvatar(String str) {
        this.anchorAvatar = str;
    }

    public String getUserAvatar() {
        return this.userAvatar;
    }

    public void setUserAvatar(String str) {
        this.userAvatar = str;
    }
}
