package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.GetAvailableAudioSourcesImpl */
/* loaded from: classes3.dex */
public class GetAvailableAudioSourcesImpl extends BasePluginImpl<String> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) throws Exception {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        arrayList.add("auto");
        arrayList.add("mic");
        arrayList.add("camcorder");
        arrayList.add("voice_recognition");
        arrayList.add("voice_communication");
        arrayList.add("unprocessed");
        hashMap.put("audioSources", arrayList);
        responseSuccess(pluginCallback, hashMap);
    }
}
