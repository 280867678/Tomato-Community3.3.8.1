package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.IncomeEntity;
import com.tomatolive.library.p136ui.view.iview.IIncomeDetailView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.IncomeDetailPresenter */
/* loaded from: classes3.dex */
public class IncomeDetailPresenter extends BasePresenter<IIncomeDetailView> {
    public IncomeDetailPresenter(Context context, IIncomeDetailView iIncomeDetailView) {
        super(context, iIncomeDetailView);
    }

    public void getPropsIncomeDataList(StateView stateView, int i, boolean z, boolean z2, String str, boolean z3) {
        if (z3) {
            addMapSubscription(this.mApiService.getIncomePropsListService(new RequestParams().getIncomeConsumeDetailParams(i, str, z3)), createObserver(z2, true, stateView, z));
        } else {
            addMapSubscription(this.mApiService.getIncomePropsPriceListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        }
    }

    public void getPropsExpenseDataList(StateView stateView, int i, boolean z, boolean z2, String str, boolean z3) {
        if (z3) {
            addMapSubscription(this.mApiService.getExpensePropsListService(new RequestParams().getIncomeConsumeDetailParams(i, str, z3)), createObserver(z2, true, stateView, z));
        } else {
            addMapSubscription(this.mApiService.getExpensePropsPriceListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        }
    }

    public void getIncomeDataList(StateView stateView, int i, boolean z, boolean z2, int i2, String str) {
        if (i2 == 1) {
            addMapSubscription(this.mApiService.getIncomeGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        } else if (i2 == 2) {
            addMapSubscription(this.mApiService.getIncomeGuardListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        } else if (i2 == 5) {
            addMapSubscription(this.mApiService.getIncomeNobleListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        } else if (i2 == 6) {
            addMapSubscription(this.mApiService.getIncomeTurntableGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        } else if (i2 == 7) {
            addMapSubscription(this.mApiService.getIncomeScoreGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, false, stateView, z));
        } else if (i2 != 8) {
        } else {
            addMapSubscription(this.mApiService.getIncomePaidLiveService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
        }
    }

    private <T extends IncomeEntity> HttpRxObserver createObserver(final boolean z, final boolean z2, StateView stateView, boolean z3) {
        return new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<T>>() { // from class: com.tomatolive.library.ui.presenter.IncomeDetailPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<T> httpResultPageModel) {
                if (httpResultPageModel == 0) {
                    return;
                }
                if (z2) {
                    ((IIncomeDetailView) IncomeDetailPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z, httpResultPageModel.totalPrice);
                } else {
                    ((IIncomeDetailView) IncomeDetailPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z, httpResultPageModel.totalGold);
                }
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IIncomeDetailView) IncomeDetailPresenter.this.getView()).onDataListFail(z);
            }
        }, stateView, z3);
    }

    public void getExpenseDataList(StateView stateView, int i, boolean z, boolean z2, int i2, String str) {
        switch (i2) {
            case 1:
                addMapSubscription(this.mApiService.getExpenseGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
            case 2:
                addMapSubscription(this.mApiService.getExpenseGuardListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
            case 3:
                addMapSubscription(this.mApiService.getExpenseCarListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
            case 4:
            default:
                return;
            case 5:
                addMapSubscription(this.mApiService.getExpenseNobleListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
            case 6:
                addMapSubscription(this.mApiService.getExpenseTurntableGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
            case 7:
                addMapSubscription(this.mApiService.getExpenseScoreGiftListService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, false, stateView, z));
                return;
            case 8:
                addMapSubscription(this.mApiService.getExpensePaidLiveService(new RequestParams().getIncomeConsumeDetailParams(i, str)), createObserver(z2, true, stateView, z));
                return;
        }
    }
}
