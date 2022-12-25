package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Utils;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.SystemInfoPlugin */
/* loaded from: classes2.dex */
public class SystemInfoPlugin extends Plugin {
    Map settingMap;

    public SystemInfoPlugin() {
        super("systeminfo");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        if (this.settingMap == null) {
            this.settingMap = Utils.launchSettings(getWebViewFragment().getActivity(), null);
        }
        pluginCallback.response(this.settingMap);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        if (this.settingMap != null) {
            this.settingMap = null;
        }
    }
}
