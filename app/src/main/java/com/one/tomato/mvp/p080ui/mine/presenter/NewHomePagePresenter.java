package com.one.tomato.mvp.p080ui.mine.presenter;

import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact;
import com.one.tomato.mvp.p080ui.mine.impl.IHomePageContact$IHomePageView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* compiled from: NewHomePagePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter */
/* loaded from: classes3.dex */
public final class NewHomePagePresenter extends MvpBasePresenter<IHomePageContact$IHomePageView> implements IHomePageContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestPersonInfo(int i, int i2) {
        ApiImplService.Companion.getApiImplService().getPersonInfo(i, i2).compose(RxUtils.schedulersTransformer()).subscribe(new Consumer<BaseResponse<UserInfo>>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$requestPersonInfo$1
            /* JADX WARN: Code restructure failed: missing block: B:11:0x001a, code lost:
                r0 = r1.this$0.getMView();
             */
            @Override // io.reactivex.functions.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(BaseResponse<UserInfo> baseResponse) {
                IHomePageContact$IHomePageView mView;
                IHomePageContact$IHomePageView mView2;
                if (baseResponse instanceof BaseResponse) {
                    mView2 = NewHomePagePresenter.this.getMView();
                    if (mView2 == null) {
                        return;
                    }
                    mView2.handlerPersonInfo(baseResponse.getData());
                } else if (!(baseResponse instanceof Throwable) || mView == null) {
                } else {
                    mView.onError(((Throwable) baseResponse).getMessage());
                }
            }
        });
    }

    public final void foucs(int i, int i2) {
        ApiImplService.Companion.getApiImplService().postFoucs(DBUtil.getMemberId(), i, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$foucs$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewHomePagePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$foucs$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerFoucs();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestCancelShield(int i, int i2) {
        ApiImplService.Companion.getApiImplService().cancelShield(i, i2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$requestCancelShield$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewHomePagePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$requestCancelShield$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerCancelShield();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestPostShield(int i, int i2, int i3) {
        ApiImplService.Companion.getApiImplService().postShield(i, i2, i3).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$requestPostShield$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                NewHomePagePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.presenter.NewHomePagePresenter$requestPostShield$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerShield();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IHomePageContact$IHomePageView mView;
                NewHomePagePresenter.this.dismissDialog();
                mView = NewHomePagePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
