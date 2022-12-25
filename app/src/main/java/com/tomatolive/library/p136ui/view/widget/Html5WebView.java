package com.tomatolive.library.p136ui.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.File;

/* renamed from: com.tomatolive.library.ui.view.widget.Html5WebView */
/* loaded from: classes4.dex */
public class Html5WebView extends WebView {
    private boolean isLoad = false;
    private boolean isTouchBoolean = true;
    private Context mContext;

    public Html5WebView(Context context) {
        super(getFixedContext(context));
        init(context);
    }

    public Html5WebView(Context context, AttributeSet attributeSet) {
        super(getFixedContext(context), attributeSet);
        init(context);
    }

    public Html5WebView(Context context, AttributeSet attributeSet, int i) {
        super(getFixedContext(context), attributeSet, i);
        init(context);
    }

    @TargetApi(21)
    public Html5WebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(getFixedContext(context), attributeSet, i, i2);
        init(context);
    }

    public static Context getFixedContext(Context context) {
        int i = Build.VERSION.SDK_INT;
        return (i < 21 || i >= 23) ? context : context.createConfigurationContext(new Configuration());
    }

    private void init(Context context) {
        this.mContext = context;
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setDefaultTextEncodingName(EncryptUtil.CHARSET);
        settings.setLoadsImagesAutomatically(true);
        saveData(settings);
        newWin(settings);
        setWebChromeClient(new BaseWebChromeClient());
        setWebViewClient(new BaseWebViewClient());
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        super.loadUrl(str);
        this.isLoad = true;
    }

    public boolean isLoadBoolean() {
        return this.isLoad;
    }

    private void newWin(WebSettings webSettings) {
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void saveData(WebSettings webSettings) {
        webSettings.setCacheMode(-1);
        File cacheDir = this.mContext.getCacheDir();
        if (cacheDir != null) {
            String absolutePath = cacheDir.getAbsolutePath();
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setAppCachePath(absolutePath);
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.Html5WebView$BaseWebViewClient */
    /* loaded from: classes4.dex */
    public static class BaseWebViewClient extends WebViewClient {
        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.Html5WebView$BaseWebChromeClient */
    /* loaded from: classes4.dex */
    public static class BaseWebChromeClient extends WebChromeClient {
        @Override // android.webkit.WebChromeClient
        public void onReceivedIcon(WebView webView, Bitmap bitmap) {
            super.onReceivedIcon(webView, bitmap);
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            callback.invoke(str, true, false);
            super.onGeolocationPermissionsShowPrompt(str, callback);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
            ((WebView.WebViewTransport) message.obj).setWebView(webView);
            message.sendToTarget();
            return true;
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isTouchBoolean) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setTouchEnable(boolean z) {
        this.isTouchBoolean = z;
    }
}
