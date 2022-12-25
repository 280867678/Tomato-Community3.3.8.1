package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.CacheApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.BoomStatusEntity;
import com.tomatolive.library.model.CheckTicketEntity;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.LiveInitInfoEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.model.OnLineUsersEntity;
import com.tomatolive.library.model.PKRecordEntity;
import com.tomatolive.library.model.PropConfigEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.QMInteractTaskListEntity;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.model.TrumpetStatusEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.p135db.GiftBoxEntity;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.iview.ITomatoLiveView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.CacheUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GsonUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.presenter.TomatoLivePresenter */
/* loaded from: classes3.dex */
public class TomatoLivePresenter extends BasePresenter<ITomatoLiveView> {
    private CompositeDisposable compositeDisposable;
    private boolean isLiveLeaveAction = false;
    private LoadingDialog dialog = null;

    public TomatoLivePresenter(Context context, ITomatoLiveView iTomatoLiveView) {
        super(context, iTomatoLiveView);
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingDialog(boolean z) {
        LoadingDialog loadingDialog;
        if (this.dialog == null) {
            this.dialog = new LoadingDialog(getContext());
        }
        if (!z || (loadingDialog = this.dialog) == null || loadingDialog.isShowing()) {
            return;
        }
        this.dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        try {
            if (this.dialog == null || !this.dialog.isShowing()) {
                return;
            }
            this.dialog.dismiss();
        } catch (Exception unused) {
        }
    }

    public void setEnterOrLeaveLiveRoomMsg(final String str) {
        if (AppUtils.isVisitor()) {
            return;
        }
        if (TextUtils.equals(ConstantUtils.ENTER_TYPE, str)) {
            this.isLiveLeaveAction = false;
            compositeDisposableAdd(Observable.timer(10L, TimeUnit.MINUTES, AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    TomatoLivePresenter.this.isLiveLeaveAction = true;
                    ((BasePresenter) TomatoLivePresenter.this).mApiService.getLiveEnterActionService(new RequestParams().getUserLiveActionParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.1.2
                    }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>(TomatoLivePresenter.this.getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.1.1
                        @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                        public void accept(Object obj) {
                        }

                        @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                            super.onSubscribe(disposable);
                            TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                        }
                    });
                }
            }));
        } else if (!this.isLiveLeaveAction) {
        } else {
            this.mApiService.getLiveLeaveActionService(new RequestParams().getUserLiveActionParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.3
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.2
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(Object obj) {
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                    TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                }
            });
        }
    }

    public void getGiftList() {
        this.mApiService.giftListV2(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.6
        }).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.5
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                GiftDownLoadManager.getInstance().updateLocalDownloadList(list);
                ArrayList arrayList = new ArrayList();
                for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
                    if (!giftDownloadItemEntity.isLuckyGift()) {
                        arrayList.add(giftDownloadItemEntity);
                    }
                }
                return Observable.just(arrayList);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<GiftDownloadItemEntity>>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<GiftDownloadItemEntity> list) {
                if (list == null) {
                    return;
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onGiftListSuccess(list);
            }
        });
    }

    public void attentionAnchor(String str, int i) {
        this.mApiService.getAttentionAnchorService(new RequestParams().getAttentionAnchorParams(str, i)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.8
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onAttentionSuccess();
            }
        });
    }

    public void getAnchorInfo(String str) {
        ApiRetrofit.getInstance().getApiService().getAnchorInfoService(new RequestParams().getAnchorInfoParams(str)).map(new ServerResultFunction<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.10
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<AnchorEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(AnchorEntity anchorEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onAnchorInfoSuccess(anchorEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onAnchorInfoFail();
            }
        });
    }

    public void getUserCardInfo(String str) {
        this.mApiService.getUserCardInfo(new RequestParams().getUserCardInfoParams(str)).map(new ServerResultFunction<UserEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.12
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<UserEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.11
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(UserEntity userEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserCardInfoSuccess(userEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str2) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserCardInfoFail(i, str2);
            }
        });
    }

    public void getUserOver() {
        getUserOver(false, null);
    }

    public void getUserOver(final boolean z, final ResultCallBack<MyAccountEntity> resultCallBack) {
        ApiRetrofit.getInstance().getApiService().getQueryBalanceService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<MyAccountEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                if (z) {
                    TomatoLivePresenter.this.showLoadingDialog(true);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(MyAccountEntity myAccountEntity) {
                if (z) {
                    TomatoLivePresenter.this.dismissProgressDialog();
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserOverSuccess(myAccountEntity);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(myAccountEntity);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                if (z) {
                    TomatoLivePresenter.this.dismissProgressDialog();
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserOverFail();
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(0, "");
                }
            }
        });
    }

    public void getCurrentOnlineUserList(final String str, long j) {
        compositeDisposableAdd(Observable.interval(0L, j, TimeUnit.SECONDS).observeOn(Schedulers.m90io()).subscribeOn(Schedulers.m90io()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.15
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                ((BasePresenter) TomatoLivePresenter.this).mApiService.getCurrentOnlineUserListService(new RequestParams().getCurrentOnlineUserList(str)).map(new ServerResultFunction<OnLineUsersEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.15.2
                }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<OnLineUsersEntity>(TomatoLivePresenter.this.getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.15.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                        TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(OnLineUsersEntity onLineUsersEntity) {
                        if (onLineUsersEntity == null) {
                            return;
                        }
                        ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAudiencesSuccess(onLineUsersEntity);
                    }
                });
            }
        }));
    }

    public void getLiveInitInfo(final String str, final String str2, final boolean z, final boolean z2, boolean z3, final boolean z4) {
        this.mApiService.getLiveInitInfoService(new RequestParams().getLiveInitInfoParams(str, str2, z3)).map(new ServerResultFunction<LiveInitInfoEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.17
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<LiveInitInfoEntity>(getContext()) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.16
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                TomatoLivePresenter.this.showLoadingDialog(z);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(LiveInitInfoEntity liveInitInfoEntity) {
                TomatoLivePresenter.this.dismissProgressDialog();
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveInitInfoSuccess(str, str2, liveInitInfoEntity, z2, z4);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str3) {
                super.onError(i, str3);
                TomatoLivePresenter.this.dismissProgressDialog();
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveInitInfoFail(i, str3);
            }
        });
    }

    public void getAdImageList(final String str) {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            initAdImageList(str);
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelListener(getContext(), str, new TomatoLiveSDK.OnAdvChannelCallbackListener() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.18
                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataSuccess(Context context, String str2) {
                    try {
                        if (TextUtils.isEmpty(str2)) {
                            TomatoLivePresenter.this.initAdImageList(str);
                            return;
                        }
                        List<BannerEntity> list = (List) GsonUtils.fromJson(str2, new TypeToken<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.18.1
                        }.getType());
                        if (list != null && !list.isEmpty()) {
                            ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdListSuccess(str, list);
                            return;
                        }
                        TomatoLivePresenter.this.initAdImageList(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                        TomatoLivePresenter.this.initAdImageList(str);
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataFail(int i, String str2) {
                    TomatoLivePresenter.this.initAdImageList(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAdImageList(final String str) {
        if (CacheUtils.isBannerListByCacheDisk(str)) {
            Observable.just(CacheUtils.getBannerListByCacheDisk(str)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.19
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<BannerEntity> list) {
                    ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdListSuccess(str, list);
                }
            });
        } else {
            ApiRetrofit.getInstance().getApiService().getBannerListService(new RequestParams().getBannerListParams(str)).map(new ServerResultFunction<List<BannerEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.21
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<BannerEntity>>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.20
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                    TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<BannerEntity> list) {
                    if (list == null) {
                        return;
                    }
                    CacheUtils.saveBannerListByCacheDisk(str, list);
                    ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdListSuccess(str, list);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdListFail(str);
                }
            });
        }
    }

    public void getLiveAdNoticeList() {
        if (TomatoLiveSDK.getSingleton().isLiveAdvChannel()) {
            initLiveAdNoticeList();
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAdvChannelLiveNoticeListener(getContext(), new TomatoLiveSDK.OnAdvChannelCallbackListener() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.22
                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataSuccess(Context context, String str) {
                    try {
                        if (TextUtils.isEmpty(str)) {
                            TomatoLivePresenter.this.initLiveAdNoticeList();
                            return;
                        }
                        BannerEntity bannerEntity = (BannerEntity) GsonUtils.fromJson(str, (Class<Object>) BannerEntity.class);
                        if (bannerEntity == null) {
                            TomatoLivePresenter.this.initLiveAdNoticeList();
                        } else {
                            ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                        }
                    } catch (Exception unused) {
                        TomatoLivePresenter.this.initLiveAdNoticeList();
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnAdvChannelCallbackListener
                public void onAdvDataFail(int i, String str) {
                    TomatoLivePresenter.this.initLiveAdNoticeList();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLiveAdNoticeList() {
        if (CacheUtils.isLiveNoticeByCacheDisk()) {
            Observable.just(CacheUtils.getLiveNoticeByCacheDisk()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<BannerEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.23
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(BannerEntity bannerEntity) {
                    ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                }
            });
        } else {
            ApiRetrofit.getInstance().getApiService().getNoticeListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<BannerEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.25
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<BannerEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.24
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                    TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(BannerEntity bannerEntity) {
                    if (bannerEntity == null) {
                        return;
                    }
                    CacheUtils.saveLiveNoticeByCacheDisk(bannerEntity);
                    ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLiveAdNoticeSuccess(bannerEntity);
                }
            });
        }
    }

    public void getBroadcastClick(String str) {
        this.mApiService.broadcastClickCountUpdate(new RequestParams().getBroadcastClickParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.27
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.26
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }
        });
    }

    public void getPersonalGuardInfo(String str) {
        this.mApiService.getMyGuardInfoService(new RequestParams().getPersonalGuardInfoParams(str)).map(new ServerResultFunction<GuardItemEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.29
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<GuardItemEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.28
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(GuardItemEntity guardItemEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onPersonalGuardInfoSuccess(guardItemEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onPersonalGuardInfoFail();
            }
        });
    }

    public void getWebSocketAddress(String str, String str2) {
        this.mApiService.getWebSocketAddressService(new RequestParams().getWebSocketAddressParams(str, str2)).map(new ServerResultFunction<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.31
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<LiveEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.30
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(LiveEntity liveEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onWebSocketAddressSuccess(liveEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onWebSocketAddressFail();
            }
        });
    }

    public void getLivePopularity(final String str, final String str2) {
        compositeDisposableAdd(Observable.interval(0L, 15L, TimeUnit.SECONDS).observeOn(Schedulers.m90io()).subscribeOn(Schedulers.m90io()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.32
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                CacheApiRetrofit.getInstance().getApiService().getLivePopularityService(str2, UserInfoManager.getInstance().getAppId(), str).map(new ServerResultFunction<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.32.3
                }).onErrorResumeNext(new HttpResultFunction<LiveEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.32.2
                }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<LiveEntity>(TomatoLivePresenter.this.getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.32.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                        TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(LiveEntity liveEntity) {
                        if (liveEntity == null) {
                            return;
                        }
                        ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onLivePopularitySuccess(NumberUtils.string2long(liveEntity.popularity));
                    }
                });
            }
        }));
    }

    public void getGiftBoxList(long j, final String str) {
        onTimerDelayAction(j, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.33
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ApiRetrofit.getInstance().getApiService().getGiftBoxListService(new RequestParams().getGiftBoxListParams(str)).map(new ServerResultFunction<List<GiftBoxEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.33.2
                }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<GiftBoxEntity>>(TomatoLivePresenter.this.getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.33.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onError(Throwable th) {
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                        TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(List<GiftBoxEntity> list) {
                        if (list == null) {
                            return;
                        }
                        ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onGiftBoxListSuccess(list);
                    }
                });
            }
        });
    }

    public void getTaskList(final boolean z) {
        ApiRetrofit.getInstance().getApiService().getTaskBoxList(new RequestParams().getTaskBoxListParams()).map(new ServerResultFunction<List<TaskBoxEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.35
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<TaskBoxEntity>>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.34
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<TaskBoxEntity> list) {
                if (list == null) {
                    return;
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskListSuccess(list, z);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskListFail();
            }
        });
    }

    public void getTaskTake(final TaskBoxEntity taskBoxEntity) {
        this.mApiService.getTaskBoxTake(new RequestParams().getTaskChangeParams(taskBoxEntity.getId())).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.37
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext()) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.36
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (obj == null) {
                    return;
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskTakeSuccess(taskBoxEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskTakeFail();
            }
        });
    }

    public void changeTaskState(final TaskBoxEntity taskBoxEntity) {
        this.mApiService.changeTaskState(new RequestParams().getTaskChangeParams(taskBoxEntity.getId())).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.39
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.38
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (obj == null) {
                    return;
                }
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskChangeSuccess(taskBoxEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTaskChangeFail(taskBoxEntity);
            }
        });
    }

    public void getTrumpetStatus() {
        this.mApiService.getTrumpetStatus(new RequestParams().getDefaultParams()).map(new ServerResultFunction<TrumpetStatusEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.41
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<TrumpetStatusEntity>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.40
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(TrumpetStatusEntity trumpetStatusEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTrumpetStatusSuccess(trumpetStatusEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTrumpetStatusFail();
            }
        });
    }

    public void sendTrumpet(String str) {
        addMapSubscription(this.mApiService.sendTrumpet(new RequestParams().getTrumpetSendParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.42
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTrumpetSendSuccess();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onTrumpetSendFail(i);
            }
        }));
    }

    public void updateTrumpetClickCount(String str) {
        this.mApiService.updateClickTrumpetCount(new RequestParams().getTrumpetSendUpdateTrumpetClickCountParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.44
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.43
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }
        });
    }

    public void updateStartLiveNoticeCount(String str) {
        this.mApiService.startLiveNoticeClickCountUpdateService(new RequestParams().getVipSeatListParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.46
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext(), false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.45
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }
        });
    }

    public void getFragmentConfig(String str) {
        this.mApiService.getPropFragmentConfigService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<PropConfigEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.48
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<PropConfigEntity>(getContext()) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.47
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PropConfigEntity propConfigEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onFragmentConfigSuccess(propConfigEntity);
            }
        });
    }

    public void getUsePropService(final PropConfigEntity propConfigEntity, String str) {
        this.mApiService.getUsePropService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.50
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>(getContext()) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.49
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUseFragmentSuccess(propConfigEntity);
            }
        });
    }

    public void getBoomStatus(String str) {
        this.mApiService.getBoomStatusService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<BoomStatusEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.52
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<BoomStatusEntity>(false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.51
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(BoomStatusEntity boomStatusEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onBoomStatusSuccess(boomStatusEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onBoomStatusFail();
            }
        });
    }

    public void showUserManageMenu(String str, String str2, ResultCallBack<UserEntity> resultCallBack) {
        addMapSubscription(this.mApiService.getJudgeUserBanPostService(new RequestParams().getJudgeUserBanPostParams(str, str2)), new HttpRxObserver(getContext(), resultCallBack));
    }

    public void watchHistoryReport(String str, long j) {
        this.mApiService.getWatchHistoryService(new RequestParams().getWatchHistoryParams(str, j)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.54
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new SimpleRxObserver<Object>(false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.53
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }
        });
    }

    public void clearCompositeDisposable() {
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void compositeDisposableAdd(Disposable disposable) {
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public void getAnchorFrozenStatus(final ResultCallBack<AnchorEntity> resultCallBack) {
        this.mApiService.getAnchorFrozenStatusService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.56
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<AnchorEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.55
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(AnchorEntity anchorEntity) {
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(anchorEntity);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(-1, th.getMessage());
                }
            }
        });
    }

    public void onUserCheckTicket(String str, final boolean z) {
        this.mApiService.getCheckTicketService(new RequestParams().getUserCheckTicketParams(str)).map(new ServerResultFunction<CheckTicketEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.58
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<CheckTicketEntity>(getContext()) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.57
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                TomatoLivePresenter.this.showLoadingDialog(z);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(CheckTicketEntity checkTicketEntity) {
                TomatoLivePresenter.this.dismissProgressDialog();
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserCheckTicketSuccess(checkTicketEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str2) {
                super.onError(i, str2);
                TomatoLivePresenter.this.dismissProgressDialog();
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onUserCheckTicketFail(i, str2);
            }
        });
    }

    public void initLocalComponentsCache() {
        if (TomatoLiveSDK.getSingleton().isLiveGameChannel()) {
            ApiRetrofit.getInstance().getApiService().getGameComponentService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.60
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<ComponentsEntity>>(false) { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.59
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                    TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<ComponentsEntity> list) {
                    CacheUtils.saveLocalComponentsCache(list);
                }
            });
        } else if (TomatoLiveSDK.getSingleton().sdkCallbackListener == null) {
        } else {
            loadChannelComponentsData(null);
        }
    }

    private void loadChannelComponentsData(ComponentsEntity componentsEntity) {
        TomatoLiveSDK.getSingleton().sdkCallbackListener.onAppCommonCallbackListener(getContext(), 274, new TomatoLiveSDK.OnCommonCallbackListener() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.61
            @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
            public void onDataFail(Throwable th, int i) {
            }

            @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
            public void onDataSuccess(Context context, Object obj) {
                if (obj != null && (obj instanceof String)) {
                    try {
                        List<ComponentsEntity> list = (List) GsonUtils.fromJson((String) obj, new TypeToken<List<ComponentsEntity>>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.61.1
                        }.getType());
                        if (list != null && !list.isEmpty()) {
                            boolean z = false;
                            for (ComponentsEntity componentsEntity2 : list) {
                                if (componentsEntity2.isCacheLotteryComponents()) {
                                    z = true;
                                }
                            }
                            if (!z) {
                                list.add(0, TomatoLivePresenter.this.getLocalLotteryComponents());
                            }
                            CacheUtils.saveLocalComponentsCache(list);
                            return;
                        }
                        list.add(TomatoLivePresenter.this.getLocalLotteryComponents());
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ComponentsEntity getLocalLotteryComponents() {
        ComponentsEntity componentsEntity = new ComponentsEntity();
        componentsEntity.callType = 2;
        componentsEntity.gameId = "1";
        componentsEntity.name = getContext().getString(R$string.fq_lottery_menu);
        return componentsEntity;
    }

    public void onGetFP(String str, final boolean z, final boolean z2) {
        ApiRetrofit.getInstance().getApiService().getPKFPService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<PKRecordEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.63
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<PKRecordEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.62
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PKRecordEntity pKRecordEntity) {
                ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onPKLiveRoomFPSuccess(z, z2, pKRecordEntity);
            }
        });
    }

    public void onOpenNobilityMsgNotice(ResultCallBack<Object> resultCallBack) {
        onTimerDelayAction(5L, resultCallBack);
    }

    public void onAttentionMsgNotice(ResultCallBack<Object> resultCallBack) {
        onTimerDelayAction(60L, resultCallBack);
    }

    public void onTimerDelayAction(long j, final ResultCallBack<Object> resultCallBack) {
        compositeDisposableAdd(Observable.timer(j, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.64
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(null);
                }
            }
        }));
    }

    public void onSendOfflinePrivateMsg() {
        ApiRetrofit.getInstance().getApiService().getSendOfflinePrivateMessageService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.66
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.65
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }
        });
    }

    public void onBuyLiveTicket(String str, final ResultCallBack<MyAccountEntity> resultCallBack) {
        ApiRetrofit.getInstance().getApiService().getBuyLiveTicketService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.68
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<MyAccountEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.67
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                TomatoLivePresenter.this.showLoadingDialog(true);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(MyAccountEntity myAccountEntity) {
                TomatoLivePresenter.this.dismissProgressDialog();
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(myAccountEntity);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                TomatoLivePresenter.this.dismissProgressDialog();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str2) {
                super.onError(i, str2);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(i, str2);
                }
            }
        });
    }

    public void sendQMInteractInviteRequest(String str, String str2, String str3, String str4, String str5, final ResultCallBack<Object> resultCallBack) {
        ApiRetrofit.getInstance().getApiService().userCommitTaskListService(new RequestParams().getUserCommitIntimateParams(str, str2, str3, str4, str5)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.70
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.69
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(obj);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str6) {
                super.onError(i, str6);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(i, str6);
                }
            }
        });
    }

    public void sendUserShowTaskListRequest(long j, final String str) {
        onTimerDelayAction(j, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.71
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ApiRetrofit.getInstance().getApiService().userShowTaskListService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.71.2
                }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.71.1
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        super.onSubscribe(disposable);
                        TomatoLivePresenter.this.compositeDisposableAdd(disposable);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(QMInteractTaskListEntity qMInteractTaskListEntity) {
                        List<QMInteractTaskEntity> list;
                        if (qMInteractTaskListEntity == null || (list = qMInteractTaskListEntity.intimateTaskList) == null || list.isEmpty()) {
                            return;
                        }
                        ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onQMInteractShowTaskSuccess(list);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onError(Throwable th) {
                        ((ITomatoLiveView) TomatoLivePresenter.this.getView()).onQMInteractShowTaskFail();
                    }
                });
            }
        });
    }

    public void sendUserPendingTaskRequest(String str, final ResultCallBack<QMInteractTaskEntity> resultCallBack) {
        ApiRetrofit.getInstance().getApiService().userPendingTaskService(new RequestParams().getLiveId(str)).map(new ServerResultFunction<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.73
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.presenter.TomatoLivePresenter.72
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                TomatoLivePresenter.this.compositeDisposableAdd(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(QMInteractTaskEntity qMInteractTaskEntity) {
                if (qMInteractTaskEntity == null || TextUtils.isEmpty(qMInteractTaskEntity.taskId)) {
                    onError(-1, "");
                    return;
                }
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 == null) {
                    return;
                }
                resultCallBack2.onSuccess(qMInteractTaskEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str2) {
                super.onError(i, str2);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(i, str2);
                }
            }
        });
    }
}
