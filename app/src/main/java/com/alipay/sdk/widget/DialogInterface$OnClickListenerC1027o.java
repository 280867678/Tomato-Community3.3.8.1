package com.alipay.sdk.widget;

import android.content.DialogInterface;
import com.alipay.sdk.app.C0952j;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;

/* renamed from: com.alipay.sdk.widget.o */
/* loaded from: classes2.dex */
class DialogInterface$OnClickListenerC1027o implements DialogInterface.OnClickListener {

    /* renamed from: a */
    final /* synthetic */ RunnableC1026n f1101a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DialogInterface$OnClickListenerC1027o(RunnableC1026n runnableC1026n) {
        this.f1101a = runnableC1026n;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        C0988a c0988a;
        this.f1101a.f1099a.cancel();
        c0988a = this.f1101a.f1100b.f1090w;
        C0954a.m4633a(c0988a, "net", "SSLDenied", "2");
        C0952j.m4646a(C0952j.m4643c());
        this.f1101a.f1100b.f1083a.finish();
    }
}
