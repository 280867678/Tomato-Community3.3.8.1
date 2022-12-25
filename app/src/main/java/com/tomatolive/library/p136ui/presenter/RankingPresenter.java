package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.widget.LinearLayout;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.iview.IRankingView;
import com.tomatolive.library.p136ui.view.widget.LoadingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.RankingPresenter */
/* loaded from: classes3.dex */
public class RankingPresenter extends BasePresenter<IRankingView> {
    public RankingPresenter(Context context, IRankingView iRankingView) {
        super(context, iRankingView);
    }

    public void getCharmTopList(StateView stateView, String str, final int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getCharmTopListService(new RequestParams().getHomeTopParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.RankingPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IRankingView) RankingPresenter.this.getView()).onCharmTopListSuccess(list, i, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((IRankingView) RankingPresenter.this.getView()).onResultError(i2);
            }
        }, stateView, z));
    }

    public void getStrengthTopList(StateView stateView, String str, final int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getStrengthTopListService(new RequestParams().getHomeStrengthTopParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.RankingPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IRankingView) RankingPresenter.this.getView()).onStrengthTopListSuccess(list, i, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((IRankingView) RankingPresenter.this.getView()).onResultError(i2);
            }
        }, stateView, z));
    }

    public void attentionAnchor(String str, int i) {
        addMapSubscription(this.mApiService.getAttentionAnchorService(new RequestParams().getAttentionAnchorParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.RankingPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IRankingView) RankingPresenter.this.getView()).onAttentionSuccess();
            }
        }));
    }

    public void getRankConfig(LoadingView loadingView, LinearLayout linearLayout) {
        if (loadingView != null) {
            loadingView.setVisibility(0);
            loadingView.showLoading();
        }
        addMapSubscription(this.mApiService.getIndexRankConfigService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<String>>() { // from class: com.tomatolive.library.ui.presenter.RankingPresenter.4
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<String> list) {
                ((IRankingView) RankingPresenter.this.getView()).onRankConfigSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IRankingView) RankingPresenter.this.getView()).onRankConfigFail();
            }
        }));
    }
}
