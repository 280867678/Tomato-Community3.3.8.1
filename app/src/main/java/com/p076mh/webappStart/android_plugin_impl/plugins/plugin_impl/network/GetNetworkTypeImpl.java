package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.network;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.NetWorkUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.network.GetNetworkTypeImpl */
/* loaded from: classes3.dex */
public class GetNetworkTypeImpl extends BasePluginImpl<String> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put(AopConstants.NETWORK_TYPE, NetWorkUtil.getCurrentNetworkType().toString());
        hashMap.put("success", true);
        hashMap.put("complete", true);
        pluginCallback.response(hashMap);
    }
}
