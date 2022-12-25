package com.amazonaws.services.p054s3.model.lifecycle;

/* renamed from: com.amazonaws.services.s3.model.lifecycle.LifecyclePrefixPredicate */
/* loaded from: classes2.dex */
public final class LifecyclePrefixPredicate extends LifecycleFilterPredicate {
    private final String prefix;

    public LifecyclePrefixPredicate(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override // com.amazonaws.services.p054s3.model.lifecycle.LifecycleFilterPredicate
    public void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor) {
        lifecyclePredicateVisitor.visit(this);
    }
}
