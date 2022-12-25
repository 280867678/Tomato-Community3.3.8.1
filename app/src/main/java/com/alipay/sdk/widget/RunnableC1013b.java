package com.alipay.sdk.widget;

import android.os.Handler;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.widget.C1011a;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.b */
/* loaded from: classes2.dex */
public class RunnableC1013b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C1011a f1080a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1013b(C1011a c1011a) {
        this.f1080a = c1011a;
    }

    @Override // java.lang.Runnable
    public void run() {
        C1011a.AlertDialogC1012a alertDialogC1012a;
        C1011a.AlertDialogC1012a alertDialogC1012a2;
        C1011a.AlertDialogC1012a alertDialogC1012a3;
        Handler handler;
        C1011a.AlertDialogC1012a alertDialogC1012a4;
        boolean z;
        alertDialogC1012a = this.f1080a.f1074e;
        if (alertDialogC1012a == null) {
            C1011a c1011a = this.f1080a;
            c1011a.f1074e = new C1011a.AlertDialogC1012a(c1011a.f1075f);
            alertDialogC1012a4 = this.f1080a.f1074e;
            z = this.f1080a.f1077k;
            alertDialogC1012a4.setCancelable(z);
        }
        try {
            alertDialogC1012a2 = this.f1080a.f1074e;
            if (alertDialogC1012a2.isShowing()) {
                return;
            }
            alertDialogC1012a3 = this.f1080a.f1074e;
            alertDialogC1012a3.show();
            handler = this.f1080a.f1078l;
            handler.sendEmptyMessageDelayed(1, 15000L);
        } catch (Exception e) {
            C0996c.m4436a(e);
        }
    }
}
