package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;

/* loaded from: classes2.dex */
public class ThreadHandoffProducer<T> implements Producer<T> {
    private final Producer<T> mInputProducer;
    private final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ThreadHandoffProducer(Producer<T> producer, ThreadHandoffProducerQueue threadHandoffProducerQueue) {
        Preconditions.checkNotNull(producer);
        this.mInputProducer = producer;
        this.mThreadHandoffProducerQueue = threadHandoffProducerQueue;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(final Consumer<T> consumer, final ProducerContext producerContext) {
        final ProducerListener listener = producerContext.getListener();
        final String id = producerContext.getId();
        final StatefulProducerRunnable<T> statefulProducerRunnable = new StatefulProducerRunnable<T>(consumer, listener, "BackgroundThreadHandoffProducer", id) { // from class: com.facebook.imagepipeline.producers.ThreadHandoffProducer.1
            @Override // com.facebook.common.executors.StatefulRunnable
            protected void disposeResult(T t) {
            }

            @Override // com.facebook.common.executors.StatefulRunnable
            /* renamed from: getResult */
            protected T mo5961getResult() throws Exception {
                return null;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.StatefulProducerRunnable, com.facebook.common.executors.StatefulRunnable
            public void onSuccess(T t) {
                listener.onProducerFinishWithSuccess(id, "BackgroundThreadHandoffProducer", null);
                ThreadHandoffProducer.this.mInputProducer.produceResults(consumer, producerContext);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.ThreadHandoffProducer.2
            @Override // com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                statefulProducerRunnable.cancel();
                ThreadHandoffProducer.this.mThreadHandoffProducerQueue.remove(statefulProducerRunnable);
            }
        });
        this.mThreadHandoffProducerQueue.addToQueueOrExecute(statefulProducerRunnable);
    }
}
