package com.alipay.sdk.app;

import com.alipay.sdk.util.C0998e;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.app.h */
/* loaded from: classes2.dex */
public class C0950h implements C0998e.AbstractC0999a {

    /* renamed from: a */
    final /* synthetic */ PayTask f929a;

    @Override // com.alipay.sdk.util.C0998e.AbstractC0999a
    /* renamed from: a */
    public void mo4415a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public C0950h(PayTask payTask) {
        this.f929a = payTask;
    }

    @Override // com.alipay.sdk.util.C0998e.AbstractC0999a
    /* renamed from: b */
    public void mo4414b() {
        this.f929a.dismissLoading();
    }
}
