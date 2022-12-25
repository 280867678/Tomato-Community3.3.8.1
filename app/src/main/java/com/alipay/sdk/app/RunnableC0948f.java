package com.alipay.sdk.app;

import android.app.Activity;

/* renamed from: com.alipay.sdk.app.f */
/* loaded from: classes2.dex */
final class RunnableC0948f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Activity f924a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0948f(Activity activity) {
        this.f924a = activity;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f924a.finish();
    }
}
