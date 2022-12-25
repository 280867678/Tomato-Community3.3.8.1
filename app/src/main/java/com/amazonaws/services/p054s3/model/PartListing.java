package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.PartListing */
/* loaded from: classes2.dex */
public class PartListing implements S3RequesterChargedResult {
    private List<PartSummary> parts;

    public void setBucketName(String str) {
    }

    public void setEncodingType(String str) {
    }

    public void setInitiator(Owner owner) {
    }

    public void setKey(String str) {
    }

    public void setOwner(Owner owner) {
    }

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
    }

    public void setStorageClass(String str) {
    }

    public void setTruncated(boolean z) {
    }

    public void setUploadId(String str) {
    }

    public void setPartNumberMarker(int i) {
        Integer.valueOf(i);
    }

    public void setNextPartNumberMarker(int i) {
        Integer.valueOf(i);
    }

    public void setMaxParts(int i) {
        Integer.valueOf(i);
    }

    public List<PartSummary> getParts() {
        if (this.parts == null) {
            this.parts = new ArrayList();
        }
        return this.parts;
    }
}
