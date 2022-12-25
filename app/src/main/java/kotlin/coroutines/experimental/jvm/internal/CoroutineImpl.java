package kotlin.coroutines.experimental.jvm.internal;

import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.Coroutines;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsJvm;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: CoroutineImpl.kt */
/* loaded from: classes4.dex */
public abstract class CoroutineImpl extends Lambda<Object> implements Coroutines<Object> {
    private final CoroutineContext _context;
    private Coroutines<Object> _facade;
    protected Coroutines<Object> completion;
    protected int label;

    protected abstract Object doResume(Object obj, Throwable th);

    public CoroutineImpl(int i, Coroutines<Object> coroutines) {
        super(i);
        this.completion = coroutines;
        this.label = this.completion != null ? 0 : -1;
        Coroutines<Object> coroutines2 = this.completion;
        this._context = coroutines2 != null ? coroutines2.getContext() : null;
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        if (coroutineContext != null) {
            return coroutineContext;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final Coroutines<Object> getFacade() {
        if (this._facade == null) {
            CoroutineContext coroutineContext = this._context;
            if (coroutineContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            this._facade = CoroutineIntrinsics.interceptContinuationIfNeeded(coroutineContext, this);
        }
        Coroutines<Object> coroutines = this._facade;
        if (coroutines != null) {
            return coroutines;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resume(Object obj) {
        Object coroutine_suspended;
        Coroutines<Object> coroutines = this.completion;
        if (coroutines == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        try {
            Object doResume = doResume(obj, null);
            coroutine_suspended = IntrinsicsJvm.getCOROUTINE_SUSPENDED();
            if (doResume == coroutine_suspended) {
                return;
            }
            if (coroutines == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }
            coroutines.resume(doResume);
        } catch (Throwable th) {
            coroutines.resumeWithException(th);
        }
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resumeWithException(Throwable exception) {
        Object coroutine_suspended;
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Coroutines<Object> coroutines = this.completion;
        if (coroutines == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        try {
            Object doResume = doResume(null, exception);
            coroutine_suspended = IntrinsicsJvm.getCOROUTINE_SUSPENDED();
            if (doResume == coroutine_suspended) {
                return;
            }
            if (coroutines == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }
            coroutines.resume(doResume);
        } catch (Throwable th) {
            coroutines.resumeWithException(th);
        }
    }

    public Coroutines<Unit> create(Coroutines<?> completion) {
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        throw new IllegalStateException("create(Continuation) has not been overridden");
    }

    public Coroutines<Unit> create(Object obj, Coroutines<?> completion) {
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        throw new IllegalStateException("create(Any?;Continuation) has not been overridden");
    }
}
