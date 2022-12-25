package com.alipay.sdk.app;

import android.content.DialogInterface;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;

/* renamed from: com.alipay.sdk.app.d */
/* loaded from: classes2.dex */
class DialogInterface$OnClickListenerC0947d implements DialogInterface.OnClickListener {

    /* renamed from: a */
    final /* synthetic */ RunnableC0946c f923a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DialogInterface$OnClickListenerC0947d(RunnableC0946c runnableC0946c) {
        this.f923a = runnableC0946c;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        C0988a c0988a;
        this.f923a.f921b.cancel();
        c0988a = this.f923a.f922c.f919c;
        C0954a.m4633a(c0988a, "net", "SSLDenied", "1");
        C0952j.m4646a(C0952j.m4643c());
        this.f923a.f920a.finish();
    }
}
