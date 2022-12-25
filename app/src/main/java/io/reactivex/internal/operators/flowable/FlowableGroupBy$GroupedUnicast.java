package io.reactivex.internal.operators.flowable;

import io.reactivex.flowables.GroupedFlowable;
import org.reactivestreams.Subscriber;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class FlowableGroupBy$GroupedUnicast<K, T> extends GroupedFlowable<K, T> {
    final FlowableGroupBy$State<T, K> state;

    public static <T, K> FlowableGroupBy$GroupedUnicast<K, T> createWith(K k, int i, FlowableGroupBy$GroupBySubscriber<?, K, T> flowableGroupBy$GroupBySubscriber, boolean z) {
        return new FlowableGroupBy$GroupedUnicast<>(k, new FlowableGroupBy$State(i, flowableGroupBy$GroupBySubscriber, k, z));
    }

    protected FlowableGroupBy$GroupedUnicast(K k, FlowableGroupBy$State<T, K> flowableGroupBy$State) {
        super(k);
        this.state = flowableGroupBy$State;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.state.subscribe(subscriber);
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
