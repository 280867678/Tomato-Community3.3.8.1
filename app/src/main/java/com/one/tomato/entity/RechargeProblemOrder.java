package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class RechargeProblemOrder implements Serializable {
    private String date;

    /* renamed from: id */
    private String f1727id;
    private int isJinYu;
    private String money;

    public String getId() {
        return this.f1727id;
    }

    public void setId(String str) {
        this.f1727id = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String str) {
        this.money = str;
    }

    public int getIsJinYu() {
        return this.isJinYu;
    }

    public void setIsJinYu(int i) {
        this.isJinYu = i;
    }
}
