package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public final class FlowableGroupBy$GroupBySubscriber<T, K, V> extends BasicIntQueueSubscription<GroupedFlowable<K, V>> implements FlowableSubscriber<T> {
    static final Object NULL_KEY = new Object();
    private static final long serialVersionUID = -3688291656102519502L;
    final int bufferSize;
    final boolean delayError;
    boolean done;
    final Subscriber<? super GroupedFlowable<K, V>> downstream;
    Throwable error;
    final Queue<FlowableGroupBy$GroupedUnicast<K, V>> evictedGroups;
    volatile boolean finished;
    final Map<Object, FlowableGroupBy$GroupedUnicast<K, V>> groups;
    final Function<? super T, ? extends K> keySelector;
    boolean outputFused;
    final SpscLinkedArrayQueue<GroupedFlowable<K, V>> queue;
    Subscription upstream;
    final Function<? super T, ? extends V> valueSelector;
    final AtomicBoolean cancelled = new AtomicBoolean();
    final AtomicLong requested = new AtomicLong();
    final AtomicInteger groupCount = new AtomicInteger(1);

    public FlowableGroupBy$GroupBySubscriber(Subscriber<? super GroupedFlowable<K, V>> subscriber, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i, boolean z, Map<Object, FlowableGroupBy$GroupedUnicast<K, V>> map, Queue<FlowableGroupBy$GroupedUnicast<K, V>> queue) {
        this.downstream = subscriber;
        this.keySelector = function;
        this.valueSelector = function2;
        this.bufferSize = i;
        this.delayError = z;
        this.groups = map;
        this.evictedGroups = queue;
        this.queue = new SpscLinkedArrayQueue<>(i);
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.upstream, subscription)) {
            this.upstream = subscription;
            this.downstream.onSubscribe(this);
            subscription.request(this.bufferSize);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.done) {
            return;
        }
        SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
        try {
            K mo6755apply = this.keySelector.mo6755apply(t);
            boolean z = false;
            Object obj = mo6755apply != null ? mo6755apply : NULL_KEY;
            FlowableGroupBy$GroupedUnicast flowableGroupBy$GroupedUnicast = this.groups.get(obj);
            if (flowableGroupBy$GroupedUnicast == null) {
                if (this.cancelled.get()) {
                    return;
                }
                flowableGroupBy$GroupedUnicast = FlowableGroupBy$GroupedUnicast.createWith(mo6755apply, this.bufferSize, this, this.delayError);
                this.groups.put(obj, flowableGroupBy$GroupedUnicast);
                this.groupCount.getAndIncrement();
                z = true;
            }
            try {
                V mo6755apply2 = this.valueSelector.mo6755apply(t);
                ObjectHelper.requireNonNull(mo6755apply2, "The valueSelector returned null");
                flowableGroupBy$GroupedUnicast.onNext(mo6755apply2);
                completeEvictions();
                if (!z) {
                    return;
                }
                spscLinkedArrayQueue.offer(flowableGroupBy$GroupedUnicast);
                drain();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            this.upstream.cancel();
            onError(th2);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.done = true;
        for (FlowableGroupBy$GroupedUnicast<K, V> flowableGroupBy$GroupedUnicast : this.groups.values()) {
            flowableGroupBy$GroupedUnicast.onError(th);
        }
        this.groups.clear();
        Queue<FlowableGroupBy$GroupedUnicast<K, V>> queue = this.evictedGroups;
        if (queue != null) {
            queue.clear();
        }
        this.error = th;
        this.finished = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.done) {
            for (FlowableGroupBy$GroupedUnicast<K, V> flowableGroupBy$GroupedUnicast : this.groups.values()) {
                flowableGroupBy$GroupedUnicast.onComplete();
            }
            this.groups.clear();
            Queue<FlowableGroupBy$GroupedUnicast<K, V>> queue = this.evictedGroups;
            if (queue != null) {
                queue.clear();
            }
            this.done = true;
            this.finished = true;
            drain();
        }
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
            completeEvictions();
            if (this.groupCount.decrementAndGet() != 0) {
                return;
            }
            this.upstream.cancel();
        }
    }

    private void completeEvictions() {
        if (this.evictedGroups != null) {
            int i = 0;
            while (true) {
                FlowableGroupBy$GroupedUnicast<K, V> poll = this.evictedGroups.poll();
                if (poll == null) {
                    break;
                }
                poll.onComplete();
                i++;
            }
            if (i == 0) {
                return;
            }
            this.groupCount.addAndGet(-i);
        }
    }

    public void cancel(K k) {
        if (k == null) {
            k = (K) NULL_KEY;
        }
        this.groups.remove(k);
        if (this.groupCount.decrementAndGet() == 0) {
            this.upstream.cancel();
            if (getAndIncrement() != 0) {
                return;
            }
            this.queue.clear();
        }
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
        SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
        Subscriber<? super GroupedFlowable<K, V>> subscriber = this.downstream;
        int i = 1;
        while (!this.cancelled.get()) {
            boolean z = this.finished;
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
            i = addAndGet(-i);
            if (i == 0) {
                return;
            }
        }
        spscLinkedArrayQueue.clear();
    }

    void drainNormal() {
        int i;
        SpscLinkedArrayQueue<GroupedFlowable<K, V>> spscLinkedArrayQueue = this.queue;
        Subscriber<? super GroupedFlowable<K, V>> subscriber = this.downstream;
        int i2 = 1;
        do {
            long j = this.requested.get();
            long j2 = 0;
            while (true) {
                i = (j2 > j ? 1 : (j2 == j ? 0 : -1));
                if (i == 0) {
                    break;
                }
                boolean z = this.finished;
                GroupedFlowable<K, V> mo6754poll = spscLinkedArrayQueue.mo6754poll();
                boolean z2 = mo6754poll == null;
                if (checkTerminated(z, z2, subscriber, spscLinkedArrayQueue)) {
                    return;
                }
                if (z2) {
                    break;
                }
                subscriber.onNext(mo6754poll);
                j2++;
            }
            if (i == 0 && checkTerminated(this.finished, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                return;
            }
            if (j2 != 0) {
                if (j != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j2);
                }
                this.upstream.request(j2);
            }
            i2 = addAndGet(-i2);
        } while (i2 != 0);
    }

    boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
        if (this.cancelled.get()) {
            spscLinkedArrayQueue.clear();
            return true;
        } else if (this.delayError) {
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
            return true;
        } else if (!z) {
            return false;
        } else {
            Throwable th2 = this.error;
            if (th2 != null) {
                spscLinkedArrayQueue.clear();
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
    public GroupedFlowable<K, V> mo6754poll() {
        return this.queue.mo6754poll();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        this.queue.clear();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
