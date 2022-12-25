package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: kotlin.NotImplementedError */
/* loaded from: classes4.dex */
public final class Standard extends Error {
    public Standard() {
        this(null, 1, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Standard(String message) {
        super(message);
        Intrinsics.checkParameterIsNotNull(message, "message");
    }

    public /* synthetic */ Standard(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "An operation is not implemented." : str);
    }
}
