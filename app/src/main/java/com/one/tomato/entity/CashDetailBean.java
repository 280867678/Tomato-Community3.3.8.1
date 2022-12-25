package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class CashDetailBean implements Serializable {
    private static final long serialVersionUID = -2559941004046646289L;
    private float actualAmount;
    private String alipayAccount;
    private String alipayName;
    private float amount;
    private String arrivalTime;
    private String bankAccount;
    private String bankName;
    private String cardName;
    private String createTime;
    private String orderId;
    private int processingState;
    private String reason;
    public String sendFailureReason;
    private int status;
    private float tax;
    private int withdrawType;

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String str) {
        this.arrivalTime = str;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public int getProcessingState() {
        return this.processingState;
    }

    public void setProcessingState(int i) {
        this.processingState = i;
    }

    public String getAlipayName() {
        return this.alipayName;
    }

    public void setAlipayName(String str) {
        this.alipayName = str;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public int getWithdrawType() {
        return this.withdrawType;
    }

    public void setWithdrawType(int i) {
        this.withdrawType = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getAlipayAccount() {
        return this.alipayAccount;
    }

    public void setAlipayAccount(String str) {
        this.alipayAccount = str;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public float getActualAmount() {
        return this.actualAmount;
    }

    public void setActualAmount(float f) {
        this.actualAmount = f;
    }

    public float getTax() {
        return this.tax;
    }

    public void setTax(float f) {
        this.tax = f;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
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
