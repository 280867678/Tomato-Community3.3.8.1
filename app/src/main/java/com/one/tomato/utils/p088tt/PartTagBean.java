package com.one.tomato.utils.p088tt;

import java.io.Serializable;

/* renamed from: com.one.tomato.utils.tt.PartTagBean */
/* loaded from: classes3.dex */
public class PartTagBean implements Serializable {
    private String eTag;
    private int partNumber;

    public PartTagBean(int i, String str) {
        this.partNumber = i;
        this.eTag = str;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int i) {
        this.partNumber = i;
    }

    public PartTagBean withPartNumber(int i) {
        this.partNumber = i;
        return this;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public PartTagBean withETag(String str) {
        this.eTag = str;
        return this;
    }
}
