package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: ContinuationInterceptor.kt */
/* loaded from: classes4.dex */
public interface ContinuationInterceptor extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    <T> Continuation<T> interceptContinuation(Continuation<? super T> continuation);

    void releaseInterceptedContinuation(Continuation<?> continuation);

    /* compiled from: ContinuationInterceptor.kt */
    /* loaded from: classes4.dex */
    public static final class Key implements CoroutineContext.Key<ContinuationInterceptor> {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }
}
