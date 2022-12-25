package com.alipay.sdk.util;

import com.alipay.sdk.app.AlipayResultActivity;
import com.alipay.sdk.app.C0952j;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.util.g */
/* loaded from: classes2.dex */
public class C1001g implements AlipayResultActivity.AbstractC0941a {

    /* renamed from: a */
    final /* synthetic */ CountDownLatch f1064a;

    /* renamed from: b */
    final /* synthetic */ C0998e f1065b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C1001g(C0998e c0998e, CountDownLatch countDownLatch) {
        this.f1065b = c0998e;
        this.f1064a = countDownLatch;
    }

    @Override // com.alipay.sdk.app.AlipayResultActivity.AbstractC0941a
    /* renamed from: a */
    public void mo4413a(int i, String str, String str2) {
        this.f1065b.f1061j = C0952j.m4647a(i, str, str2);
        this.f1064a.countDown();
    }
}
