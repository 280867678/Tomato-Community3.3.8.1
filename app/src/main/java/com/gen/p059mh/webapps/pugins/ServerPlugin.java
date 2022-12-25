package com.gen.p059mh.webapps.pugins;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IServerWorker;
import com.gen.p059mh.webapps.server.worker.ServerWorker;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import fi.iki.elonen.NanoHTTPD;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.ServerPlugin */
/* loaded from: classes2.dex */
public class ServerPlugin extends Plugin {
    protected Map<String, IServerWorker> servers = new HashMap();

    public ServerPlugin() {
        super("server");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i(str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        if (map != null && map.get(LogConstants.FOLLOW_OPERATION_TYPE) != null) {
            String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
            char c = 65535;
            if (str2.hashCode() == -690213213 && str2.equals("register")) {
                c = 0;
            }
            if (c == 0) {
                registerServer(map, pluginCallback);
                return;
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "can not find the action");
        pluginCallback.response(hashMap);
    }

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
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        pluginCallback.response(hashMap3);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        for (String str : this.servers.keySet()) {
            this.servers.get(str).destroyWorker();
        }
    }

    public NanoHTTPD.Response handleServer(String str, NanoHTTPD.IHTTPSession iHTTPSession, String str2) {
        String uri = iHTTPSession.getUri();
        for (String str3 : this.servers.keySet()) {
            IServerWorker iServerWorker = this.servers.get(str3);
            HashMap hashMap = new HashMap();
            hashMap.put("mimeType", str2);
            if (iServerWorker.checkHandle(uri, hashMap)) {
                return iServerWorker.startProxy(str, iHTTPSession, hashMap);
            }
        }
        return null;
    }
}
