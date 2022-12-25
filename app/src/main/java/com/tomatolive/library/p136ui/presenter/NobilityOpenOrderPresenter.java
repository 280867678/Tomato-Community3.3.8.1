package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenOrderView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.Collections;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.NobilityOpenOrderPresenter */
/* loaded from: classes3.dex */
public class NobilityOpenOrderPresenter extends BasePresenter<INobilityOpenOrderView> {
    public NobilityOpenOrderPresenter(Context context, INobilityOpenOrderView iNobilityOpenOrderView) {
        super(context, iNobilityOpenOrderView);
    }

    public void getInitData(StateView stateView, boolean z) {
        addMapSubscription(this.mApiService.getNobilityListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<NobilityEntity>>() { // from class: com.tomatolive.library.ui.presenter.NobilityOpenOrderPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<NobilityEntity> list) {
                if (list == null) {
                    return;
                }
                Collections.reverse(list);
                ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onNobilityListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onNobilityListFail();
            }
        }, stateView, z));
    }

    public void getUserOver() {
        addMapSubscription(this.mApiService.getQueryBalanceService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.NobilityOpenOrderPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyAccountEntity myAccountEntity) {
                if (myAccountEntity == null) {
                    ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onUserOverFail();
                } else {
                    ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onUserOverSuccess(myAccountEntity);
                }
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onUserOverFail();
            }
        }));
    }

    public void getNobilityBuy(String str, String str2, String str3, String str4) {
        addMapSubscription(this.mApiService.getNobilityBuyService(new RequestParams().getNobilityBuyParams(str, str2, str3, str4)), new HttpRxObserver(getContext(), new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.NobilityOpenOrderPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyAccountEntity myAccountEntity) {
                if (myAccountEntity == null) {
                    return;
                }
                ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onBuySuccess(myAccountEntity.getAccountBalance());
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str5) {
                ((INobilityOpenOrderView) NobilityOpenOrderPresenter.this.getView()).onBuyFail(i);
            }
        }, true));
    }
}
