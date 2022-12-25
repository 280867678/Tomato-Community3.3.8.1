package com.one.tomato.mvp.p080ui.promotion.p081ui;

import com.one.tomato.entity.PromotionTop50;
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

/* compiled from: SpreadIncomeTop50Fragment.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadIncomeTop50Fragment$requestList$1 */
/* loaded from: classes3.dex */
public final class SpreadIncomeTop50Fragment$requestList$1 implements PromotionUtil.RequestTokenListener {
    final /* synthetic */ SpreadIncomeTop50Fragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpreadIncomeTop50Fragment$requestList$1(SpreadIncomeTop50Fragment spreadIncomeTop50Fragment) {
        this.this$0 = spreadIncomeTop50Fragment;
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
        ApiImplService.Companion.getApiImplService().requestPromotionTop50(PromotionUtil.DOMAIN + "/api/app/promotion/app/queryTopPromotion", body).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this.this$0)).subscribe(new Observer<PromotionTop50>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadIncomeTop50Fragment$requestList$1$requestTokenSuccess$1
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
            public void onNext(PromotionTop50 t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                SpreadIncomeTop50Fragment$requestList$1.this.this$0.updateData(t.data);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                LogUtil.m3784i("onError : " + e.getMessage());
                SpreadIncomeTop50Fragment$requestList$1.this.this$0.updateDataFail();
            }
        });
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenFail() {
        this.this$0.updateDataFail();
    }
}
