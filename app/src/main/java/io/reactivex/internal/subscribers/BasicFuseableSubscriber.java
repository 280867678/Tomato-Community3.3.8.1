package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public abstract class BasicFuseableSubscriber<T, R> implements FlowableSubscriber<T>, QueueSubscription<R> {
    protected boolean done;
    protected final Subscriber<? super R> downstream;

    /* renamed from: qs */
    protected QueueSubscription<T> f6009qs;
    protected int sourceMode;
    protected Subscription upstream;

    protected void afterDownstream() {
    }

    protected boolean beforeDownstream() {
        return true;
    }

    public BasicFuseableSubscriber(Subscriber<? super R> subscriber) {
        this.downstream = subscriber;
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.upstream, subscription)) {
            this.upstream = subscription;
            if (subscription instanceof QueueSubscription) {
                this.f6009qs = (QueueSubscription) subscription;
            }
            if (!beforeDownstream()) {
                return;
            }
            this.downstream.onSubscribe(this);
            afterDownstream();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.done = true;
        this.downstream.onError(th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void fail(Throwable th) {
        Exceptions.throwIfFatal(th);
        this.upstream.cancel();
        onError(th);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        this.downstream.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int transitiveBoundaryFusion(int i) {
        QueueSubscription<T> queueSubscription = this.f6009qs;
        if (queueSubscription == null || (i & 4) != 0) {
            return 0;
        }
        int requestFusion = queueSubscription.requestFusion(i);
        if (requestFusion != 0) {
            this.sourceMode = requestFusion;
        }
        return requestFusion;
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        this.upstream.request(j);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.upstream.cancel();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return this.f6009qs.isEmpty();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        this.f6009qs.clear();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(R r) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
