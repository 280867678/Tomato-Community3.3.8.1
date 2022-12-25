package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.SSEResultBase;

/* renamed from: com.amazonaws.services.s3.model.InitiateMultipartUploadResult */
/* loaded from: classes2.dex */
public class InitiateMultipartUploadResult extends SSEResultBase {
    private String uploadId;

    public void setBucketName(String str) {
    }

    public void setKey(String str) {
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }
}
