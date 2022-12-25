package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.widget.TextView;
import com.one.tomato.R$id;
import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.RequestParams;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: SpreadInfoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$5 */
/* loaded from: classes3.dex */
public final class SpreadInfoActivity$request$5 implements PromotionUtil.RequestTokenListener {
    final /* synthetic */ SpreadInfoActivity this$0;

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenFail() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpreadInfoActivity$request$5(SpreadInfoActivity spreadInfoActivity) {
        this.this$0 = spreadInfoActivity;
    }

    @Override // com.one.tomato.thirdpart.promotion.PromotionUtil.RequestTokenListener
    public void requestTokenSuccess() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(AopConstants.APP_PROPERTIES_KEY, "");
        jSONObject.put("currentPage", "1");
        jSONObject.put(RequestParams.PAGE_SIZE, "1");
        jSONObject.put("merchantId", PromotionUtil.merchantId);
        jSONObject.put("uid", String.valueOf(DBUtil.getMemberId()));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestPromotionOrderRecord(PromotionUtil.DOMAIN + "/api/app/promotion/wallet/findOrders", body).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this.this$0)).subscribe(new Observer<PromotionOrderRecord>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$5$requestTokenSuccess$1
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
                ((TextView) SpreadInfoActivity$request$5.this.this$0._$_findCachedViewById(R$id.spread_withdraw_order)).setText(String.valueOf(t.totalCount));
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                LogUtil.m3784i("onError : " + e.getMessage());
            }
        });
    }
}
