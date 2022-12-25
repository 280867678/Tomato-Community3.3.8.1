package com.faceunity.beautycontrolview.entity;

/* loaded from: classes2.dex */
public class Filter {
    private String description;
    private String filterName;
    private int resId;

    public Filter(String str, int i, String str2, int i2) {
        this.filterName = str;
        this.resId = i;
        this.description = str2;
    }

    public String filterName() {
        return this.filterName;
    }

    public int resId() {
        return this.resId;
    }

    public String description() {
        return this.description;
    }
}
