package com.one.tomato.mvp.base.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.iceteck.silicompressorr.FileUtils;
import com.one.tomato.constants.Constants;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.utils.JavaAndJsMethod;
import com.one.tomato.utils.LogUtil;
import java.io.Serializable;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: BaseHtmlActivity.kt */
/* loaded from: classes3.dex */
public class BaseHtmlActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    protected HtmlConfig htmlConfig;
    protected JavaAndJsMethod javaAndJsMethod;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private final int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 2;
    private final int FILE_CHOOSER_RESULT_CODE = 1;
    private final BaseHtmlActivity$webChromeClient$1 webChromeClient = new WebChromeClient() { // from class: com.one.tomato.mvp.base.view.BaseHtmlActivity$webChromeClient$1
        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView view, int i) {
            Intrinsics.checkParameterIsNotNull(view, "view");
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
            Intrinsics.checkParameterIsNotNull(webView, "webView");
            Intrinsics.checkParameterIsNotNull(uploadMsg, "uploadMsg");
            Intrinsics.checkParameterIsNotNull(fileChooserParams, "fileChooserParams");
            BaseHtmlActivity.this.openFileChooseImpleForAndroid(uploadMsg);
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView view, String title) {
            TextView titleTV;
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(title, "title");
            super.onReceivedTitle(view, title);
            if (!BaseHtmlActivity.this.getHtmlConfig().isDynamicTitle() || (titleTV = BaseHtmlActivity.this.getTitleTV()) == null) {
                return;
            }
            titleTV.setText(title);
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            Intrinsics.checkParameterIsNotNull(origin, "origin");
            Intrinsics.checkParameterIsNotNull(callback, "callback");
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };
    private final BaseHtmlActivity$webViewClient$1 webViewClient = new WebViewClient() { // from class: com.one.tomato.mvp.base.view.BaseHtmlActivity$webViewClient$1
        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean contains$default;
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            String tag = BaseHtmlActivity.this.getTAG();
            LogUtil.m3783i(tag, "重定向的url:" + url);
            Uri uri = Uri.parse(url);
            Intrinsics.checkExpressionValueIsNotNull(uri, "uri");
            String scheme = uri.getScheme();
            if (scheme != null) {
                contains$default = StringsKt__StringsKt.contains$default(scheme, RechargeTypeAndMoney.RECHARGE_ALIPAY, false, 2, null);
                if (contains$default && BaseHtmlActivity.this.getHtmlConfig().isPay() && BaseHtmlActivity.this.getHtmlConfig().isInstallPay()) {
                    BaseHtmlActivity.this.startActivity(new Intent("android.intent.action.VIEW", uri));
                    return true;
                }
                return BaseHtmlActivity.this.webViewOverrideUrlLoading(view, url);
            }
            Intrinsics.throwNpe();
            throw null;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap bitmap) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            super.onPageStarted(view, url, bitmap);
            String tag = BaseHtmlActivity.this.getTAG();
            LogUtil.m3783i(tag, "页面开始加载的url:" + url);
            BaseHtmlActivity.this.showWaitingDialog();
            BaseHtmlActivity.this.webViewPageStarted();
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            super.onPageFinished(view, url);
            String tag = BaseHtmlActivity.this.getTAG();
            LogUtil.m3783i(tag, "页面加载结束的url:" + url);
            BaseHtmlActivity.this.hideWaitingDialog();
            BaseHtmlActivity.this.webViewPageFinished();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int i, String description, String failingUrl) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(description, "description");
            Intrinsics.checkParameterIsNotNull(failingUrl, "failingUrl");
            super.onReceivedError(view, i, description, failingUrl);
            BaseHtmlActivity.this.hideWaitingDialog();
        }

        @Override // android.webkit.WebViewClient
        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(dontResend, "dontResend");
            Intrinsics.checkParameterIsNotNull(resend, "resend");
            resend.sendToTarget();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(handler, "handler");
            Intrinsics.checkParameterIsNotNull(error, "error");
            handler.proceed();
        }
    };

    static {
        new Companion(null);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return 0;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean webViewOverrideUrlLoading(WebView view, String url) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(url, "url");
        return false;
    }

    public void webViewPageFinished() {
    }

    public void webViewPageStarted() {
    }

    /* compiled from: BaseHtmlActivity.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final HtmlConfig getHtmlConfig() {
        HtmlConfig htmlConfig = this.htmlConfig;
        if (htmlConfig != null) {
            return htmlConfig;
        }
        Intrinsics.throwUninitializedPropertyAccessException("htmlConfig");
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setHtmlConfig(HtmlConfig htmlConfig) {
        Intrinsics.checkParameterIsNotNull(htmlConfig, "<set-?>");
        this.htmlConfig = htmlConfig;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void initWebView(WebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "webView");
        WebSettings settings = webView.getSettings();
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
        this.javaAndJsMethod = new JavaAndJsMethod(this, webView);
        JavaAndJsMethod javaAndJsMethod = this.javaAndJsMethod;
        if (javaAndJsMethod == null) {
            Intrinsics.throwUninitializedPropertyAccessException("javaAndJsMethod");
            throw null;
        }
        webView.addJavascriptInterface(javaAndJsMethod, "Android");
        webView.setWebChromeClient(this.webChromeClient);
        webView.setWebViewClient(this.webViewClient);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(0);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setDisplayZoomControls(false);
        }
        webView.setDownloadListener(new DownloadListener() { // from class: com.one.tomato.mvp.base.view.BaseHtmlActivity$initWebView$1
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                BaseHtmlActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadUrl(WebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "webView");
        Serializable serializableExtra = getIntent().getSerializableExtra("html_config");
        if (serializableExtra == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.HtmlConfig");
        }
        this.htmlConfig = (HtmlConfig) serializableExtra;
        HtmlConfig htmlConfig = this.htmlConfig;
        if (htmlConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("htmlConfig");
            throw null;
        }
        if (!htmlConfig.isPay()) {
            webView.getSettings().setUserAgentString(Constants.AppKey.HEADER_KEY_USER_AGENT);
        }
        HtmlConfig htmlConfig2 = this.htmlConfig;
        if (htmlConfig2 != null) {
            webView.loadUrl(htmlConfig2.getUrl());
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("htmlConfig");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void openFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback) {
        Intrinsics.checkParameterIsNotNull(filePathCallback, "filePathCallback");
        this.mUploadMessageForAndroid5 = filePathCallback;
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        Intent intent2 = new Intent("android.intent.action.CHOOSER");
        intent2.putExtra("android.intent.extra.INTENT", intent);
        intent2.putExtra("android.intent.extra.TITLE", "Image Chooser");
        startActivityForResult(intent2, this.FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ValueCallback<Uri[]> valueCallback;
        Uri data = (intent == null || i2 != -1) ? null : intent.getData();
        if (i == this.FILE_CHOOSER_RESULT_CODE) {
            ValueCallback<Uri> valueCallback2 = this.mUploadMessage;
            if (valueCallback2 == null) {
                return;
            }
            if (valueCallback2 != null) {
                valueCallback2.onReceiveValue(data);
            }
            this.mUploadMessage = null;
        } else if (i != this.FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 || (valueCallback = this.mUploadMessageForAndroid5) == null) {
        } else {
            if (data != null) {
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(new Uri[]{data});
                }
            } else if (valueCallback != null) {
                valueCallback.onReceiveValue(new Uri[0]);
            }
            this.mUploadMessageForAndroid5 = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void webOnBack(WebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "webView");
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        HtmlConfig htmlConfig = this.htmlConfig;
        if (htmlConfig == null) {
            Intrinsics.throwUninitializedPropertyAccessException("htmlConfig");
            throw null;
        }
        if (htmlConfig.getClazz() != null) {
            HtmlConfig htmlConfig2 = this.htmlConfig;
            if (htmlConfig2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("htmlConfig");
                throw null;
            }
            startActivity(new Intent(this, htmlConfig2.getClazz()));
        }
        super.onBackPressed();
    }
}
