package com.one.tomato.mvp.base.okhttp.download;

import io.reactivex.observers.DisposableObserver;

/* loaded from: classes3.dex */
public class DownLoadSubscriber<T> extends DisposableObserver<T> {
    private ProgressCallBack fileCallBack;

    public DownLoadSubscriber(ProgressCallBack progressCallBack) {
        this.fileCallBack = progressCallBack;
    }

    @Override // io.reactivex.observers.DisposableObserver
    public void onStart() {
        super.onStart();
        ProgressCallBack progressCallBack = this.fileCallBack;
        if (progressCallBack != null) {
            progressCallBack.onStart();
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        ProgressCallBack progressCallBack = this.fileCallBack;
        if (progressCallBack != null) {
            progressCallBack.onCompleted();
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ProgressCallBack progressCallBack = this.fileCallBack;
        if (progressCallBack != null) {
            progressCallBack.onError(th);
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ProgressCallBack progressCallBack = this.fileCallBack;
        if (progressCallBack != null) {
            progressCallBack.onSuccess(t);
        }
    }
}
