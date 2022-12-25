package org.jetbrains.anko;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: Async.kt */
/* loaded from: classes4.dex */
final class AsyncKt$doAsync$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Async $context;
    final /* synthetic */ Function1 $exceptionHandler;
    final /* synthetic */ Function1 $task;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncKt$doAsync$1(Function1 function1, Async async, Function1 function12) {
        super(0);
        this.$task = function1;
        this.$context = async;
        this.$exceptionHandler = function12;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final void mo6822invoke() {
        try {
            Unit unit = (Unit) this.$task.mo6794invoke(this.$context);
        } catch (Throwable th) {
            Function1 function1 = this.$exceptionHandler;
            if ((function1 != null ? (Unit) function1.mo6794invoke(th) : null) != null) {
                return;
            }
            Unit unit2 = Unit.INSTANCE;
        }
    }
}
