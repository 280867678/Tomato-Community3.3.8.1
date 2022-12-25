package com.alipay.sdk.widget;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.widget.C1028p;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.t */
/* loaded from: classes2.dex */
public class C1035t extends WebViewClient {

    /* renamed from: a */
    final /* synthetic */ C1028p f1117a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C1035t(C1028p c1028p) {
        this.f1117a = c1028p;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        C1028p.AbstractC1030b abstractC1030b;
        abstractC1030b = this.f1117a.f1109h;
        if (!abstractC1030b.mo4312b(this.f1117a, str)) {
            return super.shouldOverrideUrlLoading(webView, str);
        }
        return true;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        C1028p.AbstractC1030b abstractC1030b;
        abstractC1030b = this.f1117a.f1109h;
        if (!abstractC1030b.mo4311c(this.f1117a, str)) {
            super.onPageFinished(webView, str);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        C1028p.AbstractC1030b abstractC1030b;
        abstractC1030b = this.f1117a.f1109h;
        if (!abstractC1030b.mo4314a(this.f1117a, i, str, str2)) {
            super.onReceivedError(webView, i, str, str2);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        C1028p.AbstractC1030b abstractC1030b;
        abstractC1030b = this.f1117a.f1109h;
        if (!abstractC1030b.mo4313a(this.f1117a, sslErrorHandler, sslError)) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }
}
