package com.tomatolive.library.p136ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AppealInfoEntity;
import com.tomatolive.library.model.AwardDetailEntity;
import com.tomatolive.library.model.MessageDetailEntity;
import com.tomatolive.library.p136ui.view.iview.IAwardDetailView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.presenter.AwardDetailPresenter */
/* loaded from: classes3.dex */
public class AwardDetailPresenter extends BasePresenter<IAwardDetailView> {
    public String anchorAvatar;

    public AwardDetailPresenter(Context context, IAwardDetailView iAwardDetailView) {
        super(context, iAwardDetailView);
    }

    public void sendLeaveMessage(String str, final String str2, String str3) {
        addMapSubscription(this.mApiService.addWinningMessage(new RequestParams().getAddMessageParams(str, str2, str3)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onAddMessageSuccess(AwardDetailPresenter.this.getAddMessageDetailEntity(str2));
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str4) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onAddMessageFailure(i, str4);
            }
        }));
    }

    public void getAwardDetail(StateView stateView, String str, boolean z) {
        addMapSubscription(this.mApiService.getAwardDetail(new RequestParams().getAwardDetailParams(str)), new HttpRxObserver(getContext(), new ResultCallBack<AwardDetailEntity>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AwardDetailEntity awardDetailEntity) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onGetAwardDetailSuccess(awardDetailEntity);
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onGetAwardDetailFailure(i, str2);
            }
        }, stateView, z));
    }

    public void getMessageDetail(String str, int i, final boolean z) {
        this.mApiService.getMessageDetail(new RequestParams().getMessageDetailParams(str, i)).map(new ServerResultFunction<HttpResultPageModel<MessageDetailEntity>>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.5
        }).flatMap(new Function<HttpResultPageModel<MessageDetailEntity>, ObservableSource<HttpResultPageModel<MessageDetailEntity>>>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.4
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<HttpResultPageModel<MessageDetailEntity>> mo6755apply(HttpResultPageModel<MessageDetailEntity> httpResultPageModel) throws Exception {
                return Observable.just(AwardDetailPresenter.this.formatMsgList(httpResultPageModel));
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<HttpResultPageModel<MessageDetailEntity>>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(HttpResultPageModel<MessageDetailEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onGetMessageDetailSuccess(httpResultPageModel.dataList, httpResultPageModel.isMorePage(), z);
            }
        });
    }

    public void addAddress(String str, final String str2) {
        addMapSubscription(this.mApiService.addAddress(new RequestParams().getAddAddressParams(str, str2)), new HttpRxObserver(getContext(), new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.presenter.AwardDetailPresenter.6
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onAddAddressSuccess(AwardDetailPresenter.this.getAddAddressDetailEntity(str2));
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str3) {
                ((IAwardDetailView) AwardDetailPresenter.this.getView()).onAddAddressFailure(i, str3);
            }
        }, true));
    }

    public void saveGivenPrizeInfo(String str, ResultCallBack<Object> resultCallBack) {
        addMapSubscription(this.mApiService.getSaveGivenPrizeInfoService(new RequestParams().getAwardDetailParams(str)), new HttpRxObserver(getContext(), resultCallBack, true));
    }

    public void getAppealInfo(String str, ResultCallBack<AppealInfoEntity> resultCallBack) {
        addMapSubscription(this.mApiService.getAppealInfoService(new RequestParams().getAppealInfoParamsByAward(str)), new HttpRxObserver(getContext(), resultCallBack));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HttpResultPageModel<MessageDetailEntity> formatMsgList(HttpResultPageModel<MessageDetailEntity> httpResultPageModel) {
        HttpResultPageModel<MessageDetailEntity> httpResultPageModel2 = new HttpResultPageModel<>();
        httpResultPageModel2.pageNumber = httpResultPageModel.pageNumber;
        httpResultPageModel2.pageSize = httpResultPageModel.pageSize;
        httpResultPageModel2.totalRowsCount = httpResultPageModel.totalRowsCount;
        httpResultPageModel2.totalPageCount = httpResultPageModel.totalPageCount;
        httpResultPageModel2.dataList = getMsgDetailList(httpResultPageModel.dataList);
        return httpResultPageModel2;
    }

    private List<MessageDetailEntity> getMsgDetailList(List<MessageDetailEntity> list) {
        for (MessageDetailEntity messageDetailEntity : list) {
            if (TextUtils.equals(messageDetailEntity.getSenderUserId(), UserInfoManager.getInstance().getUserId())) {
                messageDetailEntity.setType(288);
            } else if (TextUtils.equals(messageDetailEntity.getReceiverUserId(), UserInfoManager.getInstance().getUserId())) {
                messageDetailEntity.setType(ConstantUtils.MSG_TYPE_OTHER);
            } else {
                messageDetailEntity.setType(ConstantUtils.MSG_TYPE_OTHER);
            }
            messageDetailEntity.setAvatar(this.anchorAvatar);
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MessageDetailEntity getAddMessageDetailEntity(String str) {
        MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
        messageDetailEntity.setSenderUserId(UserInfoManager.getInstance().getUserId());
        messageDetailEntity.setType(288);
        messageDetailEntity.setContent(str);
        messageDetailEntity.setSendTime(System.currentTimeMillis() / 1000);
        return messageDetailEntity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MessageDetailEntity getAddAddressDetailEntity(String str) {
        MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
        messageDetailEntity.setSenderUserId(UserInfoManager.getInstance().getUserId());
        messageDetailEntity.setType(ConstantUtils.MSG_TYPE_ADDRESS);
        messageDetailEntity.setContent(str);
        messageDetailEntity.setSendTime(System.currentTimeMillis() / 1000);
        return messageDetailEntity;
    }

    public MessageDetailEntity getAddAddressDetailEntity(String str, String str2, String str3, boolean z, boolean z2) {
        MessageDetailEntity messageDetailEntity = new MessageDetailEntity();
        messageDetailEntity.setSenderUserId(UserInfoManager.getInstance().getUserId());
        if (z2) {
            str = null;
        }
        messageDetailEntity.setReceiverUserId(str);
        messageDetailEntity.setType(ConstantUtils.MSG_TYPE_ADDRESS);
        messageDetailEntity.setContent(str2);
        messageDetailEntity.setSelected(z);
        messageDetailEntity.setSendTime(NumberUtils.string2long(str3));
        return messageDetailEntity;
    }
}
