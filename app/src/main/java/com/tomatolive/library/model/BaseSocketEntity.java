package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class BaseSocketEntity<T> {
    private T businessData;
    private String messageType;

    /* renamed from: r */
    private String f5832r;

    /* renamed from: s */
    private String f5833s;

    /* renamed from: t */
    private String f5834t;

    public String getMessageType() {
        String str = this.messageType;
        return str == null ? "" : str;
    }

    public void setMessageType(String str) {
        this.messageType = str;
    }

    public T getBusinessData() {
        return this.businessData;
    }

    public void setBusinessData(T t) {
        this.businessData = t;
    }

    public void setRandomStr(String str) {
        this.f5832r = str;
    }

    public String getRandomStr() {
        return this.f5832r;
    }

    public void setTimestampStr(String str) {
        this.f5834t = str;
    }

    public String getTimestampStr() {
        return this.f5834t;
    }

    public void setSign(String str) {
        this.f5833s = str;
    }

    public String getSign() {
        return this.f5833s;
    }
}
