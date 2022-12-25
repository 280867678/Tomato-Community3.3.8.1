package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.googlecode.mp4parser.boxes.apple.TrackLoadSettingsAtom;
import com.p076mh.webappStart.util.ReflectManger;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.WebViewNativeView */
/* loaded from: classes2.dex */
public class WebViewNativeView extends NativeViewPlugin.NativeView {
    private final Map<String, Object> map = new HashMap();

    public WebViewNativeView(Context context) {
        super(context);
        Logger.m4113i("start load webNativeView");
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_webview, (ViewGroup) this, true);
        Logger.m4112i("WebViewNativeView", "onInitialize: " + obj);
        WebView webView = (WebView) inflate.findViewById(R$id.webview);
        initWebView(webView);
        setUrl((Map) obj, webView);
    }

    private void setUrl(Map map, WebView webView) {
        String str = (String) map.get("src");
        if (str != null) {
            if (str.startsWith("http") || str.startsWith("https")) {
                webView.loadUrl(str);
                return;
            }
            String realPath = Utils.getRealPath(getWebViewFragment().getOriginalUrl(), str);
            boolean exists = new File(realPath).exists();
            Logger.m4112i("WebViewNativeView", "onInitialize: realPath:" + realPath + ",fileExsit:" + exists);
            webView.loadUrl(realPath);
        }
    }

    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() { // from class: com.gen.mh.webapp_extensions.views.WebViewNativeView.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
                Logger.m4112i("WebViewNativeView", "onPageFinished: " + str);
                HashMap hashMap = new HashMap();
                WebViewNativeView.this.map.put("type", TrackLoadSettingsAtom.TYPE);
                hashMap.put("src", str);
                WebViewNativeView.this.sendEvent(hashMap, null);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView2, webResourceRequest, webResourceError);
                Logger.m4112i("WebViewNativeView", "onReceivedError: " + webResourceError);
                HashMap hashMap = new HashMap();
                WebViewNativeView.this.map.put("type", "error");
                hashMap.put("src", webResourceRequest.getUrl());
                WebViewNativeView.this.sendEvent(hashMap, null);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JSInterface(), "messageHandlers");
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void sendEvent(Object obj, JSResponseListener jSResponseListener) {
        super.sendEvent(obj, jSResponseListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapp_extensions.views.WebViewNativeView$JSInterface */
    /* loaded from: classes2.dex */
    public final class JSInterface {
        private JSInterface(WebViewNativeView webViewNativeView) {
        }

        @JavascriptInterface
        public void jsCall(String str, String str2) {
            Logger.m4112i("methodName", str);
            try {
                ReflectManger.invokeMethod(JSInterface.class, this, str, new Class[]{String.class}, new Object[]{str2});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
    }
}
