package com.one.tomato.mvp.p080ui.p082up.presenter;

import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpHomePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter */
/* loaded from: classes3.dex */
public class UpHomePresenter extends MvpBasePresenter<UpContarct$UpIView> implements UpContarct {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestQueryUpStatusInfo(int i) {
        ApiImplService.Companion.getApiImplService().requestQueryUpStatusInfo(DBUtil.getMemberId(), i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestQueryUpStatusInfo$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpHomePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<UpStatusBean>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestQueryUpStatusInfo$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(UpStatusBean upStatusBean) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerQueryUpStatusInfo(upStatusBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(null);
                }
            }
        });
    }

    public void requestApplyUp(String applyReason, int i) {
        Intrinsics.checkParameterIsNotNull(applyReason, "applyReason");
        ApiImplService.Companion.getApiImplService().requestApplyUp(DBUtil.getMemberId(), applyReason, i).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestApplyUp$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpHomePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestApplyUp$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerApplyUpSuccess();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerApplyError();
                }
            }
        });
    }

    public void requestQueryAchievement() {
        ApiImplService.Companion.getApiImplService().requestQueryAchievement(DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestQueryAchievement$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                UpHomePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<UpStatusBean>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestQueryAchievement$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(UpStatusBean upStatusBean) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerQueryAchiSucess(upStatusBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpContarct$UpIView mView;
                UpHomePresenter.this.dismissDialog();
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(null);
                }
            }
        });
    }

    public final void requestUpRankList(int i, int i2) {
        ApiImplService.Companion.getApiImplService().requestUpRankList(i, i2).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<UpRankListBean>>() { // from class: com.one.tomato.mvp.ui.up.presenter.UpHomePresenter$requestUpRankList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<UpRankListBean> arrayList) {
                UpContarct$UpIView mView;
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerRankList(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                UpContarct$UpIView mView;
                LogUtil.m3788d("yan");
                mView = UpHomePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }
}
