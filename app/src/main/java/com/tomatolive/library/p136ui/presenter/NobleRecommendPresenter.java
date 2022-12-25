package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.MyNobilityEntity;
import com.tomatolive.library.p136ui.view.iview.IRecommendSearchView;

/* renamed from: com.tomatolive.library.ui.presenter.NobleRecommendPresenter */
/* loaded from: classes3.dex */
public class NobleRecommendPresenter extends BasePresenter<IRecommendSearchView> {
    public NobleRecommendPresenter(Context context, IRecommendSearchView iRecommendSearchView) {
        super(context, iRecommendSearchView);
    }

    public void searchAnchor(String str) {
        addMapSubscription(this.mApiService.searchLive(new RequestParams().getLiveId(str)), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.NobleRecommendPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IRecommendSearchView) NobleRecommendPresenter.this.getView()).onDataSuccess(anchorEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IRecommendSearchView) NobleRecommendPresenter.this.getView()).onDataFail();
            }
        }, true));
    }

    public void recommend(String str, String str2) {
        addMapSubscription(this.mApiService.recommend(new RequestParams().getRecommendParams(str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.NobleRecommendPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IRecommendSearchView) NobleRecommendPresenter.this.getView()).onRecommendSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IRecommendSearchView) NobleRecommendPresenter.this.getView()).onRecommendFail(i, str3);
            }
        }, true));
    }

    public void getRecommendCount() {
        addMapSubscription(this.mApiService.getMyNobilityService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<MyNobilityEntity>() { // from class: com.tomatolive.library.ui.presenter.NobleRecommendPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyNobilityEntity myNobilityEntity) {
                if (myNobilityEntity == null) {
                    return;
                }
                ((IRecommendSearchView) NobleRecommendPresenter.this.getView()).onRecommendCount(myNobilityEntity);
            }
        }));
    }
}
