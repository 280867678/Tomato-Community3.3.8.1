package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.MyCarEntity;
import com.tomatolive.library.p136ui.view.iview.IMyCarView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.MyCarPresenter */
/* loaded from: classes3.dex */
public class MyCarPresenter extends BasePresenter<IMyCarView> {
    public MyCarPresenter(Context context, IMyCarView iMyCarView) {
        super(context, iMyCarView);
    }

    public void useCar(final MyCarEntity myCarEntity) {
        addMapSubscription(this.mApiService.getUseCarService(new RequestParams().getUseCarParams(myCarEntity.uniqueId, myCarEntity.isUsed)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.MyCarPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                if (obj == null) {
                    return;
                }
                ((IMyCarView) MyCarPresenter.this.getView()).onUseCarSuccess(myCarEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IMyCarView) MyCarPresenter.this.getView()).onUseCarFail();
            }
        }));
    }

    public void getMyCar(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getMyCarListService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<MyCarEntity>>() { // from class: com.tomatolive.library.ui.presenter.MyCarPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<MyCarEntity> list) {
                if (list == null) {
                    return;
                }
                ((IMyCarView) MyCarPresenter.this.getView()).onDataListSuccess(list, true, z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((IMyCarView) MyCarPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }
}
