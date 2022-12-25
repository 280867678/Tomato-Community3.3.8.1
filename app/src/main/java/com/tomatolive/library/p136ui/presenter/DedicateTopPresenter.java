package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.iview.IDedicateTopView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.DedicateTopPresenter */
/* loaded from: classes3.dex */
public class DedicateTopPresenter extends BasePresenter<IDedicateTopView> {
    public DedicateTopPresenter(Context context, IDedicateTopView iDedicateTopView) {
        super(context, iDedicateTopView);
    }

    public void getDataList(StateView stateView, String str, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getDedicateTopListService(new RequestParams().getContributionListParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.presenter.DedicateTopPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                ((IDedicateTopView) DedicateTopPresenter.this.getView()).onDataListSuccess(list, false, z2);
            }
        }, stateView, z));
    }
}
