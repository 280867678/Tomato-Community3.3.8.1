package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.graphics.Color;
import android.webkit.JavascriptInterface;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.FloatWebView */
/* loaded from: classes2.dex */
public class FloatWebView extends NativeViewPlugin.NativeView {
    NativeViewPlugin.NativeView.MethodHandler postMessage = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.FloatWebView.1
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(final List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            WebEngine webEngine = FloatWebView.this.webView;
            if (webEngine != null) {
                webEngine.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.FloatWebView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        String json = new Gson().toJson(list.get(0));
                        String originalUrl = FloatWebView.this.webView.getOriginalUrl();
                        WebEngine webEngine2 = FloatWebView.this.webView;
                        webEngine2.loadUrl("javascript:window.postMessage(" + json + ",'" + originalUrl + "')");
                    }
                });
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                methodCallback.run(hashMap);
                return;
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", false);
            methodCallback.run(hashMap2);
        }
    };
    WebEngine webView;

    public FloatWebView(Context context) {
        super(context);
        registerMethod("postMessage", this.postMessage);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        this.webView = WebEngineManager.getInstance().initWebEngine().init(getContext());
        this.webView.addJavascriptInterface(new JavascriptObject(), "opener");
        this.webView.setBackgroundColor(Color.parseColor("#00000000"));
        addView(this.webView.provideView());
        setFixed(true);
        if (obj != null) {
            Map map = (Map) obj;
            if (map.get("src") != null) {
                this.webView.loadUrl(String.valueOf(map.get("src")));
            }
            if (map.get("scroll") == null) {
                return;
            }
            this.webView.setScroll(Boolean.valueOf(map.get("scroll").toString()).booleanValue());
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.FloatWebView$JavascriptObject */
    /* loaded from: classes2.dex */
    class JavascriptObject {
        JavascriptObject() {
        }

        @JavascriptInterface
        public void postMessage(final String str) {
            FloatWebView.this.webView.provideView().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.FloatWebView.JavascriptObject.1
                @Override // java.lang.Runnable
                public void run() {
                    Logger.m4113i(str);
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "message");
                    hashMap.put("value", str);
                    FloatWebView.this.sendEvent(hashMap, null);
                }
            });
        }
    }
}
