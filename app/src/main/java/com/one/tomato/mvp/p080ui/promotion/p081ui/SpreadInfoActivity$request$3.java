package com.one.tomato.mvp.p080ui.promotion.p081ui;

import com.one.tomato.entity.PromotionWithdrawConfig;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: SpreadInfoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$3 */
/* loaded from: classes3.dex */
public final class SpreadInfoActivity$request$3 implements PromotionUtil.RequestTokenListener {
    final /* synthetic */ SpreadInfoActivity this$0;

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenFail() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpreadInfoActivity$request$3(SpreadInfoActivity spreadInfoActivity) {
        this.this$0 = spreadInfoActivity;
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenSuccess() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AopConstants.APP_PROPERTIES_KEY, "");
        jSONObject.put("merchantId", PromotionUtil.merchantId);
        jSONObject.put("uid", String.valueOf(DBUtil.getMemberId()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestPromotionWithdrawConfig(PromotionUtil.DOMAIN + "/api/app/promotion/wallet/queryWithDrawConfig", body).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PromotionWithdrawConfig>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$3$requestTokenSuccess$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PromotionWithdrawConfig promotionWithdrawConfig) {
                SpreadInfoActivity$request$3.this.this$0.promotionWithdrawConfig = promotionWithdrawConfig;
                SpreadInfoActivity$request$3.this.this$0.updateIncomeUI();
            }
        });
    }
}
