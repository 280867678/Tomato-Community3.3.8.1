package com.tomatolive.library.p136ui.activity.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* renamed from: com.tomatolive.library.ui.activity.home.WebViewActivity */
/* loaded from: classes3.dex */
public class WebViewActivity extends BaseActivity {
    private FrameLayout flContentView;
    private FrameLayout flRetryView;
    private boolean isFromService = false;
    private volatile boolean isLoadError = false;
    private ProgressBar mPb;
    private String mUrl;
    private Html5WebView mWebView;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_web_view;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.isFromService = getIntent().getBooleanExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, false);
        this.mUrl = getIntent().getStringExtra("url");
        this.mPb = (ProgressBar) findViewById(R$id.f5823pb);
        this.flContentView = (FrameLayout) findViewById(R$id.fl_content_view);
        this.flRetryView = (FrameLayout) findViewById(R$id.fl_retry_view);
        setActivityTitle(getIntent().getStringExtra("title"));
        initWebView();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        findViewById(R$id.tv_btn_reload).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$WebViewActivity$wUM7_IYLVJ7q4H-YgPvg7tQdKdA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WebViewActivity.this.lambda$initListener$0$WebViewActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$WebViewActivity(View view) {
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.clearHistory();
        }
        initWebView();
    }

    private void initWebView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mWebView = new Html5WebView(getApplicationContext());
        this.mWebView.setLayoutParams(layoutParams);
        this.flContentView.addView(this.mWebView);
        this.mWebView.setWebChromeClient(new Html5WebChromeClient());
        this.mWebView.setWebViewClient(new Html5WebViewClient());
        if (this.isFromService) {
            this.mWebView.getSettings().setLoadWithOverviewMode(false);
            this.mWebView.getSettings().setUseWideViewPort(false);
            getContent();
        } else {
            this.mWebView.loadUrl(this.mUrl);
        }
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$WebViewActivity$Z4VWnWfsDwDhDFVzhxwlWiVOD9o
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                WebViewActivity.this.lambda$initWebView$1$WebViewActivity(str, str2, str3, str4, j);
            }
        });
        this.mWebView.setOnKeyListener(new View.OnKeyListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$WebViewActivity$sbKSSdlUDhjyUonYD42J1kgLLgk
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return WebViewActivity.this.lambda$initWebView$2$WebViewActivity(view, i, keyEvent);
            }
        });
    }

    public /* synthetic */ void lambda$initWebView$1$WebViewActivity(String str, String str2, String str3, String str4, long j) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ boolean lambda$initWebView$2$WebViewActivity(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        }
        return false;
    }

    private void getContent() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(this.mUrl)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.activity.home.WebViewActivity.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.activity.home.WebViewActivity.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                WebViewActivity.this.mWebView.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContentView(boolean z) {
        this.mPb.setVisibility(8);
        int i = 4;
        this.flRetryView.setVisibility(z ? 4 : 0);
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            if (z) {
                i = 0;
            }
            html5WebView.setVisibility(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.home.WebViewActivity$Html5WebChromeClient */
    /* loaded from: classes3.dex */
    public class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {
        Html5WebChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            WebViewActivity.this.mPb.setProgress(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.activity.home.WebViewActivity$Html5WebViewClient */
    /* loaded from: classes3.dex */
    public class Html5WebViewClient extends Html5WebView.BaseWebViewClient {
        private Html5WebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            WebViewActivity.this.isLoadError = false;
            WebViewActivity.this.mPb.setVisibility(0);
        }

        @Override // com.tomatolive.library.p136ui.view.widget.Html5WebView.BaseWebViewClient, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("pt://")) {
                try {
                    WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                } catch (Exception unused) {
                    webView.loadUrl("https://www.potato.im/p/androids");
                }
                return false;
            } else if (Build.VERSION.SDK_INT >= 26) {
                return false;
            } else {
                webView.loadUrl(str);
                return true;
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            if (Build.VERSION.SDK_INT >= 23) {
                return;
            }
            WebViewActivity.this.isLoadError = true;
            WebViewActivity.this.initContentView(false);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (Build.VERSION.SDK_INT < 21 || !webResourceRequest.isForMainFrame()) {
                return;
            }
            WebViewActivity.this.isLoadError = true;
            WebViewActivity.this.initContentView(false);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!WebViewActivity.this.isLoadError) {
                WebViewActivity.this.initContentView(true);
            }
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.onResume();
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.onPause();
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.loadDataWithBaseURL(null, "", "text/html", EncryptUtil.CHARSET, null);
            this.mWebView.clearHistory();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null && html5WebView.canGoBack()) {
            this.mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
