package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.GetBucketAnalyticsConfigurationRequest */
/* loaded from: classes2.dex */
public class GetBucketAnalyticsConfigurationRequest extends AmazonWebServiceRequest implements Serializable {
    private String bucketName;

    /* renamed from: id */
    private String f1184id;

    public GetBucketAnalyticsConfigurationRequest() {
    }

    public GetBucketAnalyticsConfigurationRequest(String str, String str2) {
        this.bucketName = str;
        this.f1184id = str2;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public GetBucketAnalyticsConfigurationRequest withBucketName(String str) {
        setBucketName(str);
        return this;
    }

    public String getId() {
        return this.f1184id;
    }

    public void setId(String str) {
        this.f1184id = str;
    }

    public GetBucketAnalyticsConfigurationRequest withId(String str) {
        setId(str);
        return this;
    }
}
