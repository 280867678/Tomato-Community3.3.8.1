package com.youdao.sdk.ydonlinetranslate;

import com.youdao.sdk.ydonlinetranslate.other.C5182d;

/* loaded from: classes4.dex */
public class Region {
    private C5182d boundingBox;
    private String context;
    private int lineheight;
    private int linesCount;
    private String tranContent;

    public String getContext() {
        return this.context;
    }

    public void setContext(String str) {
        this.context = str;
    }

    public String getTranContent() {
        return this.tranContent;
    }

    public void setTranContent(String str) {
        this.tranContent = str;
    }

    public int getLinesCount() {
        return this.linesCount;
    }

    public void setLinesCount(int i) {
        this.linesCount = i;
    }

    public int getLineheight() {
        return this.lineheight;
    }

    public void setLineheight(int i) {
        this.lineheight = i;
    }

    public C5182d getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(C5182d c5182d) {
        this.boundingBox = c5182d;
    }
}
