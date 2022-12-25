package kotlin;

import kotlin.Result;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Result.kt */
/* loaded from: classes4.dex */
public final class ResultKt {
    public static final Object createFailure(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        return new Result.Failure(exception);
    }

    public static final void throwOnFailure(Object obj) {
        if (!(obj instanceof Result.Failure)) {
            return;
        }
        throw ((Result.Failure) obj).exception;
    }
}
