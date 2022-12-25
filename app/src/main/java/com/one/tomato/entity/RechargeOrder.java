package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class RechargeOrder {
    private String channel;
    private String channelid;
    private String msg;
    private String orderId;
    private String payee_name;
    private String platid;
    private String sdk;
    private String sdk_type;
    private int status;
    private String url;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public String getPlatid() {
        return this.platid;
    }

    public void setPlatid(String str) {
        this.platid = str;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public String getPayee_name() {
        return this.payee_name;
    }

    public void setPayee_name(String str) {
        this.payee_name = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getChannelid() {
        return this.channelid;
    }

    public void setChannelid(String str) {
        this.channelid = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getSdk() {
        return this.sdk;
    }

    public void setSdk(String str) {
        this.sdk = str;
    }

    public String getSdk_type() {
        return this.sdk_type;
    }

    public void setSdk_type(String str) {
        this.sdk_type = str;
    }
}
