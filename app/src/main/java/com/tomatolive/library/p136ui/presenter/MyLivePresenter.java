package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.model.MessageDetailEntity;
import com.tomatolive.library.model.MyLiveEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.view.iview.IMyLiveView;
import com.tomatolive.library.p136ui.view.widget.StateView;

/* renamed from: com.tomatolive.library.ui.presenter.MyLivePresenter */
/* loaded from: classes3.dex */
public class MyLivePresenter extends BasePresenter<IMyLiveView> {
    public MyLivePresenter(Context context, IMyLiveView iMyLiveView) {
        super(context, iMyLiveView);
    }

    public void initData(StateView stateView, boolean z) {
        addMapSubscription(this.mApiService.getMyLiveInitDataService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<MyLiveEntity>() { // from class: com.tomatolive.library.ui.presenter.MyLivePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(MyLiveEntity myLiveEntity) {
                ((IMyLiveView) MyLivePresenter.this.getView()).onDataSuccess(myLiveEntity);
            }
        }, stateView, z));
    }

    public void onAnchorAuth() {
        addMapSubscription(this.mApiService.getAnchorAuthService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.MyLivePresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IMyLiveView) MyLivePresenter.this.getView()).onAnchorAuthSuccess(anchorEntity);
            }
        }, true));
    }

    public void getUserGradeData() {
        addMapSubscription(this.mApiService.getUserInfoService(new RequestParams().getUserIdByIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.MyLivePresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UserEntity userEntity) {
                if (userEntity == null) {
                    return;
                }
                ((IMyLiveView) MyLivePresenter.this.getView()).onUserGradeSuccess(userEntity);
            }
        }));
    }

    public void getAnchorGradeData() {
        addMapSubscription(this.mApiService.getAnchorInfoService(new RequestParams().getUserIdByIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.MyLivePresenter.4
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IMyLiveView) MyLivePresenter.this.getView()).onAnchorGradeSuccess(anchorEntity);
            }
        }));
    }

    public void getLiveHelperAppConfig() {
        addMapSubscription(this.mApiService.getStartLiveAppConfigService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<LiveHelperAppConfigEntity>() { // from class: com.tomatolive.library.ui.presenter.MyLivePresenter.5
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
                ((IMyLiveView) MyLivePresenter.this.getView()).onLiveHelperAppConfigSuccess(liveHelperAppConfigEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IMyLiveView) MyLivePresenter.this.getView()).onLiveHelperAppConfigFail();
            }
        }));
    }

    public void getUnReadFlag(boolean z, ResultCallBack<MessageDetailEntity> resultCallBack) {
        if (z) {
            addMapSubscription(this.mApiService.getWinningUnReadService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), resultCallBack));
        } else {
            addMapSubscription(this.mApiService.getSendPrizeUnReadService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), resultCallBack));
        }
    }
}
