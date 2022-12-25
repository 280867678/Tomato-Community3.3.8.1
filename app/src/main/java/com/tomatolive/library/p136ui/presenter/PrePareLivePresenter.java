package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.CacheApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.AnchorStartLiveEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.model.CoverEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.LiveEndEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PayLiveEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.QMInteractTaskListEntity;
import com.tomatolive.library.model.RelationLastLiveEntity;
import com.tomatolive.library.model.StartLiveNotifyEntity;
import com.tomatolive.library.model.StartLiveVerifyEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import com.tomatolive.library.p136ui.view.iview.IPrepareLiveView;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.presenter.PrePareLivePresenter */
/* loaded from: classes3.dex */
public class PrePareLivePresenter extends BasePresenter<IPrepareLiveView> {
    private boolean isLiveLeaveAction = false;

    public PrePareLivePresenter(Context context, IPrepareLiveView iPrepareLiveView) {
        super(context, iPrepareLiveView);
    }

    public void startLive(String str, String str2, String str3, String str4, String str5, String str6, String str7, RelationLastLiveEntity relationLastLiveEntity) {
        addMapSubscription(this.mApiService.getStartLiveService(new RequestParams().getStartLiveParams(str, str2, str3, str4, str5, str6, str7, relationLastLiveEntity)), new HttpRxObserver(getContext(), new ResultCallBack<AnchorStartLiveEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorStartLiveEntity anchorStartLiveEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartLiveSuccess(anchorStartLiveEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str8) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartLiveFail();
            }
        }, true));
    }

    public void getTagList() {
        addMapSubscription(this.mApiService.getLabelListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<LabelEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LabelEntity> list) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onTagListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onTagListFail();
            }
        }));
    }

    public void getPreStartLiveInfo(final boolean z) {
        addMapSubscription(this.mApiService.getPreStartLiveInfoService(new RequestParams().getPreStartLiveInfoParams()), new HttpRxObserver(getContext(), new ResultCallBack<CoverEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.3
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(CoverEntity coverEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onPreStartLiveInfoSuccess(coverEntity, z);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onPreStartLiveInfoFail();
            }
        }));
    }

    public void getAnchorInfo(String str) {
        addMapSubscription(this.mApiService.getAnchorInfoService(new RequestParams().getAnchorInfoParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.4
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AnchorEntity anchorEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onAnchorInfoSuccess(anchorEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onAnchorInfoFail(i, str2);
            }
        }));
    }

    public void getLiveEndInfo(String str, String str2, final int i) {
        addMapSubscription(this.mApiService.getAnchorLiveDetailService(new RequestParams().getAnchorLiveInfoParams(str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<LiveEndEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.5
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveEndEntity liveEndEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveEndSuccess(i, liveEndEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str3) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveEndFail();
            }
        }), 3, 3);
    }

    public void getGiftList() {
        addMapSubscription(this.mApiService.giftListV2(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.6
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<GiftDownloadItemEntity> list) {
                if (list == null) {
                    return;
                }
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onGiftListSuccess(list);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onGiftListFail();
            }
        }));
    }

    public void getCurrentOnlineUserList(String str) {
        addMapSubscription(this.mApiService.getCurrentOnlineUserListService(new RequestParams().getCurrentOnlineUserList(str)), new SimpleRxObserver<OnLineUsersEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(OnLineUsersEntity onLineUsersEntity) {
                if (onLineUsersEntity == null) {
                    return;
                }
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveAudiencesSuccess(onLineUsersEntity);
            }
        });
    }

    public void uploadErrorReport(String str) {
        addMapSubscription(this.mApiService.getUploadErrorReportService(new RequestParams().getErrorReportParams(str)), new HttpRxObserver(getContext(), new ResultCallBack() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.8
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
            }
        }));
    }

    public void setEnterOrLeaveLiveRoomMsg(final String str, final String str2) {
        if (TextUtils.equals(ConstantUtils.ENTER_TYPE, str2)) {
            this.isLiveLeaveAction = false;
            Observable.timer(10L, TimeUnit.MINUTES, AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.9
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    PrePareLivePresenter.this.isLiveLeaveAction = true;
                    ((BasePresenter) PrePareLivePresenter.this).mApiService.getLiveEnterActionService(new RequestParams().getAnchorLiveActionParams(str, str2)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.9.2
                    }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.9.1
                        @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                        public void accept(Object obj) {
                        }
                    });
                }
            });
        } else if (!this.isLiveLeaveAction) {
        } else {
            this.mApiService.getLiveLeaveActionService(new RequestParams().getAnchorLiveActionParams(str, str2)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.11
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.10
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Object obj) {
                }
            });
        }
    }

    public void getLiveAdNoticeList() {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            initLiveAdNoticeList();
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelLiveNoticeListener(getContext(), new TomatoLiveSDK.OnAdvChannelCallbackListener() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.12
                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataSuccess(Context context, String str) {
                    try {
                        if (TextUtils.isEmpty(str)) {
                            PrePareLivePresenter.this.initLiveAdNoticeList();
                            return;
                        }
                        BannerEntity bannerEntity = (BannerEntity) GsonUtils.fromJson(str, (Class<Object>) BannerEntity.class);
                        if (bannerEntity == null) {
                            PrePareLivePresenter.this.initLiveAdNoticeList();
                        } else {
                            ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                        }
                    } catch (Exception unused) {
                        PrePareLivePresenter.this.initLiveAdNoticeList();
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataFail(int i, String str) {
                    PrePareLivePresenter.this.initLiveAdNoticeList();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLiveAdNoticeList() {
        if (CacheUtils.isLiveNoticeByCacheDisk()) {
            Observable.just(CacheUtils.getLiveNoticeByCacheDisk()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<BannerEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.13
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(BannerEntity bannerEntity) {
                    ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                }
            });
        } else {
            addMapSubscription(this.mApiService.getNoticeListService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<BannerEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.14
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(BannerEntity bannerEntity) {
                    if (bannerEntity == null) {
                        return;
                    }
                    CacheUtils.saveLiveNoticeByCacheDisk(bannerEntity);
                    ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                }
            }));
        }
    }

    public void getWebSocketAddress(String str, String str2) {
        addMapSubscription(this.mApiService.getWebSocketAddressService(new RequestParams().getWebSocketAddressParams(str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.15
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(LiveEntity liveEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onWebSocketAddressSuccess(liveEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onWebSocketAddressFail();
            }
        }));
    }

    public void getLivePopularity(String str) {
        CacheApiRetrofit.getInstance().getApiService().getLivePopularityService(UserInfoManager.getInstance().getAppId(), UserInfoManager.getInstance().getAppId(), str).map(new ServerResultFunction<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.18
        }).onErrorResumeNext(new HttpResultFunction<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.17
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<LiveEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.16
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(LiveEntity liveEntity) {
                if (liveEntity == null) {
                    return;
                }
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onLivePopularitySuccess(NumberUtils.string2long(liveEntity.popularity));
            }
        });
    }

    public void getGiftBoxList(String str) {
        addMapSubscription(this.mApiService.getGiftBoxListService(new RequestParams().getGiftBoxListParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<List<GiftBoxEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.19
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<GiftBoxEntity> list) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onGiftBoxListSuccess(list);
            }
        }));
    }

    public void showUserManageMenu(final int i, String str, final boolean z, final boolean z2, final String str2, final String str3) {
        addMapSubscription(this.mApiService.getJudgeUserBanPostService(new RequestParams().getJudgeUserBanPostParams(str, str3)), new HttpRxObserver(getContext(), new ResultCallBack<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.20
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str4) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UserEntity userEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onShowUserManageMenu(i, z, userEntity.isBanPostBoolean(), z2, str2, str3);
            }
        }));
    }

    public void usePopularCard(final PopularCardEntity popularCardEntity) {
        addMapSubscription(this.mApiService.usePopularityCardService(new RequestParams().getIdParams(popularCardEntity.f5844id)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.21
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onUsePopularSuccess(popularCardEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onUsePopularFail();
            }
        }));
    }

    public void getPopularCardRemainingTime() {
        addMapSubscription(this.mApiService.getPopularityCardAdditionRemainTimeService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<PopularCardEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.22
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null || popularCardEntity.remainTime <= 0) {
                    return;
                }
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onPoplarCardRemaining(popularCardEntity.remainTime);
            }
        }));
    }

    public void getRemainCount() {
        addMapSubscription(this.mApiService.getRemainCountService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<StartLiveNotifyEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.23
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(StartLiveNotifyEntity startLiveNotifyEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onRemainCountSuccess(startLiveNotifyEntity.count);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onRemainCountFail();
            }
        }));
    }

    public void onFeedbackSubmit(String str, int i, String str2, String str3) {
        addMapSubscription(this.mApiService.getAnchorFeedbackService(new RequestParams().getAnchorFeedbackParams(str, i, str2, str3)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.24
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str4) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onFeedbackSuccess();
            }
        }, true));
    }

    public void onStartLiveSubmit(boolean z, final String str, final String str2, final String str3, final String str4, final RelationLastLiveEntity relationLastLiveEntity) {
        if (z) {
            addMapSubscription(this.mApiService.getPayLiveSubmitService(new RequestParams().getStartLiveSubmitParams(str, str2, str3, str4, relationLastLiveEntity)), new HttpRxObserver(getContext(), new ResultCallBack<PayLiveEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.25
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str5) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(PayLiveEntity payLiveEntity) {
                    String str5 = payLiveEntity != null ? payLiveEntity.newPushStream : "";
                    ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartLiveSubmitSuccess(true, str, str2, str3, str4, str5, !TextUtils.isEmpty(str5) ? NumberUtils.string2long(payLiveEntity.changeTime, 60L) : 0L, relationLastLiveEntity);
                }
            }, true));
        } else {
            addMapSubscription(this.mApiService.getAnchorStartLiveSubmitService(new RequestParams().getStartLiveSubmitParams(str, str2, str3, str4, relationLastLiveEntity)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.26
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i, String str5) {
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(Object obj) {
                    ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartLiveSubmitSuccess(false, str, str2, str3, str4, null, 0L, relationLastLiveEntity);
                }
            }, true));
        }
    }

    public void onPayStartLiveVerify(final boolean z) {
        addMapSubscription(this.mApiService.getAnchorStartPayLiveService(new RequestParams().getStartPayLiveVerifyParams()), new HttpRxObserver(getContext(), new ResultCallBack<StartLiveVerifyEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.27
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(StartLiveVerifyEntity startLiveVerifyEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartPayLiveVerifySuccess(z, startLiveVerifyEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onStartPayLiveVerifyFail();
            }
        }, true));
    }

    public void initLocalComponentsCache() {
        if (TomatoLiveSDK.getSingleton().isLiveGameChannel()) {
            ApiRetrofit.getInstance().getApiService().getGameComponentService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.29
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<ComponentsEntity>>(false) { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.28
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<ComponentsEntity> list) {
                    CacheUtils.saveLocalComponentsCache(list);
                }
            });
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            ApiRetrofit.getInstance().getApiService().getGameComponentService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.32
            }).flatMap(new Function<List<ComponentsEntity>, ObservableSource<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.31
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public ObservableSource<ComponentsEntity> mo6755apply(List<ComponentsEntity> list) throws Exception {
                    ComponentsEntity componentsEntity;
                    Iterator<ComponentsEntity> it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            componentsEntity = null;
                            break;
                        }
                        componentsEntity = it2.next();
                        if (componentsEntity.isCacheLotteryComponents()) {
                            break;
                        }
                    }
                    return Observable.just(componentsEntity);
                }
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<ComponentsEntity>(false) { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.30
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(ComponentsEntity componentsEntity) {
                    PrePareLivePresenter.this.loadChannelComponentsData(componentsEntity);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    PrePareLivePresenter.this.loadChannelComponentsData(null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadChannelComponentsData(final ComponentsEntity componentsEntity) {
        TomatoLiveSDK.getSingleton().sdkCallbackListener.onAppCommonCallbackListener(getContext(), 274, new TomatoLiveSDK.OnCommonCallbackListener() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.33
            @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
            public void onDataFail(Throwable th, int i) {
            }

            @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
            public void onDataSuccess(Context context, Object obj) {
                if (obj != null && (obj instanceof String)) {
                    try {
                        List list = (List) GsonUtils.fromJson((String) obj, new TypeToken<List<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.33.1
                        }.getType());
                        if (list != null && !list.isEmpty()) {
                            if (componentsEntity != null) {
                                list.add(componentsEntity);
                            }
                            CacheUtils.saveLocalComponentsCache(list);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    public void changeLiveStream(long j, final ResultCallBack<Object> resultCallBack) {
        Observable.timer(j, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.34
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                ApiRetrofit.getInstance().getApiService().changeLiveStreamService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.34.2
                }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.34.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onError(Throwable th) {
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(Object obj) {
                        ResultCallBack resultCallBack2 = resultCallBack;
                        if (resultCallBack2 != null) {
                            resultCallBack2.onSuccess(null);
                        }
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void onError(int i, String str) {
                        super.onError(i, str);
                        ResultCallBack resultCallBack2 = resultCallBack;
                        if (resultCallBack2 != null) {
                            resultCallBack2.onError(i, str);
                        }
                    }
                });
            }
        });
    }

    public void getUserCardInfo(String str) {
        this.mApiService.getUserCardInfo(new RequestParams().getUserCardInfoParams(str)).map(new ServerResultFunction<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.36
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<UserEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.35
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(UserEntity userEntity) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onUserCardInfoSuccess(userEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str2) {
                ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onUserCardInfoFail(i, str2);
            }
        });
    }

    public void sendAnchorShowTaskListRequest(long j, final String str) {
        Observable.timer(j, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.37
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                ApiRetrofit.getInstance().getApiService().anchorShowTaskListService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.37.2
                }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.presenter.PrePareLivePresenter.37.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(QMInteractTaskListEntity qMInteractTaskListEntity) {
                        List<QMInteractTaskEntity> list;
                        if (qMInteractTaskListEntity == null || (list = qMInteractTaskListEntity.intimateTaskList) == null || list.isEmpty()) {
                            return;
                        }
                        ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onQMInteractShowTaskSuccess(list);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onError(Throwable th) {
                        ((IPrepareLiveView) PrePareLivePresenter.this.getView()).onQMInteractShowTaskFail();
                    }
                });
            }
        });
    }
}
