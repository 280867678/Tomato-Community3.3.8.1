package com.tomatolive.library.http;

import com.tomatolive.library.http.exception.ApiException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/* loaded from: classes3.dex */
public class StatisticsHttpRxObserver<T> implements Observer<T> {
    private ResultCallBack callBack;

    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public StatisticsHttpRxObserver(ResultCallBack<T> resultCallBack) {
        this.callBack = resultCallBack;
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        th.printStackTrace();
        try {
            ApiException apiException = (ApiException) th;
            if (this.callBack == null) {
                return;
            }
            this.callBack.onError(apiException.getCode(), apiException.getMsg());
        } catch (Exception unused) {
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ResultCallBack resultCallBack = this.callBack;
        if (resultCallBack != null) {
            resultCallBack.onSuccess(t);
        }
    }
}
