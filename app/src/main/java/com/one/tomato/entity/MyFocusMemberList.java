package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class MyFocusMemberList {
    private String avatar;
    private int blackId;

    /* renamed from: id */
    private int f1719id;
    private String isBlack;
    private int isFocus;
    private String isKeepSilent;
    private int keepSilentId;
    private String name;
    private String sex;
    private String signature;

    public MyFocusMemberList(int i, int i2) {
        this.f1719id = i;
        this.isFocus = i2;
    }

    public int getId() {
        return this.f1719id;
    }

    public void setId(int i) {
        this.f1719id = i;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String str) {
        this.sex = str;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    public String getIsKeepSilent() {
        return this.isKeepSilent;
    }

    public void setIsKeepSilent(String str) {
        this.isKeepSilent = str;
    }

    public String getIsBlack() {
        return this.isBlack;
    }

    public void setIsBlack(String str) {
        this.isBlack = str;
    }

    public int getKeepSilentId() {
        return this.keepSilentId;
    }

    public void setKeepSilentId(int i) {
        this.keepSilentId = i;
    }

    public int getBlackId() {
        return this.blackId;
    }

    public void setBlackId(int i) {
        this.blackId = i;
    }

    public int getIsFocus() {
        return this.isFocus;
    }

    public void setIsFocus(int i) {
        this.isFocus = i;
    }

    public boolean equals(Object obj) {
        MyFocusMemberList myFocusMemberList;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof MyFocusMemberList) && (this == (myFocusMemberList = (MyFocusMemberList) obj) || this.f1719id == myFocusMemberList.getId());
    }

    public int hashCode() {
        return this.f1719id;
    }
}
