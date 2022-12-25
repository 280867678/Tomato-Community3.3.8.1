package com.alipay.sdk.widget;

import android.view.View;

/* renamed from: com.alipay.sdk.widget.r */
/* loaded from: classes2.dex */
class RunnableC1033r implements Runnable {

    /* renamed from: a */
    final /* synthetic */ View f1115a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1033r(View$OnClickListenerC1032q view$OnClickListenerC1032q, View view) {
        this.f1115a = view;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1115a.setEnabled(true);
    }
}
