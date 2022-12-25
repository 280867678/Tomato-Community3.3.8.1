package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AppealHistoryEntity;
import com.tomatolive.library.p136ui.view.iview.IAppealListView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.AppealHistoryPresenter */
/* loaded from: classes3.dex */
public class AppealHistoryPresenter extends BasePresenter<IAppealListView> {
    public AppealHistoryPresenter(Context context, IAppealListView iAppealListView) {
        super(context, iAppealListView);
    }

    public void getDataList(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getAppealListService(new RequestParams().getPageListParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<AppealHistoryEntity>>() { // from class: com.tomatolive.library.ui.presenter.AppealHistoryPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<AppealHistoryEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IAppealListView) AppealHistoryPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((IAppealListView) AppealHistoryPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }
}
