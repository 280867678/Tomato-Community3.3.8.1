package com.amazonaws.services.p054s3.model.analytics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.analytics.StorageClassAnalysisSchemaVersion */
/* loaded from: classes2.dex */
public enum StorageClassAnalysisSchemaVersion implements Serializable {
    V_1("V_1");
    
    private final String version;

    StorageClassAnalysisSchemaVersion(String str) {
        this.version = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.version;
    }
}
