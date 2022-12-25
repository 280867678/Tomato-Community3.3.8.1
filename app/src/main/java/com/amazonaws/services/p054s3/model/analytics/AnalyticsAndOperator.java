package com.amazonaws.services.p054s3.model.analytics;

import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.analytics.AnalyticsAndOperator */
/* loaded from: classes2.dex */
public class AnalyticsAndOperator extends AnalyticsNAryOperator {
    @Override // com.amazonaws.services.p054s3.model.analytics.AnalyticsNAryOperator
    public /* bridge */ /* synthetic */ List getOperands() {
        return super.getOperands();
    }

    public AnalyticsAndOperator(List<AnalyticsFilterPredicate> list) {
        super(list);
    }

    @Override // com.amazonaws.services.p054s3.model.analytics.AnalyticsFilterPredicate
    public void accept(AnalyticsPredicateVisitor analyticsPredicateVisitor) {
        analyticsPredicateVisitor.visit(this);
    }
}
