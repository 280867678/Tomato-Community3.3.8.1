package org.jetbrains.anko;

import android.os.Handler;
import android.os.Looper;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Async.kt */
/* loaded from: classes4.dex */
final class ContextHelper {
    public static final ContextHelper INSTANCE = null;
    private static final Handler handler = null;
    private static final Thread mainThread = null;

    static {
        new ContextHelper();
    }

    private ContextHelper() {
        INSTANCE = this;
        handler = new Handler(Looper.getMainLooper());
        Thread thread = Looper.getMainLooper().getThread();
        Intrinsics.checkExpressionValueIsNotNull(thread, "Looper.getMainLooper().thread");
        mainThread = thread;
    }

    public final Handler getHandler() {
        return handler;
    }

    public final Thread getMainThread() {
        return mainThread;
    }
}
