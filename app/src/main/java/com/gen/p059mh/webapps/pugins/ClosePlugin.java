package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;

/* renamed from: com.gen.mh.webapps.pugins.ClosePlugin */
/* loaded from: classes2.dex */
public class ClosePlugin extends Plugin {
    public ClosePlugin() {
        super(MainFragment.CLOSE_EVENT);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i(MainFragment.CLOSE_EVENT);
        getWebViewFragment().close();
        pluginCallback.response(null);
    }
}
