package com.gen.p059mh.webapps.unity;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.unity.Unity */
/* loaded from: classes2.dex */
public abstract class Unity {
    Plugin.Executor executor;
    protected String mId;
    Map<String, Method> methodHandlers = new HashMap();
    IWebFragmentController webViewFragment;

    /* renamed from: com.gen.mh.webapps.unity.Unity$Method */
    /* loaded from: classes2.dex */
    public interface Method<T> {
        void call(MethodCallback<T> methodCallback, Object... objArr);
    }

    /* renamed from: com.gen.mh.webapps.unity.Unity$MethodCallback */
    /* loaded from: classes2.dex */
    public interface MethodCallback<T> {
        void run(T t);
    }

    @Deprecated
    public void onDestroy() {
    }

    public void onInitialize(Object obj) {
    }

    public void setExecutor(Plugin.Executor executor) {
        this.executor = executor;
    }

    public Plugin.Executor getExecutor() {
        return this.executor;
    }

    public Unity() {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        int i = UnityPlugin.f1303id;
        UnityPlugin.f1303id = i + 1;
        sb.append(i);
        setId(sb.toString());
    }

    public void setId(String str) {
        this.mId = str;
    }

    public String getId() {
        return this.mId;
    }

    public Object getMethods() {
        return this.methodHandlers.keySet().toArray();
    }

    public void registerMethod(String str, Method method) {
        if (method != null) {
            this.methodHandlers.put(str, method);
        }
    }

    public IWebFragmentController getWebViewFragment() {
        return this.webViewFragment;
    }

    public void setWebViewFragment(IWebFragmentController iWebFragmentController) {
        if (iWebFragmentController != null) {
            this.webViewFragment = iWebFragmentController;
        }
    }

    public void onReady() {
        Logger.m4112i("onReady", "onReady");
    }

    public void onShow() {
        Logger.m4112i("Unity", "onShow");
    }

    public void onHide() {
        Logger.m4112i("Unity", "onHide");
    }

    public void unload() {
        Logger.m4112i("Unity", "unload");
    }

    public void invokeAction(String str, final MethodCallback methodCallback, Object... objArr) {
        if (this.methodHandlers.containsKey(str)) {
            this.methodHandlers.get(str).call(new MethodCallback(this) { // from class: com.gen.mh.webapps.unity.Unity.1
                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                public void run(Object obj) {
                    MethodCallback methodCallback2 = methodCallback;
                    if (methodCallback2 != null) {
                        methodCallback2.run(obj);
                    }
                }
            }, objArr);
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "no method find");
        if (methodCallback == null) {
            return;
        }
        methodCallback.run(hashMap);
    }

    public void event(String str, final MethodCallback methodCallback, Object... objArr) {
        if (this.mId == null || this.webViewFragment == null) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("type", str);
        hashMap.put("instance", Boolean.valueOf(getId().startsWith("#")));
        hashMap.put("key", getId());
        hashMap.put("params", objArr);
        this.executor.executeEvent("unity.event", hashMap, new JSResponseListener(this) { // from class: com.gen.mh.webapps.unity.Unity.2
            @Override // com.gen.p059mh.webapps.listener.JSResponseListener
            public void onResponse(Object obj) {
                MethodCallback methodCallback2 = methodCallback;
                if (methodCallback2 != null) {
                    methodCallback2.run(obj);
                }
            }
        });
    }
}
