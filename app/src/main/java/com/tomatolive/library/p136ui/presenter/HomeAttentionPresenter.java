package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.iview.IHomeAttentionView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.HomeAttentionPresenter */
/* loaded from: classes3.dex */
public class HomeAttentionPresenter extends BasePresenter<IHomeAttentionView> {
    public HomeAttentionPresenter(Context context, IHomeAttentionView iHomeAttentionView) {
        super(context, iHomeAttentionView);
    }

    public void getAttentionAnchorListList(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getAttentionAnchorListService(new RequestParams().getPageListByIdParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAttentionPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeAttentionView) HomeAttentionPresenter.this.getView()).onAttentionListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((IHomeAttentionView) HomeAttentionPresenter.this.getView()).onAttentionListFail(z2);
            }
        }, stateView, z, false));
    }

    public void getRecommendAnchorList(StateView stateView, boolean z) {
        addMapSubscription(this.mApiService.getRecommendAnchorService(new RequestParams().getPageListParams(1, 3)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAttentionPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IHomeAttentionView) HomeAttentionPresenter.this.getView()).onRecommendListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IHomeAttentionView) HomeAttentionPresenter.this.getView()).onRecommendListFail();
            }
        }, stateView, z, false));
    }

    public void attentionAnchor(String str, int i) {
        addMapSubscription(this.mApiService.getAttentionAnchorService(new RequestParams().getAttentionAnchorParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.HomeAttentionPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IHomeAttentionView) HomeAttentionPresenter.this.getView()).onAttentionSuccess();
            }
        }));
    }
}
