package com.amazonaws.services.p054s3.model.lifecycle;

import com.amazonaws.services.p054s3.model.Tag;

/* renamed from: com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate */
/* loaded from: classes2.dex */
public final class LifecycleTagPredicate extends LifecycleFilterPredicate {
    private final Tag tag;

    public LifecycleTagPredicate(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return this.tag;
    }

    @Override // com.amazonaws.services.p054s3.model.lifecycle.LifecycleFilterPredicate
    public void accept(LifecyclePredicateVisitor lifecyclePredicateVisitor) {
        lifecyclePredicateVisitor.visit(this);
    }
}
