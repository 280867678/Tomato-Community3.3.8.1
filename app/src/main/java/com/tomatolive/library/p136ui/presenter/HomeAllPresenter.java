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
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.LiveAdEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.iview.IHomeAllView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.HomeAllPresenter */
/* loaded from: classes3.dex */
public class HomeAllPresenter extends BasePresenter<IHomeAllView> {
    public HomeAllPresenter(Context context, IHomeAllView iHomeAllView) {
        super(context, iHomeAllView);
    }

    public void getLiveList(StateView stateView, int i, boolean z, final boolean z2, LifecycleTransformer lifecycleTransformer) {
        this.mApiService.getV03AllListService(new RequestParams().getAdvChannelPageListParams(i)).map(new ServerResultFunction<HttpResultPageModel<LiveAdEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.2
        }).flatMap($$Lambda$HomeAllPresenter$f_Fn4XxGwl8qZzCKDjdjde1mBpM.INSTANCE).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(lifecycleTransformer).subscribe(new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeAllView) HomeAllPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, false, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str) {
                ((IHomeAllView) HomeAllPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z, false));
    }

    public void getLiveListFirst(StateView stateView, int i, boolean z, boolean z2, LifecycleTransformer lifecycleTransformer) {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            initLiveListFirst(stateView, i, z, z2, lifecycleTransformer);
            return;
        }
        if (stateView != null && z) {
            stateView.showLoading();
        }
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
            return;
        }
        TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelListener(getContext(), "5", new C41693(stateView, i, z, z2, lifecycleTransformer));
    }

    /* renamed from: com.tomatolive.library.ui.presenter.HomeAllPresenter$3 */
    /* loaded from: classes3.dex */
    class C41693 implements TomatoLiveSDK.OnAdvChannelCallbackListener {
        final /* synthetic */ boolean val$isDownRefresh;
        final /* synthetic */ boolean val$isShow;
        final /* synthetic */ LifecycleTransformer val$lifecycleTransformer;
        final /* synthetic */ int val$pageNum;
        final /* synthetic */ StateView val$stateLayout;

        C41693(StateView stateView, int i, boolean z, boolean z2, LifecycleTransformer lifecycleTransformer) {
            this.val$stateLayout = stateView;
            this.val$pageNum = i;
            this.val$isShow = z;
            this.val$isDownRefresh = z2;
            this.val$lifecycleTransformer = lifecycleTransformer;
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
        public void onAdvDataSuccess(Context context, String str) {
            try {
                if (TextUtils.isEmpty(str)) {
                    HomeAllPresenter.this.initLiveListFirst(this.val$stateLayout, this.val$pageNum, this.val$isShow, this.val$isDownRefresh, this.val$lifecycleTransformer);
                    return;
                }
                List list = (List) GsonUtils.fromJson(str, new TypeToken<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.3.1
                }.getType());
                if (list != null && !list.isEmpty()) {
                    HomeAllPresenter.this.initObservableZip(this.val$stateLayout, this.val$isShow, this.val$isDownRefresh, ((BasePresenter) HomeAllPresenter.this).mApiService.getV03AllListService(new RequestParams().getAdvChannelPageListParams(this.val$pageNum)).map(new ServerResultFunction<HttpResultPageModel<LiveAdEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.3.2
                    }).flatMap($$Lambda$HomeAllPresenter$3$1IwmLt4E78wB_y13XTEP7CcuhE.INSTANCE).onErrorResumeNext(new HttpResultFunction()), Observable.just(list), this.val$lifecycleTransformer);
                    return;
                }
                HomeAllPresenter.this.initLiveListFirst(this.val$stateLayout, this.val$pageNum, this.val$isShow, this.val$isDownRefresh, this.val$lifecycleTransformer);
            } catch (Exception unused) {
                HomeAllPresenter.this.initLiveListFirst(this.val$stateLayout, this.val$pageNum, this.val$isShow, this.val$isDownRefresh, this.val$lifecycleTransformer);
            }
        }

        @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
        public void onAdvDataFail(int i, String str) {
            HomeAllPresenter.this.initLiveListFirst(this.val$stateLayout, this.val$pageNum, this.val$isShow, this.val$isDownRefresh, this.val$lifecycleTransformer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLiveListFirst(StateView stateView, int i, boolean z, boolean z2, LifecycleTransformer lifecycleTransformer) {
        Observable<List<BannerEntity>> onErrorResumeNext;
        Observable<HttpResultPageModel<LiveEntity>> onErrorResumeNext2 = this.mApiService.getV03AllListService(new RequestParams().getAdvChannelPageListParams(i)).map(new ServerResultFunction<HttpResultPageModel<LiveAdEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.4
        }).flatMap($$Lambda$HomeAllPresenter$wfhwJusWh_z5Whdk2pl_dAbe06c.INSTANCE).onErrorResumeNext(new HttpResultFunction());
        if (CacheUtils.isBannerListByCacheDisk("5")) {
            onErrorResumeNext = Observable.just(CacheUtils.getBannerListByCacheDisk("5"));
        } else {
            onErrorResumeNext = this.mApiService.getBannerListService(new RequestParams().getBannerListParams("5")).map(new ServerResultFunction<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.5
            }).onErrorResumeNext(new HttpResultFunction());
        }
        initObservableZip(stateView, z, z2, onErrorResumeNext2, onErrorResumeNext, lifecycleTransformer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initObservableZip(StateView stateView, boolean z, final boolean z2, Observable<HttpResultPageModel<LiveEntity>> observable, Observable<List<BannerEntity>> observable2, LifecycleTransformer lifecycleTransformer) {
        Observable.zip(observable, observable2, $$Lambda$HomeAllPresenter$ArmQqLR41LSQb4pbdjvGPNslaBg.INSTANCE).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(lifecycleTransformer).subscribe(new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<LiveEntity>>() { // from class: com.tomatolive.library.ui.presenter.HomeAllPresenter.6
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<LiveEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IHomeAllView) HomeAllPresenter.this.getView()).onDataListSuccess(httpResultPageModel.dataList, httpResultPageModel.isHasBanner, httpResultPageModel.isMorePage(), z2);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IHomeAllView) HomeAllPresenter.this.getView()).onDataListFail(z2);
            }
        }, stateView, z, false));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ HttpResultPageModel lambda$initObservableZip$2(HttpResultPageModel httpResultPageModel, List list) throws Exception {
        if (list == null || list.isEmpty()) {
            httpResultPageModel.isHasBanner = false;
            return httpResultPageModel;
        }
        httpResultPageModel.isHasBanner = false;
        List<T> list2 = httpResultPageModel.dataList;
        if (list2 != 0 && list2.size() >= 6) {
            httpResultPageModel.isHasBanner = true;
            LiveEntity liveEntity = new LiveEntity();
            liveEntity.itemType = 2;
            liveEntity.bannerList = list;
            list2.add(6, liveEntity);
            CacheUtils.saveBannerListByCacheDisk("5", list);
        }
        return httpResultPageModel;
    }
}
