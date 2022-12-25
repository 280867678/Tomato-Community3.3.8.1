package org.jetbrains.anko;

import java.lang.ref.WeakReference;
import java.util.concurrent.Future;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Async.kt */
/* loaded from: classes4.dex */
public final class AsyncKt {
    private static final Function1<Throwable, Unit> crashLogger = AsyncKt$crashLogger$1.INSTANCE;

    public static final <T> boolean uiThread(Async<T> receiver, final Function1<? super T, Unit> f) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(f, "f");
        final T t = receiver.getWeakRef().get();
        if (t != null) {
            if (Intrinsics.areEqual(ContextHelper.INSTANCE.getMainThread(), Thread.currentThread())) {
                f.mo6794invoke(t);
                return true;
            }
            ContextHelper.INSTANCE.getHandler().post(new Runnable() { // from class: org.jetbrains.anko.AsyncKt$uiThread$1
                @Override // java.lang.Runnable
                public final void run() {
                    Function1.this.mo6794invoke(t);
                }
            });
            return true;
        }
        return false;
    }

    public static /* bridge */ /* synthetic */ Future doAsync$default(Object obj, Function1 function1, Function1 function12, int i, Object obj2) {
        if ((i & 1) != 0) {
            function1 = crashLogger;
        }
        return doAsync(obj, function1, function12);
    }

    public static final <T> Future<Unit> doAsync(T t, Function1<? super Throwable, Unit> function1, Function1<? super Async<T>, Unit> task) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        return BackgroundExecutor.INSTANCE.submit(new AsyncKt$doAsync$1(task, new Async(new WeakReference(t)), function1));
    }
}
