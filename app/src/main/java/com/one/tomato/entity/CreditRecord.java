package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class CreditRecord {
    private String amount;
    private String createTime;

    /* renamed from: id */
    private int f1705id;
    private int type;
    private String typeDes;

    public int getId() {
        return this.f1705id;
    }

    public void setId(int i) {
        this.f1705id = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getTypeDes() {
        return this.typeDes;
    }

    public void setTypeDes(String str) {
        this.typeDes = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String str) {
        this.amount = str;
    }
}
