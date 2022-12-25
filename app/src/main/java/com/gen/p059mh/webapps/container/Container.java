package com.gen.p059mh.webapps.container;

import android.support.p002v4.app.Fragment;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.unity.UnityPlugin;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.container.Container */
/* loaded from: classes2.dex */
public interface Container {

    /* renamed from: com.gen.mh.webapps.container.Container$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static AppController $default$getAppController(Container container) {
            return null;
        }

        public static void $default$initWorkUnity(Container container) {
        }
    }

    void close();

    void executeEvent(String str, Object obj, JSResponseListener jSResponseListener);

    AppController getAppController();

    NativeViewPlugin getNativeViewPlugin();

    Hashtable<String, Plugin> getPlugins();

    List<Runnable> getRunnable();

    ServerPlugin getServerPlugin();

    WebEngine getWebEngine();

    NativeViewPlugin getWorkerNativeViewPlugin();

    UnityPlugin getWorkerUnityPlugin();

    void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str);

    void initWorkUnity();

    void initializerPlugins();

    void initializerUnitObject();

    void onBackPressed();

    void processConfigs(Map map);

    Fragment provideFragment();

    <T extends NativeViewPlugin.NativeView> void registerNativeView(Class<T> cls, String str);

    void registerObject(Unity unity, String str);

    void registerPlugin(Plugin plugin);

    <T extends Unity> void registerUnity(Class<T> cls, String str);

    void registerWorkerPlugin(Plugin plugin, Hashtable<String, Plugin> hashtable, Plugin.Executor executor);

    void release();

    void releaseRunnableList();

    void setWebFragmentController(IWebFragmentController iWebFragmentController);

    void start(String str, String str2);
}
