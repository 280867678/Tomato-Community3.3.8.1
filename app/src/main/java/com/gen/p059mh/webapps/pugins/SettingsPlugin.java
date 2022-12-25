package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapps.pugins.SettingsPlugin */
/* loaded from: classes2.dex */
public class SettingsPlugin extends Plugin {
    public SettingsPlugin() {
        super("getSetting");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("scope.userInfo", true);
        hashMap.put("scope.invoice", false);
        hashMap.put("scope.invoiceTitle", false);
        hashMap.put("scope.werun", false);
        hashMap.put("scope.userLocation", true);
        hashMap.put("scope.address", true);
        hashMap.put("scope.record", true);
        hashMap.put("scope.writePhotosAlbum", true);
        hashMap.put("scope.camera", true);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("authSetting", hashMap);
        pluginCallback.response(hashMap2);
    }
}
