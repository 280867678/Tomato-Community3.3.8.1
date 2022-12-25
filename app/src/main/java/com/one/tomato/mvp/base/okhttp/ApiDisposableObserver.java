package com.one.tomato.mvp.base.okhttp;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.observers.DisposableObserver;

/* loaded from: classes3.dex */
public abstract class ApiDisposableObserver<T> extends DisposableObserver<BaseResponse<T>> {
    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    protected void onLoginResultCode(T t) {
    }

    public abstract void onResult(T t);

    public abstract void onResultError(ResponseThrowable responseThrowable);

    public void postMessage(String str) {
    }

    @Override // io.reactivex.Observer
    public /* bridge */ /* synthetic */ void onNext(Object obj) {
        onNext((BaseResponse) ((BaseResponse) obj));
    }

    @Override // io.reactivex.observers.DisposableObserver
    public void onStart() {
        super.onStart();
        if (!NetWorkUtil.isNetworkConnected()) {
            LogUtil.m3786e("无网络，读取缓存数据");
            onComplete();
        }
    }

    public void onNext(BaseResponse<T> baseResponse) {
        baseResponse.getCode();
        int code = baseResponse.getCode();
        if (code == -8) {
            onLoginResultCode(baseResponse.getData());
        } else if (code == -3) {
            LogUtil.m3786e("token已过期，请重新登录");
            CallbackUtil.loginOut(false);
        } else if (code == 0) {
            onResult(baseResponse.getData());
            postMessage(baseResponse.getMessage());
        } else {
            ResponseThrowable responseThrowable = new ResponseThrowable(baseResponse.getMessage(), baseResponse.getCode());
            responseThrowable.setData(baseResponse.getData());
            LogUtil.m3786e("code = " + baseResponse.getCode() + ",message = " + baseResponse.getMessage());
            ToastUtil.showCenterToast(baseResponse.getMessage());
            onResultError(responseThrowable);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        th.printStackTrace();
        ResponseThrowable handleException = ExceptionHandle.handleException(th);
        LogUtil.m3786e("code = " + handleException.getCode() + ",message = " + handleException.getMessage());
        DomainRequest.getInstance().switchDomainUrlByType("server");
        onResultError(handleException);
    }
}
