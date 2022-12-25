package com.tomatolive.library.p136ui.view.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.p136ui.interfaces.WebViewJSCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.LogConstants;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

/* renamed from: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog */
/* loaded from: classes3.dex */
public class ComponentsWebViewDialog extends BaseGeneralDialog {
    private ComponentsEntity componentsEntity;
    private Disposable mDisposable;
    private String mUrl;
    private Html5WebView mWebView;
    private TextView tvLoading;
    private TextView tvLoadingFail;
    private WebViewJSCallback webViewJSCallback;
    private final String JsMsgHandlerName = "messageHandlers";
    private final String JsMsgAc = LogConstants.FOLLOW_OPERATION_TYPE;
    private final String JsMsgAcKey = "change_webview_frame";
    private final String JsMsgAcValue = "proportion";
    private String JsMsgAcKeyUrl = "open_url";
    private String JsMsgAcValueUrl = "url";
    private final String JsMsgAcKeyBalance = "live_money_changed";
    private final String JsMsgAcKeyRecharge = "live_open_recharge";
    private final int CONTENT_TYPE_LOADING = 1;
    private final int CONTENT_TYPE_CONTENT = 2;
    private final int CONTENT_TYPE_FAIL = 3;
    private boolean isSendRequest = false;
    private double heightProportion = 1.0d;
    private volatile boolean isLoadError = false;
    private boolean isOneMinuteValidEnable = false;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    public float getDimAmount() {
        return 0.0f;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    protected boolean isDynamicSetWindowHeight() {
        return true;
    }

    public ComponentsWebViewDialog(@NonNull Context context, WebViewJSCallback webViewJSCallback) {
        super(context);
        this.webViewJSCallback = webViewJSCallback;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    protected int getLayoutRes() {
        return R$layout.fq_dialog_bottom_webview_game;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    public void initView() {
        this.mWebView = (Html5WebView) findViewById(R$id.web_view);
        this.tvLoadingFail = (TextView) findViewById(R$id.tv_loading_fail);
        this.tvLoading = (TextView) findViewById(R$id.tv_loading_view);
        initWebView();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    public void initListener() {
        this.mWebView.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if ((action == 0 || action == 1) && !view.hasFocus()) {
                    view.requestFocus();
                    return false;
                }
                return false;
            }
        });
        this.tvLoadingFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ComponentsWebViewDialog componentsWebViewDialog = ComponentsWebViewDialog.this;
                componentsWebViewDialog.initSendRequest(componentsWebViewDialog.componentsEntity);
            }
        });
    }

    private void initWebView() {
        this.mWebView.setBackgroundColor(0);
        this.mWebView.setWebViewClient(new Html5WebViewClient());
        this.mWebView.addJavascriptInterface(this, "messageHandlers");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContentView(int i) {
        int i2 = 0;
        this.tvLoading.setVisibility(i == 1 ? 0 : 4);
        this.tvLoadingFail.setVisibility(i == 3 ? 0 : 4);
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView != null) {
            if (i != 2) {
                i2 = 4;
            }
            html5WebView.setVisibility(i2);
        }
    }

    public void showDialog(boolean z, ComponentsEntity componentsEntity) {
        this.isSendRequest = z;
        ComponentsEntity componentsEntity2 = this.componentsEntity;
        if (componentsEntity2 == null) {
            initSendRequest(componentsEntity);
        } else if (!TextUtils.equals(componentsEntity2.f5837id, componentsEntity.f5837id)) {
            initSendRequest(componentsEntity);
        } else {
            this.componentsEntity = componentsEntity;
            ComponentsEntity componentsEntity3 = this.componentsEntity;
            this.heightProportion = componentsEntity3 != null ? componentsEntity3.getHeightProportion() : 1.0d;
            ComponentsEntity componentsEntity4 = this.componentsEntity;
            this.mUrl = componentsEntity4 != null ? componentsEntity4.targetUrl : "";
            if (!this.isOneMinuteValidEnable) {
                initSendRequest(componentsEntity);
            }
        }
        setWindowHeightByProportion(this.heightProportion);
        show();
    }

    public void onRelease() {
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.isOneMinuteValidEnable = false;
        this.mDisposable.dispose();
    }

    public void setWebViewJSCallback(WebViewJSCallback webViewJSCallback) {
        this.webViewJSCallback = webViewJSCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSendRequest(ComponentsEntity componentsEntity) {
        onRelease();
        this.componentsEntity = componentsEntity;
        ComponentsEntity componentsEntity2 = this.componentsEntity;
        this.heightProportion = componentsEntity2 != null ? componentsEntity2.getHeightProportion() : 1.0d;
        ComponentsEntity componentsEntity3 = this.componentsEntity;
        this.mUrl = componentsEntity3 != null ? componentsEntity3.targetUrl : "";
        if (!this.isOneMinuteValidEnable) {
            sendRequest();
            showCountDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog$Html5WebViewClient */
    /* loaded from: classes3.dex */
    public class Html5WebViewClient extends Html5WebView.BaseWebViewClient {
        private Html5WebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            ComponentsWebViewDialog.this.isLoadError = false;
            if (ComponentsWebViewDialog.this.isSendRequest) {
                ComponentsWebViewDialog.this.initContentView(1);
            }
        }

        @Override // com.tomatolive.library.p136ui.view.widget.Html5WebView.BaseWebViewClient, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("http") || str.startsWith("https")) {
                if (Build.VERSION.SDK_INT >= 26) {
                    return false;
                }
                webView.loadUrl(str);
                return true;
            }
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            if (Build.VERSION.SDK_INT >= 23) {
                return;
            }
            ComponentsWebViewDialog.this.isLoadError = true;
            ComponentsWebViewDialog.this.initContentView(3);
        }

        @Override // android.webkit.WebViewClient
        @TargetApi(23)
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (webResourceRequest.isForMainFrame()) {
                ComponentsWebViewDialog.this.isLoadError = true;
                ComponentsWebViewDialog.this.initContentView(3);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!ComponentsWebViewDialog.this.isLoadError) {
                ComponentsWebViewDialog.this.initContentView(2);
            }
        }
    }

    @JavascriptInterface
    public double receiveMessageFromJS(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(LogConstants.FOLLOW_OPERATION_TYPE);
            if (string.equals("change_webview_frame")) {
                double d = jSONObject.getDouble("proportion");
                double d2 = this.mWidthPixels / this.mHeightPixels;
                if (d < d2) {
                    d = d2;
                }
                this.mainHandler.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ComponentsWebViewDialog componentsWebViewDialog = ComponentsWebViewDialog.this;
                        componentsWebViewDialog.setWindowHeightByProportion(componentsWebViewDialog.heightProportion);
                    }
                });
                return d;
            } else if (TextUtils.equals(string, this.JsMsgAcKeyUrl)) {
                final String string2 = jSONObject.getString(this.JsMsgAcValueUrl);
                this.mainHandler.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.4
                    @Override // java.lang.Runnable
                    public void run() {
                        ((BaseGeneralDialog) ComponentsWebViewDialog.this).mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string2)));
                    }
                });
                return 0.0d;
            } else if (TextUtils.equals(string, "live_money_changed")) {
                this.mainHandler.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.5
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ComponentsWebViewDialog.this.webViewJSCallback != null) {
                            ComponentsWebViewDialog.this.webViewJSCallback.onLiveBalanceUpdate();
                        }
                    }
                });
                return 0.0d;
            } else if (!TextUtils.equals(string, "live_open_recharge")) {
                return 0.0d;
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.6
                    @Override // java.lang.Runnable
                    public void run() {
                        ComponentsWebViewDialog.this.dismiss();
                        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
                            TomatoLiveSDK.getSingleton().sdkCallbackListener.onLiveGameJSListener(((BaseGeneralDialog) ComponentsWebViewDialog.this).mContext, "live_open_recharge");
                        }
                    }
                });
                return 0.0d;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    private void sendRequest() {
        if (this.isSendRequest) {
            sendGameRequest();
            return;
        }
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView == null) {
            return;
        }
        html5WebView.loadUrl(this.mUrl);
    }

    private void sendGameRequest() {
        initContentView(1);
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onAppCommonCallbackListener(this.mContext, 273, new TomatoLiveSDK.OnCommonCallbackListener() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.7
                @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
                public void onDataSuccess(Context context, Object obj) {
                    if (obj instanceof String) {
                        ComponentsWebViewDialog.this.sendDataRequest((String) obj);
                    }
                }

                @Override // com.tomatolive.library.TomatoLiveSDK.OnCommonCallbackListener
                public void onDataFail(Throwable th, int i) {
                    ComponentsWebViewDialog.this.sendDataRequest(null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDataRequest(String str) {
        if (TextUtils.isEmpty(str)) {
            initContentView(3);
            return;
        }
        ComponentsEntity componentsEntity = this.componentsEntity;
        this.mUrl = AppUtils.getComponentsGameWebUrl(componentsEntity.targetUrl, componentsEntity.gameId, str);
        Html5WebView html5WebView = this.mWebView;
        if (html5WebView == null) {
            return;
        }
        html5WebView.loadUrl(this.mUrl);
    }

    private void showCountDown() {
        this.mDisposable = Flowable.intervalRange(0L, 61L, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.10
            @Override // io.reactivex.functions.Consumer
            public void accept(Subscription subscription) throws Exception {
                ComponentsWebViewDialog.this.isOneMinuteValidEnable = true;
            }
        }).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.dialog.ComponentsWebViewDialog.8
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ComponentsWebViewDialog.this.isOneMinuteValidEnable = false;
            }
        }).subscribe();
    }
}
