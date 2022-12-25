package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyTicketEntity;
import com.tomatolive.library.p136ui.view.iview.IMyTicketView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.MyTicketPresenter */
/* loaded from: classes3.dex */
public class MyTicketPresenter extends BasePresenter<IMyTicketView> {
    public MyTicketPresenter(Context context, IMyTicketView iMyTicketView) {
        super(context, iMyTicketView);
    }

    public void getDataList(StateView stateView, String str, int i, int i2, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getLotteryTicketListService(new RequestParams().getLotteryTicketParams(i2, str, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MyTicketEntity>>() { // from class: com.tomatolive.library.ui.presenter.MyTicketPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<MyTicketEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IMyTicketView) MyTicketPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i3, String str2) {
                ((IMyTicketView) MyTicketPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }
}
