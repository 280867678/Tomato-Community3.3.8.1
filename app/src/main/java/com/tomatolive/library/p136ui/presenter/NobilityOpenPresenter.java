package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.NobilityOpenPresenter */
/* loaded from: classes3.dex */
public class NobilityOpenPresenter extends BasePresenter<INobilityOpenView> {
    public NobilityOpenPresenter(Context context, INobilityOpenView iNobilityOpenView) {
        super(context, iNobilityOpenView);
    }

    public void getInitData(StateView stateView) {
        addMapSubscription(this.mApiService.getNobilityListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<NobilityEntity>>() { // from class: com.tomatolive.library.ui.presenter.NobilityOpenPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<NobilityEntity> list) {
                if (list == null) {
                    return;
                }
                ((INobilityOpenView) NobilityOpenPresenter.this.getView()).onInitDataSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((INobilityOpenView) NobilityOpenPresenter.this.getView()).onInitDataFail();
            }
        }, stateView, true));
    }
}
