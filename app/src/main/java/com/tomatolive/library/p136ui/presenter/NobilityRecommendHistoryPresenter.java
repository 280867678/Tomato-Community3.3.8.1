package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.NobilityRecommendHistoryEntity;
import com.tomatolive.library.p136ui.view.iview.INobilityRecommendHistoryView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.NobilityRecommendHistoryPresenter */
/* loaded from: classes3.dex */
public class NobilityRecommendHistoryPresenter extends BasePresenter<INobilityRecommendHistoryView> {
    public NobilityRecommendHistoryPresenter(Context context, INobilityRecommendHistoryView iNobilityRecommendHistoryView) {
        super(context, iNobilityRecommendHistoryView);
    }

    public void getDataList(StateView stateView, int i, boolean z, final boolean z2) {
        addMapSubscription(this.mApiService.getNobilityRecommendListtService(new RequestParams().getPageListParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<NobilityRecommendHistoryEntity>>() { // from class: com.tomatolive.library.ui.presenter.NobilityRecommendHistoryPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<NobilityRecommendHistoryEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((INobilityRecommendHistoryView) NobilityRecommendHistoryPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((INobilityRecommendHistoryView) NobilityRecommendHistoryPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z));
    }

    private List<NobilityRecommendHistoryEntity> getList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            NobilityRecommendHistoryEntity nobilityRecommendHistoryEntity = new NobilityRecommendHistoryEntity();
            nobilityRecommendHistoryEntity.anchorName = "安卓用户0001" + i;
            nobilityRecommendHistoryEntity.createTime = "1568801780";
            nobilityRecommendHistoryEntity.startTime = "10:00";
            nobilityRecommendHistoryEntity.endTime = "16:00";
            nobilityRecommendHistoryEntity.anonymous = "" + i;
            nobilityRecommendHistoryEntity.status = "" + i;
            arrayList.add(nobilityRecommendHistoryEntity);
        }
        return arrayList;
    }
}
