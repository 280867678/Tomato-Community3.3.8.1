package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AwardHistoryEntity;
import com.tomatolive.library.p136ui.view.iview.IAwardListView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.AwardHistoryPresenter */
/* loaded from: classes3.dex */
public class AwardHistoryPresenter extends BasePresenter<IAwardListView> {
    public AwardHistoryPresenter(Context context, IAwardListView iAwardListView) {
        super(context, iAwardListView);
    }

    public void getDataList(boolean z, String str, String str2, StateView stateView, int i, boolean z2, final boolean z3) {
        if (z) {
            addMapSubscription(this.mApiService.getAwardListService(new RequestParams().getAwardListParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<AwardHistoryEntity>>() { // from class: com.tomatolive.library.ui.presenter.AwardHistoryPresenter.1
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<AwardHistoryEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IAwardListView) AwardHistoryPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z3);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str3) {
                    ((IAwardListView) AwardHistoryPresenter.this.getView()).onDataListFail(z3);
                }
            }, stateView, z2));
        } else {
            addMapSubscription(this.mApiService.getGivenAwardListService(new RequestParams().getGivenAwardHistoryInfoParams(str, str2, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<AwardHistoryEntity>>() { // from class: com.tomatolive.library.ui.presenter.AwardHistoryPresenter.2
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<AwardHistoryEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IAwardListView) AwardHistoryPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z3);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str3) {
                    ((IAwardListView) AwardHistoryPresenter.this.getView()).onDataListFail(z3);
                }
            }, stateView, z2));
        }
    }
}
