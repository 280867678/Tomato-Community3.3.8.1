package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class ExpenseMenuEntity {
    private String totalExpensePrice = "";
    private String giftExpensePrice = "";
    private String carExpensePrice = "";
    private String guardExpensePrice = "";
    private String propExpensePrice = "";
    private String nobilityExpensePrice = "";
    private String luckyGiftExpensePrice = "";
    private String scoreGiftExpensePrice = "";
    private String liveTicketExpensePrice = "";

    public String getTotalExpensePrice() {
        return TextUtils.isEmpty(this.totalExpensePrice) ? "0" : this.totalExpensePrice;
    }

    public void setTotalExpensePrice(String str) {
        this.totalExpensePrice = str;
    }

    public String getGiftExpensePrice() {
        return TextUtils.isEmpty(this.giftExpensePrice) ? "0" : this.giftExpensePrice;
    }

    public void setGiftExpensePrice(String str) {
        this.giftExpensePrice = str;
    }

    public String getCarExpensePrice() {
        return TextUtils.isEmpty(this.carExpensePrice) ? "0" : this.carExpensePrice;
    }

    public void setCarExpensePrice(String str) {
        this.carExpensePrice = str;
    }

    public String getGuardExpensePrice() {
        return TextUtils.isEmpty(this.guardExpensePrice) ? "0" : this.guardExpensePrice;
    }

    public void setGuardExpensePrice(String str) {
        this.guardExpensePrice = str;
    }

    public String getPropsExpense() {
        return TextUtils.isEmpty(this.propExpensePrice) ? "0" : this.propExpensePrice;
    }

    public void setPropsExpense(String str) {
        this.propExpensePrice = str;
    }

    public String getNobilityExpensePrice() {
        return TextUtils.isEmpty(this.nobilityExpensePrice) ? "0" : this.nobilityExpensePrice;
    }

    public String getLuckyGiftExpensePrice() {
        return TextUtils.isEmpty(this.luckyGiftExpensePrice) ? "0" : this.luckyGiftExpensePrice;
    }

    public void setLuckyGiftExpensePrice(String str) {
        this.luckyGiftExpensePrice = str;
    }

    public String getScoreGiftExpensePrice() {
        return TextUtils.isEmpty(this.scoreGiftExpensePrice) ? "0" : this.scoreGiftExpensePrice;
    }

    public String getPaidLiveExpensePrice() {
        return TextUtils.isEmpty(this.liveTicketExpensePrice) ? "0" : this.liveTicketExpensePrice;
    }

    public String toString() {
        return "ExpenseMenuEntity{totalExpense='" + this.totalExpensePrice + "', giftExpense='" + this.giftExpensePrice + "', carExpense='" + this.carExpensePrice + "', guardExpense='" + this.guardExpensePrice + "'}";
    }
}
