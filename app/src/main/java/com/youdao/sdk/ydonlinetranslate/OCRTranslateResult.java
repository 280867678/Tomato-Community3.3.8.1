package com.youdao.sdk.ydonlinetranslate;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class OCRTranslateResult {
    private int errorCode;
    private String from;
    private String json;
    private String orientation;
    private List<Region> regions = new ArrayList();
    private int textAngle;

    /* renamed from: to */
    private String f5934to;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String str) {
        this.from = str;
    }

    public String getTo() {
        return this.f5934to;
    }

    public void setTo(String str) {
        this.f5934to = str;
    }

    public OCRTranslateResult(String str) {
        this.json = str;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String str) {
        this.json = str;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public boolean success() {
        return this.errorCode == 0;
    }

    public int getTextAngle() {
        return this.textAngle;
    }

    public void setTextAngle(int i) {
        this.textAngle = i;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String str) {
        this.orientation = str;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public void setRegions(List<Region> list) {
        this.regions = list;
    }
}
