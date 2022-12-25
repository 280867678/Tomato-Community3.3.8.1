package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.p136ui.view.iview.IAnchorAuthResultView;

/* renamed from: com.tomatolive.library.ui.presenter.AnchorAuthResultPresenter */
/* loaded from: classes3.dex */
public class AnchorAuthResultPresenter extends BasePresenter<IAnchorAuthResultView> {
    public AnchorAuthResultPresenter(Context context, IAnchorAuthResultView iAnchorAuthResultView) {
        super(context, iAnchorAuthResultView);
    }

    public void onAnchorAuth() {
        addMapSubscription(this.mApiService.getAnchorAuthService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthResultPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IAnchorAuthResultView) AnchorAuthResultPresenter.this.getView()).onAnchorAuthSuccess(anchorEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IAnchorAuthResultView) AnchorAuthResultPresenter.this.getView()).onLiveListFail();
            }
        }));
    }

    public void onCustomerService() {
        addMapSubscription(this.mApiService.getStartLiveAppConfigService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<LiveHelperAppConfigEntity>() { // from class: com.tomatolive.library.ui.presenter.AnchorAuthResultPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
                ((IAnchorAuthResultView) AnchorAuthResultPresenter.this.getView()).onCustomerServiceSuccess(liveHelperAppConfigEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IAnchorAuthResultView) AnchorAuthResultPresenter.this.getView()).onCustomerServiceFail();
            }
        }));
    }
}
