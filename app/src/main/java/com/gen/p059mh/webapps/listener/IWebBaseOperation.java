package com.gen.p059mh.webapps.listener;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.unity.Unity;
import java.util.Hashtable;

/* renamed from: com.gen.mh.webapps.listener.IWebBaseOperation */
/* loaded from: classes2.dex */
public interface IWebBaseOperation {
    NativeViewPlugin getWorkerNativeViewPlugin();

    <T extends NativeViewPlugin.NativeView> void registerNativeView(Class<T> cls, String str);

    void registerObject(Unity unity, String str);

    <T extends Unity> void registerUnity(Class<T> cls, String str);

    void registerWorkerPlugin(Plugin plugin, Hashtable<String, Plugin> hashtable, Plugin.Executor executor);
}
