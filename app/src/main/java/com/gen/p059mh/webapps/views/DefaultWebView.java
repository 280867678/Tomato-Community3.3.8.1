package com.gen.p059mh.webapps.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.PageInjectListener;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.listener.WebViewClientLoadListener;
import com.gen.p059mh.webapps.utils.InjectJsContextUtils;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.views.DefaultWebView;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.slf4j.Marker;

/* renamed from: com.gen.mh.webapps.views.DefaultWebView */
/* loaded from: classes2.dex */
public class DefaultWebView extends WebView {
    public static final String ERROR_FORMAT = "load page %s with errorCode %d errorMessage %s";
    DefaultWebChromeClient chromeClient;
    public String firstUrl;
    InjectJsContextUtils injectJsContextUtils;
    boolean isNeedInject = true;
    public Context mContext;
    public EditText mFocusDistraction;
    PageInjectListener pageInjectListener;
    PageLoadFinishListener pageLoadFinishListener;
    IWebBizOperation webViewCallback;
    WebViewClientLoadListener webViewClientLoadListener;

    public void setScroll(boolean z) {
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        super.loadUrl(str);
        if (this.firstUrl == null) {
            this.firstUrl = str;
        }
    }

    public void setNeedInject(boolean z) {
        this.isNeedInject = z;
    }

    public void setPageInjectListener(PageInjectListener pageInjectListener) {
        this.pageInjectListener = pageInjectListener;
        if (this.isNeedInject) {
            this.injectJsContextUtils = new InjectJsContextUtils(pageInjectListener.provideDefaultPath(), pageInjectListener.provideResourceType());
            this.injectJsContextUtils.setAem(pageInjectListener.aem());
        }
    }

    public void setWebViewClientLoadListener(WebViewClientLoadListener webViewClientLoadListener) {
        this.webViewClientLoadListener = webViewClientLoadListener;
        DefaultWebChromeClient defaultWebChromeClient = this.chromeClient;
        if (defaultWebChromeClient != null) {
            defaultWebChromeClient.setWebViewClientLoadListener(webViewClientLoadListener);
        }
    }

    public void setPageLoadFinishListener(PageLoadFinishListener pageLoadFinishListener) {
        this.pageLoadFinishListener = pageLoadFinishListener;
    }

    public void setWebViewCallback(IWebBizOperation iWebBizOperation) {
        this.webViewCallback = iWebBizOperation;
    }

    public DefaultWebView(Context context) {
        super(context);
        init(context);
    }

    public DefaultWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DefaultWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public void init(Context context) {
        if (isInEditMode()) {
            return;
        }
        this.mContext = context;
        this.mFocusDistraction = new EditText(context);
        this.mFocusDistraction.setBackgroundResource(17170445);
        addView(this.mFocusDistraction);
        this.mFocusDistraction.getLayoutParams().width = 1;
        this.mFocusDistraction.getLayoutParams().height = 1;
        initWebViewParams();
    }

