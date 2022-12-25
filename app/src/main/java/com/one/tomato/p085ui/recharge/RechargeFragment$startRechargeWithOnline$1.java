package com.one.tomato.p085ui.recharge;

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

/* compiled from: RechargeFragment.kt */
/* renamed from: com.one.tomato.ui.recharge.RechargeFragment$startRechargeWithOnline$1 */
/* loaded from: classes3.dex */
public final class RechargeFragment$startRechargeWithOnline$1 extends ApiDisposableObserver<RechargeOrder> {
    final /* synthetic */ RechargeTypeAndMoney $rechargeTypeAndMoney;
    final /* synthetic */ RechargeFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RechargeFragment$startRechargeWithOnline$1(RechargeFragment rechargeFragment, RechargeTypeAndMoney rechargeTypeAndMoney) {
        this.this$0 = rechargeFragment;
        this.$rechargeTypeAndMoney = rechargeTypeAndMoney;
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResult(RechargeOrder bean) {
        RechargeOrder rechargeOrder;
        RechargeOrder rechargeOrder2;
        RechargeOrder rechargeOrder3;
        RechargeOrder rechargeOrder4;
        RechargeOrder rechargeOrder5;
        RechargeOrder rechargeOrder6;
        Intrinsics.checkParameterIsNotNull(bean, "bean");
        this.this$0.hideWaitingDialog();
        this.this$0.rechargeOrder = bean;
        rechargeOrder = this.this$0.rechargeOrder;
        if (rechargeOrder != null) {
            rechargeOrder2 = this.this$0.rechargeOrder;
            String str = null;
            if (!TextUtils.isEmpty(rechargeOrder2 != null ? rechargeOrder2.getUrl() : null)) {
                this.this$0.isGoPay = true;
                if (Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_ALIPAY, this.$rechargeTypeAndMoney.payMethod)) {
                    rechargeOrder4 = this.this$0.rechargeOrder;
                    if (!TextUtils.isEmpty(rechargeOrder4 != null ? rechargeOrder4.getSdk() : null)) {
                        rechargeOrder6 = this.this$0.rechargeOrder;
                        if (!TextUtils.isEmpty(rechargeOrder6 != null ? rechargeOrder6.getSdk_type() : null)) {
                            new Thread(new Runnable() { // from class: com.one.tomato.ui.recharge.RechargeFragment$startRechargeWithOnline$1$onResult$payRunnable$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Context mContext;
                                    RechargeOrder rechargeOrder7;
                                    mContext = RechargeFragment$startRechargeWithOnline$1.this.this$0.getMContext();
                                    if (mContext != null) {
                                        PayTask payTask = new PayTask((Activity) mContext);
                                        rechargeOrder7 = RechargeFragment$startRechargeWithOnline$1.this.this$0.rechargeOrder;
                                        LogUtil.m3784i(payTask.payV2(rechargeOrder7 != null ? rechargeOrder7.getSdk() : null, true).toString());
                                        return;
                                    }
                                    throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
                                }
                            }).start();
                            return;
                        }
                    }
                    RechargeFragment rechargeFragment = this.this$0;
                    rechargeOrder5 = rechargeFragment.rechargeOrder;
                    if (rechargeOrder5 != null) {
                        str = rechargeOrder5.getUrl();
                    }
                    rechargeFragment.openPayPlatform(str);
                    return;
                }
                RechargeFragment rechargeFragment2 = this.this$0;
                rechargeOrder3 = rechargeFragment2.rechargeOrder;
                if (rechargeOrder3 != null) {
                    str = rechargeOrder3.getUrl();
                }
                rechargeFragment2.openPayPlatform(str);
                return;
            }
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.recharge_busy));
    }

    @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
    public void onResultError(ResponseThrowable e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        this.this$0.hideWaitingDialog();
    }
}
