package com.amazonaws.services.p054s3.model.metrics;

import com.amazonaws.services.p054s3.model.Tag;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsTagPredicate */
/* loaded from: classes2.dex */
public final class MetricsTagPredicate extends MetricsFilterPredicate {
    private final Tag tag;

    public MetricsTagPredicate(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return this.tag;
    }

    @Override // com.amazonaws.services.p054s3.model.metrics.MetricsFilterPredicate
    public void accept(MetricsPredicateVisitor metricsPredicateVisitor) {
        metricsPredicateVisitor.visit(this);
    }
}
