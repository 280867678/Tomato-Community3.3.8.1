package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.google.gson.Gson;
import java.io.File;
import java.net.URI;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapps.pugins.HrefPlugin */
/* loaded from: classes2.dex */
public class HrefPlugin extends Plugin {
    public HrefPlugin() {
        super("href");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap;
        String str2 = (String) new Gson().fromJson(str, (Class<Object>) String.class);
        try {
            try {
                URI create = URI.create(str2);
                if ("tmp".equals(create.getScheme())) {
                    if (new File(getWebViewFragment().getTempDir().toString() + "/" + create.getPath()).exists()) {
                        str2 = "http://" + ResourcesLoader.WORK_HOST + "/_tmp_/" + create.getPath();
                    }
                } else if ("app".equals(create.getScheme())) {
                    if (new File(getWebViewFragment().getAppFilesDir().toString() + "/" + create.getPath()).exists()) {
                        str2 = "http://" + ResourcesLoader.WORK_HOST + "/_res_/" + create.getPath();
                    }
                }
                hashMap = new HashMap();
            } catch (Exception e) {
                e.printStackTrace();
                hashMap = new HashMap();
            }
            hashMap.put("code", 0);
            hashMap.put("href", str2);
            pluginCallback.response(hashMap);
        } catch (Throwable th) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("code", 0);
            hashMap2.put("href", str2);
            pluginCallback.response(hashMap2);
            throw th;
        }
    }
}
