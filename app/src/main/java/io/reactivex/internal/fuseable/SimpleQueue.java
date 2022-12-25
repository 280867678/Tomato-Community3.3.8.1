package io.reactivex.internal.fuseable;

/* loaded from: classes4.dex */
public interface SimpleQueue<T> {
    void clear();

    boolean isEmpty();

    boolean offer(T t);

    /* renamed from: poll */
    T mo6754poll() throws Exception;
}
