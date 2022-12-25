package com.one.tomato.mvp.p080ui.p082up.presenter;

import com.one.tomato.entity.RechargeList;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardContact;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardContact$IRewardView;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RewardRechargePresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.presenter.RewardRechargePresenter */
/* loaded from: classes3.dex */
public final class RewardRechargePresenter extends MvpBasePresenter<RewardContact$IRewardView> implements RewardContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestRechargeList() {
        ApiImplService.Companion.getApiImplService().requestRechargeList().compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.presenter.RewardRechargePresenter$requestRechargeList$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                RewardRechargePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<RechargeList>>() { // from class: com.one.tomato.mvp.ui.up.presenter.RewardRechargePresenter$requestRechargeList$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RechargeList> arrayList) {
                RewardContact$IRewardView mView;
                RewardRechargePresenter.this.dismissDialog();
                mView = RewardRechargePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerRechargeList(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RewardContact$IRewardView mView;
                RewardRechargePresenter.this.dismissDialog();
                mView = RewardRechargePresenter.this.getMView();
                if (mView != null) {
                    mView.onError(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                }
            }
        });
    }

    public void requestRewardPay(Map<String, Object> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        ApiImplService.Companion.getApiImplService().requestRewardPay(map).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.up.presenter.RewardRechargePresenter$requestRewardPay$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                RewardRechargePresenter.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<String>() { // from class: com.one.tomato.mvp.ui.up.presenter.RewardRechargePresenter$requestRewardPay$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(String str) {
                RewardContact$IRewardView mView;
                RewardRechargePresenter.this.dismissDialog();
                mView = RewardRechargePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerRewardPayOk(str);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RewardContact$IRewardView mView;
                RewardRechargePresenter.this.dismissDialog();
                mView = RewardRechargePresenter.this.getMView();
                if (mView != null) {
                    mView.handlerRewardPayError();
                }
            }
        });
    }
}
