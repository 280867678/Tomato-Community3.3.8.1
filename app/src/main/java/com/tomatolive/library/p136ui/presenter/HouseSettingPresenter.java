package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.BannedEntity;
import com.tomatolive.library.p136ui.view.iview.IHouseSettingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.HouseSettingPresenter */
/* loaded from: classes3.dex */
public class HouseSettingPresenter extends BasePresenter<IHouseSettingView> {
    public HouseSettingPresenter(Context context, IHouseSettingView iHouseSettingView) {
        super(context, iHouseSettingView);
    }

    public void getDataList(StateView stateView, boolean z, String str, int i, boolean z2, final boolean z3) {
        if (z) {
            getSearchUsersList(stateView, str, z2);
        } else {
            addMapSubscription(this.mApiService.getHouseListService(new RequestParams().getAnchorIdParams(i)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<BannedEntity>>() { // from class: com.tomatolive.library.ui.presenter.HouseSettingPresenter.1
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<BannedEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IHouseSettingView) HouseSettingPresenter.this.getView()).onDataListSuccess(httpResultPageModel.totalRowsCount, httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z3);
                }
            }, stateView, z2));
        }
    }

    public void getSearchUsersList(StateView stateView, String str, boolean z) {
        addMapSubscription(this.mApiService.getSearchUserListService(new RequestParams().getSearchUsersParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<BannedEntity>>() { // from class: com.tomatolive.library.ui.presenter.HouseSettingPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<BannedEntity> list) {
                ((IHouseSettingView) HouseSettingPresenter.this.getView()).onSearchDataListSuccess(list);
            }
        }, stateView, z));
    }

    public void houseSetting(String str, int i, final int i2, final BannedEntity bannedEntity) {
        addMapSubscription(this.mApiService.getHouseManagerSetService(new RequestParams().getHouseSettingParams(str, i)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.HouseSettingPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i3, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IHouseSettingView) HouseSettingPresenter.this.getView()).onHouseSettingSuccess(i2, bannedEntity);
            }
        }, true));
    }
}
