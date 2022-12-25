package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: kotlin.coroutines.jvm.internal.DebugProbesKt */
/* loaded from: classes4.dex */
public final class DebugProbes {
    public static final void probeCoroutineResumed(Continuation<?> frame) {
        Intrinsics.checkParameterIsNotNull(frame, "frame");
    }
}
