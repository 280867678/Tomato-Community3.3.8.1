package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.NobilityOpenRecordEntity;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenRecordView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.NobilityOpenRecordPresenter */
/* loaded from: classes3.dex */
public class NobilityOpenRecordPresenter extends BasePresenter<INobilityOpenRecordView> {
    public NobilityOpenRecordPresenter(Context context, INobilityOpenRecordView iNobilityOpenRecordView) {
        super(context, iNobilityOpenRecordView);
    }

    public void getDataList(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getNobilityOpenRecordListService(new RequestParams().getPageListParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<NobilityOpenRecordEntity>>() { // from class: com.tomatolive.library.ui.presenter.NobilityOpenRecordPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<NobilityOpenRecordEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((INobilityOpenRecordView) NobilityOpenRecordPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((INobilityOpenRecordView) NobilityOpenRecordPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }
}
