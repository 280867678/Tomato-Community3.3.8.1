package com.amazonaws.services.p054s3.model.analytics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.analytics.AnalyticsConfiguration */
/* loaded from: classes2.dex */
public class AnalyticsConfiguration implements Serializable {
    private AnalyticsFilter filter;

    /* renamed from: id */
    private String f1189id;
    private StorageClassAnalysis storageClassAnalysis;

    public String getId() {
        return this.f1189id;
    }

    public void setId(String str) {
        this.f1189id = str;
    }

    public AnalyticsConfiguration withId(String str) {
        setId(str);
        return this;
    }

    public AnalyticsFilter getFilter() {
        return this.filter;
    }

    public void setFilter(AnalyticsFilter analyticsFilter) {
        this.filter = analyticsFilter;
    }

    public AnalyticsConfiguration withFilter(AnalyticsFilter analyticsFilter) {
        setFilter(analyticsFilter);
        return this;
    }

    public StorageClassAnalysis getStorageClassAnalysis() {
        return this.storageClassAnalysis;
    }

    public void setStorageClassAnalysis(StorageClassAnalysis storageClassAnalysis) {
        this.storageClassAnalysis = storageClassAnalysis;
    }

    public AnalyticsConfiguration withStorageClassAnalysis(StorageClassAnalysis storageClassAnalysis) {
        setStorageClassAnalysis(storageClassAnalysis);
        return this;
    }
}
