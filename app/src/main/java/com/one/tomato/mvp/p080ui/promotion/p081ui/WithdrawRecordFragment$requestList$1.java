package com.one.tomato.mvp.p080ui.promotion.p081ui;

import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.RequestParams;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: WithdrawRecordFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawRecordFragment$requestList$1 */
/* loaded from: classes3.dex */
public final class WithdrawRecordFragment$requestList$1 implements PromotionUtil.RequestTokenListener {
    final /* synthetic */ WithdrawRecordFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public WithdrawRecordFragment$requestList$1(WithdrawRecordFragment withdrawRecordFragment) {
        this.this$0 = withdrawRecordFragment;
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenSuccess() {
        int pageNo;
        int pageSize;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AopConstants.APP_PROPERTIES_KEY, "");
        pageNo = this.this$0.getPageNo();
        jSONObject.put("currentPage", pageNo);
        pageSize = this.this$0.getPageSize();
        jSONObject.put(RequestParams.PAGE_SIZE, pageSize);
        jSONObject.put("merchantId", PromotionUtil.merchantId);
        jSONObject.put("uid", String.valueOf(DBUtil.getMemberId()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestPromotionOrderRecord(PromotionUtil.DOMAIN + "/api/app/promotion/wallet/findOrders", body).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this.this$0)).subscribe(new Observer<PromotionOrderRecord>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawRecordFragment$requestList$1$requestTokenSuccess$1
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
            public void onNext(PromotionOrderRecord t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                WithdrawRecordFragment$requestList$1.this.this$0.updateData(t.data);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                LogUtil.m3784i("onError : " + e.getMessage());
                WithdrawRecordFragment$requestList$1.this.this$0.updateDataFail();
            }
        });
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenFail() {
        this.this$0.updateDataFail();
    }
}
