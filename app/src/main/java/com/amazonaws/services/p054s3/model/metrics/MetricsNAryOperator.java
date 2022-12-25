package com.amazonaws.services.p054s3.model.metrics;

import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsNAryOperator */
/* loaded from: classes2.dex */
abstract class MetricsNAryOperator extends MetricsFilterPredicate {
    private final List<MetricsFilterPredicate> operands;

    public MetricsNAryOperator(List<MetricsFilterPredicate> list) {
        this.operands = list;
    }

    public List<MetricsFilterPredicate> getOperands() {
        return this.operands;
    }
}
