package com.alipay.sdk.app;

import android.app.Activity;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.H5PayResultModel;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.app.g */
/* loaded from: classes2.dex */
public class RunnableC0949g implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f925a;

    /* renamed from: b */
    final /* synthetic */ boolean f926b;

    /* renamed from: c */
    final /* synthetic */ H5PayCallback f927c;

    /* renamed from: d */
    final /* synthetic */ PayTask f928d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0949g(PayTask payTask, String str, boolean z, H5PayCallback h5PayCallback) {
        this.f928d = payTask;
        this.f925a = str;
        this.f926b = z;
        this.f927c = h5PayCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        Activity activity;
        activity = this.f928d.f906b;
        H5PayResultModel h5Pay = this.f928d.h5Pay(new C0988a(activity, this.f925a, "payInterceptorWithUrl"), this.f925a, this.f926b);
        C0996c.m4435b("mspl", "inc finished: " + h5Pay.getResultCode());
        this.f927c.onPayResult(h5Pay);
    }
}
