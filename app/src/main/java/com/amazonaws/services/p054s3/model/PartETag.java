package com.amazonaws.services.p054s3.model;

/* renamed from: com.amazonaws.services.s3.model.PartETag */
/* loaded from: classes2.dex */
public class PartETag {
    private String eTag;
    private int partNumber;

    public PartETag(int i, String str) {
        this.partNumber = i;
        this.eTag = str;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public String getETag() {
        return this.eTag;
    }
}
