package kotlin.coroutines.experimental.jvm.internal;

import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.Coroutines;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CoroutineIntrinsics.kt */
/* loaded from: classes4.dex */
public final class CoroutineIntrinsics {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Coroutines<T> interceptContinuationIfNeeded(CoroutineContext context, Coroutines<? super T> continuation) {
        Coroutines<T> interceptContinuation;
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) context.get(ContinuationInterceptor.Key);
        return (continuationInterceptor == null || (interceptContinuation = continuationInterceptor.interceptContinuation(continuation)) == null) ? continuation : interceptContinuation;
    }
}
