package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.view.iview.IUserGradeView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.UserGradePresenter */
/* loaded from: classes3.dex */
public class UserGradePresenter extends BasePresenter<IUserGradeView> {
    public UserGradePresenter(Context context, IUserGradeView iUserGradeView) {
        super(context, iUserGradeView);
    }

    public void getData(StateView stateView, boolean z) {
        addMapSubscription(this.mApiService.getUserInfoService(new RequestParams().getUserIdByIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.UserGradePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UserEntity userEntity) {
                if (userEntity == null) {
                    return;
                }
                ((IUserGradeView) UserGradePresenter.this.getView()).onDataSuccess(userEntity);
            }
        }, stateView, z));
    }
}
