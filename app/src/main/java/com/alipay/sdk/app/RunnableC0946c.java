package com.alipay.sdk.app;

import android.app.Activity;
import android.webkit.SslErrorHandler;
import com.alipay.sdk.widget.C1016e;

/* renamed from: com.alipay.sdk.app.c */
/* loaded from: classes2.dex */
class RunnableC0946c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Activity f920a;

    /* renamed from: b */
    final /* synthetic */ SslErrorHandler f921b;

    /* renamed from: c */
    final /* synthetic */ C0945b f922c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0946c(C0945b c0945b, Activity activity, SslErrorHandler sslErrorHandler) {
        this.f922c = c0945b;
        this.f920a = activity;
        this.f921b = sslErrorHandler;
    }

    @Override // java.lang.Runnable
    public void run() {
        C1016e.m4349a(this.f920a, "安全警告", "安全连接证书校验无效，将无法保证访问数据的安全性，请安装支付宝后重试。", "确定", new DialogInterface$OnClickListenerC0947d(this), null, null);
    }
}
