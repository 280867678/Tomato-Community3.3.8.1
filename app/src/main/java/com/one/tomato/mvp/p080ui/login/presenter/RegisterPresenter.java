package com.one.tomato.mvp.p080ui.login.presenter;

import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.mvp.p080ui.login.impl.IRegisterContact;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.UserInfoManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RegisterPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter */
/* loaded from: classes3.dex */
public final class RegisterPresenter extends LoginPresenter implements IRegisterContact {
    public void requestRegisterUpdate(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().requestRegisterUpdate(paramsMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter$requestRegisterUpdate$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                UserInfoManager.requestUserInfo(true);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RegisterPresenter.this.dismissDialog();
            }
        });
    }

    public void verifyPhoneCode(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().verifyPhoneCode(paramsMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter$verifyPhoneCode$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                RegisterPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ForgetPsSec>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter$verifyPhoneCode$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ForgetPsSec forgetPsSec) {
                ILoginContact$ILoginView mView;
                mView = RegisterPresenter.this.getMView();
                if (mView != null) {
                    mView.handleVerifyPhoneCode(forgetPsSec);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RegisterPresenter.this.dismissDialog();
            }
        });
    }

    public final void varifyEmail(Map<String, Object> paramsMap) {
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().varifyEmail(paramsMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter$varifyEmail$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                RegisterPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ForgetPsSec>() { // from class: com.one.tomato.mvp.ui.login.presenter.RegisterPresenter$varifyEmail$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ForgetPsSec forgetPsSec) {
                ILoginContact$ILoginView mView;
                RegisterPresenter.this.dismissDialog();
                mView = RegisterPresenter.this.getMView();
                if (mView != null) {
                    mView.handleVerifyPhoneCode(forgetPsSec);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RegisterPresenter.this.dismissDialog();
            }
        });
    }
}
