package com.amazonaws.services.p054s3.model.metrics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsConfiguration */
/* loaded from: classes2.dex */
public class MetricsConfiguration implements Serializable {
    private MetricsFilter filter;

    /* renamed from: id */
    private String f1191id;

    public String getId() {
        return this.f1191id;
    }

    public void setId(String str) {
        this.f1191id = str;
    }

    public MetricsConfiguration withId(String str) {
        setId(str);
        return this;
    }

    public MetricsFilter getFilter() {
        return this.filter;
    }

    public void setFilter(MetricsFilter metricsFilter) {
        this.filter = metricsFilter;
    }

    public MetricsConfiguration withFilter(MetricsFilter metricsFilter) {
        setFilter(metricsFilter);
        return this;
    }
}