    private void initWebViewParams() {
        setScrollContainer(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        getSettings().setDomStorageEnabled(true);
        getSettings().setGeolocationEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        getSettings().setCacheMode(-1);
        getSettings().setAppCacheEnabled(true);
        getSettings().setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 16) {
            getSettings().setAllowFileAccessFromFileURLs(true);
            getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= 17) {
            getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        requestFocus(130);
        this.chromeClient = new DefaultWebChromeClient(getContext()) { // from class: com.gen.mh.webapps.views.DefaultWebView.1
            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                super.onShowCustomView(view, customViewCallback);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                if (DefaultWebView.this.getContext() == null) {
                    return false;
                }
                new AlertDialog.Builder(DefaultWebView.this.getContext()).setMessage(str2).setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: com.gen.mh.webapps.views.DefaultWebView.1.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.confirm();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() { // from class: com.gen.mh.webapps.views.DefaultWebView.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.cancel();
                    }
                }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.gen.mh.webapps.views.DefaultWebView.1.1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public void onCancel(DialogInterface dialogInterface) {
                        jsResult.cancel();
                    }
                }).create().show();
                return true;
            }

            @Override // com.gen.p059mh.webapps.views.DefaultWebChromeClient
            public void switchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener) {
                if (DefaultWebView.this.webViewCallback != null) {
                    Logger.m4113i("switchPhotoOrAlbum");
                    DefaultWebView.this.webViewCallback.doSwitchPhotoOrAlbum(photoSwitchListener);
                }
            }

            @Override // com.gen.p059mh.webapps.views.DefaultWebChromeClient
            public void startActivity(Intent intent, int i, PhotoSwitchListener photoSwitchListener) {
                IWebBizOperation iWebBizOperation = DefaultWebView.this.webViewCallback;
                if (iWebBizOperation != null) {
                    iWebBizOperation.checkPermissionAndStart(intent, i, photoSwitchListener);
                }
            }
        };
        setWebChromeClient(this.chromeClient);
        WebViewClientLoadListener webViewClientLoadListener = this.webViewClientLoadListener;
        if (webViewClientLoadListener != null) {
            this.chromeClient.setWebViewClientLoadListener(webViewClientLoadListener);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(WebEngineManager.WebViewDebug);
        }
        setWebViewClient(new C18542());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.views.DefaultWebView$2 */
    /* loaded from: classes2.dex */
    public class C18542 extends WebViewClient {
        String pageUrl = null;
        Map<String, Boolean> injectMap = new Hashtable();

        C18542() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            this.pageUrl = str;
            super.onPageStarted(webView, str, bitmap);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (DefaultWebView.this.pageLoadFinishListener == null || Build.VERSION.SDK_INT < 23) {
                return;
            }
            DefaultWebView.this.pageLoadFinishListener.onLoadPageError(new RuntimeException(String.format(DefaultWebView.ERROR_FORMAT, webResourceRequest.getUrl().toString(), Integer.valueOf(webResourceError.getErrorCode()), webResourceError.getDescription())));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            DefaultWebView defaultWebView = DefaultWebView.this;
            if (defaultWebView.pageLoadFinishListener == null || !str2.equals(defaultWebView.firstUrl)) {
                return;
            }
            DefaultWebView.this.pageLoadFinishListener.onLoadPageError(new RuntimeException(String.format(DefaultWebView.ERROR_FORMAT, str2, Integer.valueOf(i), str)));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
            if (Build.VERSION.SDK_INT < 21 || !webResourceRequest.getUrl().toString().equals(DefaultWebView.this.firstUrl)) {
                return;
            }
            DefaultWebView.this.pageLoadFinishListener.onLoadPageError(new RuntimeException(String.format(DefaultWebView.ERROR_FORMAT, webResourceRequest.getUrl().toString(), Integer.valueOf(webResourceResponse.getStatusCode()), webResourceResponse.getReasonPhrase())));
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            PageLoadFinishListener pageLoadFinishListener = DefaultWebView.this.pageLoadFinishListener;
            if (pageLoadFinishListener != null) {
                pageLoadFinishListener.onLoadPageFinish(str);
            }
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
            Logger.m4112i("shouldInterceptRequest_url", str);
            DefaultWebView defaultWebView = DefaultWebView.this;
            if (defaultWebView.isNeedInject && defaultWebView.injectJsContextUtils != null && !this.injectMap.containsKey(str)) {
                Logger.m4113i("webView shouldInterceptRequest with url" + this.injectMap.toString());
                this.injectMap.remove(str);
                WebResourceResponse loadingPage = DefaultWebView.this.injectJsContextUtils.loadingPage(webView, str);
                insertOrigin(loadingPage);
                return loadingPage;
            }
            this.injectMap.remove(str);
            WebResourceResponse shouldInterceptRequest = super.shouldInterceptRequest(webView, str);
            insertOrigin(shouldInterceptRequest);
            return shouldInterceptRequest;
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            WebResourceResponse shouldInterceptRequest;
            Logger.m4112i("shouldInterceptRequest_request", webResourceRequest.getUrl().toString());
            if (Build.VERSION.SDK_INT >= 21) {
                DefaultWebView defaultWebView = DefaultWebView.this;
                if (defaultWebView.isNeedInject && defaultWebView.injectJsContextUtils != null) {
                    boolean z = true;
                    boolean z2 = webResourceRequest.getRequestHeaders().get("Accept") != null && webResourceRequest.getRequestHeaders().get("Accept").contains("text/html");
                    if (!webResourceRequest.getMethod().equalsIgnoreCase("GET") || !z2) {
                        z = false;
                    }
                    if (z) {
                        WebResourceResponse loadingPage = DefaultWebView.this.injectJsContextUtils.loadingPage(webView, webResourceRequest);
                        if (loadingPage.getResponseHeaders() != null && loadingPage.getResponseHeaders().containsKey("Location")) {
                            final String str = loadingPage.getResponseHeaders().get("Location");
                            webView.postDelayed(new Runnable() { // from class: com.gen.mh.webapps.views.-$$Lambda$DefaultWebView$2$EbP9uz_assB3eQ9EEMxuo6pHaOI
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DefaultWebView.C18542.this.lambda$shouldInterceptRequest$0$DefaultWebView$2(str);
                                }
                            }, 100L);
                        }
                        return loadingPage;
                    }
                    this.injectMap.put(webResourceRequest.getUrl().toString(), false);
                    shouldInterceptRequest = super.shouldInterceptRequest(webView, webResourceRequest);
                } else {
                    shouldInterceptRequest = super.shouldInterceptRequest(webView, webResourceRequest);
                }
            } else {
                shouldInterceptRequest = super.shouldInterceptRequest(webView, webResourceRequest);
            }
            insertOrigin(shouldInterceptRequest);
            return shouldInterceptRequest;
        }

        public /* synthetic */ void lambda$shouldInterceptRequest$0$DefaultWebView$2(String str) {
            if (DefaultWebView.this.injectJsContextUtils.getCookie().size() != 0) {
                DefaultWebView defaultWebView = DefaultWebView.this;
                defaultWebView.syncCookie(str, defaultWebView.injectJsContextUtils.getCookie());
            }
            DefaultWebView.this.loadUrl(str);
        }

        public void synCookies(Context context, String str, String str2) {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        }

        public void insertOrigin(WebResourceResponse webResourceResponse) {
            if (webResourceResponse != null) {
                Map<String, String> hashMap = new HashMap<>();
                if (Build.VERSION.SDK_INT >= 21) {
                    hashMap = webResourceResponse.getResponseHeaders();
                }
                if (hashMap != null) {
                    if (!hashMap.containsKey("Access-Control-Allow-Origin")) {
                        hashMap.put("Access-Control-Allow-Origin", Marker.ANY_MARKER);
                    }
                    if (!hashMap.containsKey("Access-Control-Allow-Credentials")) {
                        hashMap.put("Access-Control-Allow-Credentials", "true");
                    }
                    if (!hashMap.containsKey("Access-Control-Max-Age")) {
                        hashMap.put("Access-Control-Max-Age", "1800");
                    }
                    if (!hashMap.containsKey("Access-Control-Allow-Methods")) {
                        hashMap.put("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
                    }
                    if (!hashMap.containsKey("Access-Control-Allow-Headers")) {
                        hashMap.put("Access-Control-Allow-Headers", Marker.ANY_MARKER);
                    }
                }
                if (Build.VERSION.SDK_INT < 21) {
                    return;
                }
                webResourceResponse.setResponseHeaders(hashMap);
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Logger.m4112i("shouldOverrideUrlLoading", str);
            return false;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            if (Build.VERSION.SDK_INT >= 21) {
                Logger.m4112i("shouldOverrideUrlLoading", webResourceRequest.getUrl());
                return false;
            }
            return false;
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }
    }

    public void syncCookie(String str, Map<String, String> map) {
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(getContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        if (map != null) {
            for (String str2 : map.keySet()) {
                cookieManager.setCookie(str, str2 + SimpleComparison.EQUAL_TO_OPERATION + map.get(str2).trim());
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    public DefaultWebChromeClient getPaxWebChromeClient() {
        return this.chromeClient;
    }

    public void setUserAgent(String str) {
        getSettings().setUserAgentString(str);
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    public boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        IWebBizOperation iWebBizOperation = this.webViewCallback;
        if (iWebBizOperation != null) {
            iWebBizOperation.onScrollerChange(i3, i4);
        }
        return super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
    }

    public void executeJs(String str) {
        if (Build.VERSION.SDK_INT >= 19) {
            evaluateJavascript(str, null);
        } else {
            loadUrl(str);
        }
    }
}
