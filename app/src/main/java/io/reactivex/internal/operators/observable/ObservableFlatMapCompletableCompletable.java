package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.FuseToObservable;

/* loaded from: classes4.dex */
public final class ObservableFlatMapCompletableCompletable<T> extends Completable implements FuseToObservable<T> {
    public ObservableFlatMapCompletableCompletable(ObservableSource<T> observableSource, Function<? super T, ? extends CompletableSource> function, boolean z) {
    }
}
