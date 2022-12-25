package com.gen.p059mh.webapp_extensions.plugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.util.GsonUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.SystemMenuPlugin */
/* loaded from: classes2.dex */
public class SystemMenuPlugin extends Plugin {
    public SystemMenuPlugin() {
        super("system.menu");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i(str);
        Map map = (Map) GsonUtil.getInstance().fromJson(str, (Class<Object>) Map.class);
        HashMap hashMap = new HashMap();
        if (map != null && map.get(LogConstants.FOLLOW_OPERATION_TYPE) != null) {
            if (map.get("items") != null) {
                getWebViewFragment().setScriptMenuIcons((List) map.get("items"));
            }
            hashMap.put("success", true);
            pluginCallback.response(hashMap);
            return;
        }
        hashMap.put("success", false);
        pluginCallback.response(hashMap);
    }
}
