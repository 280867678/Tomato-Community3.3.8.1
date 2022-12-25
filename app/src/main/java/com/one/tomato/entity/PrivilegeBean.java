package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class PrivilegeBean {
    private String desc;
    private String name;
    private int resId;

    public PrivilegeBean(int i, String str, String str2) {
        this.resId = i;
        this.name = str;
        this.desc = str2;
    }

    public PrivilegeBean(int i, String str) {
        this.resId = i;
        this.name = str;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int i) {
        this.resId = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }
}
