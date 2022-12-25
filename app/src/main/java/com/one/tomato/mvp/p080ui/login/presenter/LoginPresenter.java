package com.one.tomato.mvp.p080ui.login.presenter;

import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserInfoManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LoginPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.login.presenter.LoginPresenter */
/* loaded from: classes3.dex */
public class LoginPresenter extends MvpBasePresenter<ILoginContact$ILoginView> implements ILoginContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestPhoneCode(String url, String countryCode, String phone, int i) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(countryCode, "countryCode");
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        ApiImplService.Companion.getApiImplService().requestPhoneCode(url, countryCode, phone, i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestPhoneCode$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                LoginPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestPhoneCode$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LoginPresenter.this.dismissDialog();
                ToastUtil.showCenterToast((int) R.string.get_code_success);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginPresenter.this.dismissDialog();
            }
        });
    }

    public void requestLogin(String url, Map<String, Object> paramsMap, final boolean z) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(paramsMap, "paramsMap");
        ApiImplService.Companion.getApiImplService().requestLogin(url, paramsMap).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<LoginInfo>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestLogin$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(LoginInfo loginInfo) {
                ILoginContact$ILoginView mView;
                if (loginInfo != null) {
                    loginInfo.setUserType(2);
                }
                DBUtil.saveLoginInfo(loginInfo);
                mView = LoginPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerLogin(loginInfo);
                }
                if (z) {
                    UserInfoManager.requestUserInfo(true);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ILoginContact$ILoginView mView;
                LoginPresenter.this.dismissDialog();
                mView = LoginPresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onLoginResultCode(LoginInfo loginInfo) {
                ILoginContact$ILoginView mView;
                mView = LoginPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerSafetyVerify(loginInfo);
                }
            }
        });
    }

    public void requestCountryCodeFromDB() {
        Observable.create(new ObservableOnSubscribe<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestCountryCodeFromDB$1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<ArrayList<CountryDB>> emitter) {
                Intrinsics.checkParameterIsNotNull(emitter, "emitter");
                ArrayList<CountryDB> countryList = DBUtil.getCountryList();
                Intrinsics.checkExpressionValueIsNotNull(countryList, "DBUtil.getCountryList()");
                emitter.onNext(countryList);
                emitter.onComplete();
            }
        }).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new Observer<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestCountryCodeFromDB$2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public void onNext(ArrayList<CountryDB> list) {
                Intrinsics.checkParameterIsNotNull(list, "list");
                if (list.isEmpty()) {
                    LoginPresenter.this.requestCountryCodeListFormServer();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestCountryCodeListFormServer() {
        ApiImplService.Companion.getApiImplService().getCountryList().subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$requestCountryCodeListFormServer$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CountryDB> arrayList) {
                if (arrayList == null || !(!arrayList.isEmpty())) {
                    return;
                }
                DBUtil.saveCountryList(arrayList);
            }
        });
    }

    public final void senMail(String email, int i) {
        Intrinsics.checkParameterIsNotNull(email, "email");
        ApiImplService.Companion.getApiImplService().senMail(email, i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$senMail$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                LoginPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$senMail$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.get_code_success));
                LoginPresenter.this.dismissDialog();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginPresenter.this.dismissDialog();
            }
        });
    }

    public final void forgetPsBySendEmail(String email, int i) {
        Intrinsics.checkParameterIsNotNull(email, "email");
        ApiImplService.Companion.getApiImplService().senMail(email, i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$forgetPsBySendEmail$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                LoginPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$forgetPsBySendEmail$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.get_code_success));
                LoginPresenter.this.dismissDialog();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginPresenter.this.dismissDialog();
            }
        });
    }

    public final void forgetPsBySendPhone(String countryCode, String phone, int i) {
        Intrinsics.checkParameterIsNotNull(countryCode, "countryCode");
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        ApiImplService.Companion.getApiImplService().forgetPsBySendPhone(countryCode, phone, i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$forgetPsBySendPhone$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                LoginPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.presenter.LoginPresenter$forgetPsBySendPhone$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.get_code_success));
                LoginPresenter.this.dismissDialog();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LoginPresenter.this.dismissDialog();
            }
        });
    }
}
