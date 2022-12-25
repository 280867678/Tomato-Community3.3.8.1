package com.one.tomato.thirdpart.webapp;

import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;
import com.one.tomato.p085ui.recharge.RechargeExGameActivity;
import com.one.tomato.utils.LogUtil;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class AppToolsPlugin extends Plugin {
    /* JADX INFO: Access modifiers changed from: protected */
    public AppToolsPlugin() {
        super("appTools");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        LogUtil.m3784i("小程序js传递过来的数据 ： " + str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        HashMap hashMap = new HashMap();
        if (map != null && map.get("page") != null) {
            if (map.get("page").equals("exchange")) {
                RechargeExGameActivity.Companion.startActivity(getWebViewFragment().getActivity());
            } else if (map.get("page").equals("paylive")) {
                RechargeExGameActivity.Companion.startActivity(getWebViewFragment().getActivity());
            }
            hashMap.put("success", true);
            pluginCallback.response(hashMap);
            return;
        }
        hashMap.put("success", false);
        pluginCallback.response(hashMap);
    }
}
