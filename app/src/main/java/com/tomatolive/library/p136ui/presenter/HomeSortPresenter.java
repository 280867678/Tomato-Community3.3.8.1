package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.iview.IHomeSortView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.HomeSortPresenter */
/* loaded from: classes3.dex */
public class HomeSortPresenter extends BasePresenter<IHomeSortView> {
    public HomeSortPresenter(Context context, IHomeSortView iHomeSortView) {
        super(context, iHomeSortView);
    }

    public void getLiveList(StateView stateView, String str, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getSortListService(new RequestParams().getTagPageListParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeSortPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeSortView) HomeSortPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((IHomeSortView) HomeSortPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z, false));
    }

    public void getFeeLiveList(StateView stateView, String str, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getFeeTagListService(new RequestParams().getTagPageListParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeSortPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeSortView) HomeSortPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((IHomeSortView) HomeSortPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z, false));
    }
}
