package com.p076mh.webappStart.android_plugin_impl.plugins.plugin;

import com.gen.p059mh.webapps.Plugin;
import com.p076mh.webappStart.android_plugin_impl.beans.SetInnerAudioOptionParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.GetAvailableAudioSourcesImpl;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.SetInnerAudioOptionImpl;
import com.p076mh.webappStart.util.GsonUtil;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.DMMediaPlugin */
/* loaded from: classes3.dex */
public class DMMediaPlugin extends BasePlugin {
    public DMMediaPlugin() {
        super("media.tools");
    }

    private void setAudioOption(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(SetInnerAudioOptionImpl.class).action(getWebViewFragment(), GsonUtil.getInstance().fromJson(str, (Class<Object>) SetInnerAudioOptionParamsBean.class), pluginCallback);
    }

    private void getAudioSources(String str, Plugin.PluginCallback pluginCallback) throws Exception {
        getBasePluginImpl(GetAvailableAudioSourcesImpl.class).action(getWebViewFragment(), str, pluginCallback);
    }
}
