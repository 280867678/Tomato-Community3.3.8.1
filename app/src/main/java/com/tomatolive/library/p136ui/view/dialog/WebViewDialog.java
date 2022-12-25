package com.tomatolive.library.p136ui.view.dialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;

/* renamed from: com.tomatolive.library.ui.view.dialog.WebViewDialog */
/* loaded from: classes3.dex */
public class WebViewDialog extends BaseBottomDialogFragment {
    private FrameLayout flContentView;
    private FrameLayout flRetryView;
    private volatile boolean isLoadError = false;
    private String mUrl;
    private Html5WebView mWebView;
    private ProgressBar progressBar;
    private String title;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static WebViewDialog newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        WebViewDialog webViewDialog = new WebViewDialog();
        bundle.putString("title", str);
        bundle.putString("url", str2);
        webViewDialog.setArguments(bundle);
        return webViewDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.title = bundle.getString("title");
        this.mUrl = bundle.getString("url");
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_bottom_webview;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        this.progressBar = (ProgressBar) view.findViewById(R$id.f5823pb);
        this.flContentView = (FrameLayout) view.findViewById(R$id.fl_content_view);
        this.flRetryView = (FrameLayout) view.findViewById(R$id.fl_retry_view);
        initWebView();
        textView.setText(TextUtils.isEmpty(this.title) ? "" : this.title);
        view.findViewById(R$id.tv_btn_reload).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$WebViewDialog$E1bdBSSHq6BQnqhuX3uHHmpDK-8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                WebViewDialog.this.lambda$initView$0$WebViewDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$WebViewDialog(View view) {
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.loadUrl(this.mUrl);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return this.maxHeightScale;
    }

    private void initWebView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mWebView = new Html5WebView(this.mContext.getApplicationContext());
        this.mWebView.setLayoutParams(layoutParams);
        this.flContentView.addView(this.mWebView);
        this.mWebView.setWebChromeClient(new Html5WebChromeClient());
        this.mWebView.setWebViewClient(new Html5WebViewClient());
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$WebViewDialog$JnlCkCnLVQV6TkaKjIt1zQgXRqQ
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                WebViewDialog.this.lambda$initWebView$1$WebViewDialog(str, str2, str3, str4, j);
            }
        });
        this.mWebView.setOnKeyListener(new View.OnKeyListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$WebViewDialog$At-nvELL5mqxxa6LxTazgrQepwY
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return WebViewDialog.this.lambda$initWebView$2$WebViewDialog(view, i, keyEvent);
            }
        });
    }

    public /* synthetic */ void lambda$initWebView$1$WebViewDialog(String str, String str2, String str3, String str4, long j) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ boolean lambda$initWebView$2$WebViewDialog(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContentView(boolean z) {
        this.progressBar.setVisibility(8);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.WebViewDialog$Html5WebChromeClient */
    /* loaded from: classes3.dex */
    public class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {
        private Html5WebChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            WebViewDialog.this.progressBar.setProgress(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.WebViewDialog$Html5WebViewClient */
    /* loaded from: classes3.dex */
    public class Html5WebViewClient extends Html5WebView.BaseWebViewClient {
        Html5WebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            WebViewDialog.this.isLoadError = false;
            WebViewDialog.this.progressBar.setVisibility(0);
        }

        @Override // com.tomatolive.library.p136ui.view.widget.Html5WebView.BaseWebViewClient, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("pt://")) {
                try {
                    WebViewDialog.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                } catch (Exception unused) {
                    webView.loadUrl("https://www.potato.im/p/androids");
                }
                return false;
            } else if (!str.startsWith("http") || !str.startsWith("https")) {
                return true;
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    return false;
                }
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
            WebViewDialog.this.isLoadError = true;
            WebViewDialog.this.initContentView(false);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (Build.VERSION.SDK_INT < 21 || !webResourceRequest.isForMainFrame()) {
                return;
            }
            WebViewDialog.this.isLoadError = true;
            WebViewDialog.this.initContentView(false);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!WebViewDialog.this.isLoadError) {
                WebViewDialog.this.initContentView(true);
            }
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.onResume();
            this.mWebView.loadUrl(this.mUrl);
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            html5WebView.onPause();
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
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
}
