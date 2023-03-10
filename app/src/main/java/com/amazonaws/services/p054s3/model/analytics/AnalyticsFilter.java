package com.amazonaws.services.p054s3.model.analytics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.analytics.AnalyticsFilter */
/* loaded from: classes2.dex */
public class AnalyticsFilter implements Serializable {
    private AnalyticsFilterPredicate predicate;

    public AnalyticsFilter() {
    }

    public AnalyticsFilter(AnalyticsFilterPredicate analyticsFilterPredicate) {
        this.predicate = analyticsFilterPredicate;
    }

    public AnalyticsFilterPredicate getPredicate() {
        return this.predicate;
    }

    public void setPredicate(AnalyticsFilterPredicate analyticsFilterPredicate) {
        this.predicate = analyticsFilterPredicate;
    }

    public AnalyticsFilter withPredicate(AnalyticsFilterPredicate analyticsFilterPredicate) {
        setPredicate(analyticsFilterPredicate);
        return this;
    }
}
