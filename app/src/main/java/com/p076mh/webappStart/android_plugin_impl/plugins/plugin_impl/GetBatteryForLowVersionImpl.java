package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetBatteryForLowVersionImpl */
/* loaded from: classes3.dex */
public class GetBatteryForLowVersionImpl extends BasePluginImpl<String> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) {
        Intent registerReceiver = iWebFragmentController.getContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        boolean z = true;
        if (registerReceiver.getIntExtra("plugged", -1) == 0) {
            z = false;
        }
        int intExtra = registerReceiver.getIntExtra("level", -1);
        String str2 = this.TAG;
        Log.e(str2, "isCharging = " + z);
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        hashMap.put("level", Integer.valueOf(intExtra));
        hashMap.put("isCharging", Boolean.valueOf(z));
        pluginCallback.response(hashMap);
    }
}
