package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import com.amazonaws.services.p054s3.internal.SSEResultBase;

/* renamed from: com.amazonaws.services.s3.model.UploadPartResult */
/* loaded from: classes2.dex */
public class UploadPartResult extends SSEResultBase implements S3RequesterChargedResult {
    private String eTag;
    private int partNumber;

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int i) {
        this.partNumber = i;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public PartETag getPartETag() {
        return new PartETag(this.partNumber, this.eTag);
    }
}
