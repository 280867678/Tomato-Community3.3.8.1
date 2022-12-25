package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.view.iview.IHomeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.HomePresenter */
/* loaded from: classes3.dex */
public class HomePresenter extends BasePresenter<IHomeView> {
    public HomePresenter(Context context, IHomeView iHomeView) {
        super(context, iHomeView);
    }

    public void sendInitRequest(final StateView stateView, final boolean z) {
        if (isApiService()) {
            ApiRetrofit.getInstance().getApiService().getUpdateTokenService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.HomePresenter.3
            }).onErrorResumeNext(new HttpResultFunction<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.HomePresenter.2
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<UserEntity>(false) { // from class: com.tomatolive.library.ui.presenter.HomePresenter.1
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    StateView stateView2;
                    super.onSubscribe(disposable);
                    if (!z || (stateView2 = stateView) == null) {
                        return;
                    }
                    stateView2.showLoading();
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(UserEntity userEntity) {
                    if (userEntity == null) {
                        return;
                    }
                    UserInfoManager.getInstance().setToken(userEntity.getToken());
                    HomePresenter.this.initRequest(stateView, false);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    super.onError(th);
                    HomePresenter.this.initRequest(stateView, false);
                }
            });
        } else if (stateView == null) {
        } else {
            stateView.showRetry();
        }
    }

    public void getTagList(final StateView stateView, final boolean z) {
        ApiRetrofit.getInstance().getApiService().getLabelListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomePresenter.6
        }).onErrorResumeNext(new HttpResultFunction<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomePresenter.5
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<LabelEntity>>(false) { // from class: com.tomatolive.library.ui.presenter.HomePresenter.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                StateView stateView2;
                super.onSubscribe(disposable);
                if (!z || (stateView2 = stateView) == null) {
                    return;
                }
                stateView2.showLoading();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<LabelEntity> list) {
                StateView stateView2 = stateView;
                if (stateView2 != null) {
                    stateView2.showContent();
                }
                ((IHomeView) HomePresenter.this.getView()).onTagListSuccess(list);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                StateView stateView2 = stateView;
                if (stateView2 != null) {
                    stateView2.showContent();
                }
                ((IHomeView) HomePresenter.this.getView()).onTagListFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRequest(StateView stateView, boolean z) {
        TomatoLiveSDK.getSingleton().initSysConfig();
        getTagList(stateView, z);
    }
}
