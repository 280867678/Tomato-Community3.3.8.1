package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.iview.IAnchorGradeView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.AnchorGradePresenter */
/* loaded from: classes3.dex */
public class AnchorGradePresenter extends BasePresenter<IAnchorGradeView> {
    public AnchorGradePresenter(Context context, IAnchorGradeView iAnchorGradeView) {
        super(context, iAnchorGradeView);
    }

    public void getData(StateView stateView, boolean z) {
        addMapSubscription(this.mApiService.getAnchorInfoService(new RequestParams().getPreStartLiveInfoParams()), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.AnchorGradePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IAnchorGradeView) AnchorGradePresenter.this.getView()).onDataSuccess(anchorEntity);
            }
        }, stateView, z));
    }
}
