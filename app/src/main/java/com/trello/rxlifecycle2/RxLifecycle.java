package com.trello.rxlifecycle2;

import com.trello.rxlifecycle2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
public class RxLifecycle {
    private RxLifecycle() {
        throw new AssertionError("No instances");
    }

    public static <T, R> LifecycleTransformer<T> bindUntilEvent(Observable<R> observable, R r) {
        Preconditions.checkNotNull(observable, "lifecycle == null");
        Preconditions.checkNotNull(r, "event == null");
        return bind(takeUntilEvent(observable, r));
    }

    private static <R> Observable<R> takeUntilEvent(Observable<R> observable, final R r) {
        return observable.filter(new Predicate<R>() { // from class: com.trello.rxlifecycle2.RxLifecycle.1
            @Override // io.reactivex.functions.Predicate
            public boolean test(R r2) throws Exception {
                return r2.equals(r);
            }
        });
    }

    public static <T, R> LifecycleTransformer<T> bind(Observable<R> observable) {
        return new LifecycleTransformer<>(observable);
    }

    public static <T, R> LifecycleTransformer<T> bind(Observable<R> observable, Function<R, R> function) {
        Preconditions.checkNotNull(observable, "lifecycle == null");
        Preconditions.checkNotNull(function, "correspondingEvents == null");
        return bind(takeUntilCorrespondingEvent(observable.share(), function));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <R> Observable<Boolean> takeUntilCorrespondingEvent(Observable<R> observable, Function<R, R> function) {
        return Observable.combineLatest(observable.take(1L).map(function), observable.skip(1L), new BiFunction<R, R, Boolean>() { // from class: com.trello.rxlifecycle2.RxLifecycle.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // io.reactivex.functions.BiFunction
            /* renamed from: apply */
            public Boolean mo6745apply(R r, R r2) throws Exception {
                return Boolean.valueOf(r2.equals(r));
            }
        }).onErrorReturn(Functions.RESUME_FUNCTION).filter(Functions.SHOULD_COMPLETE);
    }
}
