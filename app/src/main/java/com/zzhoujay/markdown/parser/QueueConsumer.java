package com.zzhoujay.markdown.parser;

/* loaded from: classes4.dex */
public interface QueueConsumer {

    /* loaded from: classes4.dex */
    public interface QueueProvider {
        LineQueue getQueue();
    }

    void setQueueProvider(QueueProvider queueProvider);
}
