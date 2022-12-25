package com.gen.p059mh.webapp_extensions.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapp_extensions.views.player.PlayerLoadingView;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.listener.WebViewClientLoadListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.views.DefaultWebView;
import com.google.gson.Gson;

/* renamed from: com.gen.mh.webapp_extensions.views.dialog.ModalDialog */
/* loaded from: classes2.dex */
public class ModalDialog extends Dialog implements WebViewClientLoadListener {
    boolean isShow = false;
    boolean loadingViewClose = false;
    Object messageData;
    ModalCallBack modalCallBack;
    PlayerLoadingView playerLoadingView;
    String url;
    DefaultWebView webView;

    /* renamed from: com.gen.mh.webapp_extensions.views.dialog.ModalDialog$ModalCallBack */
    /* loaded from: classes2.dex */
    public interface ModalCallBack {
        void onEvent(String str, Object obj);
    }

    @Override // com.gen.p059mh.webapps.listener.WebViewClientLoadListener
    public void receiveTitle(String str) {
    }

    public ModalDialog(@NonNull Context context, ModalCallBack modalCallBack) {
        super(context, R$style.clear_dialog_theme);
        this.modalCallBack = modalCallBack;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.dialog_web_sdk_modal_view);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 1.0f;
        attributes.dimAmount = 0.0f;
        attributes.width = -1;
        attributes.height = -1;
        attributes.gravity = 17;
        window.setAttributes(attributes);
        initWebView();
    }

    private void initWebView() {
        this.webView = (DefaultWebView) findViewById(R$id.web_view);
        this.webView.setNeedInject(false);
        this.playerLoadingView = (PlayerLoadingView) findViewById(R$id.loading);
        this.webView.setWebViewClientLoadListener(this);
        this.webView.setBackgroundColor(Color.parseColor("#00000000"));
        this.webView.addJavascriptInterface(new JavascriptObject(), "opener");
        this.webView.addJavascriptInterface(new WindowObject(), "mh");
        String str = this.url;
        if (str != null) {
            this.webView.loadUrl(str);
        }
        this.webView.setPageLoadFinishListener(new PageLoadFinishListener() { // from class: com.gen.mh.webapp_extensions.views.dialog.ModalDialog.1
            @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
            public void onLoadPageError(RuntimeException runtimeException) {
            }

            @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
            public void onLoadPageFinish(String str2) {
                ModalDialog.this.webView.setVisibility(0);
            }
        });
    }

    public void loadUrl(String str) {
        this.url = str;
        DefaultWebView defaultWebView = this.webView;
        if (defaultWebView != null) {
            defaultWebView.loadUrl(str);
        }
    }

    @Override // com.gen.p059mh.webapps.listener.WebViewClientLoadListener
    public void show(boolean z) {
        PlayerLoadingView playerLoadingView = this.playerLoadingView;
        if (playerLoadingView != null) {
            playerLoadingView.setVisibility(z ? 0 : 8);
        }
        if (z || this.loadingViewClose) {
            return;
        }
        this.loadingViewClose = true;
        doPostMessage();
    }

    public void doPostMessage() {
        DefaultWebView defaultWebView = this.webView;
        if (defaultWebView == null || this.messageData == null || this.isShow) {
            return;
        }
        this.isShow = true;
        String originalUrl = defaultWebView.getOriginalUrl();
        Logger.m4114e("xxx doPostMessage", this.messageData.toString());
        DefaultWebView defaultWebView2 = this.webView;
        defaultWebView2.loadUrl("javascript:window.postMessage(" + this.messageData + ",'" + originalUrl + "')");
    }

    public void postData(Object obj) {
        this.messageData = new Gson().toJson(obj);
        if (this.loadingViewClose) {
            doPostMessage();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("xxx postData messageDat =");
        Object obj2 = this.messageData;
        sb.append(obj2 != null ? obj2.toString() : "null");
        Logger.m4115e(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.dialog.ModalDialog$JavascriptObject */
    /* loaded from: classes2.dex */
    public class JavascriptObject {
        JavascriptObject() {
        }

        @JavascriptInterface
        public void postMessage(final String str) {
            ModalDialog.this.webView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.dialog.ModalDialog.JavascriptObject.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ModalDialog.this.modalCallBack != null) {
                        Logger.m4114e("xxx", str);
                        ModalDialog.this.modalCallBack.onEvent("message", str);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.views.dialog.ModalDialog$WindowObject */
    /* loaded from: classes2.dex */
    public class WindowObject {
        WindowObject() {
        }

        @JavascriptInterface
        public void close() {
            ModalDialog.this.webView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.dialog.ModalDialog.WindowObject.1
                @Override // java.lang.Runnable
                public void run() {
                    Logger.m4115e(MainFragment.CLOSE_EVENT);
                    ModalDialog.this.dismiss();
                }
            });
        }
    }
}
