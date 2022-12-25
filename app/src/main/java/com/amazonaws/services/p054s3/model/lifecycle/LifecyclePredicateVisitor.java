package com.amazonaws.services.p054s3.model.lifecycle;

/* renamed from: com.amazonaws.services.s3.model.lifecycle.LifecyclePredicateVisitor */
/* loaded from: classes2.dex */
public interface LifecyclePredicateVisitor {
    void visit(LifecycleAndOperator lifecycleAndOperator);

    void visit(LifecyclePrefixPredicate lifecyclePrefixPredicate);

    void visit(LifecycleTagPredicate lifecycleTagPredicate);
}
