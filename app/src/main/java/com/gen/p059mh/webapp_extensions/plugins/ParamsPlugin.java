package com.gen.p059mh.webapp_extensions.plugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.ParamsPlugin */
/* loaded from: classes2.dex */
public class ParamsPlugin extends Plugin {
    public ParamsPlugin() {
        super("params");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        HashMap hashMap = new HashMap();
        if (map != null) {
            HashMap initParams = getWebViewFragment().getInitParams();
            Logger.m4114e("params", initParams.toString());
            hashMap.put("success", true);
            hashMap.put(AopConstants.APP_PROPERTIES_KEY, initParams);
            pluginCallback.response(hashMap);
            return;
        }
        hashMap.put("success", false);
        pluginCallback.response(hashMap);
    }
}
