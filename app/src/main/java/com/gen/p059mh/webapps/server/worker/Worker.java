package com.gen.p059mh.webapps.server.worker;

import android.support.p002v4.app.NotificationCompat;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.JavaVoidCallback;
import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8Value;
import com.eclipsesource.p056v8.utils.V8ObjectUtils;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.server.HandlerExecutor;
import com.gen.p059mh.webapps.server.runtime.V8BaseRuntime;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.server.worker.Worker */
/* loaded from: classes2.dex */
public class Worker extends V8BaseRuntime implements Plugin.Executor {

    /* renamed from: com.gen.mh.webapps.server.worker.Worker$MessageCallBack */
    /* loaded from: classes2.dex */
    public interface MessageCallBack {
        void onMessage(Map map);
    }

    public void afterExecute(File file) {
    }

    public void onReady(C1257V8 c1257v8) {
    }

    public Worker(IWebFragmentController iWebFragmentController) {
        super(iWebFragmentController);
    }

    public void beforeExecute(File file) {
        String str = "http://" + ResourcesLoader.WORK_HOST + file.getAbsolutePath().replace(this.webViewFragment.getWorkPath(), "");
        Logger.m4112i("beforeExecute", str);
        this.runtime.executeScript("var scriptSrc ='" + str + "'");
        initDefault();
    }

    @Override // com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void onInit() {
        super.onInit();
        this.runtime.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.Worker.1
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object, V8Array v8Array) {
                if (v8Array.length() > 0) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("type", "message");
                    V8Object object = v8Array.getObject(0);
                    hashMap.put(AopConstants.APP_PROPERTIES_KEY, V8ObjectUtils.toMap(object));
                    object.close();
                    ((V8BaseRuntime) Worker.this).webViewFragment.executeEvent(((V8BaseRuntime) Worker.this).handleKey, hashMap, null);
                }
            }
        }, "postMessage");
        this.runtime.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.Worker.2
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object, V8Array v8Array) {
                if (v8Array.length() == 3) {
                    Logger.m4113i("loadScript " + v8Array.getString(0));
                    String string = v8Array.getString(0);
                    HandlerExecutor handlerExecutor = ((V8BaseRuntime) Worker.this).handler;
                    V8Object executeObjectScript = handlerExecutor.executeObjectScript("(function(module, exports, require){\n  \n" + Worker.this.loadDataFromUrl(string) + "\n}) ", string, 0);
                    V8Array v8Array2 = new V8Array(((V8BaseRuntime) Worker.this).runtime);
                    V8Object object = v8Array.getObject(1);
                    V8Object object2 = v8Array.getObject(2);
                    V8Object object3 = object.getObject("exports");
                    v8Array2.push((V8Value) ((V8BaseRuntime) Worker.this).runtime).push((V8Value) object).push((V8Value) object3).push((V8Value) object2);
                    executeObjectScript.executeVoidFunction(NotificationCompat.CATEGORY_CALL, v8Array2);
                    object3.close();
                    object2.close();
                    object.close();
                    v8Array2.close();
                    executeObjectScript.close();
                    v8Array.close();
                }
            }
        }, "loadScript");
        this.runtime.registerJavaMethod(new JavaVoidCallback() { // from class: com.gen.mh.webapps.server.worker.Worker.3
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object, V8Array v8Array) {
                String str;
                if (v8Array.length() == 3) {
                    Logger.m4113i("loadScript2 " + v8Array.getString(0) + ".js");
                    HandlerExecutor handlerExecutor = ((V8BaseRuntime) Worker.this).handler;
                    V8Object executeObjectScript = handlerExecutor.executeObjectScript("(function(data, require){\n  \n" + Worker.this.loadDataFromPath(str) + "\n})", ((V8BaseRuntime) Worker.this).webViewFragment.getWorkPath() + "/" + v8Array.getString(0) + ".js", 0);
                    V8Array v8Array2 = new V8Array(((V8BaseRuntime) Worker.this).runtime);
                    Object obj = v8Array.get(2);
                    V8Object object = v8Array.getObject(1);
                    v8Array2.push((V8Value) ((V8BaseRuntime) Worker.this).runtime).push(obj).push((V8Value) object);
                    executeObjectScript.executeVoidFunction(NotificationCompat.CATEGORY_CALL, v8Array2);
                    object.close();
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).close();
                    }
                    v8Array2.close();
                    executeObjectScript.close();
                }
            }
        }, "loadScript2");
    }

    public void postMessage(String str, final Map<String, Object> map) {
        this.handleKey = str;
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.Worker.4
            @Override // java.lang.Runnable
            public void run() {
                V8Object v8Object = V8ObjectUtils.toV8Object(((V8BaseRuntime) Worker.this).runtime, map);
                ((V8BaseRuntime) Worker.this).runtime.add("_messageData", v8Object);
                ((V8BaseRuntime) Worker.this).runtime.executeScript("superPostMessage()");
                v8Object.close();
            }
        });
    }

    public void initDefault() {
        for (File file : createWorkerBaseFile()) {
            executeFromPath(file.getAbsolutePath());
        }
    }
}
