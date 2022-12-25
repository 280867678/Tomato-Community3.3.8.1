package io.reactivex.internal.fuseable;

/* loaded from: classes4.dex */
public interface QueueFuseable<T> extends SimpleQueue<T> {
    int requestFusion(int i);
}
