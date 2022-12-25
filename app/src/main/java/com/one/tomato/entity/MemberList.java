package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class MemberList {
    private String avatar;
    private int blackId;
    private int follow;

    /* renamed from: id */
    private int f1716id;
    private String isBlack;
    private String isKeepSilent;
    private int keepSilentId;
    private String name;
    private String sex;
    private String signature;

    public MemberList(int i, String str) {
        this.follow = 1;
        this.f1716id = i;
        this.avatar = str;
    }

    public MemberList(int i, int i2) {
        this.follow = 1;
        this.f1716id = i;
        this.follow = i2;
    }

    public int getId() {
        return this.f1716id;
    }

    public void setId(int i) {
        this.f1716id = i;
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

    public int getFollow() {
        return this.follow;
    }

    public void setFollow(int i) {
        this.follow = i;
    }

    public boolean equals(Object obj) {
        MemberList memberList;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof MemberList) && (this == (memberList = (MemberList) obj) || this.f1716id == memberList.getId());
    }

    public int hashCode() {
        return this.f1716id;
    }
}
