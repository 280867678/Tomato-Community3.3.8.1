package org.jetbrains.anko;

import java.lang.ref.WeakReference;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: org.jetbrains.anko.AnkoAsyncContext */
/* loaded from: classes4.dex */
public final class Async<T> {
    private final WeakReference<T> weakRef;

    public Async(WeakReference<T> weakRef) {
        Intrinsics.checkParameterIsNotNull(weakRef, "weakRef");
        this.weakRef = weakRef;
    }

    public final WeakReference<T> getWeakRef() {
        return this.weakRef;
    }
}
