package com.alipay.sdk.util;

import android.app.Activity;

/* renamed from: com.alipay.sdk.util.o */
/* loaded from: classes2.dex */
final class RunnableC1010o implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Activity f1072a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC1010o(Activity activity) {
        this.f1072a = activity;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1072a.finish();
    }
}
