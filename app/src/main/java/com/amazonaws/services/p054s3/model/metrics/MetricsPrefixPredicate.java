package com.amazonaws.services.p054s3.model.metrics;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsPrefixPredicate */
/* loaded from: classes2.dex */
public final class MetricsPrefixPredicate extends MetricsFilterPredicate {
    private final String prefix;

    public MetricsPrefixPredicate(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override // com.amazonaws.services.p054s3.model.metrics.MetricsFilterPredicate
    public void accept(MetricsPredicateVisitor metricsPredicateVisitor) {
        metricsPredicateVisitor.visit(this);
    }
}
