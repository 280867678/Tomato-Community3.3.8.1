package com.alipay.sdk.widget;

import android.webkit.SslErrorHandler;

/* renamed from: com.alipay.sdk.widget.n */
/* loaded from: classes2.dex */
class RunnableC1026n implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SslErrorHandler f1099a;

    /* renamed from: b */
    final /* synthetic */ C1021j f1100b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1026n(C1021j c1021j, SslErrorHandler sslErrorHandler) {
        this.f1100b = c1021j;
        this.f1099a = sslErrorHandler;
    }

    @Override // java.lang.Runnable
    public void run() {
        C1016e.m4349a(this.f1100b.f1083a, "安全警告", "安全連接證書校驗無效，將無法保證訪問資料的安全性，請安裝支付寶 Alipay 後重試。", "確定", new DialogInterface$OnClickListenerC1027o(this), null, null);
    }
}
