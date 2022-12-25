package com.trello.rxlifecycle2;

import com.trello.rxlifecycle2.internal.Preconditions;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public final class LifecycleTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {
    final Observable<?> observable;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LifecycleTransformer(Observable<?> observable) {
        Preconditions.checkNotNull(observable, "observable == null");
        this.observable = observable;
    }

    @Override // io.reactivex.ObservableTransformer
    public ObservableSource<T> apply(Observable<T> observable) {
        return observable.takeUntil(this.observable);
    }

    public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.takeUntil(this.observable.toFlowable(BackpressureStrategy.LATEST));
    }

    public SingleSource<T> apply(Single<T> single) {
        return single.takeUntil(this.observable.firstOrError());
    }

    public MaybeSource<T> apply(Maybe<T> maybe) {
        return maybe.takeUntil(this.observable.firstElement());
    }

    public CompletableSource apply(Completable completable) {
        return Completable.ambArray(completable, this.observable.flatMapCompletable(Functions.CANCEL_COMPLETABLE));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && LifecycleTransformer.class == obj.getClass()) {
            return this.observable.equals(((LifecycleTransformer) obj).observable);
        }
        return false;
    }

    public int hashCode() {
        return this.observable.hashCode();
    }

    public String toString() {
        return "LifecycleTransformer{observable=" + this.observable + '}';
    }
}
