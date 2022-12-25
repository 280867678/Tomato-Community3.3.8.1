package com.gen.p059mh.webapp_extensions.plugins;

import com.gen.p059mh.webapp_extensions.server.ImageServer;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.server.worker.ServerWorker;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.ImageServerPlugin */
/* loaded from: classes2.dex */
public class ImageServerPlugin extends ServerPlugin {
    @Override // com.gen.p059mh.webapps.pugins.ServerPlugin
    protected void registerServer(Map map, Plugin.PluginCallback pluginCallback) {
        if (map.get("src") != null && map.get("path") != null) {
            try {
                String path = new URL((String) map.get("src")).getPath();
                File file = new File(getWebViewFragment().getWorkPath() + path);
                if (this.servers.containsKey(map.get("path"))) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", false);
                    pluginCallback.response(hashMap);
                    return;
                }
                ServerWorker serverWorker = new ServerWorker(getWebViewFragment());
                if (file.exists()) {
                    serverWorker.start(file);
                }
                serverWorker.setProxyPath((String) map.get("path"));
                this.servers.put((String) map.get("path"), serverWorker);
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", true);
                pluginCallback.response(hashMap2);
                return;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (map.get("path") != null && map.get("key") != null) {
            String obj = map.get("key").toString();
            String obj2 = map.get("path").toString();
            if (this.servers.containsKey(obj2)) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", false);
                pluginCallback.response(hashMap3);
                return;
            }
            ImageServer imageServer = new ImageServer(getWebViewFragment());
            imageServer.setProxyPath(obj2);
            imageServer.setServerType(obj);
            this.servers.put((String) map.get("path"), imageServer);
            HashMap hashMap4 = new HashMap();
            hashMap4.put("success", true);
            pluginCallback.response(hashMap4);
            return;
        }
        HashMap hashMap5 = new HashMap();
        hashMap5.put("success", false);
        pluginCallback.response(hashMap5);
    }
}
