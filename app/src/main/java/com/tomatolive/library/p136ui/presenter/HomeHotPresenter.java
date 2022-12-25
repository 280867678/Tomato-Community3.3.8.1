package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.model.LiveAdEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveHelperAppConfigEntity;
import com.tomatolive.library.p136ui.view.iview.IHomeHotView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.HomeHotPresenter */
/* loaded from: classes3.dex */
public class HomeHotPresenter extends BasePresenter<IHomeHotView> {
    public HomeHotPresenter(Context context, IHomeHotView iHomeHotView) {
        super(context, iHomeHotView);
    }

    public void getLiveList(StateView stateView, int i, boolean z, final boolean z2, LifecycleTransformer lifecycleTransformer) {
        this.mApiService.getV03RecommendListService(new RequestParams().getAdvChannelPageListParams(i)).map(new ServerResultFunction<HttpResultPageModel<LiveAdEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.2
        }).flatMap($$Lambda$HomeHotPresenter$CTXuWjdk5JsC9TujC_hFBPGqq8.INSTANCE).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(lifecycleTransformer).subscribe(new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeHotView) HomeHotPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((IHomeHotView) HomeHotPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z, false));
    }

    public void onAnchorAuth() {
        addMapSubscription(this.mApiService.getAnchorAuthService(new RequestParams().getUserIdParams()), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                ((IHomeHotView) HomeHotPresenter.this.getView()).onAnchorAuthSuccess(anchorEntity);
            }
        }, true));
    }

    public void getBannerList(final String str) {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            initBannerList(str);
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelListener(getContext(), str, new TomatoLiveSDK.OnAdvChannelCallbackListener() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.4
                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataSuccess(Context context, String str2) {
                    try {
                        if (TextUtils.isEmpty(str2)) {
                            HomeHotPresenter.this.initBannerList(str);
                            return;
                        }
                        List<BannerEntity> list = (List) GsonUtils.fromJson(str2, new TypeToken<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.4.1
                        }.getType());
                        if (list != null && !list.isEmpty()) {
                            ((IHomeHotView) HomeHotPresenter.this.getView()).onBannerListSuccess(list);
                            return;
                        }
                        HomeHotPresenter.this.initBannerList(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                        HomeHotPresenter.this.initBannerList(str);
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataFail(int i, String str2) {
                    HomeHotPresenter.this.initBannerList(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBannerList(final String str) {
        if (CacheUtils.isBannerListByCacheDisk(str)) {
            Observable.just(CacheUtils.getBannerListByCacheDisk(str)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.5
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<BannerEntity> list) {
                    ((IHomeHotView) HomeHotPresenter.this.getView()).onBannerListSuccess(list);
                }
            });
        } else {
            addMapSubscription(this.mApiService.getBannerListService(new RequestParams().getBannerListParams(str)), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.6
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str2) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(List<BannerEntity> list) {
                    CacheUtils.saveBannerListByCacheDisk(str, list);
                    ((IHomeHotView) HomeHotPresenter.this.getView()).onBannerListSuccess(list);
                }
            }, false, false));
        }
    }

    public void getTopList() {
        addMapSubscription(this.mApiService.getIndexRankService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<List<IndexRankEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.7
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<IndexRankEntity> list) {
                if (list == null) {
                    return;
                }
                ((IHomeHotView) HomeHotPresenter.this.getView()).onTopListSuccess(list);
            }
        }, false, false));
    }

    public void getAllLiveList(LifecycleTransformer lifecycleTransformer) {
        TomatoLiveSDK.getSingleton().onAllLiveListUpdate(lifecycleTransformer);
    }

    public void getLiveHelperAppConfig() {
        addMapSubscription(this.mApiService.getStartLiveAppConfigService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<LiveHelperAppConfigEntity>() { // from class: com.tomatolive.library.ui.presenter.HomeHotPresenter.8
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveHelperAppConfigEntity liveHelperAppConfigEntity) {
                ((IHomeHotView) HomeHotPresenter.this.getView()).onLiveHelperAppConfigSuccess(liveHelperAppConfigEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IHomeHotView) HomeHotPresenter.this.getView()).onLiveHelperAppConfigFail();
            }
        }));
    }
}
