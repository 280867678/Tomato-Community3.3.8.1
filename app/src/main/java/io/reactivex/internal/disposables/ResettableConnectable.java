package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

/* loaded from: classes4.dex */
public interface ResettableConnectable {
    void resetIf(Disposable disposable);
}
