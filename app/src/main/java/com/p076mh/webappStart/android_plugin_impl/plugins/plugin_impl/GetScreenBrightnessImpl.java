package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ScreenBrightnessParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.GlobalMagicJavaUtil;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetScreenBrightnessImpl */
/* loaded from: classes3.dex */
public class GetScreenBrightnessImpl extends BasePluginImpl<ScreenBrightnessParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ScreenBrightnessParamsBean screenBrightnessParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", Float.valueOf(GlobalMagicJavaUtil.getWindowBrightnessForWx(iWebFragmentController.getActivity())));
        hashMap.put("success", true);
        pluginCallback.response(hashMap);
    }
}
