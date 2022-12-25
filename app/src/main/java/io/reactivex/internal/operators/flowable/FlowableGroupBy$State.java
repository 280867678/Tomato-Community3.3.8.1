package io.reactivex.internal.operators.flowable;

import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class FlowableGroupBy$State<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
    private static final long serialVersionUID = -3852313036005250360L;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final K key;
    boolean outputFused;
    final FlowableGroupBy$GroupBySubscriber<?, K, T> parent;
    int produced;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicLong requested = new AtomicLong();
    final AtomicBoolean cancelled = new AtomicBoolean();
    final AtomicReference<Subscriber<? super T>> actual = new AtomicReference<>();
    final AtomicBoolean once = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: package-private */
    public FlowableGroupBy$State(int i, FlowableGroupBy$GroupBySubscriber<?, K, T> flowableGroupBy$GroupBySubscriber, K k, boolean z) {
        this.queue = new SpscLinkedArrayQueue<>(i);
        this.parent = flowableGroupBy$GroupBySubscriber;
        this.key = k;
        this.delayError = z;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (SubscriptionHelper.validate(j)) {
            BackpressureHelper.add(this.requested, j);
            drain();
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        if (this.cancelled.compareAndSet(false, true)) {
            this.parent.cancel(this.key);
        }
    }

    @Override // org.reactivestreams.Publisher
    public void subscribe(Subscriber<? super T> subscriber) {
        if (this.once.compareAndSet(false, true)) {
            subscriber.onSubscribe(this);
            this.actual.lazySet(subscriber);
            drain();
            return;
        }
        EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), subscriber);
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
        if (this.outputFused) {
            drainFused();
        } else {
            drainNormal();
        }
    }

    void drainFused() {
        Throwable th;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        Subscriber<? super T> subscriber = this.actual.get();
        int i = 1;
        while (true) {
            if (subscriber != null) {
                if (this.cancelled.get()) {
                    spscLinkedArrayQueue.clear();
                    return;
                }
                boolean z = this.done;
                if (z && !this.delayError && (th = this.error) != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return;
                }
                subscriber.onNext(null);
                if (z) {
                    Throwable th2 = this.error;
                    if (th2 != null) {
                        subscriber.onError(th2);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
            }
            i = addAndGet(-i);
            if (i == 0) {
                return;
            }
            if (subscriber == null) {
                subscriber = this.actual.get();
            }
        }
    }

    void drainNormal() {
        int i;
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        boolean z = this.delayError;
        Subscriber<? super T> subscriber = this.actual.get();
        int i2 = 1;
        while (true) {
            if (subscriber != null) {
                long j = this.requested.get();
                long j2 = 0;
                while (true) {
                    i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                    if (i == 0) {
                        break;
                    }
                    boolean z2 = this.done;
                    Object obj = (T) spscLinkedArrayQueue.mo6754poll();
                    boolean z3 = obj == null;
                    if (checkTerminated(z2, z3, subscriber, z)) {
                        return;
                    }
                    if (z3) {
                        break;
                    }
                    subscriber.onNext(obj);
                    j2++;
                }
                if (i == 0 && checkTerminated(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, z)) {
                    return;
                }
                if (j2 != 0) {
                    if (j != Long.MAX_VALUE) {
                        this.requested.addAndGet(-j2);
                    }
                    this.parent.upstream.request(j2);
                }
            }
            i2 = addAndGet(-i2);
            if (i2 == 0) {
                return;
            }
            if (subscriber == null) {
                subscriber = this.actual.get();
            }
        }
    }

    boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber, boolean z3) {
        if (this.cancelled.get()) {
            this.queue.clear();
            return true;
        } else if (!z) {
            return false;
        } else {
            if (z3) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    subscriber.onError(th);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                this.queue.clear();
                subscriber.onError(th2);
                return true;
            } else if (!z2) {
                return false;
            } else {
                subscriber.onComplete();
                return true;
            }
        }
    }

    @Override // io.reactivex.internal.fuseable.QueueFuseable
    public int requestFusion(int i) {
        if ((i & 2) != 0) {
            this.outputFused = true;
            return 2;
        }
        return 0;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    /* renamed from: poll */
    public T mo6754poll() {
        T mo6754poll = this.queue.mo6754poll();
        if (mo6754poll != null) {
            this.produced++;
            return mo6754poll;
        }
        int i = this.produced;
        if (i == 0) {
            return null;
        }
        this.produced = 0;
        this.parent.upstream.request(i);
        return null;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        this.queue.clear();
    }
}
