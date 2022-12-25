package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.SetInnerAudioOptionParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.WxAPIConfig;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.SetInnerAudioOptionImpl */
/* loaded from: classes3.dex */
public class SetInnerAudioOptionImpl extends BasePluginImpl<SetInnerAudioOptionParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, SetInnerAudioOptionParamsBean setInnerAudioOptionParamsBean, Plugin.PluginCallback pluginCallback) throws Exception {
        Logger.m4115e("action SetInnerAudioOptionImpl");
        WxAPIConfig.InnerAudioOption.mixWithOther = setInnerAudioOptionParamsBean.isMixWithOther();
        responseSuccess(pluginCallback);
    }
}
