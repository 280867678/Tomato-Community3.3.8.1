package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.p136ui.view.iview.IMyAccountView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.MyAccountPresenter */
/* loaded from: classes3.dex */
public class MyAccountPresenter extends BasePresenter<IMyAccountView> {
    public MyAccountPresenter(Context context, IMyAccountView iMyAccountView) {
        super(context, iMyAccountView);
    }

    public void getUserOver(StateView stateView) {
        addMapSubscription(this.mApiService.getQueryBalanceService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.MyAccountPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyAccountEntity myAccountEntity) {
                if (myAccountEntity == null) {
                    ((IMyAccountView) MyAccountPresenter.this.getView()).onUserOverFail();
                } else {
                    ((IMyAccountView) MyAccountPresenter.this.getView()).onUserOverSuccess(myAccountEntity);
                }
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IMyAccountView) MyAccountPresenter.this.getView()).onUserOverFail();
            }
        }, stateView, true));
    }
}
