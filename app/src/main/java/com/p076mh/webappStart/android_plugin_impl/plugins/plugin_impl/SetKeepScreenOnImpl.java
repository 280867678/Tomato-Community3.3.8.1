package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.KeepScreenOnParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.MainHandler;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetKeepScreenOnImpl */
/* loaded from: classes3.dex */
public class SetKeepScreenOnImpl extends BasePluginImpl<KeepScreenOnParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final KeepScreenOnParamsBean keepScreenOnParamsBean, Plugin.PluginCallback pluginCallback) {
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.SetKeepScreenOnImpl.1
            @Override // java.lang.Runnable
            public void run() {
                IWebFragmentController iWebFragmentController2 = iWebFragmentController;
                if (iWebFragmentController2 == null || iWebFragmentController2.getActivity() == null || iWebFragmentController.getActivity().getWindow() == null) {
                    return;
                }
                if (keepScreenOnParamsBean.isKeepScreenOn()) {
                    iWebFragmentController.getActivity().getWindow().addFlags(128);
                } else {
                    iWebFragmentController.getActivity().getWindow().clearFlags(128);
                }
            }
        });
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
