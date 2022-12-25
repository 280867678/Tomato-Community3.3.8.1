package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.model.CarHistoryRecordEntity;
import com.tomatolive.library.p136ui.view.iview.ICarMallView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.CarMallPresenter */
/* loaded from: classes3.dex */
public class CarMallPresenter extends BasePresenter<ICarMallView> {
    public CarMallPresenter(Context context, ICarMallView iCarMallView) {
        super(context, iCarMallView);
    }

    public void getCarList(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getCarListService(new RequestParams().getScopeParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<CarEntity>>() { // from class: com.tomatolive.library.ui.presenter.CarMallPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<CarEntity> list) {
                if (list == null) {
                    return;
                }
                ((ICarMallView) CarMallPresenter.this.getView()).onDataListSuccess(list, true, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((ICarMallView) CarMallPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }

    public void buyCar(final CarEntity carEntity, final String str, final String str2) {
        addMapSubscription(this.mApiService.getBuyCarService(new RequestParams().getBuyCarParams(carEntity.f5836id, str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.CarMallPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((ICarMallView) CarMallPresenter.this.getView()).onBuyCarSuccess(carEntity, str, str2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((ICarMallView) CarMallPresenter.this.getView()).onBuyCarFail(i);
            }
        }, true));
    }

    public void getCarPurchaseCarouselRecord() {
        addMapSubscription(this.mApiService.getCarHistoryRecordListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<CarHistoryRecordEntity>>() { // from class: com.tomatolive.library.ui.presenter.CarMallPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<CarHistoryRecordEntity> list) {
                if (list == null) {
                    return;
                }
                ((ICarMallView) CarMallPresenter.this.getView()).onGetCarPurchaseCarouselRecordSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((ICarMallView) CarMallPresenter.this.getView()).onGetCarPurchaseCarouselRecordFail();
            }
        }));
    }
}
