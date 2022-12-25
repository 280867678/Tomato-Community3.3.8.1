package com.tomatolive.library.p136ui.view.sticker.core;

import android.text.TextUtils;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.IMGText */
/* loaded from: classes3.dex */
public class IMGText {
    private int color;

    /* renamed from: id */
    private String f5852id;
    private String text;

    public IMGText(String str, int i) {
        this.color = -1;
        this.text = str;
        this.color = i;
    }

    public IMGText(String str, String str2, int i) {
        this.color = -1;
        this.f5852id = str;
        this.text = str2;
        this.color = i;
    }

    public String getId() {
        return this.f5852id;
    }

    public void setId(String str) {
        this.f5852id = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.text);
    }

    public int length() {
        if (isEmpty()) {
            return 0;
        }
        return this.text.length();
    }

    public String toString() {
        return "IMGText{text='" + this.text + "', color=" + this.color + '}';
    }
}
