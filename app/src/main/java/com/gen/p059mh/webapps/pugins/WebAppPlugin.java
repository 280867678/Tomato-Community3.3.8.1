package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.WebAppCallBack;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.WebAppPlugin */
/* loaded from: classes2.dex */
public class WebAppPlugin extends Plugin {
    public WebAppPlugin(WebAppCallBack webAppCallBack) {
        super("web.app");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4114e("process", str);
        try {
            Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
            if (map == null || map.get(LogConstants.FOLLOW_OPERATION_TYPE) == null) {
                return;
            }
            WebAppPlugin.class.getDeclaredMethod(map.get(LogConstants.FOLLOW_OPERATION_TYPE).toString(), Map.class, Plugin.PluginCallback.class).invoke(this, map, pluginCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
