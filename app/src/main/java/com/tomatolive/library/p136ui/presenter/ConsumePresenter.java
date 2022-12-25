package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.ExpenseMenuEntity;
import com.tomatolive.library.p136ui.view.iview.IConsumeView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.ConsumePresenter */
/* loaded from: classes3.dex */
public class ConsumePresenter extends BasePresenter<IConsumeView> {
    public ConsumePresenter(Context context, IConsumeView iConsumeView) {
        super(context, iConsumeView);
    }

    public void getDataList(StateView stateView, boolean z, String str) {
        addMapSubscription(this.mApiService.getExpenseStatisticsService(new RequestParams().getIncomeConsumeParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<ExpenseMenuEntity>() { // from class: com.tomatolive.library.ui.presenter.ConsumePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(ExpenseMenuEntity expenseMenuEntity) {
                if (expenseMenuEntity == null) {
                    return;
                }
                ((IConsumeView) ConsumePresenter.this.getView()).onDataListSuccess(expenseMenuEntity);
            }
        }, stateView, z));
    }
}
