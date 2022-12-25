package com.alipay.sdk.widget;

import android.os.Handler;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.widget.C1011a;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.c */
/* loaded from: classes2.dex */
public class RunnableC1014c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C1011a f1081a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1014c(C1011a c1011a) {
        this.f1081a = c1011a;
    }

    @Override // java.lang.Runnable
    public void run() {
        C1011a.AlertDialogC1012a alertDialogC1012a;
        C1011a.AlertDialogC1012a alertDialogC1012a2;
        Handler handler;
        C1011a.AlertDialogC1012a alertDialogC1012a3;
        alertDialogC1012a = this.f1081a.f1074e;
        if (alertDialogC1012a != null) {
            alertDialogC1012a2 = this.f1081a.f1074e;
            if (!alertDialogC1012a2.isShowing()) {
                return;
            }
            try {
                handler = this.f1081a.f1078l;
                handler.removeMessages(1);
                alertDialogC1012a3 = this.f1081a.f1074e;
                alertDialogC1012a3.dismiss();
            } catch (Exception e) {
                C0996c.m4436a(e);
            }
        }
    }
}
