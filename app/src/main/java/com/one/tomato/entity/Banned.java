package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class Banned implements Serializable {
    private String createTime;
    private int groupId;
    private String groupName;

    /* renamed from: id */
    private int f1695id;
    private String managerName;
    private int memberId;
    private String operator;
    private String reason;
    private int status;

    public String getManagerName() {
        return this.managerName;
    }

    public void setManagerName(String str) {
        this.managerName = str;
    }

    public Banned(String str, String str2) {
        this.groupName = str;
        this.createTime = str2;
    }

    public int getId() {
        return this.f1695id;
    }

    public void setId(int i) {
        this.f1695id = i;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String str) {
        this.operator = str;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
