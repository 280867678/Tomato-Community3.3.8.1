package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.googlecode.mp4parser.boxes.apple.TrackLoadSettingsAtom;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.CommonJSPlugin */
/* loaded from: classes2.dex */
public class CommonJSPlugin extends Plugin {
    public CommonJSPlugin() {
        super("commonJS");
        new String[]{".json", ".js", ".wxml"};
        new String[]{".css", ".wxss"};
    }

    void traval(File file, HashMap<String, HashMap> hashMap, String str) {
        File[] listFiles;
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                traval(file2, hashMap, str + "/" + file2.getName());
            } else {
                String str2 = str + "/" + file2.getName();
                HashMap hashMap2 = new HashMap();
                hashMap2.put("path", str);
                hashMap2.put("target", str2);
                hashMap2.put("content", str2);
                hashMap.put(str2, hashMap2);
            }
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        String str2;
        Logger.m4113i(str);
        if (str == null || str.equals("") || str.equals("null")) {
            HashMap<String, HashMap> hashMap = new HashMap<>();
            traval(new File(getWebViewFragment().getWorkPath()), hashMap, "");
            pluginCallback.response(hashMap);
            return;
        }
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String valueOf = String.valueOf(map.get(LogConstants.FOLLOW_OPERATION_TYPE));
        char c = 65535;
        if (valueOf.hashCode() == 3327206 && valueOf.equals(TrackLoadSettingsAtom.TYPE)) {
            c = 0;
        }
        if (c == 0 && (str2 = (String) map.get("path")) != null) {
            try {
                byte[] loadData = Utils.loadData(getWebViewFragment().getWorkPath() + str2, Utils.ENCODE_TYPE.WORK, this.webViewFragment.getWACrypto());
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", true);
                hashMap2.put("result", new String(loadData));
                pluginCallback.response(hashMap2);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        pluginCallback.response(hashMap3);
    }
}
