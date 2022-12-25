package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.iview.ISearchAnchorView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.SearchAnchorPresenter */
/* loaded from: classes3.dex */
public class SearchAnchorPresenter extends BasePresenter<ISearchAnchorView> {
    public SearchAnchorPresenter(Context context, ISearchAnchorView iSearchAnchorView) {
        super(context, iSearchAnchorView);
    }

    public void getAnchorList(StateView stateView, String str, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getSearchAnchorListService(new RequestParams().getSearchAnchorListParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.SearchAnchorPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<AnchorEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((ISearchAnchorView) SearchAnchorPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((ISearchAnchorView) SearchAnchorPresenter.this.getView()).onResultError(i2);
            }
        }, stateView, z));
    }

    public void attentionAnchor(String str, int i) {
        addMapSubscription(this.mApiService.getAttentionAnchorService(new RequestParams().getAttentionAnchorParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.SearchAnchorPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((ISearchAnchorView) SearchAnchorPresenter.this.getView()).onAttentionSuccess();
            }
        }));
    }
}
