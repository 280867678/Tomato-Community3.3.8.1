package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AppealInfoEntity;
import com.tomatolive.library.p136ui.view.iview.IAppealInfoView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.AppealDetailPresenter */
/* loaded from: classes3.dex */
public class AppealDetailPresenter extends BasePresenter<IAppealInfoView> {
    public AppealDetailPresenter(Context context, IAppealInfoView iAppealInfoView) {
        super(context, iAppealInfoView);
    }

    public void getAppealInfo(StateView stateView, String str, boolean z) {
        addMapSubscription(this.mApiService.getAppealInfoService(new RequestParams().getAppealInfoParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<AppealInfoEntity>() { // from class: com.tomatolive.library.ui.presenter.AppealDetailPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AppealInfoEntity appealInfoEntity) {
                ((IAppealInfoView) AppealDetailPresenter.this.getView()).onGetAppealInfoSuccess(appealInfoEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IAppealInfoView) AppealDetailPresenter.this.getView()).onGetAppealInfoFailure(i, str2);
            }
        }, stateView, z));
    }

    public void cancelAppeal(String str) {
        addMapSubscription(this.mApiService.cancelAppealInfoService(new RequestParams().getAppealInfoParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.AppealDetailPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IAppealInfoView) AppealDetailPresenter.this.getView()).onCancelAppealSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IAppealInfoView) AppealDetailPresenter.this.getView()).onCancelAppealFailure();
            }
        }));
    }
}
