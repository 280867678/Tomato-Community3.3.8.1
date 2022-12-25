package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyNobilityEntity;
import com.tomatolive.library.p136ui.view.iview.INobilityPrivilegeView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.NobilityPrivilegePresenter */
/* loaded from: classes3.dex */
public class NobilityPrivilegePresenter extends BasePresenter<INobilityPrivilegeView> {
    public NobilityPrivilegePresenter(Context context, INobilityPrivilegeView iNobilityPrivilegeView) {
        super(context, iNobilityPrivilegeView);
    }

    public void getInitData(StateView stateView) {
        addMapSubscription(this.mApiService.getMyNobilityService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<MyNobilityEntity>() { // from class: com.tomatolive.library.ui.presenter.NobilityPrivilegePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyNobilityEntity myNobilityEntity) {
                if (myNobilityEntity == null) {
                    return;
                }
                ((INobilityPrivilegeView) NobilityPrivilegePresenter.this.getView()).onDataSuccess(myNobilityEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((INobilityPrivilegeView) NobilityPrivilegePresenter.this.getView()).onDataFail();
            }
        }, stateView, true));
    }

    public void setEnterHide(final boolean z) {
        addMapSubscription(this.mApiService.getEnterHideService(new RequestParams().getNobilityEnterHideParams(z)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.NobilityPrivilegePresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((INobilityPrivilegeView) NobilityPrivilegePresenter.this.getView()).onEnterHideSuccess(z);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((INobilityPrivilegeView) NobilityPrivilegePresenter.this.getView()).onEnterHideFail(str);
            }
        }));
    }
}
