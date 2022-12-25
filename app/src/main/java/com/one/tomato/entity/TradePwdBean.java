package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class TradePwdBean implements Serializable {
    private static final long serialVersionUID = 4962649042475962361L;
    private int leaveTimes;
    private String sec;

    public int getLeaveTimes() {
        return this.leaveTimes;
    }

    public void setLeaveTimes(int i) {
        this.leaveTimes = i;
    }

    public String getSec() {
        return this.sec;
    }

    public void setSec(String str) {
        this.sec = str;
    }
}
