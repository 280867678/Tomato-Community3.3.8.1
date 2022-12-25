package org.jetbrains.anko;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: Async.kt */
/* loaded from: classes4.dex */
final class AsyncKt$crashLogger$1 extends Lambda implements Function1<Throwable, Unit> {
    public static final AsyncKt$crashLogger$1 INSTANCE = new AsyncKt$crashLogger$1();

    AsyncKt$crashLogger$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Throwable throwable) {
        Intrinsics.checkParameterIsNotNull(throwable, "throwable");
        throwable.printStackTrace();
    }
}
