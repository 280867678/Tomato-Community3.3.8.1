package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class BalanceBean implements Serializable {
    private String alipayAccount;
    private String alipayName;
    private String balance;
    private String bankAccount;
    private String bankName;
    private double canWithdrawBalance;
    private String cardName;
    private String checkPeriod;
    private String creatTime;
    private int fixBalance;
    private boolean hasSetTransactionPwd;

    /* renamed from: id */
    private int f1694id;
    private int intLimit;
    private String limitNumberWithdraw;
    private String memberId;
    private float minTax;
    private String money;
    private int money2TomatoRate;
    private String presentBeginTime;
    private String presentEndTime;
    private boolean pwdErrorTimeLimit;
    private int singleMaxWithdraw;
    private double singleMinimumWithdraw;
    private float taxRate;
    private String upBalance;
    private String updateTime;
    private double walletBalance;

    public String getUpBalance() {
        return this.upBalance;
    }

    public void setUpBalance(String str) {
        this.upBalance = str;
    }

    public double getSingleMinimumWithdraw() {
        return this.singleMinimumWithdraw;
    }

    public void setSingleMinimumWithdraw(double d) {
        this.singleMinimumWithdraw = d;
    }

    public String getAlipayName() {
        return this.alipayName;
    }

    public void setAlipayName(String str) {
        this.alipayName = str;
    }

    public boolean isHasSetTransactionPwd() {
        return this.hasSetTransactionPwd;
    }

    public void setHasSetTransactionPwd(boolean z) {
        this.hasSetTransactionPwd = z;
    }

    public int getMoney2TomatoRate() {
        return this.money2TomatoRate;
    }

    public void setMoney2TomatoRate(int i) {
        this.money2TomatoRate = i;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String str) {
        this.cardName = str;
    }

    public float getMinTax() {
        return this.minTax;
    }

    public void setMinTax(float f) {
        this.minTax = f;
    }

    public float getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(float f) {
        this.taxRate = f;
    }

    public int getSingleMaxWithdraw() {
        return this.singleMaxWithdraw;
    }

    public void setSingleMaxWithdraw(int i) {
        this.singleMaxWithdraw = i;
    }

    public int getIntLimit() {
        return this.intLimit;
    }

    public void setIntLimit(int i) {
        this.intLimit = i;
    }

    public boolean isPwdErrorTimeLimit() {
        return this.pwdErrorTimeLimit;
    }

    public void setPwdErrorTimeLimit(boolean z) {
        this.pwdErrorTimeLimit = z;
    }

    public String getAlipayAccount() {
        return this.alipayAccount;
    }

    public void setAlipayAccount(String str) {
        this.alipayAccount = str;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String str) {
        this.balance = str;
    }

    public String getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(String str) {
        this.creatTime = str;
    }

    public int getFixBalance() {
        return this.fixBalance;
    }

    public void setFixBalance(int i) {
        this.fixBalance = i;
    }

    public int getId() {
        return this.f1694id;
    }

    public void setId(int i) {
        this.f1694id = i;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String str) {
        this.memberId = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String str) {
        this.money = str;
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

    public double getCanWithdrawBalance() {
        return this.canWithdrawBalance;
    }

    public void setCanWithdrawBalance(double d) {
        this.canWithdrawBalance = d;
    }

    public double getWalletBalance() {
        return this.walletBalance;
    }

    public void setWalletBalance(double d) {
        this.walletBalance = d;
    }

    public String getLimitNumberWithdraw() {
        return this.limitNumberWithdraw;
    }

    public void setLimitNumberWithdraw(String str) {
        this.limitNumberWithdraw = str;
    }

    public String getCheckPeriod() {
        return this.checkPeriod;
    }

    public void setCheckPeriod(String str) {
        this.checkPeriod = str;
    }

    public String getPresentBeginTime() {
        return this.presentBeginTime;
    }

    public void setPresentBeginTime(String str) {
        this.presentBeginTime = str;
    }

    public String getPresentEndTime() {
        return this.presentEndTime;
    }

    public void setPresentEndTime(String str) {
        this.presentEndTime = str;
    }
}
