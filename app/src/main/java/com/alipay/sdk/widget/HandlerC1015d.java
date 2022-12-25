package com.alipay.sdk.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.d */
/* loaded from: classes2.dex */
public class HandlerC1015d extends Handler {

    /* renamed from: a */
    final /* synthetic */ C1011a f1082a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HandlerC1015d(C1011a c1011a, Looper looper) {
        super(looper);
        this.f1082a = c1011a;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        this.f1082a.m4357c();
    }
}
