package com.alipay.sdk.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.SystemClock;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C1008n;

/* renamed from: com.alipay.sdk.app.b */
/* loaded from: classes2.dex */
public class C0945b extends WebViewClient {

    /* renamed from: a */
    private Activity f917a;

    /* renamed from: b */
    private boolean f918b;

    /* renamed from: c */
    private final C0988a f919c;

    public C0945b(Activity activity, C0988a c0988a) {
        this.f917a = activity;
        this.f919c = c0988a;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.f918b = true;
        super.onReceivedError(webView, i, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        Activity activity = this.f917a;
        if (activity == null) {
            return;
        }
        C0988a c0988a = this.f919c;
        C0954a.m4633a(c0988a, "net", "SSLError", "1" + sslError);
        activity.runOnUiThread(new RunnableC0946c(this, activity, sslErrorHandler));
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return C1008n.m4387a(this.f919c, webView, str, this.f917a);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        C0988a c0988a = this.f919c;
        C0954a.m4628b(c0988a, "biz", "h5ld", SystemClock.elapsedRealtime() + "|" + C1008n.m4368e(str));
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        C0988a c0988a = this.f919c;
        C0954a.m4628b(c0988a, "biz", "h5ldd", SystemClock.elapsedRealtime() + "|" + C1008n.m4368e(str));
    }

    /* renamed from: a */
    public void m4654a() {
        this.f917a = null;
    }

    /* renamed from: b */
    public boolean m4652b() {
        return this.f918b;
    }
}
