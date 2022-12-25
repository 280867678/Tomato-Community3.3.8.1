package com.gen.p059mh.webapps.listener;

import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.google.gson.Gson;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.gen.mh.webapps.listener.JavascriptObject */
/* loaded from: classes2.dex */
public class JavascriptObject {
    private Hashtable<String, String> cacheData;
    protected Hashtable<String, Plugin> plugins;
    IWebFragmentController webFragmentController;
    WebEngine webView;

    public JavascriptObject(Hashtable<String, String> hashtable, Hashtable<String, Plugin> hashtable2, WebEngine webEngine, IWebFragmentController iWebFragmentController) {
        this.cacheData = hashtable;
        this.plugins = hashtable2;
        this.webView = webEngine;
        this.webFragmentController = iWebFragmentController;
    }

    @JavascriptInterface
    public void _call(String str, String str2, final String str3) {
        if (this.plugins.containsKey(str)) {
            try {
                this.plugins.get(str).process(str2, new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.listener.JavascriptObject.1
                    @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                    public void response(Object obj) {
                        final String json = new Gson().toJson(obj);
                        JavascriptObject.this.webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.listener.JavascriptObject.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                JavascriptObject.this.cacheData.put(str3, Base64.encodeToString(json.getBytes(), 0));
                                WebEngine webEngine = JavascriptObject.this.webView;
                                if (webEngine != null) {
                                    webEngine.executeJs("javascript:window.api._callback('" + str3 + "')");
                                }
                            }
                        });
                    }
                });
                return;
            } catch (Exception e) {
                Log.i("ProcessError", "Error when process " + str);
                e.printStackTrace();
                this.webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.listener.JavascriptObject.2
                    @Override // java.lang.Runnable
                    public void run() {
                        JavascriptObject.this.cacheData.put(str3, Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0));
                        WebEngine webEngine = JavascriptObject.this.webView;
                        if (webEngine != null) {
                            webEngine.executeJs("javascript:window.api._callback('" + str3 + "')");
                        }
                    }
                });
                return;
            }
        }
        this.webFragmentController.getHandler().post(new Runnable() { // from class: com.gen.mh.webapps.listener.JavascriptObject.3
            @Override // java.lang.Runnable
            public void run() {
                if (JavascriptObject.this.webView == null) {
                    return;
                }
                JavascriptObject.this.cacheData.put(str3, Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0));
                WebEngine webEngine = JavascriptObject.this.webView;
                webEngine.executeJs("javascript:window.api._callback('" + str3 + "')");
            }
        });
    }

    @JavascriptInterface
    public String _callSync(String str, String str2) {
        if (this.plugins.containsKey(str)) {
            Plugin plugin = this.plugins.get(str);
            try {
                final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                final StringBuilder sb = new StringBuilder();
                plugin.process(str2, new Plugin.PluginCallback() { // from class: com.gen.mh.webapps.listener.JavascriptObject.4
                    boolean called = false;

                    @Override // com.gen.p059mh.webapps.Plugin.PluginCallback
                    public void response(Object obj) {
                        this.called = true;
                        sb.append(Base64.encodeToString(new Gson().toJson(obj).getBytes(), 0));
                        synchronized (atomicBoolean) {
                            atomicBoolean.set(true);
                            atomicBoolean.notify();
                        }
                    }

                    protected void finalize() throws Throwable {
                        if (!this.called) {
                            response(null);
                        }
                        super.finalize();
                    }
                });
                synchronized (atomicBoolean) {
                    if (atomicBoolean.get()) {
                        return sb.toString();
                    }
                    atomicBoolean.wait();
                    return sb.toString();
                }
            } catch (Exception e) {
                Log.i("ProcessError", "Error when process " + str);
                e.printStackTrace();
                return Base64.encodeToString("{\"Error\": \"Execute failed !\"}".getBytes(), 0);
            }
        }
        return Base64.encodeToString("{\"Error\": \"Plugin not found!\"}".getBytes(), 0);
    }

    @JavascriptInterface
    public String _getData(String str) {
        String str2 = this.cacheData.get(str);
        this.cacheData.remove(str);
        return str2;
    }
}
