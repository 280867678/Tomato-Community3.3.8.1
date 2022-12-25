package com.amazonaws.services.p054s3.model.analytics;

/* renamed from: com.amazonaws.services.s3.model.analytics.AnalyticsPredicateVisitor */
/* loaded from: classes2.dex */
public interface AnalyticsPredicateVisitor {
    void visit(AnalyticsAndOperator analyticsAndOperator);

    void visit(AnalyticsPrefixPredicate analyticsPrefixPredicate);

    void visit(AnalyticsTagPredicate analyticsTagPredicate);
}
