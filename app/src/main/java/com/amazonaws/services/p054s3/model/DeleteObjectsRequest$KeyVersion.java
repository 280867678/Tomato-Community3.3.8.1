package com.amazonaws.services.p054s3.model;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.DeleteObjectsRequest$KeyVersion */
/* loaded from: classes2.dex */
public class DeleteObjectsRequest$KeyVersion implements Serializable {
    private final String key;
    private final String version;

    public DeleteObjectsRequest$KeyVersion(String str) {
        this(str, null);
    }

    public DeleteObjectsRequest$KeyVersion(String str, String str2) {
        this.key = str;
        this.version = str2;
    }

    public String getKey() {
        return this.key;
    }

    public String getVersion() {
        return this.version;
    }
}
