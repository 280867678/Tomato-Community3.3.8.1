package com.one.tomato.entity;

import android.support.annotation.Nullable;

/* loaded from: classes3.dex */
public class UpSubscribePayBean {
    private String gift;
    private boolean isSelect;
    private int price;
    private String title;

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int i) {
        this.price = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getGift() {
        return this.gift;
    }

    public void setGift(String str) {
        this.gift = str;
    }

    public boolean equals(@Nullable Object obj) {
        return this.price == ((UpSubscribePayBean) obj).price;
    }
}
