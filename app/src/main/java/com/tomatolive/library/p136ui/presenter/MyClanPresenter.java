package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyClanEntity;
import com.tomatolive.library.p136ui.view.iview.IMyClanView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.MyClanPresenter */
/* loaded from: classes3.dex */
public class MyClanPresenter extends BasePresenter<IMyClanView> {
    public MyClanPresenter(Context context, IMyClanView iMyClanView) {
        super(context, iMyClanView);
    }

    public void getDataList(StateView stateView, int i, int i2, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getMyClanListService(new RequestParams().getMyClanListParams(i, i2)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MyClanEntity>>() { // from class: com.tomatolive.library.ui.presenter.MyClanPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<MyClanEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IMyClanView) MyClanPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i3, String str) {
                ((IMyClanView) MyClanPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }
}
