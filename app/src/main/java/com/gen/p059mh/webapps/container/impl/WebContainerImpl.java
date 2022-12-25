package com.gen.p059mh.webapps.container.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.container.BaseContainerFragment;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.listener.JavascriptObject;
import com.gen.p059mh.webapps.listener.PageInjectListener;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.pugins.HrefPlugin;
import com.gen.p059mh.webapps.pugins.RequestPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;
import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/* renamed from: com.gen.mh.webapps.container.impl.WebContainerImpl */
/* loaded from: classes2.dex */
public class WebContainerImpl extends BaseContainerFragment implements PageLoadFinishListener {
    String url;
    WebEngine webView;
    private Hashtable<String, String> cacheData = new Hashtable<>();
    List<Runnable> runnableList = new ArrayList();
    boolean isLoaded = false;
    boolean isFirst = true;

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        initWebView();
        return this.webView.provideView();
    }

    private void initWebView() {
        this.webView = WebEngineManager.getInstance().initWebEngine().init(getContext());
        this.webView.setPadding(0, 0, 0, 0);
        this.webView.setUserAgent(this.webView.getUserAgentString() + " WebApp/Android/3.1.1");
        this.webView.addJavascriptInterface(new JavascriptObject(this.cacheData, this.plugins, this.webView, this.webFragmentController), "_api");
        this.webView.setWebViewCallback(this.webFragmentController.getBizOperation());
        this.webView.setPageLoadFinishCallBack(this);
        this.webView.setNeedInject(this.webFragmentController.isUseApi());
        if (this.webFragmentController.isOnline()) {
            this.webView.setPageInjectListener(new PageInjectListener() { // from class: com.gen.mh.webapps.container.impl.WebContainerImpl.1
                @Override // com.gen.p059mh.webapps.listener.PageInjectListener
                public String provideDefaultPath() {
                    return ((BaseContainerFragment) WebContainerImpl.this).webFragmentController.getDefaultsPath();
                }

                @Override // com.gen.p059mh.webapps.listener.PageInjectListener
                public ResourcesLoader.ResourceType provideResourceType() {
                    return ((BaseContainerFragment) WebContainerImpl.this).webFragmentController.getResourceType();
                }

                @Override // com.gen.p059mh.webapps.listener.PageInjectListener
                public boolean aem() {
                    return ((BaseContainerFragment) WebContainerImpl.this).webFragmentController.isAem();
                }
            });
        }
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.webView.setBackgroundColor(0);
        MimeTypes.getInstance().register(new MimeType("text/css", "wxss"));
        MimeTypes.getInstance().register(new MimeType("text/xml", "wxml"));
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.url == null || this.isLoaded) {
            return;
        }
        Logger.m4115e("onViewCreated load");
        this.webView.loadUrl(this.url);
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void start(String str, String str2) {
        this.url = str;
        if (this.webView == null || this.isLoaded) {
            return;
        }
        this.isLoaded = true;
        Logger.m4115e("onStart load");
        this.webView.loadUrl(str);
    }

    @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageFinish(String str) {
        this.webFragmentController.onLoadPageFinish(str);
    }

    @Override // com.gen.p059mh.webapps.listener.PageLoadFinishListener
    public void onLoadPageError(RuntimeException runtimeException) {
        if (this.isFirst) {
            this.webFragmentController.onLoadPageError(runtimeException);
            this.isFirst = false;
            return;
        }
        Logger.m4114e("WebView", runtimeException.getMessage());
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public WebEngine getWebEngine() {
        return this.webView;
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str) {
        super.initWorkPlugin(hashtable, executor, str);
        registerWorkerPlugin(new RequestPlugin(), hashtable, executor);
        registerWorkerPlugin(new HrefPlugin(), hashtable, executor);
        registerWorkerPlugin(getWorkerNativeViewPlugin(), hashtable, executor);
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void close() {
        super.close();
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.destroy();
        }
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.Plugin.Executor
    public void executeEvent(String str, Object obj, JSResponseListener jSResponseListener) {
        String encodeToString;
        if (this.webView == null) {
            Log.e(WebContainerImpl.class.getSimpleName(), "WebView not init!");
            return;
        }
        String str2 = "" + new Date().getTime() + Math.random();
        if (jSResponseListener != null) {
            this.responsePlugin.getResponseListeners().put(str2, jSResponseListener);
        }
        if (obj == null) {
            encodeToString = Base64.encodeToString("null".getBytes(), 0);
        } else {
            encodeToString = Base64.encodeToString(new Gson().toJson(obj).getBytes(), 0);
        }
        WebViewRunnable webViewRunnable = new WebViewRunnable(str2, encodeToString, str);
        this.runnableList.add(webViewRunnable);
        this.webFragmentController.getHandler().post(webViewRunnable);
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public List<Runnable> getRunnable() {
        return this.runnableList;
    }

    @Override // com.gen.p059mh.webapps.container.BaseContainerFragment, com.gen.p059mh.webapps.container.Container
    public void releaseRunnableList() {
        super.releaseRunnableList();
        List<Runnable> list = this.runnableList;
        if (list != null) {
            list.clear();
        }
        this.runnableList = null;
    }

    /* renamed from: com.gen.mh.webapps.container.impl.WebContainerImpl$WebViewRunnable */
    /* loaded from: classes2.dex */
    public class WebViewRunnable implements Runnable {
        private String base64;

        /* renamed from: id */
        private String f1300id;
        private String name;

        public WebViewRunnable(String str, String str2, String str3) {
            this.f1300id = str;
            this.base64 = str2;
            this.name = str3;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                WebContainerImpl.this.cacheData.put(this.f1300id, this.base64);
                if (WebContainerImpl.this.webView != null) {
                    WebEngine webEngine = WebContainerImpl.this.webView;
                    webEngine.executeJs("javascript:window.native.notify2('" + this.name + "', '" + this.f1300id + "')");
                }
            } catch (Exception e) {
                WebEngine webEngine2 = WebContainerImpl.this.webView;
                if (webEngine2 != null) {
                    Logger.m4112i("dirty", Boolean.valueOf(webEngine2.isDirty()));
                }
                e.printStackTrace();
            }
            List<Runnable> list = WebContainerImpl.this.runnableList;
            if (list != null) {
                list.remove(this);
            }
        }
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void release() {
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.destroy();
            this.webView = null;
        }
    }
}
