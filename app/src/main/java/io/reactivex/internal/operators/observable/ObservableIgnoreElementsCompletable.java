package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.fuseable.FuseToObservable;

/* loaded from: classes4.dex */
public final class ObservableIgnoreElementsCompletable<T> extends Completable implements FuseToObservable<T> {
    public ObservableIgnoreElementsCompletable(ObservableSource<T> observableSource) {
    }
}
