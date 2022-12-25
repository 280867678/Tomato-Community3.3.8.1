package com.tomatolive.library.download;

import io.reactivex.observers.DefaultObserver;

/* loaded from: classes3.dex */
public abstract class FileDownLoadObserver<T> extends DefaultObserver<T> {
    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    public abstract void onDownLoadFail(Throwable th);

    public abstract void onDownLoadSuccess(T t);

    public abstract void onProgress(int i, long j);

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        onDownLoadFail(th);
    }
}
