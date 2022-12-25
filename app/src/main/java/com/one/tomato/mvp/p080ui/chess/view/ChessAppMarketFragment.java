package com.one.tomato.mvp.p080ui.chess.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
import com.one.tomato.R$id;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.JavaAndJsMethod;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: ChessAppMarketFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessAppMarketFragment */
/* loaded from: classes3.dex */
public final class ChessAppMarketFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    protected JavaAndJsMethod javaAndJsMethod;
    private final int FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5 = 2;
    private final ChessAppMarketFragment$webChromeClient$1 webChromeClient = new WebChromeClient() { // from class: com.one.tomato.mvp.ui.chess.view.ChessAppMarketFragment$webChromeClient$1
        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView view, int i) {
            Intrinsics.checkParameterIsNotNull(view, "view");
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
            Intrinsics.checkParameterIsNotNull(webView, "webView");
            Intrinsics.checkParameterIsNotNull(uploadMsg, "uploadMsg");
            Intrinsics.checkParameterIsNotNull(fileChooserParams, "fileChooserParams");
            ChessAppMarketFragment.this.openFileChooseImpleForAndroid(uploadMsg);
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView view, String title) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(title, "title");
            super.onReceivedTitle(view, title);
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            Intrinsics.checkParameterIsNotNull(origin, "origin");
            Intrinsics.checkParameterIsNotNull(callback, "callback");
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    };
    private final ChessAppMarketFragment$webViewClient$1 webViewClient = new WebViewClient() { // from class: com.one.tomato.mvp.ui.chess.view.ChessAppMarketFragment$webViewClient$1
        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String tag;
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            tag = ChessAppMarketFragment.this.getTAG();
            LogUtil.m3783i(tag, "重定向的url:" + url);
            return false;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap bitmap) {
            String tag;
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            super.onPageStarted(view, url, bitmap);
            tag = ChessAppMarketFragment.this.getTAG();
            LogUtil.m3783i(tag, "页面开始加载的url:" + url);
            ChessAppMarketFragment.this.showWaitingDialog();
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            String tag;
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(url, "url");
            super.onPageFinished(view, url);
            tag = ChessAppMarketFragment.this.getTAG();
            LogUtil.m3783i(tag, "页面加载结束的url:" + url);
            ChessAppMarketFragment.this.hideWaitingDialog();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int i, String description, String failingUrl) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(description, "description");
            Intrinsics.checkParameterIsNotNull(failingUrl, "failingUrl");
            super.onReceivedError(view, i, description, failingUrl);
            ChessAppMarketFragment.this.hideWaitingDialog();
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

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_chess_app_market;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        initWebView(webView);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.String] */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = PreferencesUtil.getInstance().getString("app_market");
        if (TextUtils.isEmpty((String) ref$ObjectRef.element)) {
            showWaitingDialog();
            ApiImplService.Companion.getApiImplService().postHomeMainNotify().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessAppMarketFragment$inintData$1
                /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.String] */
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(ArrayList<MainNotifyBean> arrayList) {
                    ChessAppMarketFragment.this.hideWaitingDialog();
                    if (arrayList == null || !(!arrayList.isEmpty())) {
                        return;
                    }
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    MainNotifyBean mainNotifyBean = arrayList.get(0);
                    Intrinsics.checkExpressionValueIsNotNull(mainNotifyBean, "t[0]");
                    ref$ObjectRef2.element = mainNotifyBean.getLinkUrl();
                    if (TextUtils.isEmpty((String) ref$ObjectRef.element)) {
                        return;
                    }
                    ((WebView) ChessAppMarketFragment.this._$_findCachedViewById(R$id.webView)).loadUrl((String) ref$ObjectRef.element);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    ChessAppMarketFragment.this.hideWaitingDialog();
                }
            });
            return;
        }
        ((WebView) _$_findCachedViewById(R$id.webView)).loadUrl((String) ref$ObjectRef.element);
    }

    protected final void initWebView(WebView webView) {
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
        Context mContext = getMContext();
        if (mContext != null) {
            this.javaAndJsMethod = new JavaAndJsMethod((ChessMainTabActivity) mContext, webView);
            JavaAndJsMethod javaAndJsMethod = this.javaAndJsMethod;
            if (javaAndJsMethod == null) {
                Intrinsics.throwUninitializedPropertyAccessException("javaAndJsMethod");
                throw null;
            }
            webView.addJavascriptInterface(javaAndJsMethod, "Android");
            webView.setWebChromeClient(this.webChromeClient);
            webView.setWebViewClient(this.webViewClient);
            if (Build.VERSION.SDK_INT >= 21) {
                webView.getSettings().setMixedContentMode(0);
            }
            if (Build.VERSION.SDK_INT >= 11) {
                webView.getSettings().setDisplayZoomControls(false);
            }
            webView.setDownloadListener(new DownloadListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessAppMarketFragment$initWebView$1
                @Override // android.webkit.DownloadListener
                public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    ChessAppMarketFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
            });
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void openFileChooseImpleForAndroid(ValueCallback<Uri[]> filePathCallback) {
        Intrinsics.checkParameterIsNotNull(filePathCallback, "filePathCallback");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        Intent intent2 = new Intent("android.intent.action.CHOOSER");
        intent2.putExtra("android.intent.extra.INTENT", intent);
        intent2.putExtra("android.intent.extra.TITLE", "Image Chooser");
        startActivityForResult(intent2, this.FILE_CHOOSER_RESULT_CODE_FOR_ANDROID_5);
    }
}
