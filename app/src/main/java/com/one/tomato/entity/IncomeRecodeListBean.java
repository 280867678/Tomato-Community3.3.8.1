package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class IncomeRecodeListBean implements Serializable {
    private static final long serialVersionUID = -8175224258435057591L;
    private String amount;
    private double balance;
    private String content;
    private String createTime;
    private String tomatoOrderId;
    private int type;

    public String getTomatoOrderId() {
        return this.tomatoOrderId;
    }

    public void setTomatoOrderId(String str) {
        this.tomatoOrderId = str;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(long j) {
        this.balance = j;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
