package com.gen.p059mh.webapp_extensions.plugins;

import android.content.Intent;
import android.net.Uri;
import com.gen.p059mh.webapp_extensions.activities.OpenWebActivity;
import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;

/* renamed from: com.gen.mh.webapp_extensions.plugins.OpenPlugin */
/* loaded from: classes2.dex */
public class OpenPlugin extends Plugin {
    public OpenPlugin() {
        super("open");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        String str2 = (String) new Gson().fromJson(str, (Class<Object>) String.class);
        if (str2.startsWith("http")) {
            Intent intent = new Intent(getWebViewFragment().getContext(), OpenWebActivity.class);
            intent.putExtra("open_url", str2);
            getWebViewFragment().getFragment().startActivity(intent);
        } else if (str2.startsWith("wapp:")) {
            Uri parse = Uri.parse(str2);
            if (parse != null) {
                getWebViewFragment().gotoNewWebApp(parse, -1);
            }
        } else if (str2 != null) {
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(Uri.parse(str2));
            getWebViewFragment().getFragment().startActivity(intent2);
        }
        pluginCallback.response(null);
    }
}
