package com.amazonaws.services.p054s3.model.metrics;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.metrics.MetricsFilterPredicate */
/* loaded from: classes2.dex */
public abstract class MetricsFilterPredicate implements Serializable {
    public abstract void accept(MetricsPredicateVisitor metricsPredicateVisitor);
}
