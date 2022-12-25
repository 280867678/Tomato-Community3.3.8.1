package org.jetbrains.anko;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Async.kt */
/* loaded from: classes4.dex */
public final class BackgroundExecutor {
    public static final BackgroundExecutor INSTANCE = null;
    private static ExecutorService executor;

    static {
        new BackgroundExecutor();
    }

    private BackgroundExecutor() {
        INSTANCE = this;
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Intrinsics.checkExpressionValueIsNotNull(newScheduledThreadPool, "Executors.newScheduledTh…().availableProcessors())");
        executor = newScheduledThreadPool;
    }

    public final <T> Future<T> submit(final Functions<? extends T> task) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        Future<T> submit = executor.submit(task == null ? null : new Callable() { // from class: org.jetbrains.anko.AsyncKt$sam$Callable$761a5578
            /* JADX WARN: Type inference failed for: r0v1, types: [V, java.lang.Object] */
            @Override // java.util.concurrent.Callable
            public final /* synthetic */ V call() {
                return Functions.this.mo6822invoke();
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(submit, "executor.submit(task)");
        return submit;
    }
}
