package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;

/* renamed from: com.amazonaws.services.s3.model.ListObjectsRequest */
/* loaded from: classes2.dex */
public class ListObjectsRequest extends AmazonWebServiceRequest {
    public void setBucketName(String str) {
    }

    public void setDelimiter(String str) {
    }

    public void setEncodingType(String str) {
    }

    public void setMarker(String str) {
    }

    public void setMaxKeys(Integer num) {
    }

    public void setPrefix(String str) {
    }

    public ListObjectsRequest(String str, String str2, String str3, String str4, Integer num) {
        setBucketName(str);
        setPrefix(str2);
        setMarker(str3);
        setDelimiter(str4);
        setMaxKeys(num);
    }

    public ListObjectsRequest withEncodingType(String str) {
        setEncodingType(str);
        return this;
    }
}
