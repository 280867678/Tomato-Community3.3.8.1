package com.amazonaws.services.p054s3.model;

import java.util.Date;

/* renamed from: com.amazonaws.services.s3.model.S3ObjectSummary */
/* loaded from: classes2.dex */
public class S3ObjectSummary {
    protected String bucketName;
    protected String eTag;
    protected String key;
    protected Date lastModified;
    protected Owner owner;
    protected long size;
    protected String storageClass;

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setETag(String str) {
        this.eTag = str;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public void setLastModified(Date date) {
        this.lastModified = date;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setStorageClass(String str) {
        this.storageClass = str;
    }

    public String toString() {
        return "S3ObjectSummary{bucketName='" + this.bucketName + "', key='" + this.key + "', eTag='" + this.eTag + "', size=" + this.size + ", lastModified=" + this.lastModified + ", storageClass='" + this.storageClass + "', owner=" + this.owner + '}';
    }
}
