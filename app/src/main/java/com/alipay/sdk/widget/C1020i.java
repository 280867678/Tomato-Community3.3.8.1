package com.alipay.sdk.widget;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/* renamed from: com.alipay.sdk.widget.i */
/* loaded from: classes2.dex */
class C1020i implements DownloadListener {

    /* renamed from: a */
    final /* synthetic */ C1019h f1086a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C1020i(C1019h c1019h) {
        this.f1086a = c1019h;
    }

    @Override // android.webkit.DownloadListener
    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setFlags(268435456);
            this.f1086a.f1083a.startActivity(intent);
        } catch (Throwable unused) {
        }
    }
}
