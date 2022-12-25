package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class ObservableGroupBy$State<T, K> extends AtomicInteger implements Disposable, ObservableSource<T> {
    private static final long serialVersionUID = -3852313036005250360L;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final K key;
    final ObservableGroupBy$GroupByObserver<?, K, T> parent;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicBoolean cancelled = new AtomicBoolean();
    final AtomicBoolean once = new AtomicBoolean();
    final AtomicReference<Observer<? super T>> actual = new AtomicReference<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public ObservableGroupBy$State(int i, ObservableGroupBy$GroupByObserver<?, K, T> observableGroupBy$GroupByObserver, K k, boolean z) {
        this.queue = new SpscLinkedArrayQueue<>(i);
        this.parent = observableGroupBy$GroupByObserver;
        this.key = k;
        this.delayError = z;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        if (!this.cancelled.compareAndSet(false, true) || getAndIncrement() != 0) {
            return;
        }
        this.actual.lazySet(null);
        this.parent.cancel(this.key);
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        return this.cancelled.get();
    }

    @Override // io.reactivex.ObservableSource
    public void subscribe(Observer<? super T> observer) {
        if (this.once.compareAndSet(false, true)) {
            observer.onSubscribe(this);
            this.actual.lazySet(observer);
            if (this.cancelled.get()) {
                this.actual.lazySet(null);
                return;
            } else {
                drain();
                return;
            }
        }
        EmptyDisposable.error(new IllegalStateException("Only one Observer allowed!"), observer);
    }

    public void onNext(T t) {
        this.queue.offer(t);
        drain();
    }

    public void onError(Throwable th) {
        this.error = th;
        this.done = true;
        drain();
    }

    public void onComplete() {
        this.done = true;
        drain();
    }

    void drain() {
        if (getAndIncrement() != 0) {
            return;
        }
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        boolean z = this.delayError;
        Observer<? super T> observer = this.actual.get();
        int i = 1;
        while (true) {
            if (observer != null) {
                while (true) {
                    boolean z2 = this.done;
                    Object obj = (T) spscLinkedArrayQueue.mo6754poll();
                    boolean z3 = obj == null;
                    if (checkTerminated(z2, z3, observer, z)) {
                        return;
                    }
                    if (z3) {
                        break;
                    }
                    observer.onNext(obj);
                }
            }
            i = addAndGet(-i);
            if (i == 0) {
                return;
            }
            if (observer == null) {
                observer = this.actual.get();
            }
        }
    }

    boolean checkTerminated(boolean z, boolean z2, Observer<? super T> observer, boolean z3) {
        if (this.cancelled.get()) {
            this.queue.clear();
            this.parent.cancel(this.key);
            this.actual.lazySet(null);
            return true;
        } else if (!z) {
            return false;
        } else {
            if (z3) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                this.actual.lazySet(null);
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                this.actual.lazySet(null);
                observer.onError(th2);
                return true;
            } else if (!z2) {
                return false;
            } else {
                this.actual.lazySet(null);
                observer.onComplete();
                return true;
            }
        }
    }
}
