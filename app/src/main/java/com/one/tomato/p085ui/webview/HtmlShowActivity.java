package com.one.tomato.p085ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.broccoli.p150bh.R;
import com.iceteck.silicompressorr.FileUtils;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.constants.Constants;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.utils.JavaAndJsMethod;
import com.one.tomato.utils.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_html_show)
/* renamed from: com.one.tomato.ui.webview.HtmlShowActivity */
/* loaded from: classes3.dex */
public class HtmlShowActivity extends BaseActivity {
    protected HtmlConfig htmlConfig;
    protected JavaAndJsMethod javaAndJsMethod;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
    @ViewInject(R.id.webView)
    protected WebView mWebView;
    private WebChromeClient webChromeClient = new WebChromeClient() { // from class: com.one.tomato.ui.webview.HtmlShowActivity.2
        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            HtmlShowActivity.this.openFileChooseImpleForAndroid(valueCallback);
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (HtmlShowActivity.this.htmlConfig.isDynamicTitle()) {
                ((BaseActivity) HtmlShowActivity.this).titleTV.setText(str);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            callback.invoke(str, true, false);
            super.onGeolocationPermissionsShowPrompt(str, callback);
        }
    };
    private WebViewClient webViewClient = new WebViewClient() { // from class: com.one.tomato.ui.webview.HtmlShowActivity.3
        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            String str2 = ((BaseActivity) HtmlShowActivity.this).TAG;
            LogUtil.m3783i(str2, "重定向的url:" + str);
            Uri parse = Uri.parse(str);
            if (parse.getScheme().contains(RechargeTypeAndMoney.RECHARGE_ALIPAY) && HtmlShowActivity.this.htmlConfig.isPay() && HtmlShowActivity.this.htmlConfig.isInstallPay()) {
                HtmlShowActivity.this.startActivity(new Intent("android.intent.action.VIEW", parse));
                return true;
            }
            return HtmlShowActivity.this.webViewOverrideUrlLoading(webView, str);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            String str2 = ((BaseActivity) HtmlShowActivity.this).TAG;
            LogUtil.m3783i(str2, "页面开始加载的url:" + str);
            HtmlShowActivity.this.showWaitingDialog();
            HtmlShowActivity.this.webViewPageStarted();
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            String str2 = ((BaseActivity) HtmlShowActivity.this).TAG;
            LogUtil.m3783i(str2, "页面加载结束的url:" + str);
            HtmlShowActivity.this.hideWaitingDialog();
            HtmlShowActivity.this.webViewPageFinished();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            HtmlShowActivity.this.hideWaitingDialog();
        }

        @Override // android.webkit.WebViewClient
        public void onFormResubmission(WebView webView, Message message, Message message2) {
            message2.sendToTarget();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    };

    protected boolean webViewOverrideUrlLoading(WebView webView, String str) {
        return false;
    }

    protected void webViewPageFinished() {
    }

    protected void webViewPageStarted() {
    }

    public static void startActivity(Context context, HtmlConfig htmlConfig) {
        Intent intent = new Intent(context, HtmlShowActivity.class);
        intent.putExtra("html_config", htmlConfig);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initWebView();
        initData();
    }

    private void initWebView() {
        WebSettings settings = this.mWebView.getSettings();
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(2);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setBlockNetworkImage(false);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        this.javaAndJsMethod = new JavaAndJsMethod(this, this.mWebView);
        this.mWebView.addJavascriptInterface(this.javaAndJsMethod, "Android");
        this.mWebView.setWebChromeClient(this.webChromeClient);
        this.mWebView.setWebViewClient(this.webViewClient);
        if (Build.VERSION.SDK_INT >= 21) {
            this.mWebView.getSettings().setMixedContentMode(0);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.mWebView.getSettings().setDisplayZoomControls(false);
        }
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: com.one.tomato.ui.webview.HtmlShowActivity.1
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                HtmlShowActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        });
    }

    private void initData() {
        initTitleBar();
        this.htmlConfig = (HtmlConfig) getIntent().getSerializableExtra("html_config");
        if (!TextUtils.isEmpty(this.htmlConfig.getTitle())) {
            this.titleTV.setText(this.htmlConfig.getTitle());
        }
        if (!this.htmlConfig.isPay()) {
            this.mWebView.getSettings().setUserAgentString(Constants.AppKey.HEADER_KEY_USER_AGENT);
        }
        this.mWebView.loadUrl(this.htmlConfig.getUrl());
    }

    protected void openFileChooseImpleForAndroid(ValueCallback<Uri[]> valueCallback) {
        this.mUploadMessageForAndroid5 = valueCallback;
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        Intent intent2 = new Intent("android.intent.action.CHOOSER");
        intent2.putExtra("android.intent.extra.INTENT", intent);
        intent2.putExtra("android.intent.extra.TITLE", "Image Chooser");
        startActivityForResult(intent2, 2);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ValueCallback<Uri[]> valueCallback;
        Uri data = (intent == null || i2 != -1) ? null : intent.getData();
        if (i == 1) {
            ValueCallback<Uri> valueCallback2 = this.mUploadMessage;
            if (valueCallback2 == null) {
                return;
            }
            valueCallback2.onReceiveValue(data);
            this.mUploadMessage = null;
        } else if (i != 2 || (valueCallback = this.mUploadMessageForAndroid5) == null) {
        } else {
            if (data != null) {
                valueCallback.onReceiveValue(new Uri[]{data});
            } else {
                valueCallback.onReceiveValue(new Uri[0]);
            }
            this.mUploadMessageForAndroid5 = null;
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return;
        }
        if (this.htmlConfig.getClazz() != null) {
            startActivity(new Intent(this, this.htmlConfig.getClazz()));
        }
        super.onBackPressed();
    }
}
