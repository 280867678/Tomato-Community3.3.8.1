package com.amazonaws.services.p054s3.model;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.MultipartUploadListing */
/* loaded from: classes2.dex */
public class MultipartUploadListing {
    private List<String> commonPrefixes = new ArrayList();
    private List<MultipartUpload> multipartUploads;

    public void setBucketName(String str) {
    }

    public void setDelimiter(String str) {
    }

    public void setEncodingType(String str) {
    }

    public void setKeyMarker(String str) {
    }

    public void setMaxUploads(int i) {
    }

    public void setNextKeyMarker(String str) {
    }

    public void setNextUploadIdMarker(String str) {
    }

    public void setPrefix(String str) {
    }

    public void setTruncated(boolean z) {
    }

    public void setUploadIdMarker(String str) {
    }

    public List<MultipartUpload> getMultipartUploads() {
        if (this.multipartUploads == null) {
            this.multipartUploads = new ArrayList();
        }
        return this.multipartUploads;
    }

    public List<String> getCommonPrefixes() {
        return this.commonPrefixes;
    }
}
