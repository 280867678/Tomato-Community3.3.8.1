package com.one.tomato.entity;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class CircleNoticeBean implements Serializable {
    private static final long serialVersionUID = 820404903811564027L;
    private String data;
    private String date;
    private int groupId;
    private String groupName;

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }
}
