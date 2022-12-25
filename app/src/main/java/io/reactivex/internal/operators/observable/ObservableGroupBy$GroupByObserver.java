package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observables.GroupedObservable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public final class ObservableGroupBy$GroupByObserver<T, K, V> extends AtomicInteger implements Observer<T>, Disposable {
    static final Object NULL_KEY = new Object();
    private static final long serialVersionUID = -3688291656102519502L;
    final int bufferSize;
    final boolean delayError;
    final Observer<? super GroupedObservable<K, V>> downstream;
    final Function<? super T, ? extends K> keySelector;
    Disposable upstream;
    final Function<? super T, ? extends V> valueSelector;
    final AtomicBoolean cancelled = new AtomicBoolean();
    final Map<Object, ObservableGroupBy$GroupedUnicast<K, V>> groups = new ConcurrentHashMap();

    public ObservableGroupBy$GroupByObserver(Observer<? super GroupedObservable<K, V>> observer, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z) {
        this.downstream = observer;
        this.keySelector = function;
        this.valueSelector = function2;
        this.bufferSize = i;
        this.delayError = z;
        lazySet(1);
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.upstream, disposable)) {
            this.upstream = disposable;
            this.downstream.onSubscribe(this);
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        try {
            K mo6755apply = this.keySelector.mo6755apply(t);
            Object obj = mo6755apply != null ? mo6755apply : NULL_KEY;
            ObservableGroupBy$GroupedUnicast observableGroupBy$GroupedUnicast = this.groups.get(obj);
            if (observableGroupBy$GroupedUnicast == null) {
                if (this.cancelled.get()) {
                    return;
                }
                observableGroupBy$GroupedUnicast = ObservableGroupBy$GroupedUnicast.createWith(mo6755apply, this.bufferSize, this, this.delayError);
                this.groups.put(obj, observableGroupBy$GroupedUnicast);
                getAndIncrement();
                this.downstream.onNext(observableGroupBy$GroupedUnicast);
            }
            try {
                V mo6755apply2 = this.valueSelector.mo6755apply(t);
                ObjectHelper.requireNonNull(mo6755apply2, "The value supplied is null");
                observableGroupBy$GroupedUnicast.onNext(mo6755apply2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            this.upstream.dispose();
            onError(th2);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ArrayList<ObservableGroupBy$GroupedUnicast> arrayList = new ArrayList(this.groups.values());
        this.groups.clear();
        for (ObservableGroupBy$GroupedUnicast observableGroupBy$GroupedUnicast : arrayList) {
            observableGroupBy$GroupedUnicast.onError(th);
        }
        this.downstream.onError(th);
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        ArrayList<ObservableGroupBy$GroupedUnicast> arrayList = new ArrayList(this.groups.values());
        this.groups.clear();
        for (ObservableGroupBy$GroupedUnicast observableGroupBy$GroupedUnicast : arrayList) {
            observableGroupBy$GroupedUnicast.onComplete();
        }
        this.downstream.onComplete();
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        if (!this.cancelled.compareAndSet(false, true) || decrementAndGet() != 0) {
            return;
        }
        this.upstream.dispose();
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.cancelled.get();
    }

    public void cancel(K k) {
        if (k == null) {
            k = (K) NULL_KEY;
        }
        this.groups.remove(k);
        if (decrementAndGet() == 0) {
            this.upstream.dispose();
        }
    }
}
