package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.ResponsePlugin */
/* loaded from: classes2.dex */
public class ResponsePlugin extends Plugin {
    private HashMap<String, JSResponseListener> responseListeners = new HashMap<>();

    public HashMap<String, JSResponseListener> getResponseListeners() {
        return this.responseListeners;
    }

    public ResponsePlugin() {
        super("response");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID);
        if (this.responseListeners.containsKey(str2)) {
            JSResponseListener jSResponseListener = this.responseListeners.get(str2);
            if (map.containsKey(OrmLiteConfigUtil.RESOURCE_DIR_NAME)) {
                jSResponseListener.onResponse(map.get(OrmLiteConfigUtil.RESOURCE_DIR_NAME));
            } else {
                jSResponseListener.onResponse(null);
            }
        }
        pluginCallback.response(null);
    }
}
