package com.tomatolive.library.http;

import android.app.Activity;
import android.content.Context;
import android.support.p002v4.app.FragmentActivity;
import android.text.TextUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.http.exception.ApiException;
import com.tomatolive.library.http.exception.ExceptionEngine;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class HttpRxObserver<T> implements Observer<T> {
    private ResultCallBack callBack;
    private WeakReference<Context> context;
    private LoadingDialog dialog;
    private boolean isShowLoadingLayout;
    private boolean isToastErrorMsg;
    private int progressType;
    private StateView stateLayout;

    public HttpRxObserver(Context context, ResultCallBack<T> resultCallBack) {
        this(context, resultCallBack, false);
    }

    public HttpRxObserver(Context context, ResultCallBack<T> resultCallBack, boolean z) {
        this(context, (ResultCallBack) resultCallBack, z, true);
    }

    public HttpRxObserver(Context context, ResultCallBack<T> resultCallBack, boolean z, boolean z2) {
        this.dialog = null;
        this.isShowLoadingLayout = true;
        this.isToastErrorMsg = true;
        this.progressType = 0;
        this.context = new WeakReference<>(context);
        this.callBack = resultCallBack;
        this.isToastErrorMsg = z2;
        if (z) {
            initProgressDialog();
        }
    }

    public HttpRxObserver(Context context, ResultCallBack<T> resultCallBack, StateView stateView, boolean z) {
        this(context, resultCallBack, stateView, z, true);
    }

    public HttpRxObserver(Context context, ResultCallBack<T> resultCallBack, StateView stateView, boolean z, boolean z2) {
        this.dialog = null;
        this.isShowLoadingLayout = true;
        this.isToastErrorMsg = true;
        this.progressType = 0;
        this.context = new WeakReference<>(context);
        this.callBack = resultCallBack;
        this.stateLayout = stateView;
        this.progressType = 1;
        this.isShowLoadingLayout = z;
        this.isToastErrorMsg = z2;
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        showLoadingView();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ResultCallBack resultCallBack = this.callBack;
        if (resultCallBack != null) {
            resultCallBack.onSuccess(t);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        th.printStackTrace();
        try {
            ApiException apiException = (ApiException) th;
            if (this.callBack != null) {
                this.callBack.onError(apiException.getCode(), apiException.getMsg());
            }
            if (AppUtils.isTokenInvalidErrorCode(apiException.getCode())) {
                showStateLayoutContent();
                dismissProgressDialog();
                Context context = this.context.get();
                if (context == null || !(context instanceof FragmentActivity)) {
                    return;
                }
                AppUtils.handlerTokenInvalid(context);
                return;
            }
            showErrorView();
            if (apiException.getCode() == 30000 || apiException.getCode() == 30001 || apiException.getCode() == 200023 || apiException.getCode() == 200163 || apiException.getCode() == 300006 || apiException.getCode() == 200166 || apiException.getCode() == 200168 || apiException.getCode() == 200111 || apiException.getCode() == 200112 || !this.isToastErrorMsg || ExceptionEngine.isExceptionErrorCode(apiException.getCode()) || TextUtils.isEmpty(apiException.getMsg())) {
                return;
            }
            ToastUtils.showShort(apiException.getMsg());
        } catch (Exception unused) {
            onComplete();
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        showContentView();
    }

    private void initProgressDialog() {
        Context context = this.context.get();
        if (context != null && this.dialog == null) {
            this.dialog = new LoadingDialog(context);
        }
    }

    private void initProgressDialog(String str) {
        Context context = this.context.get();
        if (context != null && this.dialog == null) {
            this.dialog = new LoadingDialog(context, str);
        }
    }

    private void showProgressDialog() {
        LoadingDialog loadingDialog;
        Context context = this.context.get();
        if (context == null || (loadingDialog = this.dialog) == null) {
            return;
        }
        try {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isDestroyed() || activity.isFinishing() || this.dialog == null || this.dialog.isShowing()) {
                    return;
                }
                this.dialog.show();
            } else if (loadingDialog == null || loadingDialog.isShowing()) {
            } else {
                this.dialog.show();
            }
        } catch (Exception unused) {
        }
    }

    private void dismissProgressDialog() {
        try {
            if (this.dialog == null || !this.dialog.isShowing()) {
                return;
            }
            this.dialog.dismiss();
        } catch (Exception unused) {
        }
    }

    private void showStateLayoutLoading() {
        Context context = this.context.get();
        StateView stateView = this.stateLayout;
        if (stateView == null || context == null || !this.isShowLoadingLayout) {
            return;
        }
        stateView.showLoading();
    }

    private void showStateLayoutContent() {
        Context context = this.context.get();
        StateView stateView = this.stateLayout;
        if (stateView == null || context == null) {
            return;
        }
        stateView.showContent();
    }

    private void showStateLayoutError() {
        Context context = this.context.get();
        StateView stateView = this.stateLayout;
        if (stateView == null || context == null) {
            return;
        }
        stateView.showRetry();
    }

    private void showErrorView() {
        if (this.progressType == 0) {
            dismissProgressDialog();
        } else {
            showStateLayoutError();
        }
    }

    private void showLoadingView() {
        if (this.progressType == 0) {
            showProgressDialog();
        } else {
            showStateLayoutLoading();
        }
    }

    private void showContentView() {
        if (this.progressType == 0) {
            dismissProgressDialog();
        } else {
            showStateLayoutContent();
        }
    }
}
