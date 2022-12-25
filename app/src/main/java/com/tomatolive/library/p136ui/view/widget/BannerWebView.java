package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.tomatolive.library.http.utils.EncryptUtil;

/* renamed from: com.tomatolive.library.ui.view.widget.BannerWebView */
/* loaded from: classes4.dex */
public class BannerWebView extends FrameLayout {
    private Html5WebView webView;

    public BannerWebView(@NonNull Context context) {
        this(context, null);
    }

    public BannerWebView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BannerWebView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        this.webView = new Html5WebView(getContext().getApplicationContext());
        this.webView.setBackgroundColor(0);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.getSettings().setSupportZoom(false);
        this.webView.getSettings().setDisplayZoomControls(false);
        this.webView.getSettings().setTextZoom(100);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setTouchEnable(false);
        if (Build.VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
        }
        addView(this.webView, new FrameLayout.LayoutParams(-1, -1));
    }

    public boolean isLoadBoolean() {
        Html5WebView html5WebView = this.webView;
        return html5WebView != null && html5WebView.isLoadBoolean();
    }

    public void loadUrl(String str) {
        Html5WebView html5WebView = this.webView;
        if (html5WebView != null) {
            html5WebView.loadUrl(str);
        }
    }

    public void onDestroyWebView() {
        Html5WebView html5WebView = this.webView;
        if (html5WebView != null) {
            html5WebView.loadDataWithBaseURL(null, "", "text/html", EncryptUtil.CHARSET, null);
            this.webView.clearHistory();
            removeView(this.webView);
            this.webView.destroy();
            this.webView = null;
        }
    }
}
