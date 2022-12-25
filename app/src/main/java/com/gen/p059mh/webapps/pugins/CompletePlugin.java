package com.gen.p059mh.webapps.pugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.one.tomato.entity.C2516Ad;

/* renamed from: com.gen.mh.webapps.pugins.CompletePlugin */
/* loaded from: classes2.dex */
public class CompletePlugin extends Plugin {
    public static final int DOCUMENT_READY = 0;
    public static final int GAME_READY = 2;
    public static final int WEBAPP_READY = 1;
    int completeCount = 0;
    boolean entered = false;
    boolean documentReady = false;

    public CompletePlugin() {
        super("module");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("complete", str);
        String str2 = (String) new Gson().fromJson(str, (Class<Object>) String.class);
        if (str2.equals(C2516Ad.TYPE_START) || str2.equals("begin")) {
            this.completeCount++;
        } else if (str2.equals("end")) {
            if (!this.entered) {
                int i = this.completeCount - 1;
                this.completeCount = i;
                if (i <= 0 && this.documentReady) {
                    this.entered = true;
                    getWebViewFragment().loadComplete(0);
                }
            }
        } else if (str2.equals("documentReady")) {
            this.documentReady = true;
            if (!this.entered && this.completeCount <= 0) {
                this.entered = true;
                getWebViewFragment().loadComplete(0);
            }
        } else if (str2.equals("webappReady")) {
            getWebViewFragment().loadComplete(1);
        }
        pluginCallback.response(null);
    }
}
