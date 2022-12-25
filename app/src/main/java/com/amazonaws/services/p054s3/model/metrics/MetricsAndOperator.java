package com.amazonaws.services.p054s3.model.metrics;

import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsAndOperator */
/* loaded from: classes2.dex */
public final class MetricsAndOperator extends MetricsNAryOperator {
    @Override // com.amazonaws.services.p054s3.model.metrics.MetricsNAryOperator
    public /* bridge */ /* synthetic */ List getOperands() {
        return super.getOperands();
    }

    public MetricsAndOperator(List<MetricsFilterPredicate> list) {
        super(list);
    }

    @Override // com.amazonaws.services.p054s3.model.metrics.MetricsFilterPredicate
    public void accept(MetricsPredicateVisitor metricsPredicateVisitor) {
        metricsPredicateVisitor.visit(this);
    }
}
