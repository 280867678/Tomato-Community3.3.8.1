package com.amazonaws.services.p054s3.model.lifecycle;

import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.lifecycle.LifecycleNAryOperator */
/* loaded from: classes2.dex */
abstract class LifecycleNAryOperator extends LifecycleFilterPredicate {
    private final List<LifecycleFilterPredicate> operands;

    public LifecycleNAryOperator(List<LifecycleFilterPredicate> list) {
        this.operands = list;
    }

    public List<LifecycleFilterPredicate> getOperands() {
        return this.operands;
    }
}
