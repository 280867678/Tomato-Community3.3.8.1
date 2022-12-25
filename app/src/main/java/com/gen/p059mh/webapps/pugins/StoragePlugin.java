package com.gen.p059mh.webapps.pugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.modules.AppData;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.SerializableUtils;
import com.google.gson.Gson;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* renamed from: com.gen.mh.webapps.pugins.StoragePlugin */
/* loaded from: classes2.dex */
public class StoragePlugin extends Plugin {
    AppData appData;

    public StoragePlugin() {
        super("storage");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void ready() {
        Logger.m4113i("storageOnLoad");
        this.appData = AppData.fromAppID(new File(getWebViewFragment().getWorkPath()).getName());
        StringBuilder sb = new StringBuilder();
        sb.append(new File(getWebViewFragment().getWorkPath()).getName());
        sb.append("");
        sb.append(this.appData != null);
        Logger.m4113i(sb.toString());
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("StoragePlugin", str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
        if ("set".equals(str2)) {
            Map map2 = (Map) map.get("content");
            String str3 = (String) map2.get("key");
            Object obj = map2.get("value");
            if (str3 != null && obj != null) {
                this.appData.setStorageBlob(str3, obj);
                this.appData.save();
            }
        } else if ("clear".equals(str2)) {
            String str4 = (String) map.get("key");
            if (str4 != null) {
                this.appData.clearStorageBlob(str4);
                this.appData.save();
            } else {
                this.appData.clearStorageBlob();
                this.appData.save();
            }
        } else if ("get".equals(str2)) {
            HashMap hashMap = new HashMap();
            Object storageBlob = this.appData.getStorageBlob((String) ((Map) map.get("content")).get("key"));
            if (storageBlob == null) {
                hashMap.put("success", false);
            } else {
                hashMap.put("result", storageBlob);
                hashMap.put("success", true);
            }
            pluginCallback.response(hashMap);
            return;
        } else {
            Set<String> set = null;
            if ("info".equals(str2)) {
                Map<String, Object> storageBlobMap = this.appData.getStorageBlobMap();
                if (storageBlobMap != null) {
                    set = storageBlobMap.keySet();
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put("keys", set);
                try {
                    if (storageBlobMap != null) {
                        hashMap2.put("currentSize", Integer.valueOf(SerializableUtils.serializableMap(storageBlobMap).length / 1024));
                    } else {
                        hashMap2.put("currentSize", 0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hashMap2.put("limitSize", 10240L);
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", true);
                hashMap3.put(AopConstants.APP_PROPERTIES_KEY, hashMap2);
                pluginCallback.response(hashMap3);
                return;
            } else if ("pasteboard.set".equals(str2)) {
                HashMap hashMap4 = new HashMap();
                if (map.containsKey("content")) {
                    String str5 = (String) map.get("content");
                    if (getWebViewFragment().getContext() != null) {
                        ((ClipboardManager) getWebViewFragment().getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, str5));
                        hashMap4.put("success", true);
                    } else {
                        hashMap4.put("success", false);
                    }
                } else {
                    hashMap4.put("success", false);
                }
                pluginCallback.response(hashMap4);
                return;
            } else if ("pasteboard.get".equals(str2)) {
                HashMap hashMap5 = new HashMap();
                if (getWebViewFragment().getContext() != null) {
                    ClipboardManager clipboardManager = (ClipboardManager) getWebViewFragment().getContext().getSystemService("clipboard");
                    boolean hasPrimaryClip = clipboardManager.hasPrimaryClip();
                    if (hasPrimaryClip) {
                        hashMap5.put("result", (String) clipboardManager.getPrimaryClip().getItemAt(0).getText());
                    }
                    hashMap5.put("success", Boolean.valueOf(hasPrimaryClip));
                } else {
                    hashMap5.put("success", false);
                }
                pluginCallback.response(hashMap5);
                return;
            }
        }
        HashMap hashMap6 = new HashMap();
        hashMap6.put("success", true);
        pluginCallback.response(hashMap6);
    }
}
