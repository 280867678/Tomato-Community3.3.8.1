package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.iview.ISearchAllView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.SearchAllPresenter */
/* loaded from: classes3.dex */
public class SearchAllPresenter extends BasePresenter<ISearchAllView> {
    public SearchAllPresenter(Context context, ISearchAllView iSearchAllView) {
        super(context, iSearchAllView);
    }

    public void getAnchorList(String str) {
        addMapSubscription(this.mApiService.getSearchAnchorListService(new RequestParams().getSearchAnchorListParams(str, 1)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchAllPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<AnchorEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((ISearchAllView) SearchAllPresenter.this.getView()).onAnchorListSuccess(httpResultPageModel.dataList);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((ISearchAllView) SearchAllPresenter.this.getView()).onAnchorListFail();
            }
        }));
    }

    public void getLiveList(StateView stateView, String str, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getSearchLiveListService(new RequestParams().getPageListByKeyParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchAllPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((ISearchAllView) SearchAllPresenter.this.getView()).onLiveListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((ISearchAllView) SearchAllPresenter.this.getView()).onResultError(i2);
            }
        }, stateView, z));
    }
}
