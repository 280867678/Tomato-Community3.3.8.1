package com.one.tomato.mvp.p080ui.sign.present;

import com.one.tomato.entity.SignBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.sign.impl.ISignImpl;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* compiled from: SignPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.sign.present.SignPresenter */
/* loaded from: classes3.dex */
public final class SignPresenter extends MvpBasePresenter<ISignImpl> {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public final void requestSignDay() {
        ApiImplService.Companion.getApiImplService().requestSignDay(DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.sign.present.SignPresenter$requestSignDay$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                SignPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<SignBean>() { // from class: com.one.tomato.mvp.ui.sign.present.SignPresenter$requestSignDay$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(SignBean signBean) {
                ISignImpl mView;
                SignPresenter.this.dismissDialog();
                mView = SignPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerSignDay(signBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ISignImpl mView;
                SignPresenter.this.dismissDialog();
                mView = SignPresenter.this.getMView();
                if (mView != null) {
                    mView.onError("");
                }
            }
        });
    }

    public final void requestSign() {
        ApiImplService.Companion.getApiImplService().requestSign(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.sign.present.SignPresenter$requestSign$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                SignPresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.sign.present.SignPresenter$requestSign$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                ISignImpl mView;
                SignPresenter.this.dismissDialog();
                mView = SignPresenter.this.getMView();
                if (mView != null) {
                    mView.handlerSignNumDay();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                SignPresenter.this.dismissDialog();
            }
        });
    }
}
