package com.gen.p059mh.webapp_extensions.plugins;

import android.util.Log;
import com.gen.p059mh.webapps.Plugin;

/* renamed from: com.gen.mh.webapp_extensions.plugins.StupidPlugin */
/* loaded from: classes2.dex */
public class StupidPlugin extends Plugin {
    public StupidPlugin() {
        super("stupid");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Log.e("stupid", "stupid");
    }
}
