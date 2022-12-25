package com.one.tomato.mvp.p080ui.promotion.p081ui;

import com.one.tomato.entity.PromotionBlockUrl;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: WithdrawActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$request$5 */
/* loaded from: classes3.dex */
public final class WithdrawActivity$request$5 implements PromotionUtil.RequestTokenListener {
    final /* synthetic */ WithdrawActivity this$0;

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenFail() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WithdrawActivity$request$5(WithdrawActivity withdrawActivity) {
        this.this$0 = withdrawActivity;
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenSuccess() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AopConstants.APP_PROPERTIES_KEY, "");
        jSONObject.put("merchantId", PromotionUtil.merchantId);
        jSONObject.put("uid", String.valueOf(DBUtil.getMemberId()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestPromotionBlockUrl(PromotionUtil.DOMAIN + "/api/app/promotion/app/queryBlockUrl", body).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this.this$0)).subscribe(new ApiDisposableObserver<PromotionBlockUrl>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$request$5$requestTokenSuccess$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PromotionBlockUrl promotionBlockUrl) {
                WithdrawActivity$request$5.this.this$0.promotionBlockUrl = promotionBlockUrl;
            }
        });
    }
}
