package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class CarHistoryRecordEntity {
    private String carName;
    private String postTime;
    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String str) {
        this.carName = str;
    }

    public String getPostTime() {
        return this.postTime;
    }

    public void setPostTime(String str) {
        this.postTime = str;
    }

    public CarHistoryRecordEntity() {
    }

    public CarHistoryRecordEntity(String str, String str2, String str3) {
        this.userName = str;
        this.carName = str2;
        this.postTime = str3;
    }

    public String toString() {
        return "CarHistoryRecordEntity{userName='" + this.userName + "', carName='" + this.carName + "', postTime='" + this.postTime + "'}";
    }
}
