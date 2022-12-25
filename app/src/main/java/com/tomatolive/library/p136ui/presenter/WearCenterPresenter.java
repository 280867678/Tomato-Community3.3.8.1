package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.ChatPreviewEntity;
import com.tomatolive.library.model.MedalEntity;
import com.tomatolive.library.p136ui.view.iview.IWearCenterView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tomatolive.library.ui.presenter.WearCenterPresenter */
/* loaded from: classes3.dex */
public class WearCenterPresenter extends BasePresenter<IWearCenterView> {
    public static final String DATA_TYPE_CHAT_PREVIEW = "1";
    public static final String DATA_TYPE_MEDAL_TYPE_OWN = "3";
    public static final String DATA_TYPE_MEDAL_TYPE_USED = "2";
    public static final String DATA_TYPE_PREFIX_TYPE_OWN = "5";
    public static final String DATA_TYPE_PREFIX_TYPE_USED = "4";
    private int pageSize = 30;
    private int pageNum = 1;

    public WearCenterPresenter(Context context, IWearCenterView iWearCenterView) {
        super(context, iWearCenterView);
    }

    public void getAllData(StateView stateView) {
        Observable.zip(this.mApiService.getChatPreviewService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<ChatPreviewEntity>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.1
        }).onErrorResumeNext(new HttpResultFunction()), this.mApiService.getBadgeListForUsedService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)).map(new ServerResultFunction<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.2
        }).onErrorResumeNext(new HttpResultFunction()), this.mApiService.getBadgeListForOwnService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)).map(new ServerResultFunction<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.3
        }).onErrorResumeNext(new HttpResultFunction()), this.mApiService.getPrefixListForUsedService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)).map(new ServerResultFunction<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.4
        }).onErrorResumeNext(new HttpResultFunction()), this.mApiService.getPrefixForOwnService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)).map(new ServerResultFunction<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.5
        }).onErrorResumeNext(new HttpResultFunction()), new Function5<ChatPreviewEntity, HttpResultPageModel<MedalEntity>, HttpResultPageModel<MedalEntity>, HttpResultPageModel<MedalEntity>, HttpResultPageModel<MedalEntity>, Map<String, Object>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.7
            @Override // io.reactivex.functions.Function5
            public Map<String, Object> apply(ChatPreviewEntity chatPreviewEntity, HttpResultPageModel<MedalEntity> httpResultPageModel, HttpResultPageModel<MedalEntity> httpResultPageModel2, HttpResultPageModel<MedalEntity> httpResultPageModel3, HttpResultPageModel<MedalEntity> httpResultPageModel4) throws Exception {
                HashMap hashMap = new HashMap();
                hashMap.put("1", chatPreviewEntity);
                List<MedalEntity> list = null;
                hashMap.put("2", httpResultPageModel == null ? null : httpResultPageModel.dataList);
                hashMap.put("3", httpResultPageModel2 == null ? null : httpResultPageModel2.dataList);
                hashMap.put("4", httpResultPageModel3 == null ? null : httpResultPageModel3.dataList);
                if (httpResultPageModel4 != null) {
                    list = httpResultPageModel4.dataList;
                }
                hashMap.put("5", list);
                return hashMap;
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(getLifecycleProvider().bindToLifecycle()).subscribe(new HttpRxObserver(getContext(), (ResultCallBack) new ResultCallBack<Map<String, Object>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.6
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Map<String, Object> map) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onAllDataSuccess(map);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onAllDataFail();
            }
        }, stateView, true));
    }

    public void getChatPreview() {
        addMapSubscription(this.mApiService.getChatPreviewService(new RequestParams().getDefaultParams()), new HttpRxObserver(getContext(), new ResultCallBack<ChatPreviewEntity>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.8
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(ChatPreviewEntity chatPreviewEntity) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onChatPreviewSuccess(chatPreviewEntity);
            }
        }));
    }

    public void getMedalListData(final int i) {
        if (i == 1) {
            addMapSubscription(this.mApiService.getBadgeListForUsedService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.9
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<MedalEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onMedalDataSuccess(i, httpResultPageModel.dataList);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str) {
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onMedalDataFail(i);
                }
            }));
        } else if (i != 2) {
        } else {
            addMapSubscription(this.mApiService.getBadgeListForOwnService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.10
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<MedalEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onMedalDataSuccess(i, httpResultPageModel.dataList);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str) {
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onMedalDataFail(i);
                }
            }));
        }
    }

    public void getPrefixListData(final int i) {
        if (i == 1) {
            addMapSubscription(this.mApiService.getPrefixListForUsedService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.11
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<MedalEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onPrefixDataSuccess(i, httpResultPageModel.dataList);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str) {
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onPrefixDataFail(i);
                }
            }));
        } else if (i != 2) {
        } else {
            addMapSubscription(this.mApiService.getPrefixForOwnService(new RequestParams().getPageListParams(this.pageNum, this.pageSize)), new HttpRxObserver(getContext(), new ResultCallBack<HttpResultPageModel<MedalEntity>>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.12
                @Override // com.tomatolive.library.http.ResultCallBack
                public void onSuccess(HttpResultPageModel<MedalEntity> httpResultPageModel) {
                    if (httpResultPageModel == null) {
                        return;
                    }
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onPrefixDataSuccess(i, httpResultPageModel.dataList);
                }

                @Override // com.tomatolive.library.http.ResultCallBack
                public void onError(int i2, String str) {
                    ((IWearCenterView) WearCenterPresenter.this.getView()).onPrefixDataFail(i);
                }
            }));
        }
    }

    public void equipWearCenter(final String str, final boolean z, final MedalEntity medalEntity, final int i) {
        addMapSubscription(this.mApiService.equipWearCenterService(new RequestParams().getMarkIdParams(medalEntity.markId)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.13
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onEquipSuccess(str, z, medalEntity, i);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i2, String str2) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onEquipFail(z);
            }
        }));
    }

    public void cancelWearCenter(final String str, String str2) {
        addMapSubscription(this.mApiService.cancelWearCenterService(new RequestParams().getMarkIdParams(str2)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.WearCenterPresenter.14
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onCancelWearCenterSuccess(str);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IWearCenterView) WearCenterPresenter.this.getView()).onCancelWearCenterFail();
            }
        }));
    }
}
