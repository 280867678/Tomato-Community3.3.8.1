package com.amazonaws.services.p054s3.model;

/* renamed from: com.amazonaws.services.s3.model.ReplicationDestinationConfig */
/* loaded from: classes2.dex */
public class ReplicationDestinationConfig {
    public void setStorageClass(String str) {
    }

    public void setBucketARN(String str) {
        if (str != null) {
            return;
        }
        throw new IllegalArgumentException("Bucket name cannot be null");
    }
}
