package com.zzhoujay.richtext;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/* loaded from: classes4.dex */
public class LinkHolder {
    private static final int link_color = Color.parseColor("#4078C0");
    @ColorInt
    private int color = link_color;
    private boolean underLine = true;
    private final String url;

    public LinkHolder(String str) {
        this.url = str;
    }

    @ColorInt
    public int getColor() {
        return this.color;
    }

    public boolean isUnderLine() {
        return this.underLine;
    }

    public String getUrl() {
        return this.url;
    }
}
