package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.DeleteBucketRequest */
/* loaded from: classes2.dex */
public class DeleteBucketRequest extends AmazonWebServiceRequest implements Serializable, S3AccelerateUnsupported {
    private String bucketName;

    public DeleteBucketRequest(String str) {
        setBucketName(str);
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getBucketName() {
        return this.bucketName;
    }
}
