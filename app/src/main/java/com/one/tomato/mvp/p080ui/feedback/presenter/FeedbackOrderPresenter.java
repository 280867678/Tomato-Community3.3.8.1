package com.one.tomato.mvp.p080ui.feedback.presenter;

import com.one.tomato.entity.FeedbackOrderCheck;
import com.one.tomato.entity.JCOrderRecord;
import com.one.tomato.entity.JCParameter;
import com.one.tomato.entity.RechargeOnlineUnCallback;
import com.one.tomato.entity.RechargeProblemOrder;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: FeedbackOrderPresenter.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.presenter.FeedbackOrderPresenter */
/* loaded from: classes3.dex */
public final class FeedbackOrderPresenter extends MvpBasePresenter<IFeedbackOrderContact$IFeedbackOrderView> implements IFeedbackOrderContact {
    @Override // com.one.tomato.mvp.base.presenter.MvpBasePresenter
    public void onCreate() {
    }

    public void requestOnline(int i) {
        ApiImplService.Companion.getApiImplService().requestOnlineProblemOrders(i).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<RechargeOnlineUnCallback>>() { // from class: com.one.tomato.mvp.ui.feedback.presenter.FeedbackOrderPresenter$requestOnline$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RechargeOnlineUnCallback> arrayList) {
                IFeedbackOrderContact$IFeedbackOrderView mView;
                mView = FeedbackOrderPresenter.this.getMView();
                if (mView != null) {
                    mView.handleOnline(arrayList);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                IFeedbackOrderContact$IFeedbackOrderView mView;
                mView = FeedbackOrderPresenter.this.getMView();
                if (mView != null) {
                    mView.onError("");
                }
            }
        });
    }

    public void requestJC(JCParameter jCParameter, int i, int i2) {
        if (jCParameter == null) {
            return;
        }
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        String str = domainServer.getJCUrl() + ":" + jCParameter.httpPort;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("page", i);
        jSONObject.put("size", i2);
        jSONObject.put("appUserId", DBUtil.getMemberId());
        jSONObject.put("appFlag", jCParameter.appFlag);
        jSONObject.put("clientApiKey", jCParameter.appKey);
        jSONObject.put("clientSecretKey", jCParameter.appSecret);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestJCOrders(str + "/application/public/order/list", body).compose(RxUtils.schedulersTransformer()).subscribe(new Observer<JCOrderRecord>() { // from class: com.one.tomato.mvp.ui.feedback.presenter.FeedbackOrderPresenter$requestJC$1
            @Override // io.reactivex.Observer
            public void onComplete() {
                LogUtil.m3784i("complete");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
                LogUtil.m3784i("onSubscribe");
            }

            @Override // io.reactivex.Observer
            public void onNext(JCOrderRecord t) {
                IFeedbackOrderContact$IFeedbackOrderView mView;
                Intrinsics.checkParameterIsNotNull(t, "t");
                mView = FeedbackOrderPresenter.this.getMView();
                if (mView != null) {
                    mView.handleJC(t);
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                IFeedbackOrderContact$IFeedbackOrderView mView;
                Intrinsics.checkParameterIsNotNull(e, "e");
                LogUtil.m3784i("onError : " + e.getMessage());
                mView = FeedbackOrderPresenter.this.getMView();
                if (mView != null) {
                    mView.onError("");
                }
            }
        });
    }

    public void requestCheckOrder(int i, final RechargeProblemOrder rechargeProblemOrder) {
        Intrinsics.checkParameterIsNotNull(rechargeProblemOrder, "rechargeProblemOrder");
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        String id = rechargeProblemOrder.getId();
        Intrinsics.checkExpressionValueIsNotNull(id, "rechargeProblemOrder.id");
        apiImplService.requestCheckOrder(i, id).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<FeedbackOrderCheck>() { // from class: com.one.tomato.mvp.ui.feedback.presenter.FeedbackOrderPresenter$requestCheckOrder$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(FeedbackOrderCheck feedbackOrderCheck) {
                IFeedbackOrderContact$IFeedbackOrderView mView;
                FeedbackOrderPresenter.this.dismissDialog();
                mView = FeedbackOrderPresenter.this.getMView();
                if (mView != null) {
                    mView.handleCheckOrder(feedbackOrderCheck, rechargeProblemOrder);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                FeedbackOrderPresenter.this.dismissDialog();
            }
        });
    }
}
