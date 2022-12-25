package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class TaskBoxEntity {

    /* renamed from: id */
    private String f5848id;
    private String openTime;
    private int position;
    private String propImg;
    private String propName;
    private int propNumber;
    private int status;

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public void setPropNumber(int i) {
        this.propNumber = i;
    }

    public int getPropNumber() {
        return this.propNumber;
    }

    public String getId() {
        return this.f5848id;
    }

    public void setId(String str) {
        this.f5848id = str;
    }

    public String getPropImg() {
        return this.propImg;
    }

    public void setPropImg(String str) {
        this.propImg = str;
    }

    public String getPropName() {
        return this.propName;
    }

    public void setPropName(String str) {
        this.propName = str;
    }

    public String getOpenTime() {
        return this.openTime;
    }

    public void setOpenTime(String str) {
        this.openTime = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String toString() {
        return "TaskBoxEntity{id='" + this.f5848id + ", propName='" + this.propName + "'}";
    }
}
