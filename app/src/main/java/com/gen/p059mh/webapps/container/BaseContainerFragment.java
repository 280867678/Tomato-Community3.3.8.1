package com.gen.p059mh.webapps.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.container.Container;
import com.gen.p059mh.webapps.listener.AppController;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.pugins.ClosePlugin;
import com.gen.p059mh.webapps.pugins.CommonJSPlugin;
import com.gen.p059mh.webapps.pugins.CompletePlugin;
import com.gen.p059mh.webapps.pugins.HrefPlugin;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.pugins.RequestPlugin;
import com.gen.p059mh.webapps.pugins.ResponsePlugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.pugins.SettingsPlugin;
import com.gen.p059mh.webapps.pugins.StoragePlugin;
import com.gen.p059mh.webapps.pugins.SystemInfoPlugin;
import com.gen.p059mh.webapps.pugins.WorkPlugin;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.unity.UnityPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.container.BaseContainerFragment */
/* loaded from: classes2.dex */
public abstract class BaseContainerFragment extends Fragment implements Plugin.Executor, Container {
    protected NativeViewPlugin nativeViewPlugin;
    protected Hashtable<String, Plugin> plugins = new Hashtable<>();
    protected ResponsePlugin responsePlugin;
    protected ServerPlugin serverPlugin;
    protected UnityPlugin unityPlugin;
    protected IWebFragmentController webFragmentController;
    protected NativeViewPlugin workerNativeViewPlugin;
    protected UnityPlugin workerUnityPlugin;

    @Override // com.gen.p059mh.webapps.container.Container
    public void close() {
    }

    @Override // com.gen.p059mh.webapps.Plugin.Executor
    public void executeEvent(String str, Object obj, JSResponseListener jSResponseListener) {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public /* synthetic */ AppController getAppController() {
        return Container.CC.$default$getAppController(this);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public List<Runnable> getRunnable() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public WebEngine getWebEngine() {
        return null;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public /* synthetic */ void initWorkUnity() {
        Container.CC.$default$initWorkUnity(this);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void initializerUnitObject() {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void onBackPressed() {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void processConfigs(Map map) {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public Fragment provideFragment() {
        return this;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void releaseRunnableList() {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void start(String str, String str2) {
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void setWebFragmentController(IWebFragmentController iWebFragmentController) {
        this.webFragmentController = iWebFragmentController;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public <T extends NativeViewPlugin.NativeView> void registerNativeView(Class<T> cls, String str) {
        getNativeViewPlugin().registerNativeView(cls, str);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public <T extends Unity> void registerUnity(Class<T> cls, String str) {
        getUnityPlugin().registerUnity(cls, str);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public NativeViewPlugin getNativeViewPlugin() {
        if (this.nativeViewPlugin == null) {
            this.nativeViewPlugin = new NativeViewPlugin();
        }
        return this.nativeViewPlugin;
    }

    public UnityPlugin getUnityPlugin() {
        if (this.unityPlugin == null) {
            this.unityPlugin = new UnityPlugin();
            Logger.m4115e("unityPlugin init");
        }
        return this.unityPlugin;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void registerObject(Unity unity, String str) {
        getUnityPlugin().registerObject(unity, str);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public NativeViewPlugin getWorkerNativeViewPlugin() {
        if (this.workerNativeViewPlugin == null) {
            this.workerNativeViewPlugin = new NativeViewPlugin();
        }
        return this.workerNativeViewPlugin;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public UnityPlugin getWorkerUnityPlugin() {
        if (this.workerUnityPlugin == null) {
            this.workerUnityPlugin = new UnityPlugin();
        }
        return this.workerUnityPlugin;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public ServerPlugin getServerPlugin() {
        if (this.serverPlugin == null) {
            this.serverPlugin = new ServerPlugin();
        }
        return this.serverPlugin;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void registerPlugin(Plugin plugin) {
        plugin.webViewFragment = this.webFragmentController;
        plugin.executor = this;
        this.plugins.put(plugin.getName(), plugin);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public Hashtable<String, Plugin> getPlugins() {
        return this.plugins;
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void initWorkPlugin(Hashtable<String, Plugin> hashtable, Plugin.Executor executor, String str) {
        registerWorkerPlugin(new RequestPlugin(), hashtable, executor);
        registerWorkerPlugin(new HrefPlugin(), hashtable, executor);
        registerWorkerPlugin(getWorkerNativeViewPlugin(), hashtable, executor);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void registerWorkerPlugin(Plugin plugin, Hashtable<String, Plugin> hashtable, Plugin.Executor executor) {
        plugin.webViewFragment = this.webFragmentController;
        plugin.executor = executor;
        hashtable.put(plugin.getName(), plugin);
    }

    @Override // com.gen.p059mh.webapps.container.Container
    public void initializerPlugins() {
        Logger.m4115e("initializerPlugins");
        ResponsePlugin responsePlugin = new ResponsePlugin();
        this.responsePlugin = responsePlugin;
        registerPlugin(responsePlugin);
        registerPlugin(new CompletePlugin());
        registerPlugin(new CommonJSPlugin());
        registerPlugin(new StoragePlugin());
        registerPlugin(new SettingsPlugin());
        registerPlugin(new RequestPlugin());
        registerPlugin(new HrefPlugin());
        registerPlugin(new SystemInfoPlugin());
        registerPlugin(new ClosePlugin());
        registerPlugin(getNativeViewPlugin());
        registerPlugin(getUnityPlugin());
        registerPlugin(new WorkPlugin());
        registerPlugin(getServerPlugin());
    }
}
