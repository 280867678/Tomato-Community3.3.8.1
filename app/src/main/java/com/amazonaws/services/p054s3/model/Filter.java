package com.amazonaws.services.p054s3.model;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.Filter */
/* loaded from: classes2.dex */
public class Filter implements Serializable {
    private S3KeyFilter s3KeyFilter;

    public S3KeyFilter getS3KeyFilter() {
        return this.s3KeyFilter;
    }

    public void setS3KeyFilter(S3KeyFilter s3KeyFilter) {
        this.s3KeyFilter = s3KeyFilter;
    }

    public Filter withS3KeyFilter(S3KeyFilter s3KeyFilter) {
        setS3KeyFilter(s3KeyFilter);
        return this;
    }
}
