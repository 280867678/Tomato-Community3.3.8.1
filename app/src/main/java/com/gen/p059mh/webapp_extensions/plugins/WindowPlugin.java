package com.gen.p059mh.webapp_extensions.plugins;

import android.annotation.SuppressLint;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.WindowPlugin */
/* loaded from: classes2.dex */
public class WindowPlugin extends Plugin {
    public WindowPlugin() {
        super("window.feature");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i("feature input:" + str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        if (map.get("fullscreen") != null) {
            changeFullScreen(((Boolean) map.get("fullscreen")).booleanValue());
        }
        if (map.get("orientation") != null) {
            changeOrientation((String) map.get("orientation"));
        }
        if (map.get("statusBarStyle") != null) {
            changeStatusBarStyle((String) map.get("statusBarStyle"));
        }
        HashMap hashMap = new HashMap();
        map.put("success", true);
        pluginCallback.response(hashMap);
    }

    private void changeFullScreen(final boolean z) {
        if (getWebViewFragment().getWindowConfig() != null) {
            getWebViewFragment().getWindowConfig().put("statusBarHidden", Boolean.valueOf(z));
        }
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.WindowPlugin.1
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    WindowPlugin.this.getWebViewFragment().getActivity().getWindow().addFlags(1024);
                } else {
                    WindowPlugin.this.getWebViewFragment().getActivity().getWindow().clearFlags(1024);
                }
            }
        });
    }

    private void changeOrientation(String str) {
        if (getWebViewFragment().getWindowConfig() != null) {
            getWebViewFragment().getWindowConfig().put("pageOrientation", str);
        }
        if ("portrait".equals(str)) {
            getWebViewFragment().setRequestedOrientation(1);
        } else if ("auto".equals(str)) {
            getWebViewFragment().setRequestedOrientation(4);
        } else if (!"landscape".equals(str)) {
        } else {
            getWebViewFragment().setRequestedOrientation(0);
        }
    }

    @SuppressLint({"ResourceType"})
    private void changeStatusBarStyle(final String str) {
        if (getWebViewFragment().getWindowConfig() != null) {
            getWebViewFragment().getWindowConfig().put("navigationBarTextStyle", str);
        }
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.WindowPlugin.2
            @Override // java.lang.Runnable
            public void run() {
                if ("white".equals(str)) {
                    ImmersionBar with = ImmersionBar.with(WindowPlugin.this.getWebViewFragment().getActivity());
                    with.statusBarDarkFont(false);
                    with.init();
                    return;
                }
                ImmersionBar with2 = ImmersionBar.with(WindowPlugin.this.getWebViewFragment().getActivity());
                with2.statusBarDarkFont(true, 0.2f);
                with2.init();
            }
        });
    }
}
