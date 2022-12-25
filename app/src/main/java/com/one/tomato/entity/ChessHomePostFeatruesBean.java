package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class ChessHomePostFeatruesBean {
    private int itemId;
    private int resId;
    private String text;

    public ChessHomePostFeatruesBean(int i, String str, int i2) {
        this.resId = i;
        this.text = str;
        this.itemId = i2;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int i) {
        this.resId = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int i) {
        this.itemId = i;
    }
}
