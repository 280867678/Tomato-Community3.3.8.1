package kotlin;

import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Exceptions.kt */
/* loaded from: classes4.dex */
public class ExceptionsKt__ExceptionsKt {
    public static void addSuppressed(Throwable addSuppressed, Throwable exception) {
        Intrinsics.checkParameterIsNotNull(addSuppressed, "$this$addSuppressed");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(addSuppressed, exception);
    }
}
