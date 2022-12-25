package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class CashRecodeListBean implements Serializable {
    private static final long serialVersionUID = -7810399385065190618L;
    private double actualAmount;
    private String alipayAccount;
    private String alipayName;
    private double amount;
    private String bankAccount;
    private String bankName;
    private String cardName;
    private String createTime;
    private String orderId;
    private int status;
    private double tax;
    private int withdrawType;

    public double getTax() {
        return this.tax;
    }

    public void setTax(double d) {
        this.tax = d;
    }

    public double getActualAmount() {
        return this.actualAmount;
    }

    public void setActualAmount(double d) {
        this.actualAmount = d;
    }

    public String getAlipayAccount() {
        return this.alipayAccount;
    }

    public void setAlipayAccount(String str) {
        this.alipayAccount = str;
    }

    public String getAlipayName() {
        return this.alipayName;
    }

    public void setAlipayName(String str) {
        this.alipayName = str;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double d) {
        this.amount = d;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public int getWithdrawType() {
        return this.withdrawType;
    }

    public void setWithdrawType(int i) {
        this.withdrawType = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String str) {
        this.bankAccount = str;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String str) {
        this.bankName = str;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String str) {
        this.cardName = str;
    }
}
