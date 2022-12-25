package io.reactivex.parallel;

import io.reactivex.functions.BiFunction;

/* loaded from: classes4.dex */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    @Override // io.reactivex.functions.BiFunction
    /* renamed from: apply  reason: avoid collision after fix types in other method */
    public ParallelFailureHandling mo6745apply(Long l, Throwable th) {
        return this;
    }
}
