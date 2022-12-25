package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.iview.IRankingFragmentView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.RankingFragmentPresenter */
/* loaded from: classes3.dex */
public class RankingFragmentPresenter extends BasePresenter<IRankingFragmentView> {
    public RankingFragmentPresenter(Context context, IRankingFragmentView iRankingFragmentView) {
        super(context, iRankingFragmentView);
    }

    public void getCharmTopList(StateView stateView, String str, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getCharmTopListService(new RequestParams().getHomeTopParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.RankingFragmentPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IRankingFragmentView) RankingFragmentPresenter.this.getView()).onDataListSuccess(list, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IRankingFragmentView) RankingFragmentPresenter.this.getView()).onResultError(i);
            }
        }, stateView, z));
    }

    public void getStrengthTopList(StateView stateView, String str, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getStrengthTopListService(new RequestParams().getHomeStrengthTopParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.RankingFragmentPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IRankingFragmentView) RankingFragmentPresenter.this.getView()).onDataListSuccess(list, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IRankingFragmentView) RankingFragmentPresenter.this.getView()).onResultError(i);
            }
        }, stateView, z));
    }

    public void attentionAnchor(String str, int i) {
        addMapSubscription(this.mApiService.getAttentionAnchorService(new RequestParams().getAttentionAnchorParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.RankingFragmentPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IRankingFragmentView) RankingFragmentPresenter.this.getView()).onAttentionSuccess();
            }
        }));
    }
}
