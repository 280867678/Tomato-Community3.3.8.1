package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.os.BatteryManager;
import android.os.Build;
import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;

@Deprecated
/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.GetBatteryImpl */
/* loaded from: classes3.dex */
public class GetBatteryImpl extends BasePluginImpl<String> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, String str, Plugin.PluginCallback pluginCallback) {
        BatteryManager batteryManager = (BatteryManager) WebApplication.getInstance().getApplication().getSystemService("batterymanager");
        if (Build.VERSION.SDK_INT >= 23) {
            String str2 = this.TAG;
            Log.e(str2, "process: batteryManager" + batteryManager.isCharging());
            String str3 = this.TAG;
            Log.e(str3, "process: batteryManager" + NotificationCompat.CATEGORY_STATUS);
            String str4 = this.TAG;
            Log.e(str4, "process: batteryManager2");
            String str5 = this.TAG;
            Log.e(str5, "process: batteryManager5");
        }
        int intProperty = batteryManager.getIntProperty(4);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 23) {
            z = batteryManager.isCharging();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("complete", true);
        hashMap.put("level", Integer.valueOf(intProperty));
        hashMap.put("isCharging", Boolean.valueOf(z));
        pluginCallback.response(hashMap);
    }
}
