package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class SignBean {
    private int countSignInFirst;
    private int countSignInLast;
    private boolean curDaySignIn;
    private int giftExp;

    /* renamed from: id */
    private int f1737id;
    private boolean isSigned;
    private String signInCopy;
    private int signInDays;
    private int vipGiftFirst;
    private int vipGiftLast;

    public SignBean(boolean z, int i) {
        this.isSigned = z;
        this.f1737id = i;
    }

    public SignBean() {
    }

    public int getId() {
        return this.f1737id;
    }

    public void setId(int i) {
        this.f1737id = i;
    }

    public boolean isSigned() {
        return this.isSigned;
    }

    public void setSigned(boolean z) {
        this.isSigned = z;
    }

    public int getSignInDays() {
        return this.signInDays;
    }

    public void setSignInDays(int i) {
        this.signInDays = i;
    }

    public boolean isCurDaySignIn() {
        return this.curDaySignIn;
    }

    public void setCurDaySignIn(boolean z) {
        this.curDaySignIn = z;
    }

    public int getCountSignInFirst() {
        return this.countSignInFirst;
    }

    public void setCountSignInFirst(int i) {
        this.countSignInFirst = i;
    }

    public int getVipGiftFirst() {
        return this.vipGiftFirst;
    }

    public void setVipGiftFirst(int i) {
        this.vipGiftFirst = i;
    }

    public int getCountSignInLast() {
        return this.countSignInLast;
    }

    public void setCountSignInLast(int i) {
        this.countSignInLast = i;
    }

    public int getVipGiftLast() {
        return this.vipGiftLast;
    }

    public void setVipGiftLast(int i) {
        this.vipGiftLast = i;
    }

    public int getGiftExp() {
        return this.giftExp;
    }

    public void setGiftExp(int i) {
        this.giftExp = i;
    }

    public String getSignInCopy() {
        return this.signInCopy;
    }

    public void setSignInCopy(String str) {
        this.signInCopy = str;
    }
}
