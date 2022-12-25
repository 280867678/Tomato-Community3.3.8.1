package com.one.tomato.mvp.p080ui.vip.p083ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.RechargeOrder;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VipActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.ui.VipActivity$requestRechargeOnline$1 */
/* loaded from: classes3.dex */
public final class VipActivity$requestRechargeOnline$1 extends ApiDisposableObserver<RechargeOrder> {
    final /* synthetic */ String $type;
    final /* synthetic */ VipActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public VipActivity$requestRechargeOnline$1(VipActivity vipActivity, String str) {
        this.this$0 = vipActivity;
        this.$type = str;
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResult(RechargeOrder rechargeOrder) {
        RechargeOrder rechargeOrder2;
        RechargeOrder rechargeOrder3;
        RechargeOrder rechargeOrder4;
        RechargeOrder rechargeOrder5;
        RechargeOrder rechargeOrder6;
        RechargeOrder rechargeOrder7;
        this.this$0.hideWaitingDialog();
        this.this$0.rechargeOrder = rechargeOrder;
        rechargeOrder2 = this.this$0.rechargeOrder;
        if (rechargeOrder2 != null) {
            rechargeOrder3 = this.this$0.rechargeOrder;
            String str = null;
            if (!TextUtils.isEmpty(rechargeOrder3 != null ? rechargeOrder3.getUrl() : null)) {
                this.this$0.isGoPay = true;
                if (Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_ALIPAY, this.$type)) {
                    rechargeOrder5 = this.this$0.rechargeOrder;
                    if (!TextUtils.isEmpty(rechargeOrder5 != null ? rechargeOrder5.getSdk() : null)) {
                        rechargeOrder7 = this.this$0.rechargeOrder;
                        if (!TextUtils.isEmpty(rechargeOrder7 != null ? rechargeOrder7.getSdk_type() : null)) {
                            new Thread(new Runnable() { // from class: com.one.tomato.mvp.ui.vip.ui.VipActivity$requestRechargeOnline$1$onResult$payRunnable$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Context mContext;
                                    RechargeOrder rechargeOrder8;
                                    mContext = VipActivity$requestRechargeOnline$1.this.this$0.getMContext();
                                    if (mContext != null) {
                                        PayTask payTask = new PayTask((Activity) mContext);
                                        rechargeOrder8 = VipActivity$requestRechargeOnline$1.this.this$0.rechargeOrder;
                                        LogUtil.m3784i(payTask.payV2(rechargeOrder8 != null ? rechargeOrder8.getSdk_type() : null, true).toString());
                                        return;
                                    }
                                    throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
                                }
                            }).start();
                            return;
                        }
                    }
                    VipActivity vipActivity = this.this$0;
                    rechargeOrder6 = vipActivity.rechargeOrder;
                    if (rechargeOrder6 != null) {
                        str = rechargeOrder6.getUrl();
                    }
                    vipActivity.openPayPlatform(str);
                    return;
                }
                VipActivity vipActivity2 = this.this$0;
                rechargeOrder4 = vipActivity2.rechargeOrder;
                if (rechargeOrder4 != null) {
                    str = rechargeOrder4.getUrl();
                }
                vipActivity2.openPayPlatform(str);
                return;
            }
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.recharge_busy));
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResultError(ResponseThrowable responseThrowable) {
        this.this$0.hideWaitingDialog();
    }
}
