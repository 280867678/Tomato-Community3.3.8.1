package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.IncomeMenuEntity;
import com.tomatolive.library.p136ui.view.iview.IIncomeView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.IncomePresenter */
/* loaded from: classes3.dex */
public class IncomePresenter extends BasePresenter<IIncomeView> {
    public IncomePresenter(Context context, IIncomeView iIncomeView) {
        super(context, iIncomeView);
    }

    public void getDataList(StateView stateView, boolean z, String str) {
        addMapSubscription(this.mApiService.getIncomeStatisticsService(new RequestParams().getIncomeConsumeParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<IncomeMenuEntity>() { // from class: com.tomatolive.library.ui.presenter.IncomePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(IncomeMenuEntity incomeMenuEntity) {
                if (incomeMenuEntity == null) {
                    return;
                }
                ((IIncomeView) IncomePresenter.this.getView()).onDataListSuccess(incomeMenuEntity);
            }
        }, stateView, z));
    }
}
