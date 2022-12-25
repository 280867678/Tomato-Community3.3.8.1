package kotlin.coroutines.experimental;

import kotlin.coroutines.experimental.CoroutineContext;

/* compiled from: ContinuationInterceptor.kt */
/* loaded from: classes4.dex */
public interface ContinuationInterceptor extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    <T> Coroutines<T> interceptContinuation(Coroutines<? super T> coroutines);

    /* compiled from: ContinuationInterceptor.kt */
    /* loaded from: classes4.dex */
    public static final class Key implements CoroutineContext.Key<ContinuationInterceptor> {
        static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }
}
