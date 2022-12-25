package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.p136ui.view.iview.IHiddenInRankListView;

/* renamed from: com.tomatolive.library.ui.presenter.RankHiddenPresenter */
/* loaded from: classes3.dex */
public class RankHiddenPresenter extends BasePresenter<IHiddenInRankListView> {
    public RankHiddenPresenter(Context context, IHiddenInRankListView iHiddenInRankListView) {
        super(context, iHiddenInRankListView);
    }

    public void setHiddenInRankList(boolean z) {
        addMapSubscription(this.mApiService.setHiddenInRankList(new RequestParams().getRankHiddenParams(z)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.RankHiddenPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((IHiddenInRankListView) RankHiddenPresenter.this.getView()).onModifySuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IHiddenInRankListView) RankHiddenPresenter.this.getView()).onModifyFail(str);
            }
        }));
    }
}
