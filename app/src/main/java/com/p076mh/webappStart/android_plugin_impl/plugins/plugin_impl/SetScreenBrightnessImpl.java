package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ScreenBrightnessParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.GlobalMagicJavaUtil;
import com.p076mh.webappStart.util.MainHandler;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetScreenBrightnessImpl */
/* loaded from: classes3.dex */
public class SetScreenBrightnessImpl extends BasePluginImpl<ScreenBrightnessParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final ScreenBrightnessParamsBean screenBrightnessParamsBean, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetScreenBrightnessImpl.1
            @Override // java.lang.Runnable
            public void run() {
                GlobalMagicJavaUtil.setWindowBrightnessForWx(iWebFragmentController.getActivity(), screenBrightnessParamsBean.getValue().floatValue());
            }
        });
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
