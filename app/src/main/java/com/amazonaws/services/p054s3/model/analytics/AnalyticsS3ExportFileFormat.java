package com.amazonaws.services.p054s3.model.analytics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.analytics.AnalyticsS3ExportFileFormat */
/* loaded from: classes2.dex */
public enum AnalyticsS3ExportFileFormat implements Serializable {
    CSV("CSV");
    
    private final String format;

    AnalyticsS3ExportFileFormat(String str) {
        this.format = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.format;
    }
}
