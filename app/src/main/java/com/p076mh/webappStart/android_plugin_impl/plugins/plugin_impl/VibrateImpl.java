package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.VibrateParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.VibrateUtil;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.VibrateImpl */
/* loaded from: classes3.dex */
public class VibrateImpl extends BasePluginImpl<VibrateParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, VibrateParamsBean vibrateParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        VibrateUtil.vibrate(WebApplication.getInstance().getApplication(), vibrateParamsBean.getTimeMillis());
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
