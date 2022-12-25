package com.one.tomato.mvp.p080ui.login.presenter;

import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.impl.ICountryCodeContact;
import com.one.tomato.mvp.p080ui.login.impl.ICountryCodeContact$ICountryCodeView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CountryCodePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.login.presenter.CountryCodePresenter */
/* loaded from: classes3.dex */
public final class CountryCodePresenter extends MvpBasePresenter<ICountryCodeContact$ICountryCodeView> implements ICountryCodeContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestListFromDB() {
        Observable.create(new ObservableOnSubscribe<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.CountryCodePresenter$requestListFromDB$1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<ArrayList<CountryDB>> emitter) {
                Intrinsics.checkParameterIsNotNull(emitter, "emitter");
                ArrayList<CountryDB> countryList = DBUtil.getCountryList();
                Intrinsics.checkExpressionValueIsNotNull(countryList, "DBUtil.getCountryList()");
                emitter.onNext(countryList);
                emitter.onComplete();
            }
        }).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new Observer<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.CountryCodePresenter$requestListFromDB$2
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
            public void onNext(ArrayList<CountryDB> t) {
                ICountryCodeContact$ICountryCodeView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = CountryCodePresenter.this.getMView();
                if (mView != null) {
                    mView.handleCountryListFromDB(t);
                }
            }
        });
    }

    public void requestListFromServer() {
        ApiImplService.Companion.getApiImplService().getCountryList().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<CountryDB>>() { // from class: com.one.tomato.mvp.ui.login.presenter.CountryCodePresenter$requestListFromServer$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CountryDB> arrayList) {
                ICountryCodeContact$ICountryCodeView mView;
                mView = CountryCodePresenter.this.getMView();
                if (mView != null) {
                    if (arrayList != null) {
                        mView.handleCountryListFromServer(arrayList);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ICountryCodeContact$ICountryCodeView mView;
                mView = CountryCodePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
