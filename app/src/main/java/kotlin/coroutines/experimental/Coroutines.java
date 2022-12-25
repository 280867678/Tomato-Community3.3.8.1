package kotlin.coroutines.experimental;

/* renamed from: kotlin.coroutines.experimental.Continuation */
/* loaded from: classes4.dex */
public interface Coroutines<T> {
    CoroutineContext getContext();

    void resume(T t);

    void resumeWithException(Throwable th);
}
