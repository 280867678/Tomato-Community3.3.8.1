package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

/* loaded from: classes4.dex */
public interface ObservableEmitter<T> extends Emitter<T> {
    boolean isDisposed();

    void setCancellable(Cancellable cancellable);

    void setDisposable(Disposable disposable);
}
