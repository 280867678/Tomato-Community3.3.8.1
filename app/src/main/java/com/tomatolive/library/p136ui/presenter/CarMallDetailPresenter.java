package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.p136ui.view.iview.ICarMallDetailView;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* renamed from: com.tomatolive.library.ui.presenter.CarMallDetailPresenter */
/* loaded from: classes3.dex */
public class CarMallDetailPresenter extends BasePresenter<ICarMallDetailView> {
    public CarMallDetailPresenter(Context context, ICarMallDetailView iCarMallDetailView) {
        super(context, iCarMallDetailView);
    }

    public void getUserOver() {
        ApiRetrofit.getInstance().getApiService().getQueryBalanceService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.CarMallDetailPresenter.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.CarMallDetailPresenter.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(MyAccountEntity myAccountEntity) {
                if (myAccountEntity == null) {
                    ((ICarMallDetailView) CarMallDetailPresenter.this.getView()).onUserOverFail();
                } else {
                    ((ICarMallDetailView) CarMallDetailPresenter.this.getView()).onUserOverSuccess(myAccountEntity.getAccountBalance());
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ((ICarMallDetailView) CarMallDetailPresenter.this.getView()).onUserOverFail();
            }
        });
    }

    public void buyCar(String str, final String str2, final String str3) {
        addMapSubscription(this.mApiService.getBuyCarService(new RequestParams().getBuyCarParams(str, str2, str3)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.CarMallDetailPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str4) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((ICarMallDetailView) CarMallDetailPresenter.this.getView()).onBuyCarSuccess(str2, str3);
            }
        }, true));
    }
}
