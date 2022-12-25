package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.observables.GroupedObservable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class ObservableGroupBy$GroupedUnicast<K, T> extends GroupedObservable<K, T> {
    final ObservableGroupBy$State<T, K> state;

    public static <T, K> ObservableGroupBy$GroupedUnicast<K, T> createWith(K k, int i, ObservableGroupBy$GroupByObserver<?, K, T> observableGroupBy$GroupByObserver, boolean z) {
        return new ObservableGroupBy$GroupedUnicast<>(k, new ObservableGroupBy$State(i, observableGroupBy$GroupByObserver, k, z));
    }

    protected ObservableGroupBy$GroupedUnicast(K k, ObservableGroupBy$State<T, K> observableGroupBy$State) {
        super(k);
        this.state = observableGroupBy$State;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.state.subscribe(observer);
    }

    public void onNext(T t) {
        this.state.onNext(t);
    }

    public void onError(Throwable th) {
        this.state.onError(th);
    }

    public void onComplete() {
        this.state.onComplete();
    }
}
