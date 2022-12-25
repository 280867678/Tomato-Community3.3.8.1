package com.netease.nis.captcha;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* loaded from: classes3.dex */
public class CaptchaWebView extends WebView {

    /* renamed from: a */
    private CaptchaListener f1593a;

    /* renamed from: b */
    private String[] f1594b = {"android.content.pm.PackageManager$NameNotFoundException", "java.lang.RuntimeException: Cannot load WebView", "android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed"};

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.netease.nis.captcha.CaptchaWebView$a */
    /* loaded from: classes3.dex */
    public class C2397a extends WebViewClient {
        private C2397a() {
        }

        /* renamed from: a */
        private void m3831a(String str) {
            Log.d(Captcha.TAG, String.format("WebViewClient's [%s] method has callback", str));
        }

        @Override // android.webkit.WebViewClient
        @TargetApi(23)
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            m3831a("onReceivedError");
            C2409d.m3800a("error code is:%s error description is:%s", Integer.valueOf(webResourceError.getErrorCode()), webResourceError.getDescription());
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (CaptchaWebView.this.f1593a != null) {
                CaptchaListener captchaListener = CaptchaWebView.this.f1593a;
                int errorCode = webResourceError.getErrorCode();
                captchaListener.onError(errorCode, "[onReceivedError]error code:" + webResourceError.getErrorCode() + "error desc:" + ((Object) webResourceError.getDescription()));
            }
        }

        @Override // android.webkit.WebViewClient
        @TargetApi(21)
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            if (webResourceRequest.isForMainFrame() && !webResourceRequest.getUrl().getPath().endsWith("/favicon.ico")) {
                m3831a("onReceivedHttpError");
                C2409d.m3800a("[onReceivedHttpError] status code is:%s error reason is:%s", Integer.valueOf(webResourceResponse.getStatusCode()), webResourceResponse.getReasonPhrase());
            }
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
            if (CaptchaWebView.this.f1593a != null) {
                CaptchaListener captchaListener = CaptchaWebView.this.f1593a;
                int statusCode = webResourceResponse.getStatusCode();
                captchaListener.onError(statusCode, "[onReceivedHttpError]error code:" + webResourceResponse.getStatusCode() + "error desc:" + webResourceResponse.getReasonPhrase());
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            m3831a("onReceivedSslError" + sslError.toString());
            if (C2409d.m3805a()) {
                sslErrorHandler.proceed();
            } else {
                sslErrorHandler.cancel();
            }
            if (CaptchaWebView.this.f1593a != null) {
                CaptchaWebView.this.f1593a.onError(sslError.getPrimaryError(), "[onReceivedSslError]");
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }

    public CaptchaWebView(Context context) {
        super(m3833a(context));
        m3834a();
    }

    public CaptchaWebView(Context context, AttributeSet attributeSet) {
        super(m3833a(context), attributeSet);
        m3834a();
    }

    public CaptchaWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m3834a();
    }

    /* renamed from: a */
    private static Context m3833a(Context context) {
        int i = Build.VERSION.SDK_INT;
        return (i < 21 || i >= 23) ? context : context.createConfigurationContext(new Configuration());
    }

    /* renamed from: a */
    private void m3834a() {
        boolean z;
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        try {
            setOverScrollMode(2);
        } finally {
            if (!z) {
            }
            setHorizontalScrollBarEnabled(false);
            setVerticalScrollBarEnabled(false);
            setWebChromeClient(new WebChromeClient());
            setWebViewClient(new C2397a());
            resumeTimers();
        }
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new C2397a());
        resumeTimers();
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        scrollTo(0, 0);
    }

    public void setCaptchaListener(CaptchaListener captchaListener) {
        this.f1593a = captchaListener;
    }
}
