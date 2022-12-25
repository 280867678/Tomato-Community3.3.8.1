package com.tomatolive.library.utils.live;

import android.content.Context;
import android.support.p002v4.app.FragmentActivity;
import android.text.TextUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.http.exception.ApiException;
import com.tomatolive.library.http.exception.ExceptionEngine;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public abstract class SimpleRxObserver<T> implements Observer<T> {
    private WeakReference<Context> context;
    private boolean isToastErrorMsg;

    public abstract void accept(T t);

    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    public void onError(int i, String str) {
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public SimpleRxObserver() {
        this.isToastErrorMsg = true;
    }

    public SimpleRxObserver(Context context) {
        this(context, true);
    }

    public SimpleRxObserver(Context context, boolean z) {
        this.isToastErrorMsg = true;
        this.context = new WeakReference<>(context);
        this.isToastErrorMsg = z;
    }

    public SimpleRxObserver(boolean z) {
        this.isToastErrorMsg = true;
        this.isToastErrorMsg = z;
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        th.printStackTrace();
        if (th instanceof ApiException) {
            ApiException apiException = (ApiException) th;
            int code = apiException.getCode();
            onError(code, apiException.getMsg());
            if (AppUtils.isTokenInvalidErrorCode(code)) {
                WeakReference<Context> weakReference = this.context;
                if (weakReference == null || weakReference.get() == null) {
                    return;
                }
                Context context = this.context.get();
                if (!(context instanceof FragmentActivity)) {
                    return;
                }
                AppUtils.handlerTokenInvalid(context);
            } else if (!this.isToastErrorMsg || ExceptionEngine.isExceptionErrorCode(code) || TextUtils.isEmpty(apiException.getMsg())) {
            } else {
                ToastUtils.showShort(apiException.getMsg());
            }
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        accept(t);
    }
}
